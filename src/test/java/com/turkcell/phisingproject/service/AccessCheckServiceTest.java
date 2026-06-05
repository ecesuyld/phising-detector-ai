package com.turkcell.phisingproject.service;

import com.turkcell.phisingproject.data.AccessCheckResult;
import com.turkcell.phisingproject.repository.AccessCheckRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccessCheckServiceTest {
    @Mock
    private AccessCheckRepository accessCheckRepository;
    @Mock
    private NotificationService notificationService;
    @InjectMocks
    private AccessCheckService accessCheckService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(accessCheckService, "connectionTimeout", 1000);
        ReflectionTestUtils.setField(accessCheckService, "readTimeout", 1000);
    }

    @Test
    void testCheckUrlAccessibility() {
        List<String> urls = Arrays.asList("http://example.com", "http://notfound.com");
        when(accessCheckRepository.findAllUrls()).thenReturn(urls);
        doNothing().when(notificationService).notifyAccessibleUrl(any());
        List<AccessCheckResult> results = accessCheckService.checkUrlAccessibility();
        assertEquals(2, results.size());
    }

    @Test
    void testPerformAccessCheck_InvalidUrl() {
        AccessCheckResult result = accessCheckService.performAccessCheck("invalid-url");
        assertFalse(result.isAccessible());
        assertEquals(-1, result.getHttpStatusCode());
    }
}
