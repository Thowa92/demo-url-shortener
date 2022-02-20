package com.example.urlshortener.service.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;

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
	 * Maps the short url to the long url or the domain, if the short url does not
	 * exist.
	 */
	@Override
	public String loadUrlLongByUrlShort(String urlShort, String domain) {
		Optional<UrlMappingEntity> urlMapping = urlShortenerRepository.findUrlMappingByUrlShort(urlShort);
		return urlMapping.map(mapping -> mapping.getUrlLong()).orElse(domain);
	}

	/**
	 * Tries to store the mapping.
	 * 
	 * @return boolean true, if successful. false, if mapping existed 
	 */
	@Override
	public boolean storeNewMapping(UrlMappingEntity mapping, String domain) {
		
		// generate short url, if not specified by user
		if( !isShortUrlSpecified(mapping, domain) )
		{
			mapping.setUrlShort( domain + generateRandomString( 10 ) );
		}
		
		Optional<UrlMappingEntity> urlMappingByUrlShort = urlShortenerRepository
				.findUrlMappingByUrlShort(mapping.getUrlShort());

		if (urlMappingByUrlShort.isPresent() && isDomainCorrect(mapping, domain)) {
			return false;
		}
		
		urlShortenerRepository.save( mapping );
		return true;
	}

	private boolean isShortUrlSpecified(UrlMappingEntity mapping, String domain) {
		return mapping.getUrlShort() != null 
				&& !mapping.getUrlShort().isBlank() 
				&& !mapping.getUrlShort().trim().toLowerCase().equals( domain.toLowerCase() );
	}

	private boolean isDomainCorrect(UrlMappingEntity mapping, String domain) {
		return mapping.getUrlShort().toLowerCase().startsWith(domain.toLowerCase());
	}

	private String generateRandomString( int length ) {
	    int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit( length )
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();

	    return generatedString;
	}

}
