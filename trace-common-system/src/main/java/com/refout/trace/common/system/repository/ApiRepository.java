package com.refout.trace.common.system.repository;

import com.refout.trace.common.system.domain.Api;
import com.refout.trace.datasource.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 接口的数据访问
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/7 17:31
 */
@Repository
public interface ApiRepository extends BaseRepository<Api, Long> {

	/**
	 * 根据角色ID列表查询对应的权限列表。
	 *
	 * @param roleIds 角色ID列表
	 * @return 权限列表
	 */
	@Query(value = """
			select distinct permission
			from ts_menu m
			         left join ts_role_menu rm on m.id = rm.menu_id
			where m.state = '0'
			  and m.deleted = 0
			  and m.permission is not null
			  and m.permission != ''
			  and rm.role_id in (:roleIds)
			         """, nativeQuery = true)
	List<String> findPermissionsByRoleIds(@Param("roleIds") List<Long> roleIds);

	/**
	 * 根据用户ID查询对应的权限列表。
	 *
	 * @param userId 用户ID
	 * @return 权限列表
	 */
	@Query(value = """
			select distinct m.permission
						from ts_menu m
						         left join ts_role_menu rm on m.id = rm.menu_id
						         left join ts_user_role ur on ur.role_id = rm.role_id
						where m.state = '0'
						  and m.deleted = 0
						  and m.permission is not null
						  and m.permission != ''
						  and ur.user_id in (:userId)
						order by m.permission
			""", nativeQuery = true)
	List<String> findPermissionsByUserId(@Param("userId") long userId);

}
