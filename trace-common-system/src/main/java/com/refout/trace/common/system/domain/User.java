package com.refout.trace.common.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refout.trace.common.enums.StateEnum;
import com.refout.trace.datasource.convert.enums.StateEnumConverter;
import com.refout.trace.datasource.domain.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Table(name = "ts_user")
@Entity
public class User extends AbstractEntity {

    /**
     * 用户的用户名。
     */
    @Column(name = "username")
    private String username;

    /**
     * 用户的密码。
     */
    @Column(name = "password")
    private String password;

    /**
     * 用户的昵称。
     */
    @Column(name = "nickname")
    private String nickname;

    /**
     * 用户的电子邮件地址。
     */
    @Column(name = "email")
    private String email;

    /**
     * 用户的手机号码。
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 用户的性别。 (0: 男性; 1: 女性; 2: 未知)
     */
    @Column(name = "gender")
    private String gender;

    /**
     * 用户头像的URL地址。
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 用户的状态。 (0: 正常; 1: 停用)
     */
    @Convert(converter = StateEnumConverter.class)
    @Column(name = "state", columnDefinition = "char(1) default '0'", insertable = false)
    private StateEnum state;

    @JsonIgnore
    public boolean isAdmin() {
        return Long.valueOf(1L).equals(getId());
    }

}
