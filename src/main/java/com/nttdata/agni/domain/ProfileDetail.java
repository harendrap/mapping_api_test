package com.nttdata.agni.domain;

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
@Table(name = "profile_detail")
@Data
@NoArgsConstructor 
@RequiredArgsConstructor
public class ProfileDetail {

	@Id
    @GeneratedValue()
    @Column(name="id")
    private long id;

    
    @ManyToOne()
    @JoinColumn(name = "profile_master_id")
    @JsonBackReference
    ProfileMaster profileMaster;
    
    @NonNull
    String resourceName;

    String fieldName;
    
    String type;  
    
    int min;
    
    int max;
    
    String staticValue;
    
    String description;

    Boolean isRequired;  

    Boolean isRepeating;  
    
    Boolean isExtension;  
    
    String status;
    
    Boolean isActive; 
}



