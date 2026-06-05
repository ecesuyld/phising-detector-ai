package com.turkcell.phisingproject.repository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UrlDataRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void streamAndCombineRecords(RowCallbackHandler handler) {
        String sql =
                "SELECT ID, ORIG_RECORD FROM ( " +
                        "SELECT ID, ORIG_RECORD FROM ORIG_USOM_RECORDS WHERE ORIG_RECORD IS NOT NULL " +
                        "UNION ALL " +
                        "SELECT ID, ORIG_RECORD FROM ORIG_BOZOK_RECORDS WHERE ORIG_RECORD IS NOT NULL) ";
        jdbcTemplate.query(sql, handler);
    }
}
