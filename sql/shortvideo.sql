-- 短视频表
CREATE TABLE shortVideo(
    shortvideoid int AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(30),
    publishTime DATETIME
);
-- 短视频视频表
CREATE TABLE shortVideo_video(
    shortvideoid int,
    videoid int PRIMARY KEY,
    FOREIGN KEY (shortvideoid) REFERENCES shortVideo(shortvideoid)
);
-- 短视频浏览表
CREATE TABLE shortVideo_view(
    shortvideoid int,
    viewid int PRIMARY KEY,
    FOREIGN KEY (shortvideoid) REFERENCES shortVideo(shortvideoid)
);
-- 短视频点赞表
CREATE TABLE shortVideo_like(
    shortvideoid int,
    likeid int PRIMARY KEY,
    FOREIGN KEY (shortvideoid) REFERENCES shortVideo(shortvideoid)
);
-- 短视频评论表
CREATE TABLE shortVideo_comment(
    shortvideoid int,
    commentid int PRIMARY KEY,
    FOREIGN KEY (shortvideoid) REFERENCES shortVideo(shortvideoid)
);
-- 短视频转发表
CREATE TABLE shortVideo_relay(
    shortvideoid int,
    relayid int PRIMARY KEY,
    FOREIGN KEY (shortvideoid) REFERENCES shortVideo(shortvideoid)
);
-- 短视频封面表
CREATE TABLE shortVideo_photo(
    shortvideoid int,
    photoid int PRIMARY KEY,
    FOREIGN KEY (shortvideoid) REFERENCES shortVideo(shortvideoid)
);