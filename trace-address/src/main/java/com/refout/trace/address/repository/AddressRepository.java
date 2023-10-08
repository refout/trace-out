package com.refout.trace.address.repository;

import com.refout.trace.address.domain.Address;
import com.refout.trace.datasource.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends BaseRepository<Address, Long> {

    /**
     * 通过父id查询地址
     *
     * @param parentId 父id
     * @return 地址集合
     */
    List<Address> findByParentId(Long parentId);

    /**
     * 通过地址名称模糊查询
     *
     * @param name 地址名称
     * @return 地址集合
     */
    List<Address> findByNameContainingIgnoreCase(String name);

}
