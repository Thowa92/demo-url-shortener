package com.example.urlshortener.controller;

import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.urlshortener.repository.UrlMappingEntity;
import com.example.urlshortener.service.UrlShortenerService;

/**
 * TODO Forward to new page Store new mapping
 * 
 * @author Thomas
 *
 */
@RestController
public class UrlShortenerController {

	private static final String DOMAIN = "http://localhost:8080/";

	@Autowired
	UrlShortenerService urlShortenerService;

	/**
	 * Provides a JSON containing all available mappings.
	 * 
	 * @return
	 */
	@GetMapping("/allmappings")
	Collection<UrlMappingEntity> loadAllMappings() {
		return urlShortenerService.loadAllMappings();
	}

	/**
	 * Redirects the call.
	 * 
	 * @param urlShortExtension
	 * @return
	 */
	@GetMapping("/{urlShortExtension}")
	ResponseEntity<Object> redirect(@PathVariable String urlShortExtension) {
		// TODO get Domain without constant?
		// TODO only return URL and let the frontend handle the redirect
		URI uri = URI.create(urlShortenerService.loadUrlLongByUrlShort(DOMAIN + urlShortExtension, DOMAIN));
		return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
	}
}
