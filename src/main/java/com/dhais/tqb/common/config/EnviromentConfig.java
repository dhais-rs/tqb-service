package com.dhais.tqb.common.config;

import cn.hutool.crypto.SecureUtil;
import com.dhais.tqb.common.Constants;
import com.dhais.tqb.common.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * All rights Reserved, Designed By Fan Jun
 *
 * @author Fan Jun
 * @version 1.0
 * @since 2022/6/20 11:32
 */
@Configuration
@Order(Integer.MIN_VALUE+11)
public class EnviromentConfig implements EnvironmentPostProcessor {

    Logger logger = LoggerFactory.getLogger(EnviromentConfig.class);

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        String[] activeProfiles = environment.getActiveProfiles();
        Properties properties = getConfig(activeProfiles);
        propertySources.addLast(new PropertiesPropertySource("allProp",properties));
        for (PropertySource<?> propertySource : propertySources) {
            if (propertySource.getSource() instanceof Map) {
                Map map = (Map) propertySource.getSource();
                for (Object key : map.keySet()) {
                    String keyStr = key.toString();
                    String value = environment.getProperty(keyStr);
                    String mainRegex = "druid.\\S+\\.password|redis.password";
                    if (keyStr.matches(mainRegex)) {
                        String encryptEnabled = environment.getProperty("encrypt.enabled");
                        if ("true".equalsIgnoreCase(encryptEnabled)) {
                            value = SecureUtil.des(Constants.DB_KEY.getBytes()).decryptStr(value);
                            map.put(keyStr,value);
                        }
                    }
                    PropertiesUtil.getProperties().put(keyStr,value);
                }
            }
        }
    }

    private Properties getConfig(String[] activeProfiles){
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<Resource> resourceList = new ArrayList<>();
        //这里加载公共的配置文件
        getResource(resolver,resourceList,"classpath*:/*.properties");
        getResource(resolver,resourceList,"classpath*:/log.config");
        //这里加载指定激活的配置文件
        if (activeProfiles!=null) {
            for (String activeProfile : activeProfiles) {
                String path = "classpath*:/config/"+activeProfile+"/*.properties";
                getResource(resolver,resourceList,path);
            }
        }
        try {
            PropertiesFactoryBean config = new PropertiesFactoryBean();
            config.setLocations(resourceList.toArray(new Resource[]{}));
            config.afterPropertiesSet();
            return config.getObject();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void getResource(PathMatchingResourcePatternResolver resolver,List<Resource> resourceList,String path){
        try {
            Resource[] resources = resolver.getResources(path);
            for (Resource resource : resources) {
                resourceList.add(resource);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
