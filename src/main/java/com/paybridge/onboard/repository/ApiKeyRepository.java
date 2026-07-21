package com.paybridge.onboard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.paybridge.onboard.entity.ApiKeyEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ApiKeyRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_SQL = """
            INSERT INTO api_keys (merchant_id, key_hash, key_prefix, status)
            VALUES (?, ?, ?, 'ACTIVE')
            """;

    // Only merchant_id and status are needed on the verify hot path — no reason
    // to pull the full row over the wire for a lookup gateway calls on every cache miss.
    private static final String FIND_BY_HASH_SQL = """
            SELECT merchant_id, status
            FROM api_keys
            WHERE key_hash = ?
            """;

    public void save(ApiKeyEntity apiKey) {
        jdbcTemplate.update(
                INSERT_SQL,
                apiKey.getMerchantId(),
                apiKey.getKeyHash(),
                apiKey.getKeyPrefix()
        );
    }

    public Optional<ApiKeyEntity> findByKeyHash(String keyHash) {
        List<ApiKeyEntity> results = jdbcTemplate.query(
                FIND_BY_HASH_SQL,
                (rs, rowNum) -> ApiKeyEntity.builder()
                        .merchantId(rs.getString("merchant_id"))
                        .status(rs.getString("status"))
                        .build(),
                keyHash
        );
        return results.stream().findFirst();
    }
}