package com.refout.trace.common.system.repository;


import com.refout.trace.common.system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * 提供创建、更新、查询和删除用户的功能
 *
 * @author oo w
 * @version 1.0
 * @since 2023/5/14 18:24
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * 通过用户名查找用户
	 *
	 * @param username 用户名
	 * @return 用户
	 */
	User findByUsername(String username);

	/**
	 * 通过电话查找用户
	 *
	 * @param phone 电话
	 * @return 用户
	 */
	User findByPhone(String phone);

	/**
	 * 根据手机号统计用户数量。
	 *
	 * @param phone 手机号
	 * @return 用户数量
	 */
	int countByPhone(String phone);

	/**
	 * 根据用户名统计用户数量。
	 *
	 * @param username 用户名
	 * @return 用户数量
	 */
	int countByUsername(String username);

}
