package com.refout.trace.common.system.repository;//package com.refout.trace.system.repository;
//
//import com.refout.trace.system.domain.Config;
//import org.springframework.data.r2dbc.repository.Query;
//import org.springframework.data.r2dbc.repository.R2dbcRepository;
//import org.springframework.stereotype.Repository;
//import reactor.core.publisher.Mono;
//
////@Repository
//public interface ConfigRepository extends R2dbcRepository<Config, Long> {
//
//	@Query("select value from ts_config where `key` = :key limit 1")
//	Mono<String> findValueByKey(String key);
//
//}
