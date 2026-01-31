package com.fairfood.dispatch.config;

import com.fairfood.dispatch.model.Rider;
import com.fairfood.dispatch.repository.RiderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * 骑手数据初始化配置
 * 预置测试骑手数据
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class RiderDataInitConfig {

    @Bean
    public CommandLineRunner initRiders(RiderRepository riderRepository) {
        return args -> {
            // 避免重复初始化
            if (riderRepository.count() > 0) {
                log.info("骑手数据已存在，跳过初始化");
                return;
            }

            log.info("开始初始化骑手数据...");

            List<Rider> riders = Arrays.asList(
                    Rider.builder()
                            .name("张师傅")
                            .phone("13800138001")
                            .status("AVAILABLE")
                            .latitude(39.9050)
                            .longitude(116.4100)
                            .todayDeliveries(2)
                            .rating(4.8)
                            .build(),
                    Rider.builder()
                            .name("李师傅")
                            .phone("13800138002")
                            .status("AVAILABLE")
                            .latitude(39.9100)
                            .longitude(116.4200)
                            .todayDeliveries(5)
                            .rating(4.9)
                            .build(),
                    Rider.builder()
                            .name("王师傅")
                            .phone("13800138003")
                            .status("AVAILABLE")
                            .latitude(39.9200)
                            .longitude(116.4000)
                            .todayDeliveries(1)
                            .rating(4.7)
                            .build(),
                    Rider.builder()
                            .name("赵师傅")
                            .phone("13800138004")
                            .status("BUSY")
                            .latitude(39.9150)
                            .longitude(116.4150)
                            .todayDeliveries(8)
                            .rating(4.6)
                            .build()
            );

            riderRepository.saveAll(riders);
            log.info("骑手数据初始化完成，共创建{}个骑手", riders.size());
        };
    }
}
