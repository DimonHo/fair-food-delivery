package com.fairfood.address.config;

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
                        .title("FairFood 地址服务 API文档")
                        .description("FairFood 外卖平台 - 地址服务 API 文档\n\n" +
                                "## 地址标签说明\n\n" +
                                "- `HOME` - 家\n" +
                                "- `COMPANY` - 公司\n" +
                                "- `SCHOOL` - 学校\n" +
                                "- `OTHER` - 其他")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FairFood Team")
                                .email("support@fairfood.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server().url("http://localhost:8084").description("地址服务本地环境")
                ));
    }
}
