# 健身系统 API 详细文档

## 1. 接口规范说明

### 1.1 基础URL
所有接口的基础URL为：`http://localhost:8080/api`

### 1.2 认证方式
大部分接口需要认证，认证方式为在请求头中添加：
```
Authorization: Bearer {token}
```
其中`{token}`为登录后获取的JWT令牌。

### 1.3 响应格式
所有接口的响应格式统一为JSON，包含以下字段：
```json
{
  "code": 整数, // 响应状态码，200表示成功
  "msg": "字符串", // 响应消息
  "data": 任意类型 // 响应数据
}
```

## 2. 用户管理模块

### 2.1 用户注册

**接口路径**：`/users/register`
**请求方法**：POST
**请求头**：无
**请求参数**：表单参数
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| username | String | 是 | 用户名 |
| password | String | 是 | 密码 |

**返回值**：
```json
{
  "code": 200,
  "msg": "注册成功",
  "data": null
}
```

**示例请求**：
```
POST http://localhost:8080/api/users/register
Content-Type: application/x-www-form-urlencoded

username=testuser&password=123456
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "注册成功",
  "data": null
}
```

### 2.2 用户登录

**接口路径**：`/users/login`
**请求方法**：POST
**请求头**：无
**请求参数**：表单参数
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| username | String | 是 | 用户名 |
| password | String | 是 | 密码 |

**返回值**：
```json
{
  "code": 200,
  "msg": "登录成功",
  "data": "string" // JWT令牌
}
```

**示例请求**：
```
POST http://localhost:8080/api/users/login
Content-Type: application/x-www-form-urlencoded

username=testuser&password=123456
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "登录成功",
  "data": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j"
}
```

### 2.3 获取用户信息

**接口路径**：`/users/userInfo`
**请求方法**：GET
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |

**请求参数**：无

**返回值**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "user_id": 1,
    "username": "string",
    "email": "string",
    "phone": "string",
    "created_at": "2023-12-31 12:00:00",
    "updated_at": "2023-12-31 12:00:00"
  }
}
```

**示例请求**：
```
GET http://localhost:8080/api/users/userInfo
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "user_id": 1,
    "username": "testuser",
    "email": "test@example.com",
    "phone": "13800138000",
    "created_at": "2023-12-31 12:00:00",
    "updated_at": "2023-12-31 12:00:00"
  }
}
```

### 2.4 更新用户信息

**接口路径**：`/users/update`
**请求方法**：PUT
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |
| Content-Type | application/json | 请求体格式 |

**请求参数**：JSON请求体
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| email | String | 否 | 邮箱 |
| phone | String | 否 | 手机号 |

**返回值**：
```json
{
  "code": 200,
  "msg": "更新成功",
  "data": null
}
```

**示例请求**：
```
PUT http://localhost:8080/api/users/update
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
Content-Type: application/json

{
  "email": "newemail@example.com",
  "phone": "13900139000"
}
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "更新成功",
  "data": null
}
```

### 2.5 修改密码

**接口路径**：`/users/updatePwd`
**请求方法**：PATCH
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |
| Content-Type | application/json | 请求体格式 |

**请求参数**：JSON请求体
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| old_pwd | String | 是 | 旧密码 |
| new_pwd | String | 是 | 新密码 |
| re_pwd | String | 是 | 确认新密码 |

**返回值**：
```json
{
  "code": 200,
  "msg": "密码修改成功",
  "data": null
}
```

**示例请求**：
```
PATCH http://localhost:8080/api/users/updatePwd
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
Content-Type: application/json

{
  "old_pwd": "123456",
  "new_pwd": "654321",
  "re_pwd": "654321"
}
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "密码修改成功",
  "data": null
}
```

## 3. 设施管理模块

### 3.1 获取所有设施

**接口路径**：`/admin/facilities`
**请求方法**：GET
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |

**请求参数**：无

**返回值**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": [
    {
      "facility_id": 1,
      "name": "string",
      "type": "string",
      "location": "string",
      "capacity": 10,
      "status": "AVAILABLE",
      "created_at": "2023-12-31 12:00:00",
      "updated_at": "2023-12-31 12:00:00"
    }
  ]
}
```

