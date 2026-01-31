# FairFood Backend MVP

FairFood 外卖平台后端 MVP 版本，使用 Spring Boot 3.x + Java 17 构建。

## 项目结构

```
backend/
├── pom.xml                      # 父POM文件
├── user-service/               # 用户服务 (端口: 8081)
│   ├── src/main/java/...
│   └── src/main/resources/
├── merchant-service/           # 商家服务 (端口: 8082)
├── order-service/              # 订单服务 (端口: 8083)
├── address-service/            # 地址服务 (端口: 8084)
└── dispatch-service/           # 派单服务 (端口: 8085)
```

## 技术栈

- **Spring Boot 3.2.0** + **Java 17**
- **Spring Cloud Gateway** (可选)
- **H2** 内存数据库 (方便快速测试)
- **Redis** (可选配置)
- **JWT** 认证
- **Swagger/OpenAPI** 文档

## 快速开始

### 1. 环境要求

- JDK 17+
- Maven 3.6+

### 2. 编译项目

```bash
cd backend
mvn clean compile -DskipTests
```

### 3. 启动服务

分别启动各个服务：

```bash
# 启动用户服务
cd backend/user-service
mvn spring-boot:run

# 启动商家服务
cd backend/merchant-service
mvn spring-boot:run

# 启动订单服务
cd backend/order-service
mvn spring-boot:run

# 启动地址服务
cd backend/address-service
mvn spring-boot:run

# 启动派单服务
cd backend/dispatch-service
mvn spring-boot:run
```

### 4. API 文档

各服务启动后，可通过 Swagger UI 查看 API 文档：

- User Service: http://localhost:8081/swagger-ui.html
- Merchant Service: http://localhost:8082/swagger-ui.html
- Order Service: http://localhost:8083/swagger-ui.html
- Address Service: http://localhost:8084/swagger-ui.html
- Dispatch Service: http://localhost:8085/swagger-ui.html

## API 接口

### 用户服务 (User Service)

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | /api/v1/user/register | 用户注册 |
| POST | /api/v1/user/login | 用户登录 |
| GET | /api/v1/user/profile | 获取用户信息 |

### 商家服务 (Merchant Service)

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | /api/v1/merchants | 商家列表 |
| GET | /api/v1/merchants/{id} | 商家详情 |
| GET | /api/v1/merchants/{id}/products | 商品列表 |

### 订单服务 (Order Service)

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | /api/v1/orders | 创建订单 |
| GET | /api/v1/orders | 订单列表 |
| GET | /api/v1/orders/{id} | 订单详情 |
| PUT | /api/v1/orders/{id}/status | 更新订单状态 |

### 地址服务 (Address Service)

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | /api/v1/addresses | 地址列表 |
| POST | /api/v1/addresses | 添加地址 |
| DELETE | /api/v1/addresses/{id} | 删除地址 |

### 派单服务 (Dispatch Service)

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | /api/v1/dispatch/assign | 订单派单 |
| GET | /api/v1/dispatch/orders | 待派单列表 |

## API 响应格式

所有 API 返回统一格式：

```json
{
  "code": 0,
  "message": "success",
  "data": {...}
}
```

## 测试

```bash
mvn test
```

## 许可证

Apache License 2.0
