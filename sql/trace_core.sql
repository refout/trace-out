drop table if exists tb_producer;
create table tb_producer
(
    id          bigint               not null comment '主键id' primary key,
    code        varchar(100)         not null comment '编号',
    name        varchar(128)         not null comment '名称',
    user_id     bigint               not null comment '关联的用户id',
    remark      varchar(500)         null comment '备注',
    create_time datetime             not null comment '创建时间',
    create_by   varchar(20)          not null comment '创建人',
    update_time datetime             null comment '更新时间',
    update_by   varchar(20)          null comment '更新人',
    deleted     tinyint(1) default 0 not null comment '逻辑删除（0：未删除；1：已删除）'
) comment '生产商信息';

drop table if exists tb_product;
create table tb_product
(
    id          bigint               not null comment '主键id' primary key,
    producer_id bigint               not null comment '关联的生产商id',
    code        varchar(100)         not null comment '编号',
    name        varchar(128)         not null comment '名称',
    category    varchar(100)         not null comment '分类',
    location    geometry             not null comment '位置',
    remark      varchar(500)         null comment '备注',
    create_time datetime             not null comment '创建时间',
    create_by   varchar(20)          not null comment '创建人',
    update_time datetime             null comment '更新时间',
    update_by   varchar(20)          null comment '更新人',
    deleted     tinyint(1) default 0 not null comment '逻辑删除（0：未删除；1：已删除）'
) comment '生产商产品信息';

drop table if exists tb_trace_production_obj;
create table tb_trace_production_obj
(
    id          bigint               not null comment '主键id' primary key,
    producer_id bigint               not null comment '关联的生产商id',
    product_id  bigint               not null comment '关联的生产商产品id',
    code        varchar(100)         not null comment '编号',
    name        varchar(128)         not null comment '名称',
    remark      varchar(500)         null comment '备注',
    create_time datetime             not null comment '创建时间',
    create_by   varchar(20)          not null comment '创建人',
    update_time datetime             null comment '更新时间',
    update_by   varchar(20)          null comment '更新人',
    deleted     tinyint(1) default 0 not null comment '逻辑删除（0：未删除；1：已删除）'
) comment '产品对象溯源';

drop table if exists tb_trace_production_line;
create table tb_trace_production_line
(
    id               bigint               not null comment '主键id' primary key,
    producer_id      bigint               not null comment '关联的生产商id',
    product_id       bigint               not null comment '关联的生产商产品id',
    production_id    bigint               not null comment '关联的生产商产品生产id',
    code             varchar(100)         not null comment '编号',
    name             varchar(128)         not null comment '名称',
    event_start_time datetime             not null comment '事件发生开始时间',
    event_end_time   datetime             not null comment '事件发生结束时间',
    event_category   varchar(20)          not null comment '事件分类',
    event_content    varchar(500)         not null comment '事件内容',
    remark           varchar(500)         null comment '备注',
    create_time      datetime             not null comment '创建时间',
    create_by        varchar(20)          not null comment '创建人',
    update_time      datetime             null comment '更新时间',
    update_by        varchar(20)          null comment '更新人',
    deleted          tinyint(1) default 0 not null comment '逻辑删除（0：未删除；1：已删除）'
) comment '产品对象溯源';

drop table if exists tb_trace_sale_obj;
create table tb_trace_sale_obj
(
    id                   bigint               not null comment '主键id'
        primary key,
    code                 varchar(100)         not null comment '编号',
    name                 varchar(128)         not null comment '名称',
    producer_id          bigint               not null comment '关联的生产商id',
    product_id           bigint               not null comment '关联的生产商产品id',
    production_id        bigint               not null comment '关联的生产商产品生产id',
    batch_num            VARCHAR(20)          not null comment '批次号',
    specification        VARCHAR(20)          not null comment '规格',
    quality_check_result VARCHAR(20)          not null comment '质量检测结果',
    seller               varchar(100)         not null comment '销售商',
    sale_date            datetime             not null comment '销售日期',
    remark               varchar(500)         null comment '备注',
    create_time          datetime             not null comment '创建时间',
    create_by            varchar(20)          not null comment '创建人',
    update_time          datetime             null comment '更新时间',
    update_by            varchar(20)          null comment '更新人',
    deleted              tinyint(1) default 0 not null comment '逻辑删除（0：未删除；1：已删除）'
) comment '商品对象溯源';
