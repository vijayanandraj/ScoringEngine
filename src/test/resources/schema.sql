create table rule_groups
(
    group_id   varchar(255) not null
        primary key,
    group_name varchar(255) not null,
    group_desc varchar(255) not null,
    severity   int          not null,
    category   varchar(255) not null
);



create table rules
(
    rule_id  varchar(255) not null
        primary key,
    title    varchar(255) not null,
    group_id varchar(255) not null,
    constraint rules_ibfk_1
        foreign key (group_id) references rule_groups (group_id)
);

create index group_id
    on rules (group_id);

create table scan_data
(
    id           bigint auto_increment
        primary key,
    ait_id       bigint       not null,
    component_id varchar(255) not null,
    rule_id      varchar(255) not null,
    file_name    varchar(255) not null,
    constraint scan_data_rule_fk
        foreign key (rule_id) references rules (rule_id)
);

CREATE TABLE workload_score (
                                id BIGINT NOT NULL AUTO_INCREMENT,
                                code_scan_score INT NOT NULL,
                                survey_score INT NOT NULL,
                                total_score INT NOT NULL,
                                created_date DATETIME NOT NULL,
                                modified_date DATETIME NOT NULL,
                                PRIMARY KEY (id)
);

