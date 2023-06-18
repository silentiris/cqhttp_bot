package com.sipc.config;

import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisPlusConfig {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    // 其他配置项和 Bean 的定义

    @Bean
    public SqlRunner sqlRunner() {
        return new SqlRunner(sqlSessionFactory.getClass());
    }
}
