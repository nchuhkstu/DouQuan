-- 文章表
CREATE TABLE article (
    articleid int AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(30),
    content VARCHAR(300),
    publishTime DATETIME
);

-- 文章图片表
CREATE TABLE article_photo (
    articleid int,
    photoid int PRIMARY KEY,
    FOREIGN KEY (articleid) REFERENCES article(articleid)
);

-- 文章浏览表
CREATE TABLE article_view (
    articleid int,
    viewid int PRIMARY KEY,
    FOREIGN KEY (articleid) REFERENCES article(articleid)
);

-- 文章点赞表
CREATE TABLE article_like (
    articleid int,
    likeid int PRIMARY KEY,
    FOREIGN KEY (articleid) REFERENCES article(articleid)
);

-- 文章评论表
CREATE TABLE article_comment (
    articleid int,
    commentid int PRIMARY KEY,
    FOREIGN KEY (articleid) REFERENCES article(articleid)
);

-- 文章转发表
CREATE TABLE article_relay (
    articleid int,
    relayid int PRIMARY KEY,
    FOREIGN KEY (articleid) REFERENCES article(articleid)
);