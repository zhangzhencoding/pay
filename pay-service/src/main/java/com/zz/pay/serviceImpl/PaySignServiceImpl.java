/*
 * 文件名：PaySignServiceImpl.java
 * 版权：Copyright by www.001bank.com
 * 描述：
 * 修改人：zhangguopeng
 * 修改时间：2018年1月3日z
 */

package com.zz.pay.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.zz.pay.model.PayOrderInfo;
import com.zz.pay.model.PayOrderRefundInfo;
import com.zz.pay.model.PayResponse;
import com.zz.pay.util.HttpUtils;
import com.zz.pay.vo.OrderInfoVo;
import com.zz.pay.dao.PayOrderInfoMapper;
import com.zz.pay.dao.PayOrderRefundInfoMapper;
import com.zz.pay.dao.PayResponseMapper;
import com.zz.pay.enums.PayEnum;
import com.zz.pay.service.PaySignService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 〈功能详细描述〉 支付宝支付实现基础类
 * 
 * @author zz
 * @version 2018年1月3日
 * @see PaySignServiceImpl
 * @since
 */
@Service
public class PaySignServiceImpl implements PaySignService {

	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(PaySignServiceImpl.class);

	/** 支付宝支付业务：入参app_id */
	public static final String APPID = "2017103109646684";

	/** 支付宝网关 */
	public static final String GATE = "https://openapi.alipay.com/gateway.do";

	/** 支付宝私钥 */
	public static final String APP_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDJSe/SnadGXJ+SZQMmxM/dzGlGxS1/p+aIua8nGpXEaYA+XoBavWL9oT7+cFqfL/k6x1JVcUfRj7sDvVCzHMZQCV4a86S+YR4NoqkE6j62XlGvotuV15RkXBL/iGL3S94iKMRf05+HUZ/6A9Kq6TSLxqwHkdEr8+wZUI04mcpNu1UnIDrT7eZrG3r6igSH8kl8jiuPmpG+qc4R8NH0Ash+40MUTKLt3hLe/fR0/s7+uAVtVW0TtHfNY1hcDhdUtH4ZtnKdeojNVhGx5SwiP5OvA27N7GSl6gqNjeF5nteQXAMEMJNtMvnsK99s3pNZr5epAqOYnWhpJyVHyCoIMXnhAgMBAAECggEBAJLDPgEqwdQjpJVw4SNbTNRDPl9DVTFGkmgOOw8f6yMSaVcQQDXo8ExNTUjnUqvOpsGcowN5XQwG6CyJ1FVEHbQtoFe2XeARn4ABoYrb4G3Hx3dwHvrZ/HBQUenO35m/z27ImqXj/uUDNslsECZdctqXOMcsIn8RkovCl7U1LLQPH2ddEgnGz8zLbrDScPFwm1iAB8TAs1KDjyXk2SaZa8Z0g6uBa4oDzFps+smJRjaZV5oiQZbl0erPYu4loiWBoJq0JUXOEEDk9RXCRGIrZgLlmxwOpwiMh493KZJLvieiOfa5g3GmC0ZEzkmVbA+uHMhTqRyo0FDLdfgWys6BDgECgYEA7SdlaAuOg5BNLleuOrCjOmqvhnWr2M1yHQt8Q5mtFqkQgU+YTE06RRyJa7Z6POXx/gR6TL1PqMoFy/gY6tMfGmB87FmrOaDo8RNAIe9ZlZoJ12QX1RxVcHtjOUixTFnzTSAqE0AABJYrYiGpedMZIeyPxK56BJ4lq2g5tPUG6rkCgYEA2Ujo7sayT5dp0WRn+Pc7GoHi7e/XiPcYZSfzNgszx7G75sGMZf7kOvmmsdq9k0ExRexL07LNVrP7im6gcGD9dm4RBbf777se4HHGM57G6xqy4ke0ZkSZs5gGF5477nUGVt2013bo0+zd9o1R3v7BFMvVOcZtCehuFrhbe1jK1GkCgYEAzVIidaYryLQSXmEDM0TizRy4wsz170pP1d2vxEc0mD3fUFU9IjJqlJNUVrExu6/Jh4cQ64erhBMEWcd+qzAF6axH+8jWeZpakv17tOWoxeELTDQYfiV9w1jQWzQYbGYkQ03pn6j1WwM4pElIlXSyebB5+GqmsYDKVFB0RbsuNeECgYEAsc7GPs9+jz86qs1dgBDg3to3V7qTSFPcNLsyfaFBKEM7nqP4PmcNYW12iciUpfINXkj17DE/c5opV6kbxZq5D7cWWvWqRw17QPQZ25gs86uPgzhmWeH9XUgkhCNKq+I7483d9Qwrj3EgWmhe/PrACkKbJIJKLHd1z0XiJtgMczECgYB4z4vFYB8ZmdNjqrdP43FqW7VkcPNJdaMBhZBoWzyWGp+xpk2rsKCWPhXY9lBeyvCdJ9r+367IQ4/A/M5YymJMVNLvzrjIeRSKmxJp/L/AMYnPDg/ebtxrgY6466KDAmDKnQhmb9DmCUzBuD6VeGISY4voPEn9/h0mJNj0cENW0Q==";

