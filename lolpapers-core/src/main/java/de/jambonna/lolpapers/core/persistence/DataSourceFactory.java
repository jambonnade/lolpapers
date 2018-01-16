package de.jambonna.lolpapers.core.persistence;

import de.jambonna.lolpapers.core.app.Config;
import javax.sql.DataSource;

/**
 * A storage-specific object responsible for creating a DataSource manually
 * (useful in contexts where DataSources are not managed by the environment)
 */
public interface DataSourceFactory {
    public DataSource createDataSource(Config config);
}
