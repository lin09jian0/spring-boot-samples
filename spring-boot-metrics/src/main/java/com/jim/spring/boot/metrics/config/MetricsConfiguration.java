package com.jim.spring.boot.metrics.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.cumulative.CumulativeTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author jim lin
 * 2018/9/28.
 */
@Configuration
public class MetricsConfiguration {
    @Autowired
    private MeterRegistry registry;

    @Bean
    public TimedAspect timedAspect(){
        return new TimedAspect(registry);
    }

    @Bean
    public CumulativeTimer timer(){
        CumulativeTimer timer = (CumulativeTimer)Timer.builder("timer22121")
                .publishPercentiles(0.5, 0.95,0.99,0.9999) // median and 95th percentile
                .publishPercentileHistogram(true)
                .sla(Duration.ofMillis(100))
                .minimumExpectedValue(Duration.ofMillis(1))
                .maximumExpectedValue(Duration.ofSeconds(10)).register(registry);
        return timer;
    }
    @Bean
    public DistributionSummary distributionSummary(){
        DistributionSummary distributionSummary= DistributionSummary.builder("my.ratio")
                .scale(100)
                .sla(70, 80, 90)
                .register(registry);

        return distributionSummary;
    }
}
