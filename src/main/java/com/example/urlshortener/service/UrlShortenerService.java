package com.example.urlshortener.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.urlshortener.repository.UrlMappingEntity;

@Service
public interface UrlShortenerService {

	public Collection<UrlMappingEntity> loadAllMappings();

	public Optional<UrlMappingEntity> loadMappingByUrlShort(String string, boolean incrementCounter);

	public boolean storeNewMapping(UrlMappingEntity mapping);

}
