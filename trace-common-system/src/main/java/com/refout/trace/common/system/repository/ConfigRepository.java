package com.refout.trace.common.system.repository;

import com.refout.trace.common.system.domain.Config;
import com.refout.trace.datasource.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends BaseRepository<Config, Long> {

    @Query(value = """
            select value
            from ts_config
            where name = ?1
              and (app = ?2 or app = 'common')
            order by field(app, 'common')
            limit 1
            """,
            nativeQuery = true)
    String findValueByNameAndAppWithCommon(String name, String app);

    @Query(value = """
             select count(0)
                        from ts_config
                        where name = ?1
                          and (app = ?2 or app = 'common')
            """,
            nativeQuery = true)
    int exist(String name, String app);

}
