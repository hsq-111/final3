-- 健身系统设施表结构
-- 如果您的数据库中没有此表，请执行以下SQL语句创建

CREATE TABLE IF NOT EXISTS `facility` (
    `facility_id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '设施ID',
    `name` VARCHAR(255) NOT NULL COMMENT '设施名称',
    `type` VARCHAR(100) NOT NULL COMMENT '设施类型（如：有氧器材、力量器材等）',
    `location` VARCHAR(255) NOT NULL COMMENT '设施位置',
    `capacity` INT NOT NULL COMMENT '容量',
    `status` VARCHAR(50) DEFAULT 'AVAILABLE' COMMENT '状态（AVAILABLE-可用，BUSY-忙碌，MAINTENANCE-维护中）',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设施表';

-- 插入一些示例数据
INSERT IGNORE INTO `facility` (`name`, `type`, `location`, `capacity`, `status`) VALUES
('跑步机1号', '有氧器材', '一楼东区', 1, 'AVAILABLE'),
('跑步机2号', '有氧器材', '一楼东区', 1, 'AVAILABLE'),
('哑铃套装', '力量器材', '一楼西区', 8, 'AVAILABLE'),
('动感单车1号', '有氧器材', '二楼北区', 1, 'AVAILABLE'),
('杠铃组合', '力量器材', '一楼西区', 6, 'MAINTENANCE');

-- 查询验证数据
SELECT * FROM facility;