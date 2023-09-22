package com.refout.trace.address.service.impl;

import com.refout.trace.address.domain.Address;
import com.refout.trace.address.repository.AddressRepository;
import com.refout.trace.address.service.AddressService;
import com.refout.trace.datasource.repository.BaseRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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

}
