package com.zz.pay.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.zz.pay.vo.OrderInfoVo;

import java.util.Map;

public interface PaySignService {

	/**
	 * 获取订单字符串签名
	 * @param vo
	 * @return
	 * @throws AlipayApiException
	 */
	public String getPaySign(OrderInfoVo vo) throws AlipayApiException;

	/**
	 * 调用阿里接口成功后，阿里回调我们的接口
	 * @param params
	 * @return
	 * @throws AlipayApiException
	 */
	public String aliPay_notify(Map<String, String> params) throws AlipayApiException;

	/**
	 * 获取退款标签
	 * @param refundModel
	 * @param orderInfo
	 * @return
	 * @throws AlipayApiException
	 */
	public boolean getRefundSign(AlipayTradeRefundModel refundModel, Map<String,String> orderInfo)
			throws AlipayApiException;
}
