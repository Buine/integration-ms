CREATE TABLE IF NOT EXISTS "integration"
(
    id BIGSERIAL PRIMARY KEY,
    code UUID UNIQUE NOT NULL,
    user_code UUID NOT NULL,
    name TEXT NOT NULL,
    status TEXT NOT NULL,
    host TEXT NOT NULL,
    port INTEGER NOT NULL,
    db_name TEXT NOT NULL,
    db_username TEXT NOT NULL,
    db_password TEXT NOT NULL,
    ssl BOOLEAN NOT NULL DEFAULT FALSE,
    schema JSONB,
    checksum TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_integration_user_code
    ON integration(user_code);