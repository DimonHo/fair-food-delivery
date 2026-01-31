package com.fairfood.user.config;

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
 * 配置API文档的基本信息
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI fairFoodOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FairFood API文档")
                        .description("FairFood 外卖平台后端 API 文档\n\n" +
                                "## 认证说明\n\n" +
                                "所有需要认证的接口，请在请求头中添加：\n" +
                                "- `X-User-Id`: 用户ID\n" +
                                "- `Authorization`: Bearer {token}\n\n" +
                                "## 响应格式\n\n" +
                                "所有API返回统一格式：\n" +
                                "```json\n" +
                                "{\n" +
                                "  \"code\": 0,\n" +
                                "  \"message\": \"success\",\n" +
                                "  \"data\": {...}\n" +
                                "}\n" +
                                "```")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FairFood Team")
                                .email("support@fairfood.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server().url("http://localhost:8081").description("用户服务本地环境")
                ));
    }
}
