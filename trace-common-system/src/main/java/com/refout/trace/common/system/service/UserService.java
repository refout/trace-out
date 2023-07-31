package com.refout.trace.common.system.service;

import com.refout.trace.common.system.domain.User;

/**
 * UserService 接口提供与用户领域交互的方法。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/26 14:45
 */
public interface UserService {

    /**
     * 根据用户名检索用户信息的方法。
     *
     * @param username 用户名
     * @return 与给定用户名相关联的 User 对象
     */
    User getByUsername(String username);

    /**
     * 根据给定的电话号码检索与之相关联的 User 对象。
     *
     * @param phone 电话号码
     * @return 与给定电话号码相关联的 User 对象
     */
    User getByPhone(String phone);

    /**
     * 新增
     *
     * @param user 用户信息
     * @return 是否成功
     */
    User save(User user);

}
