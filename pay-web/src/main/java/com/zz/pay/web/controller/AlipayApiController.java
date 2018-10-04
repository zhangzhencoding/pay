package com.zz.pay.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.zz.pay.dao.PayOrderInfoMapper;
import com.zz.pay.enums.PayEnum;
import com.zz.pay.model.Result;
import com.zz.pay.model.ResultSupport;
import com.zz.pay.service.PaySignService;
import com.zz.pay.util.encrypt.RSACoder;
import com.zz.pay.vo.OrderInfoVo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version 2017年12月29日
 * @see AlipayApiController
 * @since
 */
@Controller
@RequestMapping(value = "/v1/aliapi/pay")
public class AlipayApiController {

	private static final Logger logger = LoggerFactory
			.getLogger(AlipayApiController.class);

	@Autowired
	PaySignService paySignService;

	@Autowired
	private PayOrderInfoMapper payOrderInfoMapper;

	/**
	 * 
	 * Description:获取支付签名接口
	 * 
	 * @param orderInfo
	 * @return
	 * @throws Exception
	 * @see
	 */
	@ApiOperation("获取支付签名接口")
	@RequestMapping(value = "/getSign", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result<?> getSign(@RequestParam(value = "orderInfo") String orderInfo)
			throws Exception {
		logger.info(" getSign 获取支付签名接口  orderInfo:" + orderInfo);

		// PrintWriter out = response.getWriter();
		Result<String> responseModel = new ResultSupport<String>();
		responseModel.setCode(PayEnum.SUCCESS.getCode());
		responseModel.setMsg(PayEnum.SUCCESS.getMsg());
		try {
			// base64编码解码处理
			byte[] encodedData = RSACoder.decryptBASE64(orderInfo);

			// 私钥解码
			byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData,
					RSACoder.privateKey);

			String outputStr = new String(decodedData);

			logger.info("解密数据==========: " + outputStr);

			// 如果验签成功, 则返回正确的消息给前端
			OrderInfoVo orderDto = JSONObject.parseObject(outputStr,
					OrderInfoVo.class);
			orderDto.setTotalAmount(new BigDecimal("0.01")); // 测试使用的
			String sign = paySignService.getPaySign(orderDto);
			logger.info(" getSign 获取支付宝的签名结果是:" + sign);
			responseModel.setModel(sign);
			return responseModel;
		} catch (Exception e) {
			logger.error("签名异常",e);
			responseModel.setCode(PayEnum.PAY_ERROR.getCode());
			responseModel.setMsg(PayEnum.PAY_ERROR.getMsg());
			return responseModel;
		}
	}

	/**
	 * 异步通知付款状态的Controller
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws AlipayApiException
	 */
	@ApiOperation("支付通知接口")
	@RequestMapping(value ="notify", method = RequestMethod.POST)
	@ResponseBody
	public String aliPay_notify(HttpServletRequest request,
			HttpServletResponse response) throws AlipayApiException {
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		logger.info("支付宝支付结果通知" + JSON.toJSONString(requestParams));
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		String returnStr = "";
		try {
			returnStr = paySignService.aliPay_notify(params);
		} catch (AlipayApiException e) {
			logger.error("支付宝支付结果通知异常！",e);
			return "fail";
		}
		return returnStr;
	}
}