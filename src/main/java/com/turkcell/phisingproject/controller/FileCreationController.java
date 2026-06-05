package com.turkcell.phisingproject.controller;
import com.turkcell.phisingproject.service.FileCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/export")
@RequiredArgsConstructor
public class FileCreationController {
    private final FileCreationService fileCreationService;
    @PostMapping("/csv")
    public String exportCsv() {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        fileCreationService.createCsvFile();
        stopWatch.stop();

        return "CSV dosyası oluşturuldu. İşlem süresi: "
                + stopWatch.getTotalTimeSeconds() + " saniye.";

    }
}
