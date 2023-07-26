package com.refout.trace.common.system.service.impl;

import com.refout.trace.common.system.repository.MenuRepository;
import com.refout.trace.common.system.service.MenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuRepository menuRepository;

    /**
     * @param userId
     * @return
     */
    @Override
    public List<String> getPermissionByUserId(long userId) {
        return menuRepository.findPermissionsByUserId(userId);
    }

}
