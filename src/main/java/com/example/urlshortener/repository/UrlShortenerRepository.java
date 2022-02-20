package com.example.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for
 * {@link com.example.urlshortener.repository.UrlMappingEntity}.
 * 
 * @author Thomas
 *
 */
@Repository
public interface UrlShortenerRepository extends JpaRepository<UrlMappingEntity, Long>{

}
