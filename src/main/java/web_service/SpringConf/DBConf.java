package web_service.SpringConf;

import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import web_service.DAO.DatabaseDAO;
import web_service.model.Departments;
import web_service.model.Employees;
import web_service.model.Lockouts;
import web_service.model.Messages;
import web_service.model.MessagesFromClient;

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:db.properties" })
public class DBConf {

	@Autowired
	private Environment environment;
	
	@Bean
	public DatabaseDAO<?> setSessionFactory() {
		DatabaseDAO<?> db = new DatabaseDAO<>();
		db.setSessionFactory(getSessionFactory());
		return db;
	}
	
	@Bean("hibernate4AnnotatedSessionFactory")
	public SessionFactory getSessionFactory() {
		LocalSessionFactoryBuilder sf = new LocalSessionFactoryBuilder(getDataSource());
		sf.addAnnotatedClasses(new Class<?>[]{Departments.class, Employees.class, Lockouts.class, Messages.class, MessagesFromClient.class});
		sf.addProperties(getHibernateProperties());
		return sf.buildSessionFactory();
	}
	
	@Bean("dataSource")
	public BasicDataSource getDataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setPassword(environment.getProperty("db.password"));
		ds.setUsername(environment.getProperty("db.username"));
		ds.setUrl(environment.getProperty("db.url"));
		ds.setDriverClassName(environment.getProperty("db.driverClass"));
		return ds;
	}
	
    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getProperty("db.hibernate.dialect"));
        properties.put("hibernate.current_session_context_class", environment.getProperty("db.hibernate.current_session_context_class"));
        properties.put("hibernate.show_sql", environment.getProperty("db.hibernate.show_sql"));
        return properties;
    }
}
