create table vehicle_brand (
    id bigint not null auto_increment,
    version integer,
    name varchar(255) not null,
    primary key (id)
);

create table vehicle_model (
    id bigint not null auto_increment,
    version integer,
    name varchar(255) not null,
    year smallint not null,
    vehicle_brand_id bigint not null,
    primary key (id)
);

alter table vehicle_model
add constraint vehicle_model_brand_fk
foreign key (vehicle_brand_id) references vehicle_brand (id);

create table vehicle (
    id bigint not null auto_increment,
    version integer,
    price decimal(19,2) not null,
    vin varchar(255),
    vehicle_model_id bigint not null,
    contract_id bigint,
    primary key (id)
);

alter table vehicle
add constraint vehicle_model_fk
foreign key (vehicle_model_id) references vehicle_model (id);

create table customer (
    id bigint not null auto_increment,
    version integer,
    birth_date date not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    primary key (id)
);

create table leasing_contract (
    id bigint not null auto_increment,
    version integer,
    contract_number bigint not null,
    monthly_rate decimal(19,2) not null,
    customer_id bigint not null,
    primary key (id)
);

alter table leasing_contract
add constraint contract_customer_fk
foreign key (customer_id) references customer (id);

-- add FK from vehicle to leasing contract
alter table vehicle
add constraint vehicle_contract_fk
foreign key (contract_id) references leasing_contract (id);