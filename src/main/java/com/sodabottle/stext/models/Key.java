package com.sodabottle.stext.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "api_key")
@NoArgsConstructor
@Data
@ToString
public class Key {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String apiName;
	private String apiKey;
	private String apiVersion;
	private Integer currentCount;	
}