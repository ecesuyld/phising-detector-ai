package com.turkcell.phisingproject.service.impl;

import com.turkcell.phisingproject.exception.FileCreationException;
import com.turkcell.phisingproject.service.DataProcessService;
import com.turkcell.phisingproject.service.FileCreationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class FileCreationServiceImpl implements FileCreationService {

    @Value("${app.csv.output-path}")
    private String outputFilePath;

    private final DataProcessService dataProcessService;

    @Override
    public void createCsvFile() {
        List<String> records = dataProcessService.processData();
        log.info("Creating csv file: {}", outputFilePath);

        try (FileWriter fileWriter = new FileWriter(outputFilePath);
             CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {
            for (String url : records) {
                printer.printRecord(url);
            }
        } catch (IOException e) {
            log.error("Error: ", e);
            throw new FileCreationException("Dosya oluşturma ya da yazmada hata");
        }
    }
}