**示例请求**：
```
GET http://localhost:8080/api/admin/facilities
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY5OTkyOTYwMH0.abcdef123456
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": [
    {
      "facility_id": 1,
      "name": "跑步机",
      "type": "有氧器材",
      "location": "一楼东区",
      "capacity": 10,
      "status": "AVAILABLE",
      "created_at": "2023-12-31 12:00:00",
      "updated_at": "2023-12-31 12:00:00"
    },
    {
      "facility_id": 2,
      "name": "哑铃套装",
      "type": "力量器材",
      "location": "一楼西区",
      "capacity": 8,
      "status": "AVAILABLE",
      "created_at": "2023-12-31 12:00:00",
      "updated_at": "2023-12-31 12:00:00"
    }
  ]
}
```

### 3.2 获取单个设施

**接口路径**：`/admin/facilities/{id}`
**请求方法**：GET
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |

**请求参数**：路径参数
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| id | Integer | 是 | 设施ID |

**返回值**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "facility_id": 1,
    "name": "string",
    "type": "string",
    "location": "string",
    "capacity": 10,
    "status": "AVAILABLE",
    "created_at": "2023-12-31 12:00:00",
    "updated_at": "2023-12-31 12:00:00"
  }
}
```

**示例请求**：
```
GET http://localhost:8080/api/admin/facilities/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY5OTkyOTYwMH0.abcdef123456
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "facility_id": 1,
    "name": "跑步机",
    "type": "有氧器材",
    "location": "一楼东区",
    "capacity": 10,
    "status": "AVAILABLE",
    "created_at": "2023-12-31 12:00:00",
    "updated_at": "2023-12-31 12:00:00"
  }
}
```

### 3.3 添加设施

**接口路径**：`/admin/facilities`
**请求方法**：POST
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |
| Content-Type | application/json | 请求体格式 |

**请求参数**：JSON请求体
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| name | String | 是 | 设施名称 |
| type | String | 是 | 设施类型 |
| location | String | 是 | 设施位置 |
| capacity | Integer | 是 | 容量 |
| status | String | 否 | 状态(AVAILABLE/BUSY/MAINTENANCE) |

**返回值**：
```json
{
  "code": 200,
  "msg": "添加成功",
  "data": null
}
```

**示例请求**：
```
POST http://localhost:8080/api/admin/facilities
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY5OTkyOTYwMH0.abcdef123456
Content-Type: application/json

{
  "name": "新跑步机",
  "type": "有氧器材",
  "location": "一楼南区",
  "capacity": 5,
  "status": "AVAILABLE"
}
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "添加成功",
  "data": null
}
```

### 3.4 更新设施

**接口路径**：`/admin/facilities/{id}`
**请求方法**：PUT
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |
| Content-Type | application/json | 请求体格式 |

**请求参数**：
- 路径参数：
  | 参数名 | 类型 | 是否必填 | 描述 |
  |-------|------|---------|------|
  | id | Integer | 是 | 设施ID |
- JSON请求体：
  | 参数名 | 类型 | 是否必填 | 描述 |
  |-------|------|---------|------|
  | name | String | 否 | 设施名称 |
  | type | String | 否 | 设施类型 |
  | location | String | 否 | 设施位置 |
  | capacity | Integer | 否 | 容量 |
  | status | String | 否 | 状态(AVAILABLE/BUSY/MAINTENANCE) |

**返回值**：
```json
{
  "code": 200,
  "msg": "更新成功",
  "data": null
}
```

**示例请求**：
```
PUT http://localhost:8080/api/admin/facilities/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY5OTkyOTYwMH0.abcdef123456
Content-Type: application/json

