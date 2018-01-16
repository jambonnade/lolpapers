package de.jambonna.lolpapers.cli;

import de.jambonna.lolpapers.core.app.AppException;
import de.jambonna.lolpapers.core.model.FinalArticle;
import de.jambonna.lolpapers.core.model.Website;
import de.jambonna.lolpapers.core.model.WebsiteEntity;
import java.io.IOException;
import java.util.List;


/**
 * A default CLI programs printing some data
 */
public class DumpStatus extends CliAppAbstract {
    public static void main(String[] args) throws AppException, IOException {
        DumpStatus m = new DumpStatus();
        m.run(args);
    }
    
    @Override
    protected void doStuff() throws Exception {
        WebsiteEntity w = getMainDao().findWebsite(1L);
        List<FinalArticle> articles = getMainDao().getLastArticles(w, null, 5);
        
        for (FinalArticle fa : articles) {
            System.out.printf("%d - %s - %s\n", 
                    fa.getFinalContentId(), fa.getBaseContent().getCategory().getTitle(), 
                    fa.getArticleTitle());
        }
        
        System.out.println("Ok");
        
    }
}
