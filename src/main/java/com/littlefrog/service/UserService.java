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
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.security.MessageDigest;

import static com.littlefrog.common.pay.MD5Util.createMD5Sign;


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
    @Value("${trade_type}")
    private  String trade_type;

    public static final String SOURCES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private Store store = new Store();
    //商户平台设置的密钥
    @Value("${key}")
    private String key;

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
    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            String md5=new BigInteger(1, md.digest()).toString(16);
            //BigInteger会把0省略掉，需补全至32位
            return fillMD5(md5);
        } catch (Exception e) {
            throw new RuntimeException("MD5加密错误:"+e.getMessage(),e);
        }
    }

    public static String fillMD5(String md5){
        return md5.length()==32?md5:fillMD5("0"+md5);
    }
    public String Md5Sign(Map params){
        String stringTemp="";
        String newstr="";
        stringTemp ="appid="+params.get("appid")+"&body="+params.get("body")+"&mch_id="+params.get("mch_id")+"&nonce_str="+params.get("nonce_str")+"&notify_url="+params.get("notify_url")+"&out_trade_no="+params.get("out_trade_no")+"&sign_type=MD5"+"&spbill_create_ip="+params.get("spbill_create_ip")+"&total_fee="+params.get("total_fee")+"&trade_type="+params.get("trade_type");
        stringTemp+="&key="+this.key;
        System.out.println(stringTemp);
        newstr=createMD5Sign(stringTemp);
        System.out.println(newstr.toUpperCase());
        return newstr.toUpperCase();
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
        String string = httpRequest(requestUrl,params,"GET");
        //处理返回的JSON数据并返回
        JSONObject pageBean = JSONObject.parseObject(string);

        if(pageBean.containsKey("errcode")){
            return pageBean;
        }
        User user = new User();
        String openID = pageBean.getString("openid");
        String sessionId = pageBean.getString("session_key");
        user.setOpenID(openID);
        user.setSessionID(sessionId);
        user.setName("小程序用户");
        if(pageBean.containsKey("unionId")){
            String unionId = pageBean.getString("unionid");
            user.setUnionID(unionId);
        }
        User tempuser = userRepository.FindByopenID(openID);
        if(tempuser == null){
            user = userRepository.save(user);
            store.addUser(user);
            return user;
        }else{
                userRepository.UpdateSessionId(tempuser.getUserID(),sessionId);
                if(user.getUnionID()!=null){
                    userRepository.UpdateUnionId(tempuser.getUserID(),user.getUnionID());
                }
                userRepository.SetLoginTime(tempuser.getUserID(),new Date());
                user = userRepository.FindById(tempuser.getUserID());
                store.updateUserInfo(user.getUserID(),user);
                return user;
        }
    }


    private static String httpRequest(String requestUrl,Map params ,String method) {
        //buffer用于接受返回的字符
        StringBuffer buffer = new StringBuffer();
        try {
            //建立URL，把请求地址给补全，其中urlencode（）方法用于把params里的参数给取出来
            URL url = new URL(requestUrl+"?"+urlencode(params));
            //打开http连接
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod(method);
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
        store.updateUserInfo(user.getUserID(),user);
        return user;
    }

    public User AddRecharge(int id, double money, double balance){//  充钱
        userRepository.SetBalance(id,balance+money);
        User user = userRepository.FindById(id);
        store.updateUserInfo(user.getUserID(),user);
        return user;
    }

    public User payMoney(int id,double money){//付款
        userRepository.SetBalance(id,money);
        User user = userRepository.FindById(id);
        store.updateUserInfo(user.getUserID(),user);
        return user;
    }
}