{
  "name": "跑步机(更新)",
  "status": "MAINTENANCE"
}
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "更新成功",
  "data": null
}
```

### 3.5 删除设施

**接口路径**：`/admin/facilities/{id}`
**请求方法**：DELETE
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |

**请求参数**：路径参数
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| id | Integer | 是 | 设施ID |

**返回值**：
```json
{
  "code": 200,
  "msg": "删除成功",
  "data": null
}
```

**示例请求**：
```
DELETE http://localhost:8080/api/admin/facilities/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY5OTkyOTYwMH0.abcdef123456
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "删除成功",
  "data": null
}
```

## 4. 课程管理模块

### 4.1 获取课程列表

**接口路径**：`/api/fitness-classes`
**请求方法**：GET
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |

**请求参数**：查询参数
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| page | Integer | 否 | 页码（默认1） |
| pageSize | Integer | 否 | 每页数量（默认10） |
| category | String | 否 | 课程类型 |
| search | String | 否 | 搜索关键词 |

**返回值**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "total": 100,
    "list": [
      {
        "class_id": 1,
        "name": "string",
        "type": "string",
        "instructor": "string",
        "time": "2023-12-31 19:00:00",
        "location": "string",
        "capacity": 20,
        "status": "ACTIVE",
        "created_at": "2023-12-31 12:00:00",
        "updated_at": "2023-12-31 12:00:00"
      }
    ],
    "pageNum": 1,
    "pageSize": 10
  }
}
```

**示例请求**：
```
GET http://localhost:8080/api/api/fitness-classes?page=1&pageSize=10&category=瑜伽
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "total": 5,
    "list": [
      {
        "class_id": 1,
        "name": "瑜伽入门",
        "type": "瑜伽",
        "instructor": "张教练",
        "time": "2023-12-31 19:00:00",
        "location": "二楼瑜伽室",
        "capacity": 20,
        "status": "ACTIVE",
        "created_at": "2023-12-31 12:00:00",
        "updated_at": "2023-12-31 12:00:00"
      }
    ],
    "pageNum": 1,
    "pageSize": 10
  }
}
```

### 4.2 创建课程

**接口路径**：`/api/fitness-classes`
**请求方法**：POST
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |
| Content-Type | application/json | 请求体格式 |

**请求参数**：JSON请求体
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| name | String | 是 | 课程名称 |
| type | String | 是 | 课程类型 |
| instructor | String | 是 | 教练 |
| time | String | 是 | 课程时间（格式：yyyy-MM-dd HH:mm:ss） |
| location | String | 是 | 课程地点 |
| capacity | Integer | 是 | 容量 |
| status | String | 否 | 状态(ACTIVE/CANCELLED) |

**返回值**：
```json
{
  "code": 200,
  "msg": "创建成功",
  "data": null
}
```

**示例请求**：
```
POST http://localhost:8080/api/api/fitness-classes
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
Content-Type: application/json

{
  "name": "新课程",
  "type": "瑜伽",
  "instructor": "李教练",
  "time": "2024-01-05 18:00:00",
  "location": "二楼瑜伽室",
  "capacity": 15,
  "status": "ACTIVE"
}
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "创建成功",
  "data": null
}
```

### 4.3 获取课程详情

**接口路径**：`/api/fitness-classes/{id}`
**请求方法**：GET
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |

**请求参数**：路径参数
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| id | Integer | 是 | 课程ID |

**返回值**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "class_id": 1,
    "name": "string",
    "type": "string",
    "instructor": "string",
    "time": "2023-12-31 19:00:00",
    "location": "string",
    "capacity": 20,
    "status": "ACTIVE",
    "created_at": "2023-12-31 12:00:00",
    "updated_at": "2023-12-31 12:00:00"
  }
}
```

**示例请求**：
```
GET http://localhost:8080/api/api/fitness-classes/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "class_id": 1,
    "name": "瑜伽入门",
    "type": "瑜伽",
    "instructor": "张教练",
    "time": "2023-12-31 19:00:00",
    "location": "二楼瑜伽室",
    "capacity": 20,
    "status": "ACTIVE",
    "created_at": "2023-12-31 12:00:00",
    "updated_at": "2023-12-31 12:00:00"
  }
}
```

### 4.4 更新课程

**接口路径**：`/api/fitness-classes/{id}`
**请求方法**：PUT
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |
| Content-Type | application/json | 请求体格式 |

**请求参数**：
- 路径参数：
  | 参数名 | 类型 | 是否必填 | 描述 |
  |-------|------|---------|------|
  | id | Integer | 是 | 课程ID |
- JSON请求体：
  | 参数名 | 类型 | 是否必填 | 描述 |
  |-------|------|---------|------|
  | name | String | 否 | 课程名称 |
  | type | String | 否 | 课程类型 |
  | instructor | String | 否 | 教练 |
  | time | String | 否 | 课程时间（格式：yyyy-MM-dd HH:mm:ss） |
  | location | String | 否 | 课程地点 |
  | capacity | Integer | 否 | 容量 |
  | status | String | 否 | 状态(ACTIVE/CANCELLED) |

**返回值**：
```json
{
  "code": 200,
  "msg": "更新成功",
  "data": null
}
```

**示例请求**：
```
PUT http://localhost:8080/api/api/fitness-classes/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
Content-Type: application/json