	/** 支付宝公钥 */
	// public static final String ALIPAY_PUBLIC_KEY =
	// "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyUnv0p2nRlyfkmUDJsTP3cxpRsUtf6fmiLmvJxqVxGmAPl6AWr1i/aE+/nBany/5OsdSVXFH0Y+7A71QsxzGUAleGvOkvmEeDaKpBOo+tl5Rr6LbldeUZFwS/4hi90veIijEX9Ofh1Gf+gPSquk0i8asB5HRK/PsGVCNOJnKTbtVJyA60+3maxt6+ooEh/JJfI4rj5qRvqnOEfDR9ALIfuNDFEyi7d4S3v30dP7O/rgFbVVtE7R3zWNYXA4XVLR+GbZynXqIzVYRseUsIj+TrwNuzexkpeoKjY3heZ7XkFwDBDCTbTL57CvfbN6TWa+XqQKjmJ1oaSclR8gqCDF54QIDAQAB";
	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwnet8p2gpkLnDfCKPCMdqT/yyNqATO4zPFLrR9LnHrc0QQwVFPAZEeyKJRtLE8Yc/FbNefXgr+xT2qO55GK3CgQEB+ITQMfYz+ahFv9RSvgeqOhmoBdift/LgHyEeHBJyopojE/AmqB/loc9KKEt1WxFCgB9GY+xhxggN9QcaMxFfEp1FvBE3/3z+LPTWCSjeDw+rQ5pF06reQfilyry76p6VjnS38otosQ4yjZbG8Rqq/Jn6b9MAmWWLF6IkRdujKK14tdOQvnU3k0irsPUuTBj1r57hSLreHbF5ROld/waBVPHmc6Tq0mNI+FfU8HT5mZ7/Eeg+FBUWSDuk9bAGQIDAQAB";
	/** 编码方式 */
	public static final String CHARSET = "utf-8";

	/** 异步通知地址 */
	@Value("${notyfy.url}")
	// public static final String NOTIFY_URL =
	// "http://ybd.yizuanbao.cn/pay/v1/aliapi/aliPay_notify";
	// "http://pay.dev.001bank.com:8087/pay/v1/aliapi/aliPay_notify"
	public String NOTIFY_URL;
	/** 通知借款端支付结果 */
	@Value("${payResult.url}")
	public String payResultUrl;
	
	// public static String payResultUrl="http://192.168.81.53:8080/cash/v1/api/game/acceptEvaluateResult";
	
	public static AlipayTradeAppPayResponse response;
	public AlipayClient alipayClient;

	@Autowired
	PayOrderInfoMapper payOrderInfoMapper;
	@Autowired
	PayResponseMapper payResponseMapper;
	@Autowired
	PayOrderRefundInfoMapper payOrderRefundInfoMapper;

	public static String alipaypublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwnet8p2gpkLnDfCKPCMdqT/yyNqATO4zPFLrR9LnHrc0QQwVFPAZEeyKJRtLE8Yc/FbNefXgr+xT2qO55GK3CgQEB+ITQMfYz+ahFv9RSvgeqOhmoBdift/LgHyEeHBJyopojE/AmqB/loc9KKEt1WxFCgB9GY+xhxggN9QcaMxFfEp1FvBE3/3z+LPTWCSjeDw+rQ5pF06reQfilyry76p6VjnS38otosQ4yjZbG8Rqq/Jn6b9MAmWWLF6IkRdujKK14tdOQvnU3k0irsPUuTBj1r57hSLreHbF5ROld/waBVPHmc6Tq0mNI+FfU8HT5mZ7/Eeg+FBUWSDuk9bAGQIDAQAB";

