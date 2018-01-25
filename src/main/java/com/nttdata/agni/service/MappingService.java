package com.nttdata.agni.service;


import com.nttdata.agni.domain.MappingList;
import com.nttdata.agni.domain.User;
import com.nttdata.agni.dao.jpa.MappingListRepository;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
public class MappingService {

    private static final Logger log = LoggerFactory.getLogger(MappingService.class);



    @Autowired
    private MappingListRepository mappingListRepository; 
 
    @Autowired
    CounterService counterService;

    @Autowired
    GaugeService gaugeService;

    public MappingService() {
    
    }
     
    public void createMapping(String mapname, List<MappingList> mappingList) {
    	// mappingItem2Repository.save(mappingList);
    	for (MappingList entity : mappingList) {
    		entity.setMapname(mapname);
    		mappingListRepository.save(entity);
        }
        
    }

    public List<MappingList> createMapping2(String mapname, List<MappingList> mappingList) {
    	// mappingItem2Repository.save(mappingList);
    	List<MappingList> mappingListReturn = new ArrayList<MappingList>();
    	for (MappingList entity : mappingList) {
    		entity.setMapname(mapname);
    		mappingListReturn.add(mappingListRepository.save(entity));
        }
        return mappingListReturn;
    }

    public List<MappingList> getMapping(String mapname) {
        return mappingListRepository.findByMapname(mapname);
    }
    
	public List<MappingList> findByMapname(String mapname) {
		// TODO Auto-generated method stub
		return mappingListRepository.findByMapname(mapname);
	}

	public List<MappingList> updateMapping(List<MappingList> mappingList) {
        return mappingListRepository.save(mappingList);
    }

    public void deleteByMapname(String name) {
        mappingListRepository.deleteByMapname(name);
    }
    
    public void deleteMapping(List<MappingList> mappingList) {
        mappingListRepository.delete(mappingList);
    }

    public MappingList getById(long id) {
        return mappingListRepository.findOne(id);
    }

    public void updateOne(MappingList mappingOne) {
    	mappingListRepository.save(mappingOne);
    }

    public void deleteById(Long id) {
    	 mappingListRepository.delete(id);
    }
//not working
	public void deleteMappingByIdX(long id) {
		// TODO Auto-generated method stub
		mappingListRepository.deleteById(id);
	}
//not working
	public MappingList getMappingByIdX(long id) {
		// TODO Auto-generated method stub
		return mappingListRepository.findById(id);
	}  
    //http://goo.gl/7fxvVf
    public Page<MappingList> getAllMappings(Integer page, Integer size) {
        Page pageOfMappings = mappingListRepository.findAll(new PageRequest(page, size));
        // example of adding to the /metrics
        if (size > 50) {
            counterService.increment("nttdata.MappingService.getAll.largePayload");
        }
        return pageOfMappings;
    }







    
}


