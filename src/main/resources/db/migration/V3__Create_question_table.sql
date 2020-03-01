create table question
(
	id int auto_increment primary key not null,
	title varchar(50) not null,
	description text null,
	comment_count int default 0 null,
	view_count int default 0 null,
	like_count int default 0 null,
	gmt_create bigint null,
	gmt_modify bigint null,
	creator int null,
	tags varchar(256) null
);
