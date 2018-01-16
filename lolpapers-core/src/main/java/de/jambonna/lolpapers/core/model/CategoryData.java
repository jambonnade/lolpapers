package de.jambonna.lolpapers.core.model;

/**
 * A thread-safe read-only object holding Category entity data
 */
public class CategoryData {
    private final Long id;
    private final String code;
    private final String title;
    private final String requestPath;

    public CategoryData(Long id, String code, String title, String requestPath) {
        if (id == null || code == null || title == null || requestPath == null) {
            throw new IllegalArgumentException("Invalid category data");
        }
        
        this.id = id;
        this.code = code;
        this.title = title;
        this.requestPath = requestPath;
    }
    
    public CategoryData(Category category) {
        this(category.getCategoryId(), category.getCode(), category.getTitle(), category.getUrlRequestPath());
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getRequestPath() {
        return requestPath;
    }
    
    
}
