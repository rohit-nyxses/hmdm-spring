package com.hmdm.persistence.mapper;

import com.hmdm.persistence.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

//    @Select("SELECT * FROM users WHERE login = #{login}")
  // User findByLogin(@Param("login") String login);
//
//    @Select("SELECT * FROM users WHERE email = #{email}")
//    User findByEmail(@Param("email") String email);

    @Select("""
    SELECT u.id AS id,
           u.login AS login,
           u.email AS email,
           u.name AS name,
           u.password AS password,
           u.customerid AS customerId,
           u.userroleid AS userRoleId,
           u.alldevicesavailable AS allDevicesAvailable,
           u.allconfigavailable AS allConfigAvailable,
           u.passwordreset AS passwordReset,
           u.authtoken AS authToken,              -- ✅ added here
           ur.id AS ur_id,
           ur.name AS ur_name,
           ur.description AS ur_description,
           ur.superadmin AS ur_superAdmin
    FROM users u
    LEFT JOIN userroles ur ON u.userroleid = ur.id
    WHERE u.login = #{login}
""")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "login", column = "login"),
            @Result(property = "email", column = "email"),
            @Result(property = "name", column = "name"),
            @Result(property = "password", column = "password"),
            @Result(property = "customerId", column = "customerId"),
            @Result(property = "allDevicesAvailable", column = "allDevicesAvailable"),
            @Result(property = "allConfigAvailable", column = "allConfigAvailable"),
            @Result(property = "passwordReset", column = "passwordReset"),
            @Result(property = "authToken", column = "authToken"),  // ✅ added mapping
            @Result(property = "userRole.id", column = "ur_id"),
            @Result(property = "userRole.name", column = "ur_name"),
            @Result(property = "userRole.description", column = "ur_description"),
            @Result(property = "userRole.superAdmin", column = "ur_superAdmin")
    })
    User findByLogin(@Param("login") String login);

    @Select("""
    SELECT u.id AS id,
           u.login AS login,
           u.email AS email,
           u.name AS name,
           u.password AS password,
           u.customerid AS customerId,
           u.userroleid AS userRoleId,
           u.alldevicesavailable AS allDevicesAvailable,
           u.allconfigavailable AS allConfigAvailable,
           u.passwordreset AS passwordReset,
           u.authtoken AS authToken,              -- ✅ added here
           ur.id AS ur_id,
           ur.name AS ur_name,
           ur.description AS ur_description,
           ur.superadmin AS ur_superAdmin
    FROM users u
    LEFT JOIN userroles ur ON u.userroleid = ur.id
    WHERE u.email = #{email}
""")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "login", column = "login"),
            @Result(property = "email", column = "email"),
            @Result(property = "name", column = "name"),
            @Result(property = "password", column = "password"),
            @Result(property = "customerId", column = "customerId"),
            @Result(property = "allDevicesAvailable", column = "allDevicesAvailable"),
            @Result(property = "allConfigAvailable", column = "allConfigAvailable"),
            @Result(property = "passwordReset", column = "passwordReset"),
            @Result(property = "authToken", column = "authToken"),  // ✅ added mapping
            @Result(property = "userRole.id", column = "ur_id"),
            @Result(property = "userRole.name", column = "ur_name"),
            @Result(property = "userRole.description", column = "ur_description"),
            @Result(property = "userRole.superAdmin", column = "ur_superAdmin")
    })
    User findByEmail(@Param("email") String email);


    @Update("""
        UPDATE users SET
            password = #{password},
            passwordReset = #{passwordReset},
            authToken = #{authToken},
            passwordResetToken = #{passwordResetToken}
        WHERE id = #{id}
    """)
    void setNewPassword(User user);

}

