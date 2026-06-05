package com.turkcell.phisingproject.service;

import com.turkcell.phisingproject.exception.DataAccessException;
import com.turkcell.phisingproject.repository.UrlDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DataProcessService {
    private final UrlDataRepository repository;

    public List<String> processData() {
        List<String> processedRecords = new ArrayList<>();
        Set<String> uniqueRecords = new HashSet<>();

        repository.streamAndCombineRecords((RowCallbackHandler) rs -> {
            var origRecord = rs.getString("ORIG_RECORD");
            if (uniqueRecords.add(origRecord)) {
                processedRecords.add(origRecord);
            }
        });

        if (processedRecords.isEmpty()) {
            throw new DataAccessException("No processed records found.");
        }
        return processedRecords;
    }
}

