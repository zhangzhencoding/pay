package com.zz.pay.dao;

import com.zz.pay.model.PayResponse;

public interface PayResponseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_response
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_response
     *
     * @mbggenerated
     */
    int insert(PayResponse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_response
     *
     * @mbggenerated
     */
    int insertSelective(PayResponse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_response
     *
     * @mbggenerated
     */
    PayResponse selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_response
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(PayResponse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_response
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(PayResponse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_response
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PayResponse record);
}