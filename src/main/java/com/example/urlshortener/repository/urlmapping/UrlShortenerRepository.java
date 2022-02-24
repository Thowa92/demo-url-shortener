package com.example.urlshortener.repository.urlmapping;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for
 * {@link com.example.urlshortener.repository.urlmapping.UrlMappingEntity}.
 * 
 * @author Thomas
 *
 */
@Repository
public interface UrlShortenerRepository extends JpaRepository<UrlMappingEntity, Long>{
	public Optional<UrlMappingEntity> findUrlMappingByUrlShort( String urlShort );
}
