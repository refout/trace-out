package com.refout.trace.address.service.impl;

import com.refout.trace.address.domain.Address;
import com.refout.trace.address.repository.AddressRepository;
import com.refout.trace.address.service.AddressService;
import com.refout.trace.datasource.repository.BaseRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 地址管理service实现
 *
 * @author oo w
 * @version 1.0
 * @since 2023/10/8 9:17
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressRepository addressRepository;

    /**
     * 获取对应实体类的Repository
     *
     * @return 实体类对应的Repository
     */
    @Override
    public BaseRepository<Address, Long> repository() {
        return addressRepository;
    }

    /**
     * 通过父id查询地址
     *
     * @param parentId 父id
     * @return 地址集合
     */
    @Override
    public List<Address> getByParentId(Long parentId) {
        if (parentId == null) {
            parentId = 0L;
        }
        return addressRepository.findByParentId(parentId);
    }

    /**
     * 查询省级地址
     *
     * @return 省级地址
     */
    @Override
    public List<Address> getProvince() {
        return addressRepository.findByParentId(0L);
    }

    /**
     * 通过地址名称模糊查询
     *
     * @param name 地址名称
     * @return 地址集合
     */
    @Override
    public List<Address> getByLikeName(String name) {
        if (name == null || name.isBlank()) {
            return Collections.emptyList();
        }
        return addressRepository.findByNameContainingIgnoreCase(name);
    }

}
