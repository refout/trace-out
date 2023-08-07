package com.refout.trace.common.system.service.impl;

import com.refout.trace.common.system.domain.Api;
import com.refout.trace.common.system.repository.ApiRepository;
import com.refout.trace.common.system.service.ApiService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 接口服务类
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/7 17:21
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Resource
    private ApiRepository apiRepository;

    /**
     * 通过用户id查询接口权限字段
     *
     * @param userId 用户id
     * @return 接口权限：{@link Api#getPermission()}
     */
    @Override
    public List<String> getPermissionByUserId(long userId) {
        return apiRepository.findPermissionsByUserId(userId);
    }

}
