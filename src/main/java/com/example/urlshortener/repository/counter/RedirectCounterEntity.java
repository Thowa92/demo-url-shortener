package com.example.urlshortener.repository.counter;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.urlshortener.repository.urlmapping.UrlMappingEntity;

/**
 * Tracks the redirects including a timestamp to analyze the traffic.
 * Multiple entries can belong to one {@link com.example.urlshortener.repository.urlmapping.UrlMappingEntity}.
 * 
 * @author Thomas
 *
 */
@Entity
@Table(name = "URL_MAPPINGS_REDIRECT_COUNTER")
public class RedirectCounterEntity {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	Long id;
	
    @ManyToOne
    @JoinColumn(name="URL_MAPPING_ID", nullable=false)
	UrlMappingEntity urlMapping;
	
	@Column( name = "COUNTER" )
	int counter;
	
	@Column( name = "REDIRECT_DATE" )
	Date redirectDate;
	
	public RedirectCounterEntity()
	{
		// for Hibernate
	}

	public RedirectCounterEntity(UrlMappingEntity urlMapping, int counter, Date redirectDate) {
		super();
		this.urlMapping = urlMapping;
		this.counter = counter;
		this.redirectDate = redirectDate;
	}

	@Override
	public String toString() {
		return "ID: " + id + " UrlMappingId: " + urlMapping.getId() + " Counter: " + counter + " Redirect Date: " + redirectDate;
	}
	
	public UrlMappingEntity getUrlMapping() {
		return urlMapping;
	}

	public void setUrlMapping(UrlMappingEntity urlMapping) {
		this.urlMapping = urlMapping;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public Date getRedirectDate() {
		return redirectDate;
	}

	public void setRedirectDate(Date redirectDate) {
		this.redirectDate = redirectDate;
	}

	public Long getId() {
		return id;
	}
}
