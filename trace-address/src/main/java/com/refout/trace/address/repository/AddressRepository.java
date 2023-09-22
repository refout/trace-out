package com.refout.trace.address.repository;

import com.refout.trace.address.domain.Address;
import com.refout.trace.datasource.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends BaseRepository<Address, Long> {

}
