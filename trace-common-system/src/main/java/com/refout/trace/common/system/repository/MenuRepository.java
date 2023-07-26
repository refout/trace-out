package com.refout.trace.common.system.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.awt.*;
import java.util.List;
import java.util.Set;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

	@Query("select distinct permission " +
			"from ts_menu m " +
			"         left join ts_role_menu rm on m.id = rm.menu_id " +
			"where m.status = '0' " +
			"  and m.deleted = 0" +
			"  and m.permission is not null" +
			"  and m.permission != ''" +
			"  and rm.role_id in (:roleIds)")
	Set<String> findPermissionsByRoleIds(@Param("roleIds") List<Long> roleIds);

	@Query("select distinct m.permission " +
			"from ts_menu m" +
			"         left join ts_role_menu rm on m.id = rm.menu_id" +
			"         left join ts_user_role ur on ur.role_id = rm.role_id " +
			"where m.status = '0'" +
			"  and m.deleted = 0" +
			"  and m.permission is not null" +
			"  and m.permission != ''" +
			"  and ur.user_id in (:userId)" +
			"order by m.permission;")
	Set<String> findPermissionsByUserId(@Param("userId") long userId);

}
