package com.refout.trace.common.system.domain;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * TODO
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/26 23:10
 */
@Entity(name = "ts_role_menu")
@Table(name = "ts_role_menu", schema = "trace")
public class RoleMenu {

    @Id
    @Column(name = "role_id")
    private long roleId;


    @Id
    @Column(name = "menu_id")
    private long menuId;

}
