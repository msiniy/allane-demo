-- create some brands and models
insert into vehicle_brand (name)
values ('Audi'), ('BMW'), ('Mercedes-Benz'), ('Opel'), ('Porsche'), ('Volkswagen');

insert into vehicle_model(name, year, vehicle_brand_id)
values
    ('A4', 2016, 1), ('A6', 2018, 1), ('TT', 2014, 1),
    ('New Six', 1977, 2), ('i3', 2017, 2), ('X7', 2014, 2), ('Isetta', 1955, 2),
    ('E-Class', 2016, 3), ('SLK-Class', 2011, 3),
    ('Astra', 2015, 4), ('Corsa', 2019, 4), ('Kadett', 1995, 4), ('Mokka', 2020, 4), ('Omega', 2004, 4),
    ('930', 1989, 5), ('Boxter', 2003, 5), ('Macan', 2019, 5),
    ('Amarok', 2022, 6), ('Passat', 2010, 6), ('Tiguan', 2016, 6);

-- create a couple of customer
insert into customer (first_name, last_name, birth_date)
values
    ('Max', 'Mustermann', '1980-01-01'),
    ('Erika', 'Musterfrau', '1980-01-01');

-- create some contracts
insert into leasing_contract (contract_number, customer_id, monthly_rate)
values
    (1, 1, 300),
    (2, 1, 350),
    (3, 2, 250),
    (4, 2, 450);

-- create some vehicles
insert into vehicle (vehicle_model_id, price, vin, contract_id)
values
    (1, 38400, 'WA1CMAFPXEA115549', 1),
    (5, 44500, '5UXKR6C56E0303444', 2),
    (8, 54600, null, 3),
    (10, 17700, null, 4),
    (17, 77800, 'WP0AF2A93BS785679', null),
    (19, 33300, null, null);



