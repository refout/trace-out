package com.refout.trace.common.system.service.impl;

import com.refout.trace.common.system.domain.User;
import com.refout.trace.common.system.repository.UserRepository;
import com.refout.trace.common.system.service.UserService;
import jakarta.annotation.Resource;
import jakarta.persistence.Column;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 新增
     *
     * @param user 用户信息
     * @return 新增用户
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
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

    /**
     * 分页查询
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<User> getPage(User user, Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(1, 10);

        Specification<User> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            ReflectionUtils.doWithFields(User.class, field -> {
                Column column = field.getDeclaredAnnotation(Column.class);
                if (column == null) {
                    return;
                }
                field.setAccessible(true);
                Object value = field.get(user);
                if (value == null) {
                    return;
                }
                predicates.add(criteriaBuilder.like(root.get(column.name()), "%" + value + "%"));
            });

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return userRepository.findAll(specification, pageable);
    }

}
