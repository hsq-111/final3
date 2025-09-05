# 设施管理接口修复说明

## 问题描述
原系统中的设施（器材）管理接口存在以下问题：
1. 获取所有设备接口异常
2. 添加设备异常

## 修复内容

### 1. 数据库字段映射修复
**问题**: MyBatis注解查询中使用 `SELECT *` 可能导致数据库字段（snake_case）与Java属性（camelCase）映射失败。

**修复**: 在 `FacilityMapper.java` 中：
- 明确指定查询字段名
- 添加 `@Results` 注解进行字段映射
- 确保 `facility_id` 正确映射到 `facilityId`
- 确保 `created_at` 和 `updated_at` 正确映射

### 2. 响应状态码修复
**问题**: `Result` 类返回的状态码与API文档不一致。

**修复**: 修改 `Result.java` 中的状态码：
- 成功响应：从 `code=1` 改为 `code=200`
- 错误响应：从 `code=0` 改为 `code=500`

### 3. 输入验证增强
**问题**: 缺少对输入数据的验证。

**修复**: 在 `Facility.java` 中添加验证注解：
- `@NotBlank` 验证必填字段
- `@NotNull` 和 `@Min` 验证容量字段

### 4. 错误处理改进
**问题**: 错误处理不够完善，难以定位问题。

**修复**: 
- 在 `FacilityController.java` 中添加详细的异常处理
- 在 `FacilityServiceImpl.java` 中添加日志记录
- 增加参数验证和空值检查

### 5. 日志配置优化
**修复**: 在 `application.yml` 中添加：
- MyBatis SQL日志输出
- 数据库操作日志
- 便于调试的日志级别

## 数据库要求

确保数据库中存在 `facility` 表，表结构如下：

```sql
CREATE TABLE `facility` (
    `facility_id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `type` VARCHAR(100) NOT NULL,
    `location` VARCHAR(255) NOT NULL,
    `capacity` INT NOT NULL,
    `status` VARCHAR(50) DEFAULT 'AVAILABLE',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

详细的建表脚本请参考：`src/main/resources/db/facility_table.sql`

## API接口

### 获取所有设施
```
GET /api/admin/facilities
```

### 添加设施
```
POST /api/admin/facilities
Content-Type: application/json

{
  "name": "设施名称",
  "type": "设施类型", 
  "location": "设施位置",
  "capacity": 10,
  "status": "AVAILABLE"
}
```

### 其他接口
- `GET /api/admin/facilities/{id}` - 获取单个设施
- `PUT /api/admin/facilities/{id}` - 更新设施
- `DELETE /api/admin/facilities/{id}` - 删除设施

## 测试

项目包含以下测试：
1. `FacilityControllerTest` - 控制器层单元测试
2. `FacilityMapperIntegrationTest` - 数据访问层集成测试

运行测试以验证修复是否成功。

## 故障排除

如果仍然遇到问题，请检查：

1. **数据库连接**：确认MySQL服务运行正常，连接参数正确
2. **数据库表**：确认 `facility` 表存在且结构正确
3. **字段映射**：查看应用日志，确认SQL执行和字段映射正常
4. **依赖项**：确保项目包含必要的Spring Boot和MyBatis依赖

查看日志文件中的详细错误信息，特别关注：
- 数据库连接错误
- SQL语法错误  
- 字段映射错误
- 验证失败信息