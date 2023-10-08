package com.refout.trace.address.service;

import com.refout.trace.address.domain.Address;
import com.refout.trace.datasource.service.CrudService;

import java.util.List;

/**
 * 地址管理service
 *
 * @author oo w
 * @version 1.0
 * @since 2023/10/8 9:17
 */
public interface AddressService extends CrudService<Address,Long> {

    /**
     * 通过父id查询地址
     *
     * @param parentId 父id
     * @return 地址集合
     */
    List<Address> getByParentId(Long parentId);

    /**
     * 查询省级地址
     *
     * @return 省级地址
     */
    List<Address> getProvince();

    /**
     * 通过地址名称模糊查询
     *
     * @param name 地址名称
     * @return 地址集合
     */
    List<Address> getByLikeName(String name);

}
