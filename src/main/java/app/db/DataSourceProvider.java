package app.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public class DataSourceProvider {
    private static final HikariDataSource ds;
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/moviesdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
        config.setUsername("user");
        config.setPassword("pass");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        ds = new HikariDataSource(config);
    }
    public static DataSource getDataSource(){ return ds; }
}
