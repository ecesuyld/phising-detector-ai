package com.turkcell.phisingproject.repository;

import com.turkcell.phisingproject.data.AccessCheckResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class NotificationRepository {

    private static final Logger log = LoggerFactory.getLogger(NotificationRepository.class);

    private final List<AccessCheckResult> notificationHistory = new CopyOnWriteArrayList<>();

    public void saveNotification(AccessCheckResult result) {
        notificationHistory.add(result);
        log.debug("Notification saved for URL: {}", result.getUrl());
    }

    public List<AccessCheckResult> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(notificationHistory));
    }
}

