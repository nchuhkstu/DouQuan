-- 图片表
CREATE TABLE photo(
    photoid int AUTO_INCREMENT PRIMARY KEY,
    url VARCHAR(100),
    time DATETIME
);
INSERT INTO photo (url, time) VALUES ('defaultAvatar.jpg', '2024-04-11 13:06:52');