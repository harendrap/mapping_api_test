package com.nttdata.agni.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/*
 * @author Harendra
 */
@Entity
@Table(name = "mapping_master")
@Data @NoArgsConstructor @RequiredArgsConstructor
public class MappingMaster {


	@Id
    @GeneratedValue()
    private long id;
    
	@NonNull
    String name;

 
    @OneToMany(mappedBy = "mappingMaster", cascade = CascadeType.ALL,orphanRemoval=true, targetEntity=MappingDetail.class)
    @Column(nullable = true)
    @JsonManagedReference
    List <MappingDetail> mappingDetail;
    

    String status;
    

    String resources;
    
 
    
}



