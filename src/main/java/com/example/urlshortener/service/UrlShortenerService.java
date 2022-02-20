package com.example.urlshortener.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.example.urlshortener.repository.UrlMappingEntity;

@Service
public interface UrlShortenerService {

	public Collection<UrlMappingEntity> loadAllMappings();

	public String loadUrlLongByUrlShort(String string, String domain);

}
