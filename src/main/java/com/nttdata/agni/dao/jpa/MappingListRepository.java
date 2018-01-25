
package com.nttdata.agni.dao.jpa;



import com.nttdata.agni.domain.MappingList;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository can be used to delegate CRUD operations against the data source: http://goo.gl/P1J8QH
 */
public interface MappingListRepository extends JpaRepository<MappingList, Long> {
	
    Page findAll(Pageable pageable);
    //MappingList save(List<MappingList> mappingitem2);
    
    List<MappingList> findByMapname(String mapname);

	void deleteByMapname(String name);

	void deleteById(long id);

	MappingList findById(long id);
}


