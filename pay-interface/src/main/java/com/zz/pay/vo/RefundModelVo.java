/*
 * 文件名：RefundModelVo.java
 * 版权：Copyright by www.001bank.com
 * 描述：
 * 修改人：zz
 * 修改时间：2018年1月3日
 */

package com.zz.pay.vo;

import java.math.BigDecimal;

/**
 * 〈功能详细描述〉:退款接收的对象的参数
 * 
 * @author zz
 * @version 2018年1月3日
 * @see RefundModelVo
 * @since
 */
public class RefundModelVo {
	// out_trade_no 订单号
	private String outTradeNo; // 支付时传入的商户订单号，与trade_no必填一个
	private String trade_no;// 支付时返回的支付宝交易号，与out_trade_no必填一个
	private String out_request_no; // 本次退款请求流水号，部分退款时必传
	private BigDecimal refund_amount; //

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getOut_request_no() {
		return out_request_no;
	}

	public void setOut_request_no(String out_request_no) {
		this.out_request_no = out_request_no;
	}

	public BigDecimal getRefund_amount() {
		return refund_amount;
	}

	public void setRefund_amount(BigDecimal refund_amount) {
		this.refund_amount = refund_amount;
	}

}
