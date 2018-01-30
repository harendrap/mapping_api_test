package com.nttdata.agni.service;


import com.nttdata.agni.domain.MappingDetail;
import com.nttdata.agni.domain.MappingMaster;
import com.nttdata.agni.dao.jpa.MappingMasterRepository;
import com.nttdata.agni.dao.jpa.MappingDetailRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
public class MappingService2 {

    private static final Logger log = LoggerFactory.getLogger(MappingService2.class);



    @Autowired
    private MappingMasterRepository mappingRepository; 
 
    @Autowired
    MappingDetailRepository mappingDetailRepository;

    public MappingService2() {
    
    }
     
    public MappingMaster createMappingMaster(MappingMaster mappingMaster) {
        return mappingRepository.save(mappingMaster);
    }
    
    
    public MappingMaster getMappingMaster(long id) {
        return mappingRepository.findOne(id);
    }

    public void updateMappingMaster(MappingMaster mappingMaster) {
        mappingRepository.save(mappingMaster);
    }

    public void deleteMappingMaster(Long id) {
        mappingRepository.delete(id);
    }

	public List<MappingMaster> getAllMappings() {
		// TODO Auto-generated method stub
		return (List<MappingMaster>) mappingRepository.findAll();
	}
    public MappingDetail getMappingDetail(long id) {
        return mappingDetailRepository.findOne(id);
    }

    public void updateMappingDetail(MappingDetail mappingMaster) {
        mappingDetailRepository.save(mappingMaster);
    }
	public void deleteMappingDetail(Long id) {
		// TODO Auto-generated method stub
		 mappingDetailRepository.delete(id);
	}



    
}


