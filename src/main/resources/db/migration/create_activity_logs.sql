-- ============================================================
-- Tabla global de activity logs
-- ============================================================
CREATE TABLE IF NOT EXISTS activity_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    action VARCHAR(50) NOT NULL,
    resource_type VARCHAR(30) NOT NULL,
    resource_id BIGINT,
    resource_name VARCHAR(255),
    project_id BIGINT,
    project_name VARCHAR(255),
    detail TEXT,
    timestamp DATETIME(6) NOT NULL,

    INDEX idx_action (action),
    INDEX idx_resource_type (resource_type),
    INDEX idx_user_id (user_id),
    INDEX idx_project_id (project_id),
    INDEX idx_timestamp (timestamp)
);
