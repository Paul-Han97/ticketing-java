-- Ticketing Database Initialization Script

-- Create tables
CREATE TABLE IF NOT EXISTS ticket_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ticket (
    id SERIAL PRIMARY KEY,
    ticket_type_id INTEGER NOT NULL REFERENCES ticket_type(id),
    status VARCHAR(50) DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
