insert into banks (id, created, updated, name, year_of_foundation, bank_type) values (1,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'Mono',2009,'COMMUNITY');
insert into banks (id, created, updated, name, year_of_foundation, bank_type) values (2,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'Alfa',2003,'COMMERCIAL');
insert into banks (id, created, updated, name, year_of_foundation, bank_type) values (3,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'Raiffaisen',2000,'COMMERCIAL');
insert into banks (id, created, updated, name, year_of_foundation, bank_type) values (4,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'Privat',1996,'OFFSHORE');
insert into banks (id, created, updated, name, year_of_foundation, bank_type) values (5,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'A-Bank',2017,'COMMUNITY');
insert into banks (id, created, updated, name, year_of_foundation, bank_type) values (6,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'Oschad',2002,'SAVINGS');
insert into banks (id, created, updated, name, year_of_foundation, bank_type) values (7,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'Sport Bank',2010,'CREDIT_UNION');
insert into banks (id, created, updated, name, year_of_foundation, bank_type) values (8,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'Pumb',2008,'CREDIT_UNION');
insert into banks (id, created, updated, name, year_of_foundation, bank_type) values (9,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'Mega',2003,'COMMERCIAL');
insert into banks (id, created, updated, name, year_of_foundation, bank_type) values (10,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'OTP',2020,'SAVINGS');
insert into banks (id, created, updated, name, year_of_foundation, bank_type) values (11,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'TAS',2008,'COMMUNITY');
insert into banks (id, created, updated, name, year_of_foundation, bank_type) values (12,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'Ukrsib',2012,'COMMERCIAL');
insert into banks (id, created, updated, name, year_of_foundation, bank_type) values (13,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'Unex',2010,'OFFSHORE');
insert into banks (id, created, updated, name, year_of_foundation, bank_type) values (14,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'Neo',2018,'CREDIT_UNION');

insert into clients (id, created, updated, first_name, last_name, age) values (1,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'Elena','Homenko',18);
insert into clients (id, created, updated, first_name, last_name, age) values (2,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'Egor','Lenko',24);
insert into clients (id, created, updated, first_name, last_name, age) values (3,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'Alexander','Beztsennyi',30);
insert into clients (id, created, updated, first_name, last_name, age) values (4,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'Alexander','Kucheryavyi',20);
insert into clients (id, created, updated, first_name, last_name, age) values (5,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'Zahar','Morozov',19);
insert into clients (id, created, updated, first_name, last_name, age) values (6,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'Anastasia','Shestoperova',44);
insert into clients (id, created, updated, first_name, last_name, age) values (7,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'Eduard','Plastun',50);

insert into bank_client values (1, 1);
insert into bank_client values (2, 1);
insert into bank_client values (3, 1);
insert into bank_client values (4, 2);
insert into bank_client values (5, 3);
insert into bank_client values (6, 3);
insert into bank_client values (7, 3);
insert into bank_client values (8, 3);
insert into bank_client values (9, 3);
insert into bank_client values (10, 3);
insert into bank_client values (7, 4);
insert into bank_client values (11, 5);
insert into bank_client values (12, 5);
insert into bank_client values (13, 5);
insert into bank_client values (14, 6);
insert into bank_client values (14, 7);