create table USER(
id int(11) primary key not null auto_increment,
account_id VARCHAR(100),
name       VARCHAR(50),
token      CHAR(36),
gmt_create BIGINT,
gmt_modify BIGINT
);