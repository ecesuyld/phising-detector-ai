package com.turkcell.phisingproject.controller;

import com.turkcell.phisingproject.data.AccessCheckResult;
import com.turkcell.phisingproject.service.AccessCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/access-check")
@RequiredArgsConstructor
public class AccessCheckController {

    private final AccessCheckService accessCheckService;

    @PostMapping("/run")
    public ResponseEntity<List<AccessCheckResult>> runAccessCheck() {
        List<AccessCheckResult> results = accessCheckService.checkUrlAccessibility();
        return ResponseEntity.ok(results);
    }

    @PostMapping("/check")
    public ResponseEntity<AccessCheckResult> checkSingleUrl(@RequestParam String url) {
        AccessCheckResult result = accessCheckService.performAccessCheck(url);
        return ResponseEntity.ok(result);
    }
}

