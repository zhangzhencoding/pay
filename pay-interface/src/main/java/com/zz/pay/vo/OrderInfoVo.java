package com.zz.pay.vo;

import java.math.BigDecimal;

/**
 * 〈功能详细描述〉:订单信息接收参数对象
 * 
 * @author zz
 * @version 2018年1月3日
 * @see OrderInfoVo
 * @since
 */
public class OrderInfoVo {
	// 交易描述
	private String body;

	// 交易标题
	private String subject;

	// out_trade_no 订单号
	private String outTradeNo;

	// 该笔订单允许的最晚付款时间，逾期将关闭交易
	private String timeoutExpress;

	// 订单支付总金额
	private BigDecimal totalAmount;

	// 账户ID
	private String platformUserNo;

	// 标的号
	private String borrowNid;

	// 备注
	private String remark;
	
	// 评估费类型（1 首款，2 尾款）
	private String paymentKind;
	
	private String userId;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTimeoutExpress() {
		return timeoutExpress;
	}

	public void setTimeoutExpress(String timeoutExpress) {
		this.timeoutExpress = timeoutExpress;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPlatformUserNo() {
		return platformUserNo;
	}

	public void setPlatformUserNo(String platformUserNo) {
		this.platformUserNo = platformUserNo;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPaymentKind() {
		return paymentKind;
	}

	public void setPaymentKind(String paymentKind) {
		this.paymentKind = paymentKind;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
