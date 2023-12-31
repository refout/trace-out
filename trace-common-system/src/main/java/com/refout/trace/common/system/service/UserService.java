package com.refout.trace.common.system.service;

import com.refout.trace.common.system.domain.User;
import com.refout.trace.datasource.service.CrudService;

/**
 * UserService 接口提供与用户领域交互的方法。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/26 14:45
 */
public interface UserService extends CrudService<User, Long> {

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
     * 根据手机号统计用户是否存在。
     *
     * @param phone 手机号
     * @return 用户是否存在
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean existWithPhone(String phone);

    /**
     * 根据用户名统计用户是否存在。
     *
     * @param username 用户名
     * @return 用户是否存在
     */
    boolean existWithUsername(String username);

}