{
  "name": "高级瑜伽",
  "time": "2024-01-05 19:00:00"
}
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "更新成功",
  "data": null
}
```

### 4.5 删除课程

**接口路径**：`/api/fitness-classes/{id}`
**请求方法**：DELETE
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |

**请求参数**：路径参数
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| id | Integer | 是 | 课程ID |

**返回值**：
```json
{
  "code": 200,
  "msg": "删除成功",
  "data": null
}
```

**示例请求**：
```
DELETE http://localhost:8080/api/api/fitness-classes/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "删除成功",
  "data": null
}
```

### 4.6 获取课程参与者

**接口路径**：`/api/fitness-classes/{id}/participants`
**请求方法**：GET
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |

**请求参数**：路径参数
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| id | Integer | 是 | 课程ID |

**返回值**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": [
    {
      "user_id": 1,
      "username": "string",
      "phone": "string",
      "email": "string"
    }
  ]
}
```

**示例请求**：
```
GET http://localhost:8080/api/api/fitness-classes/1/participants
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": [
    {
      "user_id": 1,
      "username": "张三",
      "phone": "13800138001",
      "email": "zhangsan@example.com"
    },
    {
      "user_id": 2,
      "username": "李四",
      "phone": "13800138002",
      "email": "lisi@example.com"
    }
  ]
}
```

## 5. 预约管理模块

### 5.1 获取预约列表

**接口路径**：`/api/reservations`
**请求方法**：GET
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |

**请求参数**：查询参数
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| page | Integer | 否 | 页码（默认1） |
| pageSize | Integer | 否 | 每页数量（默认10） |
| status | String | 否 | 预约状态（CONFIRMED/CANCELLED） |
| userId | Integer | 否 | 用户ID |

**返回值**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "total": 100,
    "list": [
      {
        "reservation_id": 1,
        "user_id": 1,
        "facility_id": 1,
        "start_time": "2023-12-31 09:00:00",
        "end_time": "2023-12-31 10:00:00",
        "status": "CONFIRMED",
        "created_at": "2023-12-31 12:00:00",
        "updated_at": "2023-12-31 12:00:00"
      }
    ],
    "pageNum": 1,
    "pageSize": 10
  }
}
```

**示例请求**：
```
GET http://localhost:8080/api/api/reservations?page=1&pageSize=10&status=CONFIRMED
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "total": 3,
    "list": [
      {
        "reservation_id": 1,
        "user_id": 1,
        "facility_id": 1,
        "start_time": "2023-12-31 09:00:00",
        "end_time": "2023-12-31 10:00:00",
        "status": "CONFIRMED",
        "created_at": "2023-12-31 12:00:00",
        "updated_at": "2023-12-31 12:00:00"
      }
    ],
    "pageNum": 1,
    "pageSize": 10
  }
}
```

### 5.2 创建预约

**接口路径**：`/api/reservations`
**请求方法**：POST
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |
| Content-Type | application/json | 请求体格式 |

**请求参数**：JSON请求体
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| classId | Integer | 是 | 课程ID |
| startTime | String | 是 | 开始时间（格式：yyyy-MM-dd HH:mm:ss） |
| endTime | String | 是 | 结束时间（格式：yyyy-MM-dd HH:mm:ss） |

**返回值**：
```json
{
  "code": 200,
  "msg": "预约成功",
  "data": null
}
```

**示例请求**：
```
POST http://localhost:8080/api/api/reservations
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
Content-Type: application/json

