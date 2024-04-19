-- 用户账户表
CREATE TABLE user_account (
    userid INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(15),
    password VARCHAR(30),
    email VARCHAR(30),
    phone VARCHAR(11)
);

-- 用户扩展信息表
CREATE TABLE user_extend (
    userid INT PRIMARY KEY,
    avatarid INT,
    gender VARCHAR(1),
    birthday DATE,
    registrationTime DATETIME,
    selfIntroduction VARCHAR(100),
    name VARCHAR(15),
    FOREIGN KEY (userid) REFERENCES user_account(userid)
);

-- 用户登录表
CREATE TABLE user_login (
    userid INT,
    loginTime DATETIME,
    loginPlace VARCHAR(30),
    PRIMARY KEY (userid, loginTime),
    FOREIGN KEY (userid) REFERENCES user_account(userid)
);

-- 用户相册表
CREATE TABLE user_photo (
    userid INT,
    photoid INT PRIMARY KEY,
    FOREIGN KEY (userid) REFERENCES user_account(userid)
);

-- 用户文章表
CREATE TABLE user_article (
    userid INT,
    articleid INT PRIMARY KEY,
    FOREIGN KEY (userid) REFERENCES user_account(userid)
);

-- 用户视频表
CREATE TABLE user_video (
    userid INT,
    shortvideoid INT PRIMARY KEY,
    FOREIGN KEY (userid) REFERENCES user_account(userid)
);

-- 用户头像表
CREATE TABLE user_avatar(
    userid INT,
    photoid INT,
    PRIMARY KEY(userid,photoid),
    FOREIGN KEY (userid) REFERENCES user_account(userid)
);