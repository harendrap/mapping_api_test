package com.nttdata.agni.domain;

import java.util.regex.Pattern;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 * @author Harendra
 */
@Entity
@Table(name = "mappings")
@Getter @Setter @ToString
public class MappingList {

    @Id
    @GeneratedValue()
    @Column(name="mapping_id")
    private long id;
    
    @Column(name="mapping_name")
    String mapname;
        
	@Column(name="field_name")
    String hl7;
    
    @Column(name="field_path")
    String fhir;

    @Column(name="profile_name")
    String fhirResource;
    
    @Column(name="segment_name")
    String hl7Seg;
    

    public MappingList() {
    }

    public MappingList( String fhir,String hl7) {
        this.hl7 = hl7;
        this.fhir = fhir;
        this.mapname = "default";
        this.fhirResource = fhir.split(Pattern.quote("."))[0];
        this.hl7Seg = hl7.split(Pattern.quote("."))[0];
                
    }
    public MappingList( String fhir,String hl7,String mapname) {
        this.hl7 = hl7;
        this.fhir = fhir;
        this.mapname = mapname;
        this.fhirResource = fhir.split(Pattern.quote("."))[0];
        this.hl7Seg = hl7.split(Pattern.quote("."))[0];
                
    }


 

    
}



