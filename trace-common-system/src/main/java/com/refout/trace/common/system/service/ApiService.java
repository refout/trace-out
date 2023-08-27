package com.refout.trace.common.system.service;

import com.refout.trace.common.system.domain.Api;
import com.refout.trace.datasource.service.DbService;

import java.util.List;

/**
 * 接口服务类
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/7 17:21
 */
public interface ApiService extends DbService<Api, Long> {

    /**
     * 通过用户id查询接口权限字段
     *
     * @param userId 用户id
     * @return 接口权限：{@link Api#getPermission()}
     */
    List<String> getPermissionByUserId(final long userId);

}
