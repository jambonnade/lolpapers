package de.jambonna.lolpapers.core.persistence;

import de.jambonna.lolpapers.core.app.AppException;
import de.jambonna.lolpapers.core.model.ArticleTemplate;
import de.jambonna.lolpapers.core.model.BaseArticle;
import de.jambonna.lolpapers.core.model.Category;
import de.jambonna.lolpapers.core.model.FinalArticle;
import de.jambonna.lolpapers.core.model.NewsFeed;
import de.jambonna.lolpapers.core.model.TemplatePlaceholder;
import de.jambonna.lolpapers.core.model.Url;
import de.jambonna.lolpapers.core.model.User;
import de.jambonna.lolpapers.core.model.WebsiteEntity;
import java.util.Date;
import java.util.List;

/**
 * All DAO operations needed for the App features.
 * Common notes :
 *  - Exceptions during these operations are wrapped in a AppException
 *  - All returned entities subtype of ModelAbstract have the App injected
 *  - Some "save" operations also save related entities if their flag "toSave"
 *      is set. In this case, this flag is reset if this entity is has been saved
 *  - Updates of entities like Category and Url are reflected in the App's SharedData
 */
public interface MainDao extends Dao {
    /**
     * Fetches all Url entities
     * 
     * @return Unsorted list for Url entities
     * @throws AppException 
     */
    public List<Url> getAllUrls() throws AppException;
    
    /**
     * Updates Url entities affected by the given url data.
     * For example : a new url for an existing key will created a new Url entity
     * and update the existing to mark it as "history".
     * 
     * @param url holds data of the desired Url entity (this instance will 
     *      not be persisted itself)
     * @throws AppException 
     */
    public void updateUrlsFor(Url url) throws AppException;
    
    /**
     * Loads a WebsiteEntity of given id
     * 
     * @param id the website id
     * @return a WebsiteEntity or null if not found
     * @throws AppException 
     */
    public WebsiteEntity findWebsite(Long id) throws AppException;
    
    /**
     * Saves the given WebsiteEntity
     * 
     * @param website the WebsiteEntity
     * @throws AppException 
     */
    public void saveWebsite(WebsiteEntity website) throws AppException;
    
    /**
     * Fetches all Category entities
     * 
     * @return a list of all Category entities, sorted by position
     * @throws AppException 
     */
    public List<Category> getAllCategories() throws AppException;
    
    /**
     * Loads a Category entity of given id
     * 
     * @param id the category id
     * @return a Category entity or null if not found
     * @throws AppException 
     */
    public Category findCategory(Long id) throws AppException;
    
    /**
     * Saves the given Category entity
     * 
     * @param category the Category entity
     * @throws AppException 
     */
    public void saveCategory(Category category) throws AppException;
    
    /**
     * Fetches all NewsFeed entities
     * 
     * @return an unordered list of NewsFeed entities
     * @throws AppException 
     */
    public List<NewsFeed> getAllNewsFeeds() throws AppException;
    
    /**
     * Saves a NewsFeed entity
     * 
     * @param newsFeed the NewsFeed entity
     * @throws AppException 
     */
    public void saveNewsFeed(NewsFeed newsFeed) throws AppException;
    
    /**
     * Loads an ArticleTemplate entity of given id
     * 
     * @param id the id
     * @return the ArticleTemplate or null if not found
     * @throws AppException 
     */
    public ArticleTemplate findArticleTemplate(Long id) throws AppException;
    
    /**
     * Fetchs all ArticleTemplates created by the given user that are not finished
     * (can still be edited)
     * 
     * @param user the User
     * @return the ArticleTemplate list, ordered by creation date
     * @throws AppException 
     */
    public List<ArticleTemplate> getUserUnfinishedArticleTemplates(User user) throws AppException;
    
    /**
     * Saves an ArticleTemplate entity. Also saves related Users 
     * and BaseContent if asked for.
     * 
     * @param articleTemplate the ArticleTemplate entity
     * @throws AppException 
     */
    public void saveArticleTemplate(ArticleTemplate articleTemplate) throws AppException;
    
    /**
     * Loads a BaseArticle entity of given id
     * 
     * @param id the BaseContent id
     * @return the BaseArticle entity or null if not found
     * @throws AppException 
     */
    public BaseArticle findBaseArticle(Long id) throws AppException;
    
