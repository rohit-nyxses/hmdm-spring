package com.hmdm.persistence;


import com.hmdm.persistence.domain.User;
import com.hmdm.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UnsecureDAO {

    private final UserMapper userMapper;

    public User findByLogin(String login) {
        return userMapper.findByLogin(login);
    }

    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    public User findByLoginOrEmail(String login) {
        User user = userMapper.findByLogin(login);
        if (user == null) {
            user = userMapper.findByEmail(login);
        }
        return user;
    }

    public void setUserNewPasswordUnsecure(User user) {
        userMapper.setNewPassword(user);
    }
}
