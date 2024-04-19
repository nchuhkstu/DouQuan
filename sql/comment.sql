create table comment(
    commentid int auto_increment primary key,
    userid int,
    content varchar(100),
    commentTime datetime
);
create table comment_photo(
    commentid int,
    photoid int primary key,
    foreign key(commentid) references comment(commentid)
);
create table comment_like(
    commentid int,
    likeid int primary key,
    foreign key(commentid) references comment(commentid)
);