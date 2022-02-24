package com.example.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RedirectCounterRepository extends JpaRepository<RedirectCounterEntity, Long>{

}

