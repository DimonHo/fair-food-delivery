package com.fairfood.merchant.config;

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
                        .title("FairFood 商家服务 API文档")
                        .description("FairFood 外卖平台 - 商家服务 API 文档\n\n" +
                                "## 接口说明\n\n" +
                                "- `GET /api/v1/merchants` - 获取商家列表，支持按位置筛选\n" +
                                "- `GET /api/v1/merchants/{id}` - 获取商家详情\n" +
                                "- `GET /api/v1/merchants/{id}/products` - 获取商家商品列表")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FairFood Team")
                                .email("support@fairfood.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server().url("http://localhost:8082").description("商家服务本地环境")
                ));
    }
}
