package com.refout.trace.system.repository;

import com.refout.trace.datasource.repository.BaseRepository;
import com.refout.trace.system.domain.Role;
import org.springframework.stereotype.Repository;

/**
 * 提供创建、更新、查询和删除角色的功能
 *
 * @author oo w
 * @version 1.0
 * @since 2023/5/14 18:24
 */
@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {

}