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
	 * 
	 * Increments the Counter.
	 */
	@Override
	public String loadUrlLongByUrlShort(String urlShort, String domain, boolean incrementCounter) {
		Optional<UrlMappingEntity> urlMapping = urlShortenerRepository.findUrlMappingByUrlShort(urlShort);
		
		if ( incrementCounter )
		{
			// increment, if mapping exists
			urlMapping.ifPresent( mapping -> { 
				mapping.setCounter( mapping.getCounter() + 1 );
				urlShortenerRepository.save( mapping ); 
				});
		}
		
		return urlMapping.map(mapping -> mapping.getUrlLong()).orElse(domain);
	}

	/**
	 * Tries to store the mapping and generates the short url, if not specified.
	 * 
	 * @return boolean true, if successful. false, if mapping existed 
	 */
	@Override
	public boolean storeNewMapping(UrlMappingEntity mapping, String domain) {
		
		// generate short url, if not specified by user
		if( !isShortUrlSpecified(mapping, domain) )
		{
			mapping.setUrlShort( generateUniqueShortUrl(domain) );
		}
		
		// check uniqueness
		Optional<UrlMappingEntity> urlMappingByUrlShort = urlShortenerRepository
				.findUrlMappingByUrlShort(mapping.getUrlShort());

		if (urlMappingByUrlShort.isPresent() && isDomainCorrect(mapping, domain)) {
			return false;
		}
		
		urlShortenerRepository.save( mapping );
		return true;
	}

	/**
	 * Generates a unique short urls with 10 characters after the domain.
	 * This method generates new urls, until one new url was generated. 
	 * 
	 * @param domain
	 * @return
	 */
	private String generateUniqueShortUrl(String domain) {
		String generatedShortUrl = "";
		boolean newShortUrlGenerated = false;
		while( !newShortUrlGenerated )
		{
			generatedShortUrl = domain + generateRandomString( 10 );
			newShortUrlGenerated = urlShortenerRepository.findUrlMappingByUrlShort(generatedShortUrl).isEmpty();
		}
		return generatedShortUrl;
	}

	/**
	 * Check if short Url was set by the user.
	 * No, empty or only domain count as not specified.
	 * 
	 * @param mapping
	 * @param domain
	 * @return
	 */
	private boolean isShortUrlSpecified(UrlMappingEntity mapping, String domain) {
		return mapping.getUrlShort() != null 
				&& !mapping.getUrlShort().isBlank() 
				&& !mapping.getUrlShort().trim().toLowerCase().equals( domain.toLowerCase() );
	}

	/**
	 * Checks, if the short url starts with the correct domain.
	 * 
	 * @param mapping
	 * @param domain
	 * @return
	 */
	private boolean isDomainCorrect(UrlMappingEntity mapping, String domain) {
		return mapping.getUrlShort().toLowerCase().startsWith(domain.toLowerCase());
	}

	/**
	 * Generates a random string of length 10 (https://www.baeldung.com/java-random-string).
	 * 
	 * @param length
	 * @return
	 */
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
