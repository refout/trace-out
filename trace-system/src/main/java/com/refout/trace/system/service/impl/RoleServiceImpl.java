package com.refout.trace.system.service.impl;

import com.refout.trace.datasource.repository.BaseRepository;
import com.refout.trace.system.domain.Role;
import com.refout.trace.system.repository.RoleRepository;
import com.refout.trace.system.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;

    /**
     * 获取对应实体类的Repository
     *
     * @return 实体类对应的Repository
     */
    @Override
    public BaseRepository<Role, Long> repository() {
        return roleRepository;
    }

}
