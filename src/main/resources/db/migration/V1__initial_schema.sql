-- Create user table
CREATE TABLE "user" (
                        id UUID PRIMARY KEY,
                        username VARCHAR(255) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL,
                        full_name VARCHAR(255) NOT NULL,
                        created_at VARCHAR(255) NOT NULL,
                        updated_at VARCHAR(255) NOT NULL
);