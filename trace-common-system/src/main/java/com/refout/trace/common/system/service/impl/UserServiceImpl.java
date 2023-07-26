package com.refout.trace.common.system.service.impl;

import com.refout.trace.common.system.domain.User;
import com.refout.trace.common.system.repository.UserRepository;
import com.refout.trace.common.system.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    /**
     * 根据用户名检索用户信息的方法。
     *
     * @param username 用户名
     * @return 与给定用户名相关联的 User 对象
     */
    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 根据给定的电话号码检索与之相关联的 User 对象。
     *
     * @param phone 电话号码
     * @return 与给定电话号码相关联的 User 对象
     */
    @Override
    public User getByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

}
