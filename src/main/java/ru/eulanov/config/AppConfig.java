package ru.eulanov.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import ru.eulanov.dao.AnnouncementDao;
import ru.eulanov.dao.UserDao;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * basic configuration class
 */
@Configuration
@PropertySource(value = "classpath:hibernate.properties")
public class AppConfig {

    @Autowired
    private Environment environment;

    @Bean
    public AnnouncementDao announcementDao() {
        return new AnnouncementDao();
    }

    @Bean
    public UserDao userDao() {
        return new UserDao();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("hibernate.driver"));
        dataSource.setUrl(environment.getRequiredProperty("hibernate.url"));
        dataSource.setUsername(environment.getRequiredProperty("hibernate.user"));
        dataSource.setPassword(environment.getRequiredProperty("hibernate.password"));
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("ru.eulanov.models");
        sessionFactory.setHibernateProperties(properties());
        return sessionFactory;
    }

    private Properties properties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        return properties;
    }
}
