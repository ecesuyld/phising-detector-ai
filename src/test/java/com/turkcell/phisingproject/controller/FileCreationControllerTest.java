package com.turkcell.phisingproject.controller;

import com.turkcell.phisingproject.service.FileCreationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileCreationControllerTest {
    @Mock
    private FileCreationService fileCreationService;

    @InjectMocks
    private FileCreationController fileCreationController;

    @Test
    void exportCsv_shouldReturnSuccessMessage() {
        doNothing().when(fileCreationService).createCsvFile();
        String result = fileCreationController.exportCsv();
        assertTrue(result.contains("CSV file is created successfully:"));
    }
}

