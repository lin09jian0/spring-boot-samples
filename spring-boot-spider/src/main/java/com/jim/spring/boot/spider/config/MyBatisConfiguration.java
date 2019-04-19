package com.jim.spring.boot.spider.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.jim.spring.boot.spider.config.setting.DruidSettings;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author jim lin
 *         date 2018/8/29
 */
@Configuration
@EnableConfigurationProperties(DruidSettings.class)
public class MyBatisConfiguration {

    @Resource
    private DruidSettings druidSettings;

    @Bean(name = "druidDataSource",initMethod = "init", destroyMethod = "close")
    @Primary
    public DruidDataSource druidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(druidSettings.getDriverClassName());
        druidDataSource.setUrl(druidSettings.getUrl());
        druidDataSource.setUsername(druidSettings.getUsername());
        druidDataSource.setPassword(druidSettings.getPassword());
        druidDataSource.setInitialSize(druidSettings.getInitialSize());
        druidDataSource.setMinIdle(druidSettings.getMinIdle());
        druidDataSource.setMaxActive(druidSettings.getMaxActive());
        druidDataSource.setMaxWait(druidSettings.getMaxWait());
        druidDataSource.setTimeBetweenEvictionRunsMillis(druidSettings.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(druidSettings.getMinEvictableIdleTimeMillis());
        String validationQuery = druidSettings.getValidationQuery();
        if (validationQuery != null && !"".equals(validationQuery)) {
            druidDataSource.setValidationQuery(validationQuery);
        }
        druidDataSource.setTestWhileIdle(druidSettings.isTestWhileIdle());
        druidDataSource.setTestOnBorrow(druidSettings.isTestOnBorrow());
        druidDataSource.setTestOnReturn(druidSettings.isTestOnReturn());
        if(druidSettings.isPoolPreparedStatements()){
            druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(druidSettings.getMaxPoolPreparedStatementPerConnectionSize());
        }
        try {//这是最关键的,否则SQL监控无法生效
            druidDataSource.setFilters(druidSettings.getFilters());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String connectionPropertiesStr = druidSettings.getConnectionProperties();
        if(connectionPropertiesStr != null && !"".equals(connectionPropertiesStr)){
            Properties connectProperties = new Properties();
            String[] propertiesList = connectionPropertiesStr.split(";");
            for(String propertiesTmp:propertiesList){
                String[] obj = propertiesTmp.split("=");
                String key = obj[0];
                String value = obj[1];
                connectProperties.put(key,value);
            }
            druidDataSource.setConnectProperties(connectProperties);
        }
        druidDataSource.setUseGlobalDataSourceStat(druidSettings.isUseGlobalDataSourceStat());
        return druidDataSource;
    }

    /**
     * 配置监控功能的Servlet
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        reg.addInitParameter("loginUsername", druidSettings.getUsername());
        reg.addInitParameter("loginPassword", druidSettings.getPassword());
        reg.addInitParameter("logSlowSql", druidSettings.getLogShowSql());
        return reg;
    }

    /**
     * 配置监控功能的Filter
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        return filterRegistrationBean;
    }


    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(){
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(druidDataSource());
        try {
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            configuration.setMapUnderscoreToCamelCase(true);
            sqlSessionFactory.setConfiguration(configuration);
            sqlSessionFactory.setTypeAliasesPackage("com.jim.spring.boot.spider.po");
            sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mapper/**/*.xml"));
            sqlSessionFactory.setVfs(SpringBootVFS.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(druidDataSource());
    }

}
