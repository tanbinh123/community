-- 创建数据库
create database community;
use community;

--查看数据库编码
show variables LIKE 'collation_%';

--如果不是utf8 collation_database 则修改为 utf8 编码
--修改数据库编码属性
ALTER DATABASE oj CHARACTER SET utf8 COLLATE utf8_unicode_ci;

--创建用户表
--创建用户表
CREATE TABLE users
(
    id INT,
    userName VARCHAR(20) NOT NULL,
    password VARCHAR(60) NOT NULL,
    email VARCHAR(30) NOT NULL,
    sex VARCHAR(2) NOT NULL,
    age int,
    birthday varchar(19),
    school varchar(20),
    creationTime VARCHAR(19),
    lastTime VARCHAR(19),
    headUrl VARCHAR(30),
    PRIMARY KEY (id)
);

--设置用户名不重复
ALTER TABLE community.users ADD UNIQUE (userName);
--设置邮箱不重复
ALTER TABLE community.users ADD UNIQUE (email);

--设置主键自增
alter table users modify id int auto_increment;
--设置主键从1000开始自增
alter table users AUTO_INCREMENT=1000;

--用户权限表
create table userPermission
(
    id int,
    power int default 1,
    modifier varchar(20) not null,
    updateTime varchar(19) not null,
    constraint privileges_FK foreign key(id) references users(id)
);

alter table userPermission add updateTime varchar(19) not null ;
alter table userPermission change  column modifer modifier varchar(20)
insert into userPermission values (1,0);

