package com.turkcell.phisingproject.service;

import com.turkcell.phisingproject.data.AccessCheckResult;
import com.turkcell.phisingproject.repository.AccessCheckRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessCheckService {

    private static final Logger log = LoggerFactory.getLogger(AccessCheckService.class);

    private final AccessCheckRepository accessCheckRepository;
    private final NotificationService notificationService;

    @Value("${access-check.connection-timeout}")
    private int connectionTimeout;

    @Value("${access-check.read-timeout}")
    private int readTimeout;

    @Scheduled(fixedRateString = "${access-check.scheduler.fixed-rate}")
    public List<AccessCheckResult> checkUrlAccessibility() {
        log.info("Starting scheduled URL accessibility check...");
        List<String> urls = accessCheckRepository.findAllUrls();
        List<AccessCheckResult> results = new ArrayList<>();
        int accessibleCount = 0;

        for (String url : urls) {
            AccessCheckResult result = performAccessCheck(url);
            results.add(result);
            if (result.isAccessible()) {
                accessibleCount++;
                notificationService.notifyAccessibleUrl(result);
            }
        }

        log.info("URL accessibility check completed. Total: {}, Accessible: {}", urls.size(), accessibleCount);
        return results;
    }

    public AccessCheckResult performAccessCheck(String urlString) {
        int statusCode = -1;
        boolean accessible = false;

        try {
            String fullUrl = urlString.startsWith("http") ? urlString : "http://" + urlString;
            URL url = new URL(fullUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(connectionTimeout);
            connection.setReadTimeout(readTimeout);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("User-Agent", "PhishingAccessChecker/1.0");

            statusCode = connection.getResponseCode();
            accessible = statusCode >= 200 && statusCode < 400;
            connection.disconnect();
        } catch (Exception e) {
            log.debug("URL not accessible: {} - {}", urlString, e.getMessage());
        }

        return AccessCheckResult.builder()
                .url(urlString)
                .accessible(accessible)
                .httpStatusCode(statusCode)
                .checkedAt(LocalDateTime.now())
                .build();
    }
}

