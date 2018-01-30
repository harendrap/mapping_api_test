package com.nttdata.agni.domain;

import java.util.regex.Pattern;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/*
 * @author Harendra
 */
@Entity
@Table(name = "mapping_detail")
@Data
@NoArgsConstructor
public class MappingDetail {

	public MappingDetail( String fhir,String hl7) {
        this.hl7 = hl7;
        this.fhir = fhir;
                
    }


	@Id
    @GeneratedValue()
    private long id;

    
    @ManyToOne()
    @JoinColumn(name = "mapping_master_id")
    @JsonBackReference
    MappingMaster mappingMaster;

        
    String hl7;
    
    String fhir;

    String fhirResource;
    
    String hl7Seg;

    String type;  

    Boolean isRequired;  
    Boolean isRepeating;  
    Boolean isExtension;  
    
    String status;
   
 

    
}



