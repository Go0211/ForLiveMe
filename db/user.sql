CREATE TABLE `User` (
	`userIndex`	int	NOT NULL primary key AUTO_INCREMENT,
	`email`	varchar(100)	not NULL unique key,
	`nickname`	varchar(50)	not NULL,
	`name`	varchar(30)	not NULL,
	`password`	varchar(255)	not NULL,
	`regdate`	timestamp	not NULL default current_timestamp,
	`image`	text
);