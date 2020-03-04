alter table question modify id bigint auto_increment;
alter table comment modify gmt_modify bigint null comment '更新时间';
alter table user modify id bigint auto_increment;