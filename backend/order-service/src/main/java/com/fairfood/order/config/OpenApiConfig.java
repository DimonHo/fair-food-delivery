package com.fairfood.order.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger/OpenAPI配置类
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI fairFoodOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FairFood 订单服务 API文档")
                        .description("FairFood 外卖平台 - 订单服务 API 文档\n\n" +
                                "## 订单状态说明\n\n" +
                                "- `PENDING` - 待付款\n" +
                                "- `CONFIRMED` - 已确认\n" +
                                "- `PREPARING` - 准备中\n" +
                                "- `DELIVERING` - 配送中\n" +
                                "- `COMPLETED` - 已完成\n" +
                                "- `CANCELLED` - 已取消\n\n" +
                                "## 状态流转\n\n" +
                                "PENDING → CONFIRMED → PREPARING → DELIVERING → COMPLETED\n\n" +
                                "任意状态 → CANCELLED (在可取消范围内)")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FairFood Team")
                                .email("support@fairfood.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server().url("http://localhost:8083").description("订单服务本地环境")
                ));
    }
}
