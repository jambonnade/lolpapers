package de.jambonna.lolpapers.core.persistence.mysql;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import de.jambonna.lolpapers.core.app.Config;
import javax.sql.DataSource;


public class DataSourceFactory implements de.jambonna.lolpapers.core.persistence.DataSourceFactory {

    @Override
    public DataSource createDataSource(Config config) {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL(config.getDatasourceUrl());
        ds.setUser(config.getDatasourceUsername());
        ds.setPassword(config.getDatasourcePassword());
        return ds;
    }

}
