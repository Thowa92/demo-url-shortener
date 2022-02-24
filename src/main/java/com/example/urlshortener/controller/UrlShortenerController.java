package com.example.urlshortener.controller;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.urlshortener.repository.urlmapping.UrlMappingEntity;
import com.example.urlshortener.service.UrlShortenerService;

/**
 * @author Thomas
 *
 */
@RestController
public class UrlShortenerController {

	@Autowired
	UrlShortenerService urlShortenerService;

	/**
	 * Provides a JSON containing all available mappings.
	 * 
	 * @return
	 */
	@CrossOrigin
	@GetMapping("/allmappings")
	Collection<UrlMappingEntity> loadAllMappings() {
		return urlShortenerService.loadAllMappings();
	}

	/**
	 * Tries to redirect the call.
	 * 
	 * @param urlShortExtension
	 * @return
	 */
	@GetMapping("/{urlShortExtension}")
	ResponseEntity<Object> redirect(@PathVariable String urlShortExtension) {
		Optional<UrlMappingEntity> urlMappingEntity = urlShortenerService.loadMappingByUrlShort(urlShortExtension,
				true);

		return urlMappingEntity.map(
				mapping -> ResponseEntity.status(HttpStatus.FOUND).location(URI.create(mapping.getUrlLong())).build())
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("Was not able to redirect. Url mapping does not exist."));
	}

	/**
	 * Tries to store new mapping.
	 * 
	 * @param mapping
	 * @return
	 */
	@CrossOrigin
	@PostMapping("/newmapping")
	ResponseEntity<String> storeNewMapping(@RequestBody UrlMappingEntity mapping) {
		if (urlShortenerService.storeNewMapping(mapping)) {
			return new ResponseEntity<String>(HttpStatus.ACCEPTED);
		}

		return new ResponseEntity<String>("Could not store mapping. Make sure that the shortened URL is not in use.",
				HttpStatus.CONFLICT);
	}

}
