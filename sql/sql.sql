drop schema if exists trace;
create schema trace collate utf8mb4_0900_ai_ci;

use trace;

drop table if exists ts_user;
create table ts_user
(
    id          bigint(20)             not null comment '主键id'
        primary key,
    username    varchar(20)            not null comment '用户名',
    password    varchar(128)           not null comment '密码',
    nickname    varchar(20)            not null comment '昵称',
    email       varchar(128)           null comment '邮箱',
    phone       varchar(20)            not null comment '手机号',
    gender      char(1)    default '2' null comment '性别（0：男；1：女；2：未知）',
    avatar      varchar(100)           null comment '头像地址',
    user_type   char(1)    default '1' not null comment '用户类型（0：管理员；1：普通用户）',
    state       char(1)    default '0' not null comment '状态（0：正常；1：停用）',
    create_time datetime               not null comment '创建时间',
    create_by   varchar(20)            not null comment '创建人',
    update_time datetime               null comment '更新时间',
    update_by   varchar(20)            null comment '更新人',
    deleted     tinyint(1) default 0   not null comment '逻辑删除（0：未删除；1：已删除）'
)    comment '用户信息';

INSERT INTO ts_user (id, username, password, nickname, email, phone, gender, avatar, user_type, state, create_time, create_by, update_time, update_by, deleted)
VALUES (1, 'admin', '$2a$10$QqJlLWbLfKx2JvQOi7.iVuiSOHt0YmUMxP7U4ikrNx8qDDLKW27zy', '管理员', null, '18000000000', '2', null, '0', '0', sysdate(), 'admin', null, null, 0);

drop table if exists ts_role;
create table ts_role
(
    id          bigint(20)             not null comment '主键id'
        primary key,
    role_name   varchar(30)            not null comment '角色名称',
    state       char(1)    default '0' not null comment '状态（0：正常；1：停用）',
    remark      varchar(500)           null comment '备注',
    create_time datetime               not null comment '创建时间',
    create_by   varchar(20)            not null comment '创建人',
    update_time datetime               null comment '更新时间',
    update_by   varchar(20)            null comment '更新人',
    deleted     tinyint(1) default 0   not null comment '逻辑删除（0：未删除；1：已删除）'
) comment '角色信息';

drop table if exists ts_user_role;
create table ts_user_role
(
    user_id bigint(20) not null comment '用户id',
    role_id bigint(20) not null comment '角色id',
    primary key (user_id, role_id)
) comment '用户和角色关联信息';

drop table if exists ts_api;
create table ts_api
(
    id          bigint(20)             not null comment '主键id'
        primary key,
    api_name    varchar(50)            not null comment '接口名称',
    path        varchar(200) default '' comment '路由地址',
    state       char(1)      default 0 comment '接口状态（0正常 1停用）',
    permission  varchar(100) default null comment '权限标识',
    remark      varchar(500) default '' comment '备注',
    create_time datetime               not null comment '创建时间',
    create_by   varchar(20)            not null comment '创建人',
    update_time datetime               null comment '更新时间',
    update_by   varchar(20)            null comment '更新人',
    deleted     tinyint(1)   default 0 not null comment '逻辑删除（0：未删除；1：已删除）'
) comment = '接口权限表';

drop table if exists ts_role_api;
create table ts_role_api
(
    role_id bigint(20) not null comment '角色ID',
    api_id bigint(20) not null comment '接口ID',
    primary key (role_id, api_id)
) comment = '角色和接口关联表';

drop table if exists ts_login_log;
create table ts_login_log
(
    id             bigint(20)               not null comment '主键id'
        primary key,
    username       varchar(20)  default ''  null comment '用户账号',
    ip_addr        varchar(64)  default ''  null comment '登录IP地址',
    login_location varchar(255) default ''  null comment '登录地点',
    browser        varchar(50)  default ''  null comment '浏览器类型',
    os             varchar(50)  default ''  null comment '操作系统',
    state          char(1)      default '0' null comment '登录状态（0成功 1失败）',
    msg            varchar(255) default ''  null comment '提示消息',
    create_time    datetime                 null comment '访问时间',
    deleted        tinyint(1)   default 0   not null comment '逻辑删除（1：删除 0：正常）'
) comment '系统访问记录';

