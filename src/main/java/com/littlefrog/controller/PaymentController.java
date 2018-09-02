package com.littlefrog.controller;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;


import com.littlefrog.common.Response;
import com.littlefrog.common.pay.PayUtil;
import com.littlefrog.common.pay.PaymentDto;
import com.littlefrog.common.pay.UUIDHexGenerator;
import com.littlefrog.common.pay.XmlUtil;
import com.littlefrog.entity.Order;
import com.littlefrog.entity.User;
import com.littlefrog.service.OrderService;
import com.littlefrog.service.UserService;
import org.dom4j.Document;

import org.dom4j.DocumentException;

import org.dom4j.Element;

import org.dom4j.io.SAXReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;

import java.io.ByteArrayInputStream;

import java.io.InputStream;

import java.io.UnsupportedEncodingException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

import static com.littlefrog.common.ResultGenerator.genFailResult;
import static com.littlefrog.common.ResultGenerator.genSuccessResult;


@RestController
@CrossOrigin
public class PaymentController{
    @Value("spbill_create_ip")
    private String spbill_create_ip;//终端IP
    private final String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";//统一下单API接口链接
    //商户平台设置的密钥
    @Value("${key}")
    private  String key; // 商户支付密钥
    @Value("${appid}")
    private String appid;
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


    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @PostMapping("api/user/recharge")
    public Response recharge(@RequestHeader String appid, @RequestParam int amount, @RequestParam Integer id) throws ParseException, UnsupportedEncodingException, DocumentException {
        if (!appid.equals(this.appid)) {
            return genFailResult("错误的appid");
        }
        User user = userService.getUserInfo(id);
        if (user == null) {
            return genFailResult("该用户不存在!");
        } else {
            Order order = orderService.addOrder(new Order(user.getUserID()));
            JSONObject response = rechargeservice(user.getOpenID(),order.getOrderID(),amount);
            if (response.getString("return_code") == "FAIL") {
                return genFailResult(response.getString("return_msg"));
            } else {
               return genSuccessResult(response);
            }
        }
    }

    public JSONObject rechargeservice(String openId,int orderId,int total_fee) throws UnsupportedEncodingException, DocumentException {

        JSONObject JsonObject = new JSONObject() ;

        String nonce_str = userService.RandomString(new Random(),32);//随机字符串

        String today = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        String code = PayUtil.createCode(8);

        String out_trade_no = Integer.toString(orderId);//商户订单号



        String openid = openId;//用户标识

        PaymentDto paymentPo = new PaymentDto();

        paymentPo.setAppid(appid);

        paymentPo.setMch_id(mch_id);

        paymentPo.setNonce_str(nonce_str);

        String newbody = new String(body.getBytes("ISO-8859-1"),"UTF-8");//以utf-8编码放入paymentPo，微信支付要求字符编码统一采用UTF-8字符编码

        paymentPo.setBody(newbody);

        paymentPo.setOut_trade_no(out_trade_no);

        paymentPo.setTotal_fee(Integer.toString(total_fee));

        paymentPo.setSpbill_create_ip(spbill_create_ip);

        paymentPo.setNotify_url(notify_url);

        paymentPo.setTrade_type(trade_type);

        paymentPo.setOpenid(openid);

        // 把请求参数打包成数组

        Map<String, Object> sParaTemp = new HashMap();

        sParaTemp.put("appid", paymentPo.getAppid());

        sParaTemp.put("mch_id", paymentPo.getMch_id());

        sParaTemp.put("nonce_str", paymentPo.getNonce_str());

        sParaTemp.put("body",  paymentPo.getBody());

        sParaTemp.put("out_trade_no", paymentPo.getOut_trade_no());

        sParaTemp.put("total_fee",paymentPo.getTotal_fee());

        sParaTemp.put("spbill_create_ip", paymentPo.getSpbill_create_ip());

        sParaTemp.put("notify_url",paymentPo.getNotify_url());

        sParaTemp.put("trade_type", paymentPo.getTrade_type());

        sParaTemp.put("openid", paymentPo.getOpenid());
        // 除去数组中的空值和签名参数

        String mysign = userService.Md5Sign(sParaTemp); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

        paymentPo.setSign(mysign);
        //打包要发送的xml
        String respXml = XmlUtil.messageToXML(paymentPo);
        // 打印respXml发现，得到的xml中有“__”不对，应该替换成“_”
        respXml = respXml.replace("__", "_");

        String param = respXml;

        //String result = SendRequestForUrl.sendRequest(url, param);//发起请求

        String result = PayUtil.httpRequest(url, "POST", param);

        System.out.println("请求微信预支付接口，返回 result："+result);

        // 将解析结果存储在Map中

        Map map = new HashMap();

        InputStream in=new ByteArrayInputStream(result.getBytes());

        // 读取输入流

        SAXReader reader = new SAXReader();

        Document document = reader.read(in);

        // 得到xml根元素

        Element root = document.getRootElement();

        // 得到根元素的所有子节点

        List<Element> elementList = root.elements();

        for (Element element : elementList) {

            map.put(element.getName(), element.getText());

        }

        // 返回信息

        String return_code = map.get("return_code").toString();//返回状态码

        String return_msg = map.get("return_msg").toString();//返回信息

        String result_code="fail";
        if("SUCCESS".equals(return_code)){
            result_code = map.get("result_code").toString();//返回状态码
        }



        System.out.println("请求微信预支付接口，返回 code：" + return_code);

        System.out.println("请求微信预支付接口，返回 msg：" + return_msg);

        if("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)){

            // 业务结果

            String prepay_id = map.get("prepay_id").toString();//返回的预付单信息

            String nonceStr = UUIDHexGenerator.generate();

            JsonObject.put("nonceStr", nonceStr);

            JsonObject.put("package", "prepay_id=" + prepay_id);

            Long timeStamp = System.currentTimeMillis() / 1000;

            JsonObject.put("timeStamp", timeStamp + "");

            String stringSignTemp = "appId=" + appid + "&nonceStr=" + nonceStr + "&package=prepay_id=" + prepay_id + "&signType=MD5&timeStamp=" + timeStamp;

            //再次签名

            String paySign = PayUtil.sign(stringSignTemp, key, "utf-8").toUpperCase();

            JsonObject.put("paySign", paySign);

        }else{
            JsonObject.put("error_code",return_code);
            JsonObject.put("error_msg",return_msg);
        }

        return JsonObject;

    }





    /**

     * 预支付时填写的 notify_url ，支付成功后的回调接口

     * @param request

     */
    @RequestMapping("/user/recharge/getresult")
    @ResponseBody
    public void paycallback(HttpServletRequest request) {

        try {
            Map<String, Object> dataMap = XmlUtil.parseXML(request);
            if("SUCCESS".equals(dataMap.get("return_code")) && "SUCCESS".equals(dataMap.get("result_code"))){
                int orderId = Integer.parseInt(dataMap.get("out_trade_no").toString());
                Optional<Order> optional = orderService.getById(orderId);
                if(optional.isPresent()){
                    orderService.setIsRecharge(orderId,true);
                }
            }
            System.out.println(JSON.toJSONString(dataMap));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
