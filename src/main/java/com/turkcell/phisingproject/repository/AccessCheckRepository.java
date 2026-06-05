package com.turkcell.phisingproject.repository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccessCheckRepository {

    private static final Logger log = LoggerFactory.getLogger(AccessCheckRepository.class);

    public List<String> findAllUrls() {
        List<String> urls = new ArrayList<>();
        try {
            ClassPathResource resource = new ClassPathResource("csv/phishing_candidates.csv");
            Reader reader = new InputStreamReader(resource.getInputStream());
            CSVParser parser = CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build()
                    .parse(reader);

            for (CSVRecord records : parser) {
                String url = records.get("url").trim();
                if (!url.isEmpty()) {
                    urls.add(url);
                }
            }
            parser.close();
        } catch (Exception e) {
            log.error("Error reading phishing candidates CSV: {}", e.getMessage(), e);
        }
        return urls;
    }
}

