package com.example.urlshortener.service.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.urlshortener.repository.urlmapping.UrlMappingEntity;
import com.example.urlshortener.repository.urlmapping.UrlShortenerRepository;
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
	 * Maps the short URL to the long URL.
	 * Increments the Counter.
	 */
	@Override
	public Optional<UrlMappingEntity> loadMappingByUrlShort(String urlShort, boolean incrementCounter) {
		Optional<UrlMappingEntity> urlMapping = urlShortenerRepository.findUrlMappingByUrlShort(urlShort);

		if (incrementCounter) {
			// increment, if mapping exists
			urlMapping.ifPresent(mapping -> {
				mapping.setCounter(mapping.getCounter() + 1);
				urlShortenerRepository.save(mapping);
			});
		}

		return urlMapping;
	}

	/**
	 * Tries to store the mapping and generates the short URL, if not specified.
	 * 
	 * @return boolean true, if successful. false, if mapping existed
	 */
	@Override
	public boolean storeNewMapping(UrlMappingEntity mapping) {

		// generate short URL, if not specified by user
		if (!isShortUrlSpecified(mapping)) {
			mapping.setUrlShort(generateUniqueShortUrl());
		}

		// check uniqueness
		Optional<UrlMappingEntity> urlMappingByUrlShort = urlShortenerRepository
				.findUrlMappingByUrlShort(mapping.getUrlShort());

		if (urlMappingByUrlShort.isPresent()) {
			return false;
		}

		urlShortenerRepository.save(mapping);
		return true;
	}

	/**
	 * Generates a unique short URLs with 10 characters. This
	 * method generates URLs, until one new URL was generated.
	 * 
	 * @return
	 */
	private String generateUniqueShortUrl() {
		String generatedShortUrl = "";
		boolean newShortUrlGenerated = false;
		while (!newShortUrlGenerated) {
			generatedShortUrl = generateRandomString(10);
			newShortUrlGenerated = urlShortenerRepository.findUrlMappingByUrlShort(generatedShortUrl).isEmpty();
		}
		return generatedShortUrl;
	}

	/**
	 * Check if short URL was set by the user (non null and non empty).
	 * 
	 * @param mapping
	 * @return
	 */
	private boolean isShortUrlSpecified(UrlMappingEntity mapping) {
		return mapping.getUrlShort() != null && !mapping.getUrlShort().isBlank();
	}

	/**
	 * Generates a random string of length 10
	 * (https://www.baeldung.com/java-random-string).
	 * 
	 * @param length
	 * @return
	 */
	private String generateRandomString(int length) {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(length)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

}
