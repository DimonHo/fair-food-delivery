package com.fairfood.dispatch.config;

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
                        .title("FairFood 派单服务 API文档")
                        .description("FairFood 外卖平台 - 派单服务 API 文档\n\n" +
                                "## 派单算法说明\n\n" +
                                "- `NEAREST` - 选择距离最近的可用骑手\n" +
                                "- `LOAD_BALANCE` - 选择当前负载最低的骑手\n\n" +
                                "## 派单状态说明\n\n" +
                                "- `ASSIGNED` - 已派单\n" +
                                "- `ACCEPTED` - 已接单\n" +
                                "- `DELIVERING` - 配送中\n" +
                                "- `COMPLETED` - 已完成\n" +
                                "- `FAILED` - 失败")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FairFood Team")
                                .email("support@fairfood.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server().url("http://localhost:8085").description("派单服务本地环境")
                ));
    }
}