drop table if exists ts_config;
create table ts_config
(
    id            bigint(20)                   not null comment '主键id' primary key,
    name          varchar(128)                 not null comment '配置名称',
    value         varchar(512)                 not null comment '配置值',
    belong        varchar(64)                  not null comment '配置所属功能',
    app           varchar(64) default 'common' not null comment '配置所属应用,common表示所有应用可用',
    history_value json                         null comment '历史配置:[{value:1,time:2023-08-08 08:08:08 888}]',
    remark        varchar(512)                 not null comment '备注',
    create_time   datetime                     not null comment '创建时间',
    create_by     varchar(20)                  not null comment '创建人',
    update_time   datetime                     null comment '更新时间',
    update_by     varchar(20)                  null comment '更新人',
    deleted       tinyint(1)  default 0        not null comment '逻辑删除（0：未删除；1：已删除）',
    constraint ts_config_pk unique (name, app)
) comment '配置表';

drop table if exists ts_operation_log;
create table ts_operation_log
(
    id                 bigint(20)               not null comment '主键id'
        primary key,
    title              varchar(50)   default '' null comment '模块标题',
    business_type      int           default 0  null comment '业务类型（0其它 1新增 2修改 3删除）',
    method             varchar(1000) default '' null comment '方法名称',
    request_method     varchar(10)   default '' null comment '请求方式',
    operation_type     int           default 0  null comment '操作类别（0其它 1后台用户 2手机端用户）',
    operation_name     varchar(50)   default '' null comment '操作人员',
    operation_url      varchar(2000) default '' null comment '请求URL',
    operation_ip       varchar(50)   default '' null comment '主机地址',
    operation_location varchar(255)  default '' null comment '操作地点',
    operation_param    varchar(2000) default '' null comment '请求参数',
    json_result        json                     null comment '返回参数',
    state              char(1)       default 0  null comment '操作状态（0正常 1异常）',
    error_msg          varchar(2000) default '' null comment '错误消息',
    create_time        datetime                 null comment '操作时间',
    deleted            tinyint(1)    default 0  not null comment '逻辑删除（1：删除 0：正常）'
) comment '操作日志记录';

drop table if exists tb_express_form;
create table tb_express_form
(
    id                        bigint(20)           not null comment '主键id'
        primary key,
    tenant_id                 bigint(20)           not null comment '租户id',
    sender_name               varchar(20)          not null comment '发件人',
    sender_phone              varchar(20)          not null comment '发件人电话',
    sender_address_province   varchar(10)          not null comment '发件人地址-省',
    sender_address_city       varchar(20)          not null comment '发件人地址-市',
    sender_address_district   varchar(20)          not null comment '发件人地址-区',
    sender_address_detail     varchar(20)          not null comment '发件人地址-详细地址（如***街***号）',
    receiver_address_province varchar(10)          not null comment '发件人地址-省',
    receiver_address_city     varchar(20)          not null comment '发件人地址-市',
    receiver_address_district varchar(20)          not null comment '发件人地址-区',
    receiver_address_detail   varchar(20)          not null comment '发件人地址-详细地址（如***街***号）',
    send_item                 varchar(20)          not null comment '寄件物品名',
    send_weight               decimal(10, 2)       not null comment '寄件物品重量',
    remark                    varchar(200)         null comment '备注',
    create_time               datetime             not null comment '创建时间',
    create_by                 varchar(20)          not null comment '创建人',
    update_time               datetime             null comment '更新时间',
    update_by                 varchar(20)          null comment '更新人',
    deleted                   tinyint(1) default 0 not null comment '逻辑删除（0：未删除；1：已删除）'
) comment '快递单信息';

