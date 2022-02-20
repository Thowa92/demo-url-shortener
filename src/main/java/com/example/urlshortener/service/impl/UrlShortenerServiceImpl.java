package com.example.urlshortener.service.impl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.urlshortener.repository.UrlMappingEntity;
import com.example.urlshortener.repository.UrlShortenerRepository;
import com.example.urlshortener.service.UrlShortenerService;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

	@Autowired
	UrlShortenerRepository urlShortenerRepository;

	@Override
	public Collection<UrlMappingEntity> loadAllMappings() {
		return urlShortenerRepository.findAll();
	}

	/**
	 * Maps the short url to the long url or the domain, if the short url does not exist.
	 */
	@Override
	public String loadUrlLongByUrlShort(String urlShort, String domain) {
		Optional<UrlMappingEntity> urlMapping = urlShortenerRepository.findUrlMappingByUrlShort(urlShort);
		return urlMapping.map(mapping -> mapping.getUrlLong()).orElse( domain );
	}

}
