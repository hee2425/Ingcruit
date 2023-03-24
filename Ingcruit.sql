create Table Member
(
    MemberID varchar2(20) not null primary key,
    MemberName varchar2(20) not null,
    MemberPw varchar2(20) not null,
    JobID varchar2(10),
    PhoneNumber varchar2(20) Unique not null
);

create Table Company(
    CompanyID number(6,0) not null primary key,
    CompanyName varchar(30) not null,
    Grade number(1,0) default 1 not null,
    City varchar2(30) not null
);

create Table Recruitment(
    RecuitID number(6,0) not null primary key,
    JobID varchar(10) not null,
    DeadlineDate DATE not null,
    rCount number(3,0) not null,
    TypeOfTalent varchar2(20),
    FP char(2) default 'F' not null,
    
    CompanyID number(6,0) not null,
    constraint fk_comid foreign key(CompanyID) references Company(CompanyID)
);

create Table rPast(
    RecuitID number(6,0) not null primary key,
    JobID varchar(10) not null,
    DeadlineDate DATE not null,
    rCount number(3,0) not null,
    TypeOfTalent varchar2(20),
    FP char(2) default 'F' not null,
    
    CompanyID number(6,0) not null,
    constraint fk_comid foreign key(CompanyID) references Company(CompanyID)
);

create Table Apply(
    MemberID varchar2(20) not null,
    constraint fk_memid foreign key(MemberID) references Member(MemberID),
    
    RecuitID number(6,0) not null,
    constraint fk_remid foreign key(RecuitID) references Recruitment(RecuitID)
);
     