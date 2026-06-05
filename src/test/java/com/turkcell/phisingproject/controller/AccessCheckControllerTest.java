package com.turkcell.phisingproject.controller;

import com.turkcell.phisingproject.data.AccessCheckResult;
import com.turkcell.phisingproject.service.AccessCheckService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccessCheckControllerTest {
    @Mock
    private AccessCheckService accessCheckService;

    @InjectMocks
    private AccessCheckController accessCheckController;


    @Test
    void runAccessCheck_shouldReturnResults() {
        List<AccessCheckResult> mockResults = Arrays.asList(new AccessCheckResult(), new AccessCheckResult());
        when(accessCheckService.checkUrlAccessibility()).thenReturn(mockResults);

        ResponseEntity<List<AccessCheckResult>> response = accessCheckController.runAccessCheck();
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void checkSingleUrl_shouldReturnResult() {
        AccessCheckResult mockResult = new AccessCheckResult();
        when(accessCheckService.performAccessCheck("http://test.com")).thenReturn(mockResult);

        ResponseEntity<AccessCheckResult> response = accessCheckController.checkSingleUrl("http://test.com");
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(mockResult, response.getBody());
    }
}
