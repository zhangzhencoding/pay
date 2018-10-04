package com.zz.pay.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PayOrderInfo implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order_info.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order_info.out_trade_no
     *
     * @mbggenerated
     */
    private String outTradeNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order_info.trade_no
     *
     * @mbggenerated
     */
    private String tradeNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order_info.body
     *
     * @mbggenerated
     */
    private String body;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order_info.subject
     *
     * @mbggenerated
     */
    private String subject;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order_info.timeout_express
     *
     * @mbggenerated
     */
    private String timeoutExpress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order_info.total_amount
     *
     * @mbggenerated
     */
    private BigDecimal totalAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order_info.platform_user_no
     *
     * @mbggenerated
     */
    private String platformUserNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order_info.borrow_nid
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order_info.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order_info.trade_status
     *
     * @mbggenerated
     */
    private String tradeStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order_info.pay_type
     *
     * @mbggenerated
     */
    private Integer payType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order_info.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order_info.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order_info.paymentKind
     *
     * @mbggenerated
     */
    private String paymentkind;
    
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pay_order_info
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order_info.id
     *
     * @return the value of pay_order_info.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order_info.id
     *
     * @param id the value for pay_order_info.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order_info.out_trade_no
     *
     * @return the value of pay_order_info.out_trade_no
     *
     * @mbggenerated
     */
    public String getOutTradeNo() {
        return outTradeNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order_info.out_trade_no
     *
     * @param outTradeNo the value for pay_order_info.out_trade_no
     *
     * @mbggenerated
     */
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order_info.trade_no
     *
     * @return the value of pay_order_info.trade_no
     *
     * @mbggenerated
     */
    public String getTradeNo() {
        return tradeNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order_info.trade_no
     *
     * @param tradeNo the value for pay_order_info.trade_no
     *
     * @mbggenerated
     */
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order_info.body
     *
     * @return the value of pay_order_info.body
     *
     * @mbggenerated
     */
    public String getBody() {
        return body;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order_info.body
     *
     * @param body the value for pay_order_info.body
     *
     * @mbggenerated
     */
    public void setBody(String body) {
        this.body = body == null ? null : body.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order_info.subject
     *
     * @return the value of pay_order_info.subject
     *
     * @mbggenerated
     */
    public String getSubject() {
        return subject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order_info.subject
     *
     * @param subject the value for pay_order_info.subject
     *
     * @mbggenerated
     */
    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order_info.timeout_express
     *
     * @return the value of pay_order_info.timeout_express
     *
     * @mbggenerated
     */
    public String getTimeoutExpress() {
        return timeoutExpress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order_info.timeout_express
     *
     * @param timeoutExpress the value for pay_order_info.timeout_express
     *
     * @mbggenerated
     */
    public void setTimeoutExpress(String timeoutExpress) {
        this.timeoutExpress = timeoutExpress == null ? null : timeoutExpress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order_info.total_amount
     *
     * @return the value of pay_order_info.total_amount
     *
     * @mbggenerated
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order_info.total_amount
     *
     * @param totalAmount the value for pay_order_info.total_amount
     *
     * @mbggenerated
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order_info.platform_user_no
     *
     * @return the value of pay_order_info.platform_user_no
     *
     * @mbggenerated
     */
    public String getPlatformUserNo() {
        return platformUserNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order_info.platform_user_no
     *
     * @param platformUserNo the value for pay_order_info.platform_user_no
     *
     * @mbggenerated
     */
    public void setPlatformUserNo(String platformUserNo) {
        this.platformUserNo = platformUserNo == null ? null : platformUserNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order_info.borrow_nid
     *
     * @return the value of pay_order_info.borrow_nid
     *
     * @mbggenerated
     */
    public String getBorrowNid() {
        return borrowNid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order_info.borrow_nid
     *
     * @param borrowNid the value for pay_order_info.borrow_nid
     *
     * @mbggenerated
     */
    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order_info.remark
     *
     * @return the value of pay_order_info.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order_info.remark
     *
     * @param remark the value for pay_order_info.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order_info.trade_status
     *
     * @return the value of pay_order_info.trade_status
     *
     * @mbggenerated
     */
    public String getTradeStatus() {
        return tradeStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order_info.trade_status
     *
     * @param tradeStatus the value for pay_order_info.trade_status
     *
     * @mbggenerated
     */
    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus == null ? null : tradeStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order_info.pay_type
     *
     * @return the value of pay_order_info.pay_type
     *
     * @mbggenerated
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order_info.pay_type
     *
     * @param payType the value for pay_order_info.pay_type
     *
     * @mbggenerated
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order_info.create_time
     *
     * @return the value of pay_order_info.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order_info.create_time
     *
     * @param createTime the value for pay_order_info.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order_info.update_time
     *
     * @return the value of pay_order_info.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order_info.update_time
     *
     * @param updateTime the value for pay_order_info.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order_info.paymentKind
     *
     * @return the value of pay_order_info.paymentKind
     *
     * @mbggenerated
     */
    public String getPaymentkind() {
        return paymentkind;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order_info.paymentKind
     *
     * @param paymentkind the value for pay_order_info.paymentKind
     *
     * @mbggenerated
     */
    public void setPaymentkind(String paymentkind) {
    	this.paymentkind = paymentkind;
    }

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_order_info
     *
     * @mbggenerated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PayOrderInfo other = (PayOrderInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOutTradeNo() == null ? other.getOutTradeNo() == null : this.getOutTradeNo().equals(other.getOutTradeNo()))
            && (this.getTradeNo() == null ? other.getTradeNo() == null : this.getTradeNo().equals(other.getTradeNo()))
            && (this.getBody() == null ? other.getBody() == null : this.getBody().equals(other.getBody()))
            && (this.getSubject() == null ? other.getSubject() == null : this.getSubject().equals(other.getSubject()))
            && (this.getTimeoutExpress() == null ? other.getTimeoutExpress() == null : this.getTimeoutExpress().equals(other.getTimeoutExpress()))
            && (this.getTotalAmount() == null ? other.getTotalAmount() == null : this.getTotalAmount().equals(other.getTotalAmount()))
            && (this.getPlatformUserNo() == null ? other.getPlatformUserNo() == null : this.getPlatformUserNo().equals(other.getPlatformUserNo()))
            && (this.getBorrowNid() == null ? other.getBorrowNid() == null : this.getBorrowNid().equals(other.getBorrowNid()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getTradeStatus() == null ? other.getTradeStatus() == null : this.getTradeStatus().equals(other.getTradeStatus()))
            && (this.getPayType() == null ? other.getPayType() == null : this.getPayType().equals(other.getPayType()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getPaymentkind() == null ? other.getPaymentkind() == null : this.getPaymentkind().equals(other.getPaymentkind()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_order_info
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOutTradeNo() == null) ? 0 : getOutTradeNo().hashCode());
        result = prime * result + ((getTradeNo() == null) ? 0 : getTradeNo().hashCode());
        result = prime * result + ((getBody() == null) ? 0 : getBody().hashCode());
        result = prime * result + ((getSubject() == null) ? 0 : getSubject().hashCode());
        result = prime * result + ((getTimeoutExpress() == null) ? 0 : getTimeoutExpress().hashCode());
        result = prime * result + ((getTotalAmount() == null) ? 0 : getTotalAmount().hashCode());
        result = prime * result + ((getPlatformUserNo() == null) ? 0 : getPlatformUserNo().hashCode());
        result = prime * result + ((getBorrowNid() == null) ? 0 : getBorrowNid().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getTradeStatus() == null) ? 0 : getTradeStatus().hashCode());
        result = prime * result + ((getPayType() == null) ? 0 : getPayType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getPaymentkind() == null) ? 0 : getPaymentkind().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_order_info
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", outTradeNo=").append(outTradeNo);
        sb.append(", tradeNo=").append(tradeNo);
        sb.append(", body=").append(body);
        sb.append(", subject=").append(subject);
        sb.append(", timeoutExpress=").append(timeoutExpress);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", platformUserNo=").append(platformUserNo);
        sb.append(", borrowNid=").append(borrowNid);
        sb.append(", remark=").append(remark);
        sb.append(", tradeStatus=").append(tradeStatus);
        sb.append(", payType=").append(payType);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", paymentkind=").append(paymentkind);
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}