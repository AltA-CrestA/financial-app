create table users
(
    id         int primary key not null,
    step_code  varchar(100) not null,
    created_at timestamp not null default current_timestamp
);

create table income
(
    id          serial primary key not null,
    user_id     int not null,
    type_id     int not null,
    amount      decimal(10,2) not null,
    description varchar(255) null,
    created_at  timestamp not null default current_timestamp,
    updated_at  timestamp null default current_timestamp
);

create table expense
(
    id          bigint primary key not null,
    user_id     int not null,
    type_id     int not null,
    amount      decimal(10,2) not null,
    description varchar(255) null,
    created_at  timestamp not null default current_timestamp,
    updated_at  timestamp null default current_timestamp
);

create table income_type
(
    id          bigint primary key not null,
    name        varchar(255) null,
    created_at  timestamp not null default current_timestamp,
    updated_at  timestamp null default current_timestamp
);

create table expense_type
(
    id          bigint primary key not null,
    name        varchar(255) null,
    created_at  timestamp not null default current_timestamp,
    updated_at  timestamp null default current_timestamp
);