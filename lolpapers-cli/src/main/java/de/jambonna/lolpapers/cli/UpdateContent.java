package de.jambonna.lolpapers.cli;

import de.jambonna.lolpapers.core.model.ContentUpdate;


/**
 * Does the news feed update and the base article creation/cleaning
 */
public class UpdateContent extends CliAppAbstract {

    
    public static void main(String[] args) {
        UpdateContent m = new UpdateContent();
        m.run(args);
    }

    @Override
    protected void doStuff() throws Exception {
        ContentUpdate cu = new ContentUpdate();
        cu.setApp(getApp());
        cu.setMainDao(getMainDao());
        
        cu.updateNewsFeeds();
        cu.createNewsFeedArticles();
        cu.outdateArticles();
    }
}
