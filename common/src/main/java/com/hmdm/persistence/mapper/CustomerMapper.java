package com.hmdm.persistence.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CustomerMapper {

    @Update("UPDATE customers SET lastLoginTime = #{time} WHERE id = #{id}")
    int recordLastLoginTime(@Param("id") int customerId, @Param("time") long time);

    @Select("SELECT lastLoginTime FROM customers WHERE id = #{id}")
    Long getLastLoginTime(@Param("id") int customerId);


}