{
  "classId": 1,
  "startTime": "2024-01-05 19:00:00",
  "endTime": "2024-01-05 20:30:00"
}
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "预约成功",
  "data": null
}
```

### 5.3 获取预约详情

**接口路径**：`/api/reservations/{id}`
**请求方法**：GET
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |

**请求参数**：路径参数
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| id | Integer | 是 | 预约ID |

**返回值**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "reservation_id": 1,
    "user_id": 1,
    "facility_id": 1,
    "start_time": "2023-12-31 09:00:00",
    "end_time": "2023-12-31 10:00:00",
    "status": "CONFIRMED",
    "created_at": "2023-12-31 12:00:00",
    "updated_at": "2023-12-31 12:00:00"
  }
}
```

**示例请求**：
```
GET http://localhost:8080/api/api/reservations/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "reservation_id": 1,
    "user_id": 1,
    "facility_id": 1,
    "start_time": "2023-12-31 09:00:00",
    "end_time": "2023-12-31 10:00:00",
    "status": "CONFIRMED",
    "created_at": "2023-12-31 12:00:00",
    "updated_at": "2023-12-31 12:00:00"
  }
}
```

### 5.4 更新预约

**接口路径**：`/api/reservations/{id}`
**请求方法**：PUT
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |
| Content-Type | application/json | 请求体格式 |

**请求参数**：
- 路径参数：
  | 参数名 | 类型 | 是否必填 | 描述 |
  |-------|------|---------|------|
  | id | Integer | 是 | 预约ID |
- JSON请求体：
  | 参数名 | 类型 | 是否必填 | 描述 |
  |-------|------|---------|------|
  | startTime | String | 否 | 开始时间（格式：yyyy-MM-dd HH:mm:ss） |
  | endTime | String | 否 | 结束时间（格式：yyyy-MM-dd HH:mm:ss） |

**返回值**：
```json
{
  "code": 200,
  "msg": "更新成功",
  "data": null
}
```

**示例请求**：
```
PUT http://localhost:8080/api/api/reservations/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
Content-Type: application/json

{
  "startTime": "2024-01-05 19:30:00",
  "endTime": "2024-01-05 21:00:00"
}
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "更新成功",
  "data": null
}
```

### 5.5 取消预约

**接口路径**：`/api/reservations/{id}/cancel`
**请求方法**：POST
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |

**请求参数**：路径参数
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| id | Integer | 是 | 预约ID |

**返回值**：
```json
{
  "code": 200,
  "msg": "取消成功",
  "data": null
}
```

**示例请求**：
```
POST http://localhost:8080/api/api/reservations/1/cancel
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "取消成功",
  "data": null
}
```

## 6. 用户偏好模块

### 6.1 获取用户偏好

**接口路径**：`/api/user-preferences`
**请求方法**：GET
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |

**请求参数**：查询参数
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| userId | Integer | 否 | 用户ID（可选，如果不提供则使用当前登录用户） |

**返回值**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "preference_id": 1,
    "user_id": 1,
    "facility_type": "有氧器材",
    "frequency": "每周3次",
    "created_at": "2023-12-31 12:00:00",
    "updated_at": "2023-12-31 12:00:00"
  }
}
```

**示例请求**：
```
GET http://localhost:8080/api/api/user-preferences?userId=1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "preference_id": 1,
    "user_id": 1,
    "facility_type": "有氧器材",
    "frequency": "每周3次",
    "created_at": "2023-12-31 12:00:00",
    "updated_at": "2023-12-31 12:00:00"
  }
}
```

### 6.2 更新用户偏好

**接口路径**：`/api/user-preferences`
**请求方法**：PUT
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |
| Content-Type | application/json | 请求体格式 |

**请求参数**：JSON请求体
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| facility_type | String | 是 | 设施类型 |
| frequency | String | 是 | 使用频率 |

**返回值**：
```json
{
  "code": 200,
  "msg": "更新成功",
  "data": null
}
```

**示例请求**：
```
PUT http://localhost:8080/api/api/user-preferences
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
Content-Type: application/json

