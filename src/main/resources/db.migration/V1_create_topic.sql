create table topics (
id bigint not null auto_increment,
title varchar (250) not null,
message varchar (750) not null,
createDate varchar (11) not null,
status tinyint (2) not null,
author varchar (25) not null,
course varchar (75) not null,
answers varchar (750),

primary key(id)
);