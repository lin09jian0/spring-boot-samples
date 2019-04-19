package com.jim.spring.boot.spider.config.setting;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jim lin
 *         date 2018/8/29
 */
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class DruidSettings {
    private String type = "com.alibaba.druid.pool.DruidDataSource";
    private String driverClassName = "com.mysql.jdbc.Driver";
    private String url;
    private String username;
    private String password;

    private Integer initialSize = 5;
    private Integer minIdle = 5;
    private Integer maxActive = 20;
    /** 配置获取连接等待超时的时间   */
    private Long maxWait = 60000L;
    /** 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒   */
    private Long timeBetweenEvictionRunsMillis = 60000L;
    /** 配置一个连接在池中最小生存的时间，单位是毫秒   */
    private Long minEvictableIdleTimeMillis = 300000L;
    private String validationQuery = "SELECT 1 FROM DUAL";
    private boolean testWhileIdle = true;
    private boolean testOnBorrow = false;
    private boolean testOnReturn = false;
    private boolean poolPreparedStatements = true;
    private Integer maxPoolPreparedStatementPerConnectionSize = 20;
    /** 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙 */
    private String filters = "stat,log4j";
    /** 通过connectProperties属性来打开mergeSql功能；慢SQL记录  */
    private String connectionProperties = "druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000";
    private boolean useGlobalDataSourceStat;
    private String logShowSql = "true";

}
