CREATE TABLE `props` (
    `key` VARCHAR(80) NOT NULL,
    `value` VARCHAR(250) NOT NULL,
    PRIMARY KEY (`key`)
) ENGINE=InnoDB;

CREATE TABLE `website` (
    `website_id` BIGINT NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(30) NOT NULL,
    `url_key` VARCHAR(30) NOT NULL,
    `name` VARCHAR(80) NOT NULL,
    `page_name` VARCHAR(80) NOT NULL,
    `locale_code` VARCHAR(5) NOT NULL,
    `default_timezone_code` VARCHAR(60) NOT NULL,
    CONSTRAINT `UNIQ_WEBSITE_CODE` UNIQUE (`code`),
    PRIMARY KEY (`website_id`)
) ENGINE=InnoDB;

CREATE TABLE `category` (
    `category_id` BIGINT NOT NULL AUTO_INCREMENT,
    `website_id` BIGINT NOT NULL,
    `code` VARCHAR(80) NOT NULL,
    `url_key` VARCHAR(80) NOT NULL,
    `title` VARCHAR(80) NOT NULL,
    `position` INT NOT NULL DEFAULT 0,
--     `theme` VARCHAR(30) NOT NULL,
    CONSTRAINT `FK_CATEGORY_WEBSITE_ID` 
        FOREIGN KEY (`website_id`) REFERENCES `website` (`website_id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `UNIQ_CATEGORY_CODE` UNIQUE (`code`),
    PRIMARY KEY (`category_id`)
) ENGINE=InnoDB;

CREATE TABLE `url` (
    `url_id` BIGINT NOT NULL AUTO_INCREMENT,
    `identifier` VARCHAR(250) NOT NULL,
    `request_path` VARCHAR(250) NOT NULL,
    `dest_path` VARCHAR(250) NOT NULL,
    `history` INT NOT NULL DEFAULT 0,
    KEY `IDX_URL_IDENTIFIER` (`identifier`),
    CONSTRAINT `UNIQ_URL_REQUEST_PATH` UNIQUE (`request_path`),
    PRIMARY KEY (`url_id`)
) ENGINE=InnoDB;

CREATE TABLE `user` (
    `user_id` BIGINT NOT NULL AUTO_INCREMENT,
    `version_number` BIGINT NOT NULL,
    `created_at` DATETIME NOT NULL,
    `screen_name` VARCHAR(80) NOT NULL,
    `url_key` VARCHAR(250) NOT NULL,
    
    `active` INT NOT NULL DEFAULT 1,
    `trusted` INT NOT NULL DEFAULT 0,

    `twitter_user_id` BIGINT NULL,
    `twitter_oauth_token` VARCHAR(250) NULL,
    `twitter_oauth_token_secret` VARCHAR(250) NULL,

    `last_article_limit_reset` DATETIME NOT NULL,
    `started_article_count` INT NOT NULL DEFAULT 0,

    `placeholder_count` INT NOT NULL DEFAULT 0,
    `replacement_count` INT NOT NULL DEFAULT 0,
    `finished_article_count` INT NOT NULL DEFAULT 0,

    `sdef_tooltips_disabled` INT NOT NULL DEFAULT 0,
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB;

CREATE TABLE `news_feed` (
    `news_feed_id` BIGINT NOT NULL AUTO_INCREMENT,
    `url` VARCHAR(250) NOT NULL,
    `code` VARCHAR(30) NOT NULL,
    `active` INT NOT NULL DEFAULT 1,
    `title` VARCHAR(250) NOT NULL,
    `updated_at` DATETIME NOT NULL,
    `category_id` BIGINT NULL,
    `item_limit` INT NULL,
    CONSTRAINT `FK_NEWS_FEED_CATEGORY_ID` 
        FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
        ON DELETE SET NULL ON UPDATE CASCADE,
    PRIMARY KEY (`news_feed_id`)
) ENGINE=InnoDB;

CREATE TABLE `news_feed_item` (
    `news_feed_item_id` BIGINT NOT NULL AUTO_INCREMENT,
    `news_feed_id` BIGINT NOT NULL,
    `title` VARCHAR(250) NOT NULL,
    `uid` VARCHAR(250) NOT NULL,
    `url` VARCHAR(250) NOT NULL,
    `pub_date` DATETIME NULL,
    CONSTRAINT `FK_NEWS_FEED_ITEM_NEWS_FEED_ID` 
        FOREIGN KEY (`news_feed_id`) REFERENCES `news_feed` (`news_feed_id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (`news_feed_item_id`)
) ENGINE=InnoDB;

CREATE TABLE `base_content` (
    `base_content_id` BIGINT NOT NULL AUTO_INCREMENT,
    `dtype` ENUM('article') NOT NULL,
    `version_number` BIGINT NOT NULL,
    `created_at` DATETIME NOT NULL,
    `identifier` VARCHAR(290) NOT NULL,
    `status` ENUM('AVAILABLE', 'TAKEN', 'REJECTED', 'ON_ERROR', 'OUTDATED') NOT NULL,
    `category_id` BIGINT NULL,
    `user_id` BIGINT NULL,
    `article_src_url` VARCHAR(250) NULL,
    `article_canonical_url` VARCHAR(250) NULL,
    `article_title` VARCHAR(250) NULL,
    `article_description` TEXT NULL,
    `article_content` TEXT NULL,
    `article_orig_html` MEDIUMTEXT NULL,
    `article_image_src_url` VARCHAR(250) NULL,
    `article_image_ok` INT NULL,
    `article_files_base_name` VARCHAR(250) NULL,
    `article_rejection_reason` ENUM('NO_TITLE', 'TOO_SHORT', 'TOO_LONG', 'USER_WRONG_CONTENT') NULL,
    CONSTRAINT `UNIQ_BASE_CONTENT_IDENTIFIER` UNIQUE (`identifier`),
    CONSTRAINT `UNIQ_BASE_CONTENT_CANONICAL_URL` UNIQUE (`article_canonical_url`),
    CONSTRAINT `FK_BASE_CONTENT_USER_ID` 
        FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
        ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT `FK_BASE_CONTENT_CATEGORY_ID` 
        FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
        ON DELETE SET NULL ON UPDATE CASCADE,
    PRIMARY KEY (`base_content_id`)
) ENGINE=InnoDB;


CREATE TABLE `content_template` (
    `content_template_id` BIGINT NOT NULL AUTO_INCREMENT,
    `dtype` ENUM('article') NOT NULL,
    `version_number` BIGINT NOT NULL,
    `base_content_id` BIGINT NOT NULL,
    `created_at` DATETIME NOT NULL,
    `user_id` BIGINT NOT NULL,
    `last_save_at` DATETIME NOT NULL,
    `finished_at` DATETIME NULL,
    `completed_at` DATETIME NULL,
    `article_title` TEXT NULL,
    `article_description` TEXT NULL,
    `article_description_rejected` INT NULL DEFAULT 0,
    `article_content` TEXT NULL,
    `article_content_removed_blocks` TEXT NULL,
    CONSTRAINT `FK_CONTENT_TEMPLATE_BASE_CONTENT_ID` 
        FOREIGN KEY (`base_content_id`) REFERENCES `base_content` (`base_content_id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `FK_CONTENT_TEMPLATE_USER_ID` 
        FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (`content_template_id`)
) ENGINE=InnoDB;

CREATE TABLE `template_placeholder` (
    `template_placeholder_id` BIGINT NOT NULL AUTO_INCREMENT,
    `version_number` BIGINT NOT NULL,
    `content_template_id` BIGINT NOT NULL,
    `created_by_user_id` BIGINT NOT NULL,
    `created_at` DATETIME NOT NULL,
    `reference` VARCHAR(80) NOT NULL,
    `from_word` INT NOT NULL,
    `nb_words` INT NOT NULL,
    `orig_text` VARCHAR(250) NOT NULL,
    `use_placeholder` VARCHAR(80) NULL,
    `semantic_link_with` VARCHAR(80) NULL,
    `definition` VARCHAR(250) NULL,
    `definition_replacement` VARCHAR(250) NULL,

    `replacement_started_at` DATETIME NULL,
    `replacement_by_user_id` BIGINT NULL,
    `replacement_at` DATETIME NULL,
    `replacement_text` VARCHAR(250) NULL,
    `replacement_definition_flags` VARCHAR(250) NULL,

    CONSTRAINT `FK_TEMPLATE_PLACEHOLDER_CONTENT_TEMPLATE_ID` 
        FOREIGN KEY (`content_template_id`) REFERENCES `content_template` (`content_template_id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `FK_TEMPLATE_PLACEHOLDER_CREATED_BY_USER_ID` 
        FOREIGN KEY (`created_by_user_id`) REFERENCES `user` (`user_id`)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `FK_TEMPLATE_PLACEHOLDER_REPLACEMENT_BY_USER_ID` 
        FOREIGN KEY (`replacement_by_user_id`) REFERENCES `user` (`user_id`)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `UNIQ_TEMPLATE_PLACEHOLDER_CONTENT_TEMPLATE_ID_REFERENCE` UNIQUE (`content_template_id`, `reference`), 
    PRIMARY KEY (`template_placeholder_id`)
) ENGINE=InnoDB;


CREATE TABLE `final_content` (
    `final_content_id` BIGINT NOT NULL AUTO_INCREMENT,
    `dtype` ENUM('article') NOT NULL,
--     `version_number` BIGINT NOT NULL,
    `base_content_id` BIGINT NOT NULL,
    `content_template_id` BIGINT NULL,
    `created_at` DATETIME NOT NULL,
    `upvotes` INT NOT NULL DEFAULT 0,
    `article_title` VARCHAR(250) NULL,
    `article_with_description` INT NULL DEFAULT 0,
    `article_description` TEXT NULL,
    `article_content` TEXT NULL,
    `article_url_key` VARCHAR(250) NOT NULL,
    CONSTRAINT `FK_FINAL_CONTENT_BASE_CONTENT_ID` 
        FOREIGN KEY (`base_content_id`) REFERENCES `base_content` (`base_content_id`)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `FK_FINAL_CONTENT_CONTENT_TEMPLATE_ID` 
        FOREIGN KEY (`content_template_id`) REFERENCES `content_template` (`content_template_id`)
        ON DELETE SET NULL ON UPDATE CASCADE,
    PRIMARY KEY (`final_content_id`)
) ENGINE=InnoDB;

CREATE TABLE `final_content_upvote` (
    `final_content_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    CONSTRAINT `FK_FINAL_CONTENT_UPVOTE_FINAL_CONTENT_ID` 
        FOREIGN KEY (`final_content_id`) REFERENCES `final_content` (`final_content_id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `FK_FINAL_CONTENT_UPVOTE_USER_ID` 
        FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (`final_content_id`, `user_id`)
) ENGINE=InnoDB;

CREATE TABLE `final_content_participant` (
    `final_content_participant_id` BIGINT NOT NULL AUTO_INCREMENT,
    `final_content_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    `initiator` INT NOT NULL DEFAULT 0,
    `placeholder_count` INT NOT NULL DEFAULT 0,
    `replacement_count` INT NOT NULL DEFAULT 0,
    CONSTRAINT `FK_FINAL_CONTENT_PARTICIPANT_FINAL_CONTENT_ID` 
        FOREIGN KEY (`final_content_id`) REFERENCES `final_content` (`final_content_id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `FK_FINAL_CONTENT_PARTICIPANT_USER_ID` 
        FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    PRIMARY KEY (`final_content_participant_id`)
) ENGINE=InnoDB;

