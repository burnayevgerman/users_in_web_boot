package web.application.users.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db.properties")
@ComponentScan("web.application.users")
@EnableTransactionManagement
public class DataBaseConfig {
    Environment env;

    @Autowired
    public DataBaseConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.user"));
        dataSource.setPassword(env.getProperty("db.password"));

        dataSource.setInitialSize(Integer.parseInt(env.getProperty("db.initial_size")));
        dataSource.setMinIdle(Integer.parseInt(env.getProperty("db.min_idle")));
        dataSource.setMaxIdle(Integer.parseInt(env.getProperty("db.max_idle")));
        dataSource.setDurationBetweenEvictionRuns(
                Duration.ofMillis(Long.parseLong(env.getProperty("db.duration_between_eviction_runs"))));
        dataSource.setMinEvictableIdle(
                Duration.ofMillis(Long.parseLong(env.getProperty("db.min_evictable_idle"))));
        dataSource.setTestOnBorrow(Boolean.getBoolean(env.getProperty("db.test_on_borrow")));
        dataSource.setValidationQuery(env.getProperty("db.validation_query"));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emFactory = new LocalContainerEntityManagerFactoryBean();
        emFactory.setDataSource(dataSource());
        emFactory.setPackagesToScan(env.getProperty("db.entity.package"));
        emFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emFactory.setJpaProperties(hibernateProperties());
        return emFactory;
    }


    @Bean
    public Properties hibernateProperties() {
        try {
            Properties hProp = new Properties();
            hProp.load(getClass().getClassLoader().getResourceAsStream("hibernate.properties"));
            return hProp;
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't find 'hibernate.properties' in classpath!");
        }
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
