package com.jim.spring.boot.spider.config.setting;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jim lin
 * @date 2018/12/13.
 */

@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerSettings {

    private Boolean enable = false;
}
