package com.paybridge.onboard.repository;

import java.time.Instant;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.paybridge.onboard.entity.MerchantEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MerchantRepository {

    private final JdbcTemplate jdbcTemplate;

    // RETURNING gives back the DB-computed created_at rather than approximating
    // it in the application layer — the database is the source of truth for its
    // own timestamp, not the JVM clock at the calling instant.
    private static final String INSERT_SQL = """
            INSERT INTO merchants (merchant_id, business_name, email, environment)
            VALUES (?, ?, ?, ?)
            RETURNING created_at
            """;

    public Instant save(MerchantEntity merchant) {
    	log.info("Saving merchant to database: {}", merchant);
        return jdbcTemplate.queryForObject(
                INSERT_SQL,
                (rs, rowNum) -> rs.getTimestamp("created_at").toInstant(),
                merchant.getMerchantId(),
                merchant.getBusinessName(),
                merchant.getEmail(),
                merchant.getEnvironment()
        );
    }
}