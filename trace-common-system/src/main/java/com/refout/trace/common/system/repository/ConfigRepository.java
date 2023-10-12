package com.refout.trace.common.system.repository;

import com.refout.trace.common.system.domain.Config;
import com.refout.trace.datasource.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * 配置信息仓库接口，用于访问Config表。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/12 23:33
 */
@Component
@Repository
public interface ConfigRepository extends BaseRepository<Config, Long> {

    /**
     * 根据名称和应用程序查询Config表中的value字段，并按照app字段的值进行排序，取第一条结果。
     * 如果app字段的值为 'common' ，则表示公共配置。
     *
     * @param name 配置名称
     * @param app  应用程序名称
     * @return 查询到的value字段值
     */
    @Query(value = """
            select value
            from ts_config
            where name = ?1
              and (app = ?2 or app = 'common')
            order by field(app, 'common')
            limit 1
            """, nativeQuery = true)
    String findValueByNameAndAppWithCommon(String name, String app);

    /**
     * 根据名称和应用程序查询Config表中是否存在记录。
     *
     * @param name 配置名称
     * @param app  应用程序名称
     * @return 记录存在时返回1，否则返回0
     */
    @Query(value = """
             select count(0)
                        from ts_config
                        where name = ?1
                          and (app = ?2 or app = 'common')
            """, nativeQuery = true)
    int exist(String name, String app);

}