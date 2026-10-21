-- Migration versao 2 da Blog API.
-- UUID do Hibernate 6 é armazenado por padrão como BINARY(16) no MySql.
-- SQL CODE
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY_KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
)