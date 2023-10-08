package com.refout.trace.address.controller;

import com.refout.trace.address.domain.Address;
import com.refout.trace.address.service.AddressService;
import com.refout.trace.common.web.controller.CrudController;
import com.refout.trace.datasource.service.CrudService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 地址管理controller
 *
 * @author oo w
 * @version 1.0
 * @since 2023/10/8 10:25
 */
@RestController
@RequestMapping("/address")
public class AddressController implements CrudController<Address, Long> {

    @Resource
    private AddressService addressService;

    /**
     * 获取数据库查询service
     *
     * @return 数据库查询service
     */
    @Override
    public CrudService<Address, Long> dbBaseService() {
        return addressService;
    }

    /**
     * 省级地址查询
     *
     * @return 省级地址
     */
    @GetMapping("/province")
    public List<Address> getProvince() {
        return addressService.getProvince();
    }

    /**
     * 通过父id查询地址
     *
     * @param parentId 父id
     * @return 地址集合
     */
    @GetMapping("/children/{parentId}")
    public List<Address> getByParentId(@PathVariable("parentId") Long parentId) {
        return addressService.getByParentId(parentId);
    }

    /**
     * 通过地址名称模糊查询
     *
     * @param name 地址名称
     * @return 地址集合
     */
    @GetMapping("/like/name/{name}")
    public List<Address> getByLikeName(@PathVariable("name") String name) {
        return addressService.getByLikeName(name);
    }

}
