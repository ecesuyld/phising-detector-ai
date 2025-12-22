package com.internshipProject.ESS_AI_project.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

@Repository
public class EssDataRepository {

    private final JdbcTemplate jdbcTemplate;

    public EssDataRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void streamAndCombineRecords(RowCallbackHandler handler) {

        String sql =
                "SELECT ID, ORIG_RECORD FROM ( " +
                        "SELECT ID, ORIG_RECORD FROM ORIG_USOM_RECORDS WHERE ORIG_RECORD IS NOT NULL " +
                        "UNION ALL " +
                        "SELECT ID, ORIG_RECORD FROM ORIG_BOZOK_RECORDS WHERE ORIG_RECORD IS NOT NULL) " ;

        // streaming tetiklenir
        jdbcTemplate.query(sql, handler);
    }
}
