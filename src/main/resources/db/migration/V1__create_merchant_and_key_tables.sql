CREATE TABLE merchants (
    id                  BIGSERIAL PRIMARY KEY,
    merchant_id         VARCHAR(50) NOT NULL UNIQUE,
    business_name       VARCHAR(255) NOT NULL,
    email               VARCHAR(255) NOT NULL UNIQUE,
    environment         VARCHAR(20) NOT NULL DEFAULT 'SANDBOX'
                        CHECK (environment IN ('SANDBOX', 'LIVE')),
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE api_keys (
    id                  BIGSERIAL PRIMARY KEY,
    merchant_id         VARCHAR(50) NOT NULL REFERENCES merchants(merchant_id),
    key_hash            VARCHAR(64) NOT NULL UNIQUE,
    key_prefix          VARCHAR(20) NOT NULL,
    status              VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
                        CHECK (status IN ('ACTIVE', 'REVOKED')),
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    revoked_at          TIMESTAMPTZ NULL
);

CREATE INDEX idx_api_keys_key_hash ON api_keys(key_hash);
CREATE INDEX idx_api_keys_merchant_id ON api_keys(merchant_id);