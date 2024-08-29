-- Drop column summary and add title, link in all info tables

alter table company_info drop column summary;
alter table company_info add column title varchar(300) not null;
alter table company_info add column link varchar(1000) not null;

alter table company_info drop column summary;
alter table country_info add column title varchar(300) not null;
alter table country_info add column link varchar(1000) not null;

alter table company_info drop column summary;
alter table theme_info add column title varchar(1000) not null;
alter table theme_info add column link varchar(1000) not null;
