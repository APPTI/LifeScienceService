package com.littlefrog.service;

import com.alibaba.fastjson.JSONObject;
import com.littlefrog.entity.User;
import com.littlefrog.respository.UserRepository;
import com.littlefrog.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;


import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.security.MessageDigest;


@Service("UserService")
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //secretId
    @Value("${secretId}")
    private  String secretId;
    //appid
    @Value("${appid}")
    private  String appid ;
    //商户号
    @Value("${mch_id}")
    private  String mch_id;
    //商品描述
    @Value("${body}")
    private  String body;
    //通知地址
    @Value("${notify_url}")
    private  String notify_url;
    //交易类型
    @Value("${JSAPI}")
    private  String trade_type;

    public static final String SOURCES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private Store store = new Store();
    //商户平台设置的密钥
    @Value("${key}")
    private String key;
    private String spbill_create_ip;

    public User addUser(String sessionId,String openID){
        User user=userRepository.save(new User(sessionId,openID));
        store.addUser(user);
        return user;
    }

    public User addUser(String openID,String sessionId,String unionId){
        User user=userRepository.save(new User(sessionId,openID,unionId));
        store.addUser(user);
        return user;
    }

    public List<User> findByIds(List<Integer> useridList){
        List<User> userList = userRepository.findAllById(useridList);
        return userList;
    }
    public List<User> getAllUser(){
        List<User> u= userRepository.findall();
        return u;
    }

    public User getUserInfo(int id){
        User user = store.getById(id);
        if(user == null) {
            user = userRepository.FindById(id);
            if (user == null){
                return null;
            } else {
                store.addUser(user);
            }
        }
        return user;
    }

    public String RandomString(Random random, int length){
        String characters=this.SOURCES;
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }
    public String Md5Sign(Map params){
        String stringTemp="";
        String newstr="";
        Queue<String> paramsSet=new PriorityQueue<String>();
        for(Object key : params.keySet()){
            paramsSet.add(key+"="+params.get(key));
        }
        for(String string : paramsSet){
            if(stringTemp!="")
                stringTemp+="&";
            stringTemp+=string;
        }
        stringTemp+="&key="+this.key;
        try {
            MessageDigest md5=MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
             newstr=base64en.encode(md5.digest(stringTemp.getBytes("utf-8")));
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return newstr;
    }
    public JSONObject recharge(String openId,int orderId, int total_fee, HttpServletRequest request) {
        String requestUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        Map params = new HashMap();
        params.put("appid",this.appid);
        //我们请求的字符串
        params.put("mch_id",this.mch_id);
        //数字签名，###填你的数字签名，可以在你的个人中心看到
        params.put("body",this.body);
        //随机字符串
        params.put("nonce_str",this.RandomString(new Random(),16));
        //验证类型
        params.put("sign_type","MD5");
        //商品描述
        params.put("body",this.body);
        //商户订单号
        params.put("out_trade_no",orderId);
        //价格
        params.put("total_fee",total_fee);
        //交易类型
        params.put("trade_type",this.trade_type);
        params.put("openid",openId);
        //终端ip
        params.put("spbill_create_ip",request.getRemoteAddr());
        //通知地址
        params.put("notify_url",this.notify_url);
        //签名
        params.put("sign",this.Md5Sign(params));
        //调用httpRequest方法，这个方法主要用于请求地址，并加上请求参数
        String string = httpRequest(requestUrl,params);
        //处理返回的JSON数据并返回
        JSONObject pageBean = JSONObject.parseObject(string);
        return pageBean;
    }

    public Object login(String code) {
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        Map params = new HashMap();
        params.put("appid",this.appid);
        //我们请求的字符串
        params.put("secret",this.secretId);
        //数字签名，###填你的数字签名，可以在你的个人中心看到
        params.put("js_code",code);
        //验证类型
        params.put("grant_type","authorization_code");
        //调用httpRequest方法，这个方法主要用于请求地址，并加上请求参数
        String string = httpRequest(requestUrl,params);
        //处理返回的JSON数据并返回
        JSONObject pageBean = JSONObject.parseObject(string);

        if(pageBean.containsKey("errcode")){
            return pageBean;
        }
        User user = new User();
        String openID = pageBean.getString("openid");
        String sessionId = pageBean.getString("session_key");
        user.setOpenid(openID);
        user.setSessionId(sessionId);
        if(pageBean.containsKey("unionId")){
            String unionId = pageBean.getString("unionid");
            user.setUnionId(unionId);
        }
        User tempuser = userRepository.FindByopenID(openID);
        if(tempuser == null){
            user = userRepository.save(user);
            store.addUser(user);
            return user;
        }else{
                userRepository.UpdateSessionId(tempuser.getId(),sessionId);
                if(user.getUnionId()!=null){
                    userRepository.UpdateUnionId(tempuser.getId(),user.getUnionId());
                }
                userRepository.SetLoginTime(tempuser.getId(),new Date());
                user = userRepository.FindById(tempuser.getId());
                store.updateUserInfo(user.getId(),user);
                return user;
        }
    }

    private static String httpRequest(String requestUrl,Map params) {
        //buffer用于接受返回的字符
        StringBuffer buffer = new StringBuffer();
        try {
            //建立URL，把请求地址给补全，其中urlencode（）方法用于把params里的参数给取出来
            URL url = new URL(requestUrl+"?"+urlencode(params));
            //打开http连接
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();
            //获得输入
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            //将bufferReader的值给放到buffer里
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            //关闭bufferReader和输入流
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            //断开连接
            httpUrlConn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回字符串
        return buffer.toString();
    }



    public static String urlencode(Map<String,Object>data) {
        //将map里的参数变成像 showapi_appid=###&showapi_sign=###&的样子
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public User setUserInfo(int id, int gender, String name, String phoneNum){
        userRepository.UpdateInfo(id,gender,name,phoneNum);
        User user = userRepository.FindById(id);
        store.updateUserInfo(user.getId(),user);
        return user;
    }

    public User AddRecharge(int id, double money, double balance){//  充钱
        userRepository.SetBalance(id,balance+money);
        User user = userRepository.FindById(id);
        store.updateUserInfo(user.getId(),user);
        return user;
    }

    public User payMoney(int id,double money){//付款
        userRepository.SetBalance(id,money);
        User user = userRepository.FindById(id);
        store.updateUserInfo(user.getId(),user);
        return user;
    }
}
