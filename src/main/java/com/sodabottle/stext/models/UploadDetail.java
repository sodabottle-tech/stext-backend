package com.sodabottle.stext.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "upload_details")
@NoArgsConstructor
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadDetail {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer pageNo;
    private String shortText;
    
    @Column(name="long_text" , length = 65535, columnDefinition="TEXT", nullable=true)
    @Type(type="text")
    private String longText;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="upload_id", nullable=false)
    private Upload upload;

	public UploadDetail(Long id, Integer pageNo, String shortText) {
		this.id = id;
		this.pageNo = pageNo;
		this.shortText = shortText;
	}
    
}
