package com.turkcell.phisingproject.service;

import com.turkcell.phisingproject.exception.DataAccessException;
import com.turkcell.phisingproject.repository.UrlDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DataProcessServiceTest {
    @Mock
    private UrlDataRepository repository;
    @InjectMocks
    private DataProcessService dataProcessService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessData_Success() {
        doAnswer(invocation -> {
            RowCallbackHandler handler = invocation.getArgument(0);
            java.sql.ResultSet rs1 = mock(java.sql.ResultSet.class);
            when(rs1.toString()).thenReturn("url1");
            java.sql.ResultSet rs2 = mock(java.sql.ResultSet.class);
            when(rs2.toString()).thenReturn("url2");
            handler.processRow(rs1);
            handler.processRow(rs2);
            return null;
        }).when(repository).streamAndCombineRecords(any());
        List<String> result = dataProcessService.processData();
        assertEquals(2, result.size());
    }

    @Test
    void testProcessData_Empty() {
        doNothing().when(repository).streamAndCombineRecords(any());
        assertThrows(DataAccessException.class, () -> dataProcessService.processData());
    }
}
