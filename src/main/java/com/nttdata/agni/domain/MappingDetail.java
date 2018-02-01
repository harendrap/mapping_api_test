package com.nttdata.agni.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @author Harendra
 */
@Entity
@Table(name = "mapping_detail")
@Data
@NoArgsConstructor
public class MappingDetail {

	public MappingDetail( String fieldName, String hl7Segment , String hl7Field) {
        this.fieldName = fieldName;
        this.hl7Segment = hl7Segment;
        this.hl7Field = hl7Field;
                
    }


	@Id
    @GeneratedValue()
    private long id;

    
    @ManyToOne()
    @JoinColumn(name = "mapping_master_id")
    @JsonBackReference
    MappingMaster mappingMaster;

        
    String hl7Field;
    
    String fieldName;

    String fhirResource;
    
    String hl7Segment;

    String type;  

    Boolean isRequired;  
    Boolean isRepeating;  
    Boolean isExtension;  
    
    String status;
   
 

    
}



