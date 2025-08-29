CREATE TABLE IF NOT EXISTS credit_applications (
    id SERIAL PRIMARY KEY,
    amount NUMERIC(15, 2) NOT NULL,
    term_months INTEGER NOT NULL,
    income NUMERIC(15, 2) NOT NULL,
    current_debt NUMERIC(15, 2) NOT NULL,
    credit_rating INTEGER NOT NULL,
    status VARCHAR(20) DEFAULT 'IN_PROCESS' NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX IF NOT EXISTS idx_credit_applications_status ON credit_applications(status);