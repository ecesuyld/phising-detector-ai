package com.turkcell.phisingproject.service;

import com.turkcell.phisingproject.exception.FileCreationException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileCreationServiceTest {
    @Mock
    private DataProcessService dataProcessService;
    @InjectMocks
    private FileCreationService fileCreationService;

    private String tempFilePath;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        File tempFile = File.createTempFile("test", ".csv");
        tempFilePath = tempFile.getAbsolutePath();
        ReflectionTestUtils.setField(fileCreationService, "outputFilePath", tempFilePath);
    }

    @Test
    void testCreateCsvFile_Success() throws IOException {
        List<String> records = Arrays.asList("url1", "url2");
        when(dataProcessService.processData()).thenReturn(records);
        fileCreationService.createCsvFile();
        List<String> lines = Files.readAllLines(new File(tempFilePath).toPath());
        assertTrue(lines.contains("url1"));
        assertTrue(lines.contains("url2"));
    }

    @Test
    void testCreateCsvFile_Failure() {
        when(dataProcessService.processData()).thenThrow(new FileCreationException("error"));
        assertThrows(FileCreationException.class, () -> fileCreationService.createCsvFile());
    }
}

