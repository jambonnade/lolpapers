package de.jambonna.lolpapers.core.model;

import java.util.List;

/**
 * Object in charge of changing an entity state to have a slightly different
 * url request path in the process of finding an available url
 */
public interface EntityUrlUpdater {
    public List<Url> getUrls();
    public boolean nextBasePath();
}
