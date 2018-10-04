package com.zz.pay.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.zz.pay.dao.PayOrderInfoMapper;
import com.zz.pay.dao.PayResponseMapper;
import com.zz.pay.model.PayOrderInfo;
import com.zz.pay.model.ResultSupport;
import com.zz.pay.service.PaySignService;
import com.zz.pay.vo.OrderInfoVo;
import com.zz.pay.enums.PayEnum;
import com.zz.pay.enums.ResConstants;
import com.zz.pay.model.Result;
import com.zz.pay.util.encrypt.RSACoder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zz on 2018/01/02
 */
@Api(description = "验签,支付,退款接口")
@Controller
@RequestMapping("/v1/alipay/payOrderInfo")
public class YbdPayController {

	private static final Logger log = LoggerFactory.getLogger(YbdPayController.class);
	
	public static String alipaypublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwnet8p2gpkLnDfCKPCMdqT/yyNqATO4zPFLrR9LnHrc0QQwVFPAZEeyKJRtLE8Yc/FbNefXgr+xT2qO55GK3CgQEB+ITQMfYz+ahFv9RSvgeqOhmoBdift/LgHyEeHBJyopojE/AmqB/loc9KKEt1WxFCgB9GY+xhxggN9QcaMxFfEp1FvBE3/3z+LPTWCSjeDw+rQ5pF06reQfilyry76p6VjnS38otosQ4yjZbG8Rqq/Jn6b9MAmWWLF6IkRdujKK14tdOQvnU3k0irsPUuTBj1r57hSLreHbF5ROld/waBVPHmc6Tq0mNI+FfU8HT5mZ7/Eeg+FBUWSDuk9bAGQIDAQAB";
	
	public static final String CHARSET = "utf-8";
	
	@Value("${payResult.url}")
	public String payResultUrl_1;
	
	@Value("${notyfy.url}")
	public String NOTIFY_URL;
	
	public String payResultUrl="http://192.168.81.53:8080/cash/v1/api/game/acceptEvaluateResult";
	
	// public String payResultUrl="http://192.168.60.84:8080/cash/v1/api/game/acceptEvaluateResult";
	
	@Autowired
	PayOrderInfoMapper payOrderInfoMapper;
	
	@Autowired
	PaySignService paySignService;
	
	@Autowired
	PayResponseMapper payResponseMapper;
	
	@ApiOperation("订单解密方法, orderInfo 订单解密之后的字符串")
	@RequestMapping(value = "verifyOrderInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result<OrderInfoVo> verifyOrderInfo(
			@RequestParam(value = "orderInfo") String orderInfo) {
		
		log.info("verifyOrderInfo入参==========: " + orderInfo);
		
		Result<OrderInfoVo> result = new ResultSupport<OrderInfoVo>();
		try {
			// base64编码解码处理
			byte[] encodedData = RSACoder.decryptBASE64(orderInfo);

			// 私钥解码
			byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData,
					RSACoder.privateKey);

			String outputStr = new String(decodedData);

			log.info("解密数据==========: " + outputStr);

			// 如果验签成功, 则返回正确的消息给前端
			OrderInfoVo orderDto = JSONObject.parseObject(outputStr,
					OrderInfoVo.class);
			result.setCode(ResConstants.SUCCESS.getCode());
			result.setMsg(ResConstants.SUCCESS.getMsg());
			result.setModel(orderDto);

		} catch (Exception e) {
			result.setCode("500");
			result.setMsg("订单有问题，请联系系统管理员或稍后再试！");
			result.setModel(null);

			log.info("订单解密方法 verifyOrderInfo 发生异常错误: " + e);
		}

