package com.example.urlshortener.repository.counter;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for
 * {@link com.example.urlshortener.repository.counter.RedirectCounterEntity}.
 * 
 * @author Thomas
 *
 */
public interface RedirectCounterRepository extends JpaRepository<RedirectCounterEntity, Long>{

}

