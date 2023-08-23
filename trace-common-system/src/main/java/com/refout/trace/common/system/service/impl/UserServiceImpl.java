package com.refout.trace.common.system.service.impl;

import com.refout.trace.common.system.domain.User;
import com.refout.trace.common.system.repository.UserRepository;
import com.refout.trace.common.system.service.UserService;
import com.refout.trace.datasource.repository.BaseRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    /**
     * 获取对应实体类的Repository
     *
     * @return 实体类对应的Repository
     */
    @Override
    public BaseRepository<User, Long> repository() {
        return userRepository;
    }

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

    /**
     * 根据手机号统计用户是否存在。
     *
     * @param phone 手机号
     * @return 用户是否存在
     */
    @Override
    public boolean existWithPhone(String phone) {
        return userRepository.countByPhone(phone) > 0;
    }

    /**
     * 根据用户名统计用户是否存在。
     *
     * @param username 用户名
     * @return 用户是否存在
     */
    @Override
    public boolean existWithUsername(String username) {
        return userRepository.countByUsername(username) > 0;
    }

}
