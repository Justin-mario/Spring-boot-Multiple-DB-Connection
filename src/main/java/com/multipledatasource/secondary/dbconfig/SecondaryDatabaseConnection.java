package com.multipledatasource.secondary.dbconfig;


import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
		entityManagerFactoryRef = "secondaryEntityManagerFactory",
		transactionManagerRef = "secondaryTransactionManager",
		basePackages = {"com.multipledatasource.secondary.repo"}
		)
public class SecondaryDatabaseConnection {
		
	@Value("${spring.secondary.datasource.url}")
    private String url;
	
	@Value("${spring.secondary.datasource.username}")
    private String username;
	
	@Value("${spring.secondary.datasource.password}")
    private String password;
	
	
	@Bean(name = "secondaryDbDataSource")
	public DataSource secondaryDbDataSource(){
        return DataSourceBuilder.create()
        		.url(url)
        		.username(username)
        		.password(password)
        		.build();
    }
	
	@Bean(name = "secondaryEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean 
	secondaryEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("secondaryDbDataSource") DataSource secondaryDataSource) {
		Map<String, String> props = new HashMap<> ();
		props.put("hibernate.physical_naming_strategy"
				, CamelCaseToUnderscoresNamingStrategy.class.getName());
		props.put("hibernate.hbm2ddl.auto", "update");
		props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		return builder
				.dataSource(secondaryDataSource)
				.packages("com.multipledatasource.secondary.models")
				.properties ( props )
				.build();
	}
	
	@Bean(name = "secondaryTransactionManager")
	public PlatformTransactionManager secondaryTransactionManager(
			@Qualifier("secondaryEntityManagerFactory") EntityManagerFactory
			secondaryEntityManagerFactory) {
		return new JpaTransactionManager(secondaryEntityManagerFactory);
	}

}
