drop table user;
drop table account;



create table user
(
  U_name varchar(20) primary key,
  U_pswd varchar(20),
  U_bird varchar(50),
  U_sex varchar(50) character set gbk
);  
insert into user values('13527924151','4151','1982/02/23','Å®');
insert into user values('13712653456','3456','1982/02/23','ÄÐ');

create table account
(_id int(20)  primary key,
 name varchar(20),
 balance int(20)
);


