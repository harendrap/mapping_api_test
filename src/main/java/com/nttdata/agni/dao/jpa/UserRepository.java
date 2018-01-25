package com.nttdata.agni.dao.jpa;

import com.nttdata.agni.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository can be used to delegate CRUD operations against the data source: http://goo.gl/P1J8QH
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findUserByCity(String city);
    Page findAll(Pageable pageable);
	
}
