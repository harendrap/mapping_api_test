package com.nttdata.agni.domain;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/*
 * @author Harendra
 */
@Entity
@Table(name = "mapping_master")
@Data
@NoArgsConstructor
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
 
    String mappingType; 
    
    String messageType;
    
    String bundleType ;
    
    String referenceUrlOptions;
    
    String resourceCreationRules;
    
    String additionalNotes;
    
    String baseProfileTypeId;
 
    
}



