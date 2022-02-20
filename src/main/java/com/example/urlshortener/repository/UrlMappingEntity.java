package com.example.urlshortener.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Contains all relevant information for a Url mapping.
 * 
 * @author Thomas
 */
@Entity
@Table(name = "URL_MAPPINGS")
public class UrlMappingEntity {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	Long id;
	
	@Column( name = "URL_SHORT" )
	String urlShort;
	
	@Column( name = "URL_LONG" )
	String urlLong;
	
	@Column( name = "COUNTER" )
	int counter;

	public UrlMappingEntity()
	{
		// for Hibernate
	}
	
	public UrlMappingEntity(String urlShort, String urlLong) {
		super();
		this.urlShort = urlShort;
		this.urlLong = urlLong;
		counter = 0;
	}
	
	@Override
	public String toString() {
		return "urlShort: " + urlShort + " urlLong: " + urlLong + " id: " + id + " counter: " + counter;
	}

	public String getUrlShort() {
		return urlShort;
	}

	public void setUrlShort(String urlShort) {
		this.urlShort = urlShort;
	}

	public Long getId() {
		return id;
	}

	public String getUrlLong() {
		return urlLong;
	}

	public int getCounter() {
		return counter;
	}
	
	public int setCounter( int counter ) {
		return this.counter = counter;
	}

}