{
  "facility_type": "力量器材",
  "frequency": "每周5次"
}
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "更新成功",
  "data": null
}
```

### 6.3 获取用户偏好分析

**接口路径**：`/api/user-preferences/analysis`
**请求方法**：GET
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |

**请求参数**：查询参数
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| userId | Integer | 否 | 用户ID（可选，如果不提供则使用当前登录用户） |

**返回值**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "user_id": 1,
    "preferred_facility_types": ["有氧器材", "力量器材"],
    "visit_pattern": {
      "weekdays": ["周一", "周三", "周五"],
      "average_duration": "60分钟"
    },
    "suggested_courses": [
      {
        "course_id": 1,
        "course_name": "HIIT训练"
      }
    ]
  }
}
```

**示例请求**：
```
GET http://localhost:8080/api/api/user-preferences/analysis?userId=1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "user_id": 1,
    "preferred_facility_types": ["有氧器材", "力量器材"],
    "visit_pattern": {
      "weekdays": ["周一", "周三", "周五"],
      "average_duration": "60分钟"
    },
    "suggested_courses": [
      {
        "course_id": 1,
        "course_name": "HIIT训练"
      },
      {
        "course_id": 2,
        "course_name": "力量训练基础"
      }
    ]
  }
}
```

## 7. 推荐系统模块

### 7.1 获取推荐课程

**接口路径**：`/api/recommendations/classes`
**请求方法**：GET
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |

**请求参数**：查询参数
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| page | Integer | 否 | 页码（默认1） |
| pageSize | Integer | 否 | 每页数量（默认10） |
| category | String | 否 | 课程类型 |

**返回值**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "total": 100,
    "list": [
      {
        "class_id": 1,
        "name": "string",
        "type": "string",
        "instructor": "string",
        "time": "2023-12-31 19:00:00",
        "location": "string",
        "capacity": 20,
        "status": "ACTIVE",
        "recommendation_score": 0.85
      }
    ],
    "pageNum": 1,
    "pageSize": 10
  }
}
```

**示例请求**：
```
GET http://localhost:8080/api/api/recommendations/classes?page=1&pageSize=10
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "total": 3,
    "list": [
      {
        "class_id": 1,
        "name": "瑜伽入门",
        "type": "瑜伽",
        "instructor": "张教练",
        "time": "2023-12-31 19:00:00",
        "location": "二楼瑜伽室",
        "capacity": 20,
        "status": "ACTIVE",
        "recommendation_score": 0.85
      }
    ],
    "pageNum": 1,
    "pageSize": 10
  }
}
```

### 7.2 提交推荐反馈

**接口路径**：`/api/recommendations/feedback`
**请求方法**：POST
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |
| Content-Type | application/json | 请求体格式 |

**请求参数**：JSON请求体
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| classId | Integer | 是 | 课程ID |
| feedback | String | 是 | 反馈（LIKE/DISLIKE/NEUTRAL） |

**返回值**：
```json
{
  "code": 200,
  "msg": "反馈提交成功",
  "data": null
}
```

**示例请求**：
```
POST http://localhost:8080/api/api/recommendations/feedback
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
Content-Type: application/json

{
  "classId": 1,
  "feedback": "LIKE"
}
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "反馈提交成功",
  "data": null
}
```

### 7.3 获取推荐分析

**接口路径**：`/api/recommendations/analysis`
**请求方法**：GET
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |

**请求参数**：无

**返回值**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "total_recommendations": 100,
    "click_rate": 0.35,
    "conversion_rate": 0.2,
    "top_categories": ["瑜伽", "高强度", "力量训练"],
    "average_score": 4.2
  }
}
```

**示例请求**：
```
GET http://localhost:8080/api/api/recommendations/analysis
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY5OTkyOTYwMH0.1a2b3c4d5e6f7g8h9i0j
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "total_recommendations": 100,
    "click_rate": 0.35,
    "conversion_rate": 0.2,
    "top_categories": ["瑜伽", "高强度", "力量训练"],
    "average_score": 4.2
  }
}
```

## 8. 数据统计模块

### 8.1 获取系统概览数据

