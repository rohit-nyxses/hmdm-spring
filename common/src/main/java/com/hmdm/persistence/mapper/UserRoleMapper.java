package com.hmdm.persistence.mapper;

import com.hmdm.persistence.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRoleMapper {
    @Select("SELECT * FROM user_roles WHERE id = #{id}")
    UserRole findById(@Param("id") int id);
}
