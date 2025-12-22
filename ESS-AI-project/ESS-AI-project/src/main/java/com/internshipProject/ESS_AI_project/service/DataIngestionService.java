package com.internshipProject.ESS_AI_project.service;

import com.internshipProject.ESS_AI_project.repository.EssDataRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class DataIngestionService {

    private static final Logger log = LoggerFactory.getLogger(DataIngestionService.class);
    private final EssDataRepository repository;

    @Value("${app.csv.output-path}")
    private String outputFilePath;

    private final Set<String> uniqueRecords = new HashSet<>();

    public DataIngestionService(EssDataRepository repository) {
        this.repository = repository;
    }

    public void processAndCreateCsv() {
        uniqueRecords.clear();

        log.info("CSV oluşturma işlemi başlıyor: {}", outputFilePath);

        try (FileWriter fileWriter = new FileWriter(outputFilePath);
             CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {

            repository.streamAndCombineRecords((RowCallbackHandler) rs -> {
                String origRecord = rs.getString("ORIG_RECORD");

                if (origRecord != null && !origRecord.trim().isEmpty() && uniqueRecords.add(origRecord)) {
                    try {
                        printer.printRecord( origRecord );

                    } catch (IOException e) {
                        throw new RuntimeException("CSV yazma akışı kesintiye uğradı.", e);
                    }
                }
            });
            printer.flush();

            log.info("CSV DOSYASI HAZIR.");
            log.info("Toplam Kayıt: {}", uniqueRecords.size());

        } catch (Exception e) {
            log.error("HATA: Veri İşleme Servisi başarısız oldu.", e);
        }
    }
}
