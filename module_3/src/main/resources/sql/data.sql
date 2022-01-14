insert into users values (1, 22, CURRENT_TIMESTAMP(), 'Zahar', 'Morozov');
insert into users values (2, 18, CURRENT_TIMESTAMP(), 'Eduard', 'Plastun');
insert into users values (3, 36, CURRENT_TIMESTAMP(), 'Alina', 'Levchenko');
insert into users values (4, 19, CURRENT_TIMESTAMP(), 'Egor', 'Lenko');
insert into users values (5, 52, CURRENT_TIMESTAMP(), 'Alexander', 'Beztsennyi');

insert into categories values (1, false, 'Products');
insert into categories values (2, false, 'Cinema');
insert into categories values (3, true, 'Salary');
insert into categories values (4, false, 'Clothes');
insert into categories values (5, true, 'Cashback withdrawal');
insert into categories values (6, false, 'Restaurants');
insert into categories values (7, false, 'Taxi');
insert into categories values (8, true, 'Prize');
insert into categories values (9, true, 'Present from parents');
insert into categories values (10, false, 'Utilities');

insert into accounts values (1, 'PERSONAL', 0, 1);
insert into accounts values (2, 'BUSINESS', 0, 1);
insert into accounts values (3, 'PERSONAL', 0, 2);
insert into accounts values (4, 'SAVINGS', 0, 3);
insert into accounts values (5, 'BUSINESS', 0, 4);
insert into accounts values (6, 'PERSONAL', 0, 5);
insert into accounts values (7, 'SAVINGS', 0, 2);