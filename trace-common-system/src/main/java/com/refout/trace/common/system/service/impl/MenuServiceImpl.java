package com.refout.trace.common.system.service.impl;

import com.refout.trace.common.system.repository.MenuRepository;
import com.refout.trace.common.system.service.MenuService;
import jakarta.annotation.Resource;

import java.util.Set;

public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuRepository menuRepository;

    /**
     * @param userId
     * @return
     */
    @Override
    public Set<String> getPermissionByUserId(long userId) {
        return menuRepository.findPermissionsByUserId(userId);
    }

}
