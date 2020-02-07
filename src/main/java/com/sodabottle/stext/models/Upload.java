package com.sodabottle.stext.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "upload")
@NoArgsConstructor
@Data
@ToString
public class Upload {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    private String downloadUri;
    private String type;
    private Long size;
    
    @OneToMany(mappedBy="upload")
    @JsonIgnore
    private Set<UploadDetail> uploadDetails;
    
	public Upload(String name, String downloadUri, String type, Long size) {
		this.name = name;
		this.downloadUri = downloadUri;
		this.type = type;
		this.size = size;
	}
}
