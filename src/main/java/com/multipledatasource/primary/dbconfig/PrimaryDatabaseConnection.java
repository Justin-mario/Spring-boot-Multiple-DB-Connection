package com.multipledatasource.primary.dbconfig;


import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
		basePackages = {"com.multipledatasource.primary.repo"}
		)
public class PrimaryDatabaseConnection {
	
	@Value("${spring.primary.datasource.url}")
    private String url;
	
	@Value("${spring.primary.datasource.username}")
    private String username;
	
	@Value("${spring.primary.datasource.password}")
    private String password;
	
	
	@Primary
	@Bean()
    public DataSource dataSource(){
        return DataSourceBuilder.create()
        		.url(url)
        		.username(username)
        		.password(password)
        		.build();
    }
	
	@Primary
	@Bean()
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			EntityManagerFactoryBuilder builder,
			 DataSource primaryDataSource) {
		Map<String, String> props = new HashMap<>();
		props.put("hibernate.physical_naming_strategy"
				, CamelCaseToUnderscoresNamingStrategy.class.getName());
		props.put("hibernate.hbm2ddl.auto", "update");
		props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		return builder
				.dataSource(primaryDataSource)
				.packages("com.multipledatasource.primary.models")
				.properties(props)
				.build();
	}
	
	@Primary
	@Bean()
	public PlatformTransactionManager transactionManager(
			EntityManagerFactory
			primaryEntityManagerFactory) {
		return new JpaTransactionManager(primaryEntityManagerFactory);
	}
}
