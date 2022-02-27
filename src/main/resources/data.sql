
INSERT INTO URL_MAPPINGS ( url_short, url_long, counter ) 
VALUES ( 'JPA', 'https://spring.io/guides/gs/accessing-data-jpa/', 5 );

INSERT INTO URL_MAPPINGS ( url_short, url_long, counter ) 
VALUES ( 'h2', 'https://www.baeldung.com/spring-boot-h2-database', 0 );

INSERT INTO URL_MAPPINGS ( url_short, url_long, counter ) 
VALUES ( 'howtojava', 'https://howtodoinjava.com/spring-boot/command-line-runner-interface-example/', 0 );


INSERT INTO URL_MAPPINGS_REDIRECT_COUNTER ( URL_MAPPING_ID, COUNTER, REDIRECT_DATE )
VALUES ( 1, 2, DATE_TRUNC('MINUTE', current_timestamp-1 ) );

INSERT INTO URL_MAPPINGS_REDIRECT_COUNTER ( URL_MAPPING_ID, COUNTER, REDIRECT_DATE )
VALUES ( 1, 4, DATE_TRUNC('MINUTE', current_timestamp ) );

INSERT INTO URL_MAPPINGS_REDIRECT_COUNTER ( URL_MAPPING_ID, COUNTER, REDIRECT_DATE )
VALUES ( 3, 4, DATE_TRUNC('MINUTE', current_timestamp-5 ) );