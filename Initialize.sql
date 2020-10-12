set autocommit=0;
create table Student
(
    student_id       varchar(8) primary key not null,
    name             varchar(30)            not null,
    Email            varchar(20),
    GPA              numeric(3, 2),
    check (GPA between 0 and 4),
    yearOfUnderGrads numeric(1, 0),
    check (yearOfUnderGrads between 0 and 9),
    yearOfMaster     numeric(1, 0),
    check (yearOfMaster between 0 and 9),
    yearOfGraduate   numeric(1, 0),
    check (yearOfGraduate between 0 and 9)
)charset=UTF8;
create table Professor
(
    professor_id varchar(8) primary key not null,
    name         varchar(30)            not null,
    Email        varchar(20)
)charset=UTF8;
create table Course
(
    major        varchar(4)    not null,
    id           numeric(5, 0) not null,
    check (id >= 10000),
    academic         numeric(4, 0) not null,
    check (academic between 2000 and 2500),
    semester     varchar(6)    not null,
    check (semester in ('Spring', 'Fall', 'Summer')),
    CRN          numeric(5, 0) not null,
    primary key (academic, semester, CRN)
)charset=UTF8;
insert into Student (student_id, name, Email, GPA, yearOfUnderGrads, yearOfMaster, yearOfGraduate)
values ('yuan226', 'Yizhen Yuan', 'yuan226@purdue.edu', 3.75, 4, 1, 0);
insert into Student(student_id, name, Email, GPA, yearOfUnderGrads, yearOfMaster, yearOfGraduate)
values ('su170', 'Zhenhuan Su', 'su170@purdue.edu', null, 4, 0, 0);
insert into Student(student_id, name, Email, GPA, yearOfUnderGrads, yearOfMaster, yearOfGraduate)
values ('anishiho', 'Ayaka Nishihori', 'anishiho@purdue.edu', null, 4, 0, 0);
insert into Student(student_id, name, Email, GPA, yearOfUnderGrads, yearOfMaster, yearOfGraduate)
values ('jmckeev', 'Riley McKeever', 'jmckeev@purdue.edu', null, null, 0, 0);
insert into Student(student_id, name, Email, GPA, yearOfUnderGrads, yearOfMaster, yearOfGraduate)
values ('luo227', 'Dan Luo', 'luo227', 3.77, 0, 2, 0);
insert into Professor (professor_id, name, Email)
values ('hbenotma', 'Hisham Benotman', 'hbenota@purdue.edu');
insert into Professor (professor_id, name, Email) value ('turkstra', 'Jeffrey Turkstra', 'turkstra@purdue.edu');
insert into Professor (professor_id, name, Email) value ('jblocki', 'Jeremiah Blocki', 'jblocki@purdue.edu');
insert into Course (major, id, academic, semester, CRN)
values ('CS', 34800, 2020, 'Fall', 11374);
insert into Course (major, id, academic, semester, CRN)
values ('CS', 38100, 2020, 'Fall', 13247);

create table Takes
(
    student_id varchar(8)    not null,
    academic       numeric(4, 0) not null,
    semester   varchar(6)    not null,
    CRN        numeric(5, 0) not null,
    grade      varchar(2),
    check ( grade in ('A+', 'A', 'A-', 'B+', 'B', 'B-', 'C+', 'C', 'C-', 'D+', 'D', 'D-', 'F', 'S', 'P') ),
    primary key (student_id, academic, semester, CRN),
    foreign key (student_id) references Student (student_id),
    foreign key (academic, semester, CRN) references Course (academic, semester, CRN)
)charset=UTF8;
create table Teaches
(
    professor_id varchar(8)    not null,
    academic     numeric(4, 0) not null,
    semester     varchar(6)    not null,
    CRN          numeric(5, 0) not null,
    primary key (professor_id, academic, semester, CRN),
    foreign key (professor_id) references Professor (professor_id),
    foreign key (academic, semester, CRN) references Course (academic, semester, CRN)
)charset=UTF8;
create table TA
(
    student_id varchar(8)    not null,
    academic       numeric(4, 0) not null,
    semester   varchar(6)    not null,
    CRN        numeric(5, 0) not null,
    primary key (student_id, academic, semester, CRN),
    foreign key (student_id) references Student (student_id),
    foreign key (academic, semester, CRN) references Course (academic, semester, CRN)
)charset=UTF8;

insert into Takes (student_id, academic, semester, CRN, grade)
values ('yuan226', 2020, 'Fall', 11374, null);
insert into Takes(student_id, academic, semester, CRN, grade)
values ('anishiho', 2020, 'Fall', 11374, null);
insert into Takes(student_id, academic, semester, CRN, grade)
values ('su170', 2020, 'Fall', 11374, null);
insert into Takes(student_id, academic, semester, CRN, grade)
values ('jmckeev', 2020, 'Fall', 11374, null);
insert into TA(student_id, academic, semester, CRN)
values ('luo227', 2020, 'Fall', 11374);
insert into Teaches(professor_id, academic, semester, CRN)
values ('hbenotma', 2020, 'Fall', 11374);

create table Assignment
(
    assginment_id      int auto_increment primary key not null,
    academic        numeric(4, 0)                  not null,
    semester    varchar(6)                     not null,
    CRN         numeric(5, 0)                  not null,
    description varchar(30),
    weight      numeric(4, 2),
    due_date    timestamp,
    foreign key (academic, semester, CRN) references Course (academic, semester, CRN)
)charset=UTF8;

insert into Assignment(assginment_id, academic, semester, CRN, description, weight, due_date)
values (default, 2020, 'Fall', 11374, 'project part 1 due', null, 20200923235900);
insert into Assignment(assginment_id, academic, semester, CRN, description, weight, due_date)
values (default, 2020, 'Fall', 11374, 'project part 2 due', null, 20201023000000);
insert into Assignment(assginment_id, academic, semester, CRN, description, weight, due_date)
values (default, 2020, 'Fall', 11374, 'project part 3 due', null, 20201204000000);

create table Team
(
    team_id   int        not null,
    student_id varchar(8) not null,
    assginment_id     int,
    primary key (team_id, student_id, due_id),
    foreign key (student_id) references Student (student_id),
    foreign key (due_id) references Due (due_id)
)charset=UTF8;

insert into Team(team_id, student_id, due_id)
values (1, 'yuan226', 1);
insert into Team(team_id, student_id, due_id)
values (1, 'yuan226', 2);
insert into Team(team_id, student_id, due_id)
values (1, 'yuan226', 3);
insert into Team(team_id, student_id, due_id)
values (1, 'su170', 1);
insert into Team(team_id, student_id, due_id)
values (1, 'su170', 2);
insert into Team(team_id, student_id, due_id)
values (1, 'su170', 3);
insert into Team(team_id, student_id, due_id)
values (1, 'anishiho', 1);
insert into Team(team_id, student_id, due_id)
values (1, 'anishiho', 2);
insert into Team(team_id, student_id, due_id)
values (1, 'anishiho', 3);
insert into Team(team_id, student_id, due_id)
values (1, 'jmckeev', 1);
insert into Team(team_id, student_id, due_id)
values (1, 'jmckeev', 2);
insert into Team(team_id, student_id, due_id)
values (1, 'jmckeev', 3);

create table Friend
(
    main   varchar(8) not null,
    target varchar(8) not null,

    primary key (main, target),
    foreign key (main) references Student (student_id),
    foreign key (target) references Student (student_id)
)charset=UTF8;
insert into Friend(main, target)
values ('yuan226', 'su170');
insert into Friend(main, target)
values ('yuan226', 'anishiho');
COMMIT;
