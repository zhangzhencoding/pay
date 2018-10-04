/**
 * 
 */
package com.zz.pay.enums;

/**
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version 2017年12月29日
 * @see PayEnum
 * @since
 */
public enum PayEnum {

	SUCCESS("200", "成功"), 
	PAY_ERROR("201", "支付系统异常"), 
	ORDERINFO_ERROR("202","订单号重复"),
	SIGN_ERROR("203","签名验证失败"),
	REFUND_SUCCESS("200","退款成功"),
	REFUND_ERROE("204","退款失败"),
	
	ALIPAY_PAY("1","支付宝支付");

	private final String code; // 编码

	private final String msg; // 描述

	private PayEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public String getMsg(String desc) {
		return msg + ":" + desc;
	}
}
