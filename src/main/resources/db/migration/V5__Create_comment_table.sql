create table comment
(
	id bigint auto_increment primary key,
	parent_id bigint not null comment '父类id',
	type int not null comment '回复类型，0,1，一级回复和二级回复',
	commentator int not null comment '评论人id',
	gmt_create bigint not null comment '创建时间',
	gmt_modify bigint not null comment '更新时间',
	like_count int default 0 null comment '点赞数'
);