		return result;
	}

	@ApiOperation("根据orderId查询订单支付信息")
	@RequestMapping(value = "queryOrderPayInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result<PayOrderInfo> queryOrderPayInfo(
			@RequestParam(value = "outTradeNo") String outTradeNo) {

		Result<PayOrderInfo> result = new ResultSupport<PayOrderInfo>();
		try {
			PayOrderInfo info = payOrderInfoMapper.selectByOutTradeNo(outTradeNo);
			
			result.setCode(ResConstants.SUCCESS.getCode());
			result.setMsg(ResConstants.SUCCESS.getMsg());
			result.setModel(info);
		} catch (Exception e) {
			result.setCode("500");
			result.setMsg("订单有问题，请联系系统管理员或稍后再试！");
			result.setModel(null);

			log.info("根据orderId查询订单支付信息方法 queryOrderPayInfo 发生异常错误: " + e.getMessage());
		}

		return result;
	}
	
	@ApiOperation("退款接口")
	@RequestMapping(value = "refundOrder", method = RequestMethod.POST)
	@ResponseBody
	public Result<Map<String,String>> refundOrder(@RequestBody Map<String,String> orderInfo) { 
		
		log.info("退款接口refundOrder入参==========: " + JSON.toJSONString(orderInfo));
		
		Result<Map<String,String>> result = new ResultSupport<Map<String,String>>();
		
		Map<String,String> rstMap = new HashMap<String,String>();
		
		if(StringUtils.isBlank(orderInfo.get("outTradeNo")) 
				|| StringUtils.isBlank(orderInfo.get("refundAmount"))
				|| StringUtils.isBlank(orderInfo.get("borrowNid"))
				|| StringUtils.isBlank(orderInfo.get("platformUserNo"))
				) {
			result.setCode(PayEnum.REFUND_ERROE.getCode());
			result.setMsg("订单号或退款金额或标的号或账户id为空,请校验后再进行退款操作!");
			result.setModel(null);
			return result;
		}
		
		String outTradeNo = orderInfo.get("outTradeNo");
		String refundAmount = orderInfo.get("refundAmount");
		
		PayOrderInfo payInfo = payOrderInfoMapper.selectByOutTradeNo(outTradeNo);
		if(null != payInfo && "TRADE_SUCCESS".equals(payInfo.getTradeStatus())) {
			AlipayTradeRefundModel refundModel = new AlipayTradeRefundModel();
			// refundModel.setTradeNo(outTradeNo);
			refundModel.setTradeNo(payInfo.getTradeNo());
			refundModel.setRefundAmount(refundAmount);
			refundModel.setOutRequestNo(String.valueOf(System.currentTimeMillis()));
			
			try {
				boolean refund =  paySignService.getRefundSign(refundModel,orderInfo);
				if(refund) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					result.setCode(PayEnum.REFUND_SUCCESS.getCode());
					result.setMsg(PayEnum.REFUND_SUCCESS.getMsg());
					
					rstMap.put("outTradeNo", outTradeNo);
					rstMap.put("refundTime", sdf.format(new Date()));
					
					result.setModel(rstMap);
				} else {
					result.setCode(PayEnum.REFUND_ERROE.getCode());
					result.setMsg(PayEnum.REFUND_ERROE.getMsg());
					result.setModel(null);
				}
			} catch (AlipayApiException e) {
				e.printStackTrace();
				log.info("请求退款异常refundOrder ======= " + e.toString());
				result.setCode(PayEnum.REFUND_ERROE.getCode());
				result.setMsg(PayEnum.REFUND_ERROE.getMsg());
				result.setModel(null);
				return result;
			}
		} else {
			result.setCode(PayEnum.REFUND_ERROE.getCode());
			result.setMsg("无此订单,或该笔订单未支付成功");
			result.setModel(null);
		}
		
		log.info("退款服务接口  refundOrder 返回的结果是: " + JSON.toJSONString(result));
		
		return result;
	}

	
	/*@ApiOperation("支付宝异步回调测试方法")
	@RequestMapping(value = "aliPay_notify", method = RequestMethod.POST)
	@ResponseBody
	public String aliPay_notify(@RequestBody Map<String, String> params)
			throws AlipayApiException {
		
		String s = paySignService.aliPay_notify(params);
		System.out.println(s);
		
		log.info("支付宝支付结果通知" + JSON.toJSONString(params));
		System.out.println(payResultUrl_1);
		System.out.println(NOTIFY_URL);
		String returnStr = "fail";
		try {
			// boolean flag = AlipaySignature.rsaCheckV1(params, alipaypublicKey,
			//		CHARSET, "RSA2");
			boolean flag = true;
			log.info("==========1===========flag=" + flag);
			if (flag) {
				// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
				// 交易状态
				String tradeStatus = (String) params.get("trade_status");
				String notify_type = (String) params.get("notify_type");
				// 商户订单号
				String out_trade_no = params.get("out_trade_no");
				
				// 这里做一下拦截,如果该笔订单已经是交易成功或交易关闭状态, 那么就不再去在支付记录表中插入记录以及通知业务方
				//PayOrderInfo payInfo = payOrderInfoMapper.selectByOutTradeNo(out_trade_no);
				//if(null != payInfo && ("TRADE_SUCCESS".equals(payInfo.getTradeStatus()) || "TRADE_CLOSED".equals(payInfo.getTradeStatus()))) {
				//	return "success";
				//}
				
				PayResponse record = new PayResponse();
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
					//String passback_params = URLDecoder.decode(params.get("passback_params"));

					String send_pay_date = params.get("send_pay_date");
					String notifyId = params.get("notify_id");
					// 根据异步回调修改订单状态以及其他一些参数值
					PayOrderInfo payOrderInfo = payOrderInfoMapper
							.selectByOutTradeNo(out_trade_no);
					payOrderInfo.setTradeNo(trade_no);
					payOrderInfo.setUpdateTime(new Date());
					payOrderInfo.setTradeStatus(tradeStatus);

					log.info("支付宝异步回调通知对象为====="
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
					param.put("userId", "53000169");
					param.put("paymentKind", "1");
					param.put("resultWay", "2");
					//String rst = HttpClientUtil.post(payResultUrl, JSON.toJSONString(param));
					
					Map<String, String> param2 = new HashMap<String, String>();
					param2.put("request", JSON.toJSONString(param));
					
					String rst = HttpUtils.httpPostWithJSON(payResultUrl, JSON.toJSONString(param));
					log.info("支付回调业务方传入参数是 param = " + JSON.toJSONString(param));
					log.info("支付回调业务方返回结果是 rst = " + rst);
					if ("TRADE_SUCCESS".equals(tradeStatus)) {
						returnStr = "success"; // 退款成功
					} else if ("TRADE_FINISHED".equals(tradeStatus)) {
						
					}
				}
			}
		}  catch (Exception e) {
			log.error("支付结果通知借款的失败", e);
			returnStr = "fail";
		}
		return returnStr;
	}*/
	
	/*@ApiOperation("退款接口测试")
	@RequestMapping(value = "refundOrderTest", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> refundOrderTest(@RequestBody Map<String,String> orderInfo) throws Exception { 
		String refundResultUrl = "http://192.168.188.193:8087/pay/v1/alipay/payOrderInfo/refundOrder";
		
		String rst = HttpUtils.httpPostWithJSON(refundResultUrl, JSON.toJSONString(orderInfo));
		
		Result<String> result = new ResultSupport<String>();
		
		result.setCode(PayEnum.REFUND_ERROE.getCode());
		result.setMsg(PayEnum.REFUND_ERROE.getMsg());
		result.setModel(rst);
		
		log.info("退款服务接口  refundOrderTest 返回的结果是: " + JSON.toJSONString(rst));
		
		return result;
	}*/
}