    /**
     * Loads a BaseArticle entity by its string identifier
     * 
     * @param identifier the identifier field value
     * @return the BaseArticle entity or null if not found
     * @throws AppException 
     */
    public BaseArticle findBaseArticleByIdentifier(String identifier) throws AppException;
    
    /**
     * Loads a BaseArticle entity by its canonical Url. This url is unique and
     * can identify a BaseArticle
     * 
     * @param url the canonical url field value
     * @return the BaseArticle entity or null if not found
     * @throws AppException 
     */
    public BaseArticle findBaseArticleByCanonicalUrl(String url) throws AppException;
    
    /**
     * Fetches BaseArticle entities of status AVAILABLE. Can be filtered by
     * website
     * 
     * @param website a WebsiteEntity or null
     * @return an unsorted list of BaseArticle entities
     * @throws AppException 
     */
    public List<BaseArticle> getAvailableBaseArticles(WebsiteEntity website) throws AppException;
    
    /**
     * Saves a BaseArticle entity
     * 
     * @param baseArticle the BaseArticle entity
     * @throws AppException 
     */
    public void saveBaseArticle(BaseArticle baseArticle) throws AppException;
    
    /**
     * Loads a TemplatePlaceholder entity of given id
     * 
     * @param id the TemplatePlaceholder id
     * @return the TemplatePlaceholder entity or null if not found
     * @throws AppException 
     */
    public TemplatePlaceholder findTemplatePlaceholder(Long id) throws AppException;
    
    /**
     * Fetches all TemplatePlaceholder entities that need a replacement text
     * 
     * @param website the WebsiteEntity filter (mandatory)
     * @return the TemplatePlaceholder list, ordered by ContentTemplate creation 
     *      then placeholder position in the text
     * @throws AppException 
     */
    public List<TemplatePlaceholder> getOrderedUnfilledTemplatePlaceholders(WebsiteEntity website) throws AppException;
    
    /**
     * Saves a TemplatePlaceholder entity. Also saves the User that filled
     * replacement text if asked.
     * 
     * @param templatePlaceholder the TemplatePlaceholder entity
     * @throws AppException 
     */
    public void saveTemplatePlaceholder(TemplatePlaceholder templatePlaceholder) throws AppException;
    
    /**
     * Retrieves last maxNb created FinalArticle entities, filtered by website
     * or by category, sorted by creation date descending.
     * 
     * @param website WebsiteEntity filter or null if filtering by Category
     * @param category Category filter or null if filtering by WebsiteEntity
     * @param maxNb max number of entities returned
     * @return the FinalArticle entity list
     * @throws AppException 
     */
    public List<FinalArticle> getLastArticles(WebsiteEntity website, Category category, int maxNb) throws AppException;
    
    /**
     * Retrieves maxNb most upvoted FinalArticle entities, created since minDate.
     * The list is sorted by upvote number descending and creation date descending
     * if even. Same filtering than getLastArticles
     * 
     * @param website WebsiteEntity filter or null if filtering by Category
     * @param category Category filter or null if filtering by WebsiteEntity
     * @param maxNb max number of entities returned
     * @param minDate minimum FinalArticle creation date
     * @return the FinalArticle entity list
     * @throws AppException 
     */
    public List<FinalArticle> getTopArticles(WebsiteEntity website, Category category, int maxNb, Date minDate) throws AppException;
    
    /**
     * Loads a FinalArticle entity of given id
     * 
     * @param id the FinalArticle id
     * @return the FinalArticle entity or null if not found
     * @throws AppException 
     */
    public FinalArticle findFinalArticle(Long id) throws AppException;
    
    /**
     * Saves a FinalArticle entity. 
     * Also saves related Url, ContentTemplate and Users if asked
     * 
     * @param finalArticle the FinalArticle entity 
     * @throws AppException 
     */
    public void saveFinalArticle(FinalArticle finalArticle) throws AppException;
    
    /**
     * Loads a User entity of given id
     * 
     * @param id the user id
     * @return the User entity or null if not found
     * @throws AppException 
     */
    public User findUser(Long id) throws AppException;
    
    /**
     * Loads a User entity by a Twitter account id
     * 
     * @param id the Twitter account id
     * @return the User entity or null if not found
     * @throws AppException 
     */
    public User findUserByTwitterId(Long id) throws AppException;
    
    /**
     * Saves a User entity
     * 
     * @param user the User entity
     * @throws AppException 
     */
    public void saveUser(User user) throws AppException;
}
