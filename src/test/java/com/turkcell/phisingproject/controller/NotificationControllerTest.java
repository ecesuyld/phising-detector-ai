package com.turkcell.phisingproject.controller;

import com.turkcell.phisingproject.data.AccessCheckResult;
import com.turkcell.phisingproject.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationControllerTest {
    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    void getNotificationHistory_shouldReturnResults() {
        List<AccessCheckResult> mockResults = Arrays.asList(new AccessCheckResult(), new AccessCheckResult());
        when(notificationRepository.findAll()).thenReturn(mockResults);

        ResponseEntity<List<AccessCheckResult>> response = notificationController.getNotificationHistory();
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }
}
