package com.dhais.tqb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * All rights Reserved, Designed By Fan Jun
 *
 * @author Fan Jun
 * @version 1.0
 * @since 2022/6/20 14:38
 */
@SpringBootApplication
@MapperScan("com.dhais.tqb.mapper")
public class TqbApplication {

    public static void main(String[] args) {
        SpringApplication.run(TqbApplication.class);
    }
}
