package backend.admin.config;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Value("${datasource.url}")
    private String url;
    @Value("${datasource.username}")
    private String username;
    @Value("${datasource.password}")
    private String password;
    @Value("${datasource.driverClass}")
    private String driverClass;

    @Bean
    public DataSource masterDataSource(){
        PooledDataSource dataSource=new PooledDataSource();
        dataSource.setDriver(driverClass);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        return dataSource;
    }

}
