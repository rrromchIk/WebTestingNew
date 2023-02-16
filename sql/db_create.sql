create table test
(
    id                  bigint auto_increment
        primary key,
    name                varchar(25) not null,
    subject             varchar(25) not null,
    difficulty          smallint    not null,
    duration            int         not null,
    number_of_questions int         not null,
    constraint test_name_uindex
        unique (name)
);

create table user
(
    id       bigint auto_increment
        primary key,
    login    varchar(25)                  not null,
    password varchar(100)                 not null,
    name     varchar(25)                  not null,
    surname  varchar(25)                  not null,
    email    varchar(25)                  not null,
    status   varchar(10) default 'active' null,
    role     varchar(10) default 'client' null,
    avatar   longblob                     null,
    constraint user_login_uindex
        unique (login)
);

create table question
(
    id        bigint auto_increment
        primary key,
    test_id   bigint                              not null,
    text      varchar(400)                        not null,
    type      varchar(20) default 'single_answer' not null,
    max_score int                                 not null,
    constraint question_id_uindex
        unique (id),
    constraint question_test__fk
        foreign key (test_id) references test (id)
            on delete cascade
);

create table user_test
(
    user_id       bigint                            not null,
    test_id       bigint                            not null,
    starting_time timestamp                         null,
    ending_time   timestamp                         null,
    result        float                             null,
    status        varchar(15) default 'not_started' null,
    constraint user_test_test__fk
        foreign key (test_id) references test (id),
    constraint user_test_user__fk
        foreign key (user_id) references user (id)
            on delete cascade
);

create table user_token
(
    user_id         bigint       not null,
    token           varchar(150) not null,
    expiration_date timestamp    not null,
    constraint user_token_user__fk
        foreign key (user_id) references user (id)
            on delete cascade
);

create table user_answer
(
    question_id bigint       not null,
    user_id     bigint       not null,
    text        varchar(100) not null,
    constraint user_answer_question__fk
        foreign key (question_id) references question (id)
            on delete cascade,
    constraint user_answer_user__fk
        foreign key (user_id) references user (id)
            on delete cascade
);

create table question_answer_variants
(
    question_id bigint       not null,
    text        varchar(100) not null,
    constraint question_answer_variants_question__fk
        foreign key (question_id) references question (id)
            on delete cascade
);

create table question_correct_answer
(
    question_id bigint       not null,
    text        varchar(100) null,
    constraint question_correct_answer_text_uindex
        unique (text),
    constraint question_correct_answer_question__fk
        foreign key (question_id) references question (id)
            on delete cascade
)