**接口路径**：`/api/data/overview`
**请求方法**：GET
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |

**请求参数**：无

**返回值**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "total_users": 1000,
    "active_users_today": 150,
    "total_facilities": 50,
    "available_facilities": 45,
    "total_classes": 200,
    "upcoming_classes": 20,
    "total_reservations": 500,
    "confirmed_reservations": 480
  }
}
```

**示例请求**：
```
GET http://localhost:8080/api/api/data/overview
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY5OTkyOTYwMH0.abcdef123456
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "total_users": 1000,
    "active_users_today": 150,
    "total_facilities": 50,
    "available_facilities": 45,
    "total_classes": 200,
    "upcoming_classes": 20,
    "total_reservations": 500,
    "confirmed_reservations": 480
  }
}
```

### 8.2 获取热门课程统计

**接口路径**：`/api/data/popular-classes`
**请求方法**：GET
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |

**请求参数**：查询参数
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| days | Integer | 否 | 统计天数（默认7） |
| limit | Integer | 否 | 返回数量（默认10） |

**返回值**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": [
    {
      "class_id": 1,
      "class_name": "string",
      "type": "string",
      "total_participants": 150,
      "booking_rate": 0.95
    }
  ]
}
```

**示例请求**：
```
GET http://localhost:8080/api/api/data/popular-classes?days=7&limit=10
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY5OTkyOTYwMH0.abcdef123456
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": [
    {
      "class_id": 1,
      "class_name": "瑜伽入门",
      "type": "瑜伽",
      "total_participants": 150,
      "booking_rate": 0.95
    },
    {
      "class_id": 2,
      "class_name": "HIIT训练",
      "type": "高强度",
      "total_participants": 120,
      "booking_rate": 0.9
    }
  ]
}
```

### 8.3 获取用户活跃度统计

**接口路径**：`/api/data/user-activity`
**请求方法**：GET
**请求头**：
| 请求头名 | 值 | 描述 |
|---------|-----|------|
| Authorization | Bearer {token} | 认证令牌 |

**请求参数**：查询参数
| 参数名 | 类型 | 是否必填 | 描述 |
|-------|------|---------|------|
| days | Integer | 否 | 统计天数（默认30） |

**返回值**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "daily_active_users": [
      {"date": "2023-12-25", "count": 120},
      {"date": "2023-12-26", "count": 145}
    ],
    "average_session_duration": "75分钟",
    "peak_hours": ["18:00-19:00", "19:00-20:00"],
    "retention_rate": {
      "day_1": 0.8,
      "day_7": 0.6,
      "day_30": 0.4
    }
  }
}
```

**示例请求**：
```
GET http://localhost:8080/api/api/data/user-activity?days=30
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY5OTkyOTYwMH0.abcdef123456
```

**示例响应**：
```json
{
  "code": 200,
  "msg": "获取成功",
  "data": {
    "daily_active_users": [
      {"date": "2023-12-25", "count": 120},
      {"date": "2023-12-26", "count": 145},
      {"date": "2023-12-27", "count": 130}
    ],
    "average_session_duration": "75分钟",
    "peak_hours": ["18:00-19:00", "19:00-20:00"],
    "retention_rate": {
      "day_1": 0.8,
      "day_7": 0.6,
      "day_30": 0.4
    }
  }
}
```

## 9. 导入到APIFOX说明

### 9.1 导入方法

1. 打开APIFOX软件
2. 点击左上角的"导入/导出"按钮
3. 选择"导入接口"选项
4. 在弹出的对话框中选择"Markdown"格式
5. 复制本文档的内容到文本框中
6. 点击"确定"按钮完成导入

### 9.2 生成前端页面

导入完成后，您可以使用APIFOX的"Mock数据"功能生成模拟数据，然后使用"前端代码生成"功能根据API文档生成前端页面代码。

1. 在APIFOX中选择导入的API接口
2. 点击"Mock"标签页，开启Mock功能
3. 点击"前端代码生成"按钮
4. 选择您需要的前端框架（如Vue、React等）
5. 根据向导完成前端代码的生成

这样您就可以基于生成的前端代码快速开发健身系统的前端页面了。