	public PaySignServiceImpl() {
		logger.info("=============PaySignServiceImpl init==========");
		this.alipayClient = new DefaultAlipayClient(
				"https://openapi.alipay.com/gateway.do", APPID,
				APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2"); // 获得初始化的AlipayClient
	}

	/**
	 * 获取签名字符串数据 Description:
	 * 
	 * @return
	 * @see
	 */
	@Override
	public String getPaySign(OrderInfoVo vo) throws AlipayApiException {
		// AlipayClient alipayClient = new DefaultAlipayClient(GATE, APPID,
		// APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");

		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		PayOrderInfo payOrderInfo = new PayOrderInfo();
		// 赋值 ,相同对象属性copy
		BeanUtils.copyProperties(vo, payOrderInfo);

		model.setBody(payOrderInfo.getBody());
		model.setSubject(payOrderInfo.getSubject());
		model.setOutTradeNo(payOrderInfo.getOutTradeNo());
		model.setTimeoutExpress(StringUtils.isBlank(payOrderInfo
				.getTimeoutExpress()) ? "12h" : payOrderInfo
				.getTimeoutExpress());
		model.setTotalAmount(payOrderInfo.getTotalAmount().toString());
		model.setProductCode("QUICK_MSECURITY_PAY");
		request.setBizModel(model);
		request.setNotifyUrl(NOTIFY_URL);
		// 这里和普通的接口调用不同，使用的是sdkExecute
		response = alipayClient.sdkExecute(request);

		String outTradeNo = payOrderInfo.getOutTradeNo();
		PayOrderInfo payInfo = payOrderInfoMapper
				.selectByOutTradeNo(outTradeNo);

		if (null != payInfo) {
			return response.getBody();
		} else {
			payOrderInfo.setPayType(Integer.parseInt(PayEnum.ALIPAY_PAY.getCode()));
			payOrderInfo.setCreateTime(new Date());
			payOrderInfo.setTimeoutExpress("12h"); // 12小时有效期
			payOrderInfo.setPaymentkind(vo.getPaymentKind());
			payOrderInfo.setUserId(vo.getUserId());
			
			logger.info("插入支付数据为===============" + JSON.toJSONString(payOrderInfo));
			payOrderInfoMapper.insert(payOrderInfo); // 保存数据

			// 就是orderString
			// 可以直接给客户端请求，无需再做处理。
			return response.getBody();
		}
	}

	/**
	 * 
	 * Description: 支付宝通知处理方法
	 * 
	 * @param params
	 * @return
	 * @see
	 */
	@Override
	public String aliPay_notify(Map<String, String> params)
			throws AlipayApiException {
		logger.info("支付宝支付结果通知" + JSON.toJSONString(params));
		String returnStr = "fail";
		try {
			boolean flag = AlipaySignature.rsaCheckV1(params, alipaypublicKey,
					CHARSET, "RSA2");
			logger.info("==========1===========flag=" + flag);
			
			if (flag) {
				// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
				// 交易状态
				String tradeStatus = (String) params.get("trade_status");
				String notify_type = (String) params.get("notify_type");
				// 商户订单号
				String out_trade_no = params.get("out_trade_no");
				
				// 这里做一下拦截,如果该笔订单已经是交易成功或交易关闭状态, 那么就不再去在支付记录表中插入记录以及通知业务方
				PayOrderInfo payInfo = payOrderInfoMapper.selectByOutTradeNo(out_trade_no);
				if(null != payInfo && ("TRADE_SUCCESS".equals(payInfo.getTradeStatus()) || "TRADE_CLOSED".equals(payInfo.getTradeStatus()))) {
					return "success";
				}
				
				PayResponse record =new PayResponse();
				record.setAlipayTrade("notify");
				if(params !=null)
				record.setBoby(JSON.toJSONString(params));
				record.setOutTradeNo(out_trade_no);
				record.setCreateTime(new Date());
				payResponseMapper.insert(record);
				if ("trade_status_sync".equals(notify_type)) {
					// 付款金额
					String buyer_pay_amount = params.get("buyer_pay_amount");
					// 支付宝交易号
					String trade_no = params.get("trade_no");
					// 附加数据
					// String passback_params = URLDecoder.decode(params.get("passback_params"));

					String send_pay_date = params.get("send_pay_date");
					String notifyId = params.get("notify_id");
					// 根据异步回调修改订单状态以及其他一些参数值
					PayOrderInfo payOrderInfo = payOrderInfoMapper
							.selectByOutTradeNo(out_trade_no);
					payOrderInfo.setTradeNo(trade_no);
					payOrderInfo.setUpdateTime(new Date());
					payOrderInfo.setTradeStatus(tradeStatus);
					
					logger.info("支付宝异步回调通知对象为====="
							+ JSON.toJSONString(payOrderInfo));

					payOrderInfoMapper.updateByPrimaryKey(payOrderInfo);
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("orderNo", out_trade_no);
					param.put("totailAmount", buyer_pay_amount);
					param.put("payTime", new Date());
					// param.put("paymentKind", "1");
					if("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_CLOSED".equals(tradeStatus)) {
						param.put("status", 1);
					} else {
						param.put("status", 0);
					}
					param.put("userId", payOrderInfo.getUserId());
					param.put("paymentKind", payOrderInfo.getPaymentkind());
					param.put("resultWay", "2");
					String rst = HttpUtils.httpPostWithJSON(payResultUrl, JSON.toJSONString(param));
					logger.info("支付回调业务方传入参数是 param = " + JSON.toJSONString(param));
					logger.info("调用业务费返回结果是 rst = " + rst);
					if ("TRADE_SUCCESS".equals(tradeStatus)) {
						returnStr = "success"; // 支付成功
					} else if ("TRADE_FINISHED".equals(tradeStatus)) {
						
					}
				}
			}
		} catch (AlipayApiException e) {
			logger.error("支付通知异常", e);
			returnStr = "fail";
		} catch (Exception e) {
			logger.error("支付结果通知借款的失败", e);
			returnStr = "fail";
		}
		return returnStr;
	}

	/**
	 * 
	 * Description: 支付宝退款签名方法
	 * 
	 * @param refundModel
	 *            退款对象
	 * @return
	 * @see
	 */
	public boolean getRefundSign(AlipayTradeRefundModel refundModel,Map<String,String> orderInfo)
			throws AlipayApiException {
		// AlipayClient alipayClient = new DefaultAlipayClient(
		// "https://openapi.alipay.com/gateway.do", APPID,
		// APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2"); //
		// 获得初始化的AlipayClient
		
		logger.info("退款服务入参: AlipayTradeRefundModel = {}, orderInfo = {}", refundModel, orderInfo);
		
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();// 创建API对应的request类
		// request.setBizContent(JSON.toJSONString(refundModel));// 设置业务参数
		request.setBizModel(refundModel);

		AlipayTradeRefundResponse response = alipayClient.execute(request);// 通过alipayClient调用API，获得对应的response类
		
		logger.info("支付宝退款返回参数 response = " + JSON.toJSONString(response));
		
		PayResponse record =new PayResponse();
		record.setAlipayTrade("AlipayTradeRefundModel");
		record.setBoby(response.getBody());
		record.setOutTradeNo(refundModel.getOutTradeNo());
		record.setCreateTime(new Date());
		payResponseMapper.insert(record); //记录返回数据日志
		
		PayOrderRefundInfo refundInfo = new PayOrderRefundInfo();
		refundInfo.setBorrowNid(orderInfo.get("borrowNid"));
		refundInfo.setOutTradeNo(orderInfo.get("outTradeNo"));
		refundInfo.setPlatformUserNo(orderInfo.get("platformUserNo"));
		refundInfo.setRefundFee(new BigDecimal(orderInfo.get("refundAmount")));
		refundInfo.setCreateTime(new Date());
		refundInfo.setTradeNo(response.getTradeNo());
		refundInfo.setBuyerLogonId(response.getBuyerLogonId());
		refundInfo.setBuyerUserId(response.getBuyerUserId());
		refundInfo.setGmtRefundPay(response.getGmtRefundPay());
		refundInfo.setPayType(Integer.parseInt(PayEnum.ALIPAY_PAY.getCode()));
		refundInfo.setRefundStatus(response.getCode());
		refundInfo.setRemark(orderInfo.get("remark"));
		refundInfo.setUpdateTime(new Date());
		
		payOrderRefundInfoMapper.insert(refundInfo);
		
		if (response.isSuccess()) {
			return true;
		}
		// String boby = response.getBody();
		// System.out.print(boby);
		return false;
	}

	public static void main(String[] args) throws Exception {
		String outTradeNo = "zz20180104194829321";
		AlipayTradeRefundModel refundModel = new AlipayTradeRefundModel();
		refundModel.setOutTradeNo(outTradeNo);
		refundModel.setRefundAmount("0.01");
		refundModel.setOutRequestNo("HZ01RF001");
		// // refundModel.set
		//
		AlipayClient alipayClient = new DefaultAlipayClient(
				"https://openapi.alipay.com/gateway.do", APPID,
				APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2"); // 获得初始化的AlipayClient

		//
		// AlipayClient alipayClient = new
		// DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APPID,
		// APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
		// //获得初始化的AlipayClient
		AlipayTradeQueryRequest alipayTradeQueryRequest = new AlipayTradeQueryRequest();// 创建API对应的request类
		AlipayTradeQueryModel bizModel = new AlipayTradeQueryModel();
		bizModel.setOutTradeNo(outTradeNo);
		// bizModel.setTradeNo("2018010321001004980297808783");

		alipayTradeQueryRequest.setBizModel(bizModel);
		// request.setBizContent("{" +
		// "\"out_trade_no\":\"100342312764001\","+
		// //"   \"trade_no\":\"2014112611001004680073956707\"" +
		// "}");//设置业务参数
		AlipayTradeQueryResponse alipayTradeQueryResponse = alipayClient
				.execute(alipayTradeQueryRequest);// 通过alipayClient调用API，获得对应的response类
		System.out.print("==alipayTradeQueryResponse=="
				+ alipayTradeQueryResponse.getBody());

		AlipayTradeRefundRequest alipayTradeRefundRequest = new AlipayTradeRefundRequest();// 创建API对应的request类
		// request.setBizContent(JSON.toJSONString(refundModel));// 设置业务参数
		// request.setBizContent("{" +
		// "    \"out_trade_no\":\"100342312764001\"," +
		// "    \"out_request_no\":\"1000012\"," +
		// "    \"refund_amount\":\"0.01\"" +
		// "  }");//设置业务参数
		alipayTradeRefundRequest.setBizModel(refundModel);
		AlipayTradeRefundResponse alipayTradeRefundResponse = alipayClient
				.execute(alipayTradeRefundRequest);// 通过alipayClient调用API，获得对应的response类

		if (alipayTradeRefundResponse.isSuccess()) {
			System.out.println("退款调用成功");
		} else {
			System.out.println("退款调用失败");
		}

		String boby = alipayTradeRefundResponse.getBody();
		System.out.print("=alipayTradeRefundResponse==" + boby);

		AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
		// request.setBizContent("{" +
		// "\"trade_no\":\"20150320010101001\"," +
		// "\"out_trade_no\":\"2014112611001004680073956707\"," +
		// "\"out_request_no\":\"2014112611001004680073956707\"" +
		// "}");
		AlipayTradeFastpayRefundQueryModel alipayTradeFastpayRefundQueryModel = new AlipayTradeFastpayRefundQueryModel();
		alipayTradeFastpayRefundQueryModel.setOutTradeNo(outTradeNo);
		// alipayTradeFastpayRefundQueryModel
		// .setTradeNo("2018010321001004980297808783");
		String outRequestNo = String.valueOf(System.currentTimeMillis());
		alipayTradeFastpayRefundQueryModel.setOutRequestNo(outRequestNo);
		request.setBizModel(alipayTradeFastpayRefundQueryModel);
		AlipayTradeFastpayRefundQueryResponse alipayTradeFastpayRefundQueryResponse = alipayClient
				.execute(request);
		if (alipayTradeFastpayRefundQueryResponse.isSuccess()) {
			System.out.println("alipayTradeFastpayRefundQueryModel调用成功");
		} else {
			System.out.println("调用失败");
		}
		boby = alipayTradeFastpayRefundQueryResponse.getBody();
		System.out.print("=alipayTradeRefundResponse==" + boby);

		// BizContentModel biz_content = new BizContentModel();
		// biz_content.setBody("支付test");
		// biz_content.setOut_trade_no("100342312764513");
		// biz_content.setSubject("支付test subject");
		// biz_content.setTimeout_express("10h");
		// biz_content.setTotal_amount("0.02");
		// System.err.println(JSON.toJSON(biz_content));
		//
		// String inputStr = JSON.toJSON(biz_content).toString();
		// // 公钥加密
		// byte[] encodedData = RSACoder.encryptByPublicKey(inputStr,
		// RSACoder.publicKey);
		// // base64编码处理
		// String data = RSACoder.encryptBASE64(encodedData);
		// // base64编码解码处理
		// encodedData = RSACoder.decryptBASE64(data);
		// // 私钥解码
		// byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData,
		// RSACoder.privateKey);
		// String outputStr = new String(decodedData);
		//
		// System.err.println("===outputStr===" + outputStr);
		//
		// String sign = PaySignUtil.getsign(biz_content);
		// System.err.println(sign);
	}

}
