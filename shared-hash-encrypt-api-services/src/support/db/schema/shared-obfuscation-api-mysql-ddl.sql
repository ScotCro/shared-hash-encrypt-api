DROP TABLE IF EXISTS `client_api_keys`;

CREATE TABLE `client_api_keys` (
    `api_key` VARCHAR(255) NOT NULL,
    `issued_to` VARCHAR(45) NOT NULL,
    `effective_start_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `effective_end_date` TIMESTAMP NULL DEFAULT NULL,
    `created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `created_by` VARCHAR(50) NOT NULL,
    `updated` TIMESTAMP NULL DEFAULT NULL,
    `updated_by` VARCHAR(50),
    PRIMARY KEY (`api_key`)
);
