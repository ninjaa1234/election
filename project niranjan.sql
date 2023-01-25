CREATE TABLE candidates (
candidate_id varchar(255) PRIMARY KEY,
name VARCHAR(255),
party_id INT,
constituency VARCHAR(255),
email VARCHAR(255),
phone VARCHAR(255),
FOREIGN KEY (party_id) REFERENCES party(party_id)
);

CREATE TABLE voters (
voter_id VARCHAR(255) PRIMARY KEY,
name VARCHAR(255),
password VARCHAR(255),
age int,
constituency VARCHAR(255),
email VARCHAR(255),
phone VARCHAR(255),
voted BOOLEAN
);

CREATE TABLE votes (
    vote_id INT PRIMARY KEY unique,
    voter_id VARCHAR(255),
    candidate_id VARCHAR(255),
	vote_date DATE,
    vote_time TIME,
    FOREIGN KEY (voter_id) REFERENCES voters(voter_id),
    FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id)
);
ALTER TABLE votes ADD CONSTRAINT chk_constituency CHECK (EXISTS (SELECT 1 FROM candidates c JOIN voters v ON c.constituency = v.constituency WHERE c.candidate_id = candidate_id AND v.voter_id = voter_id));

CREATE TABLE vote_count (
    candidate_id INT,
    vote_count INT,
    constituency VARCHAR(255),
	vote_count_date DATE,
    vote_count_time TIME,
    PRIMARY KEY (candidate_id),
    FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id)
);

CREATE TABLE party (
    party_id INT,
    party_name varchar(255) UNIQUE,
    party_president VARCHAR(255),
    party_headquarter varchar(255),
	party_sign varchar(255) UNIQUE,
    PRIMARY KEY (party_id)
);

CREATE TABLE admin(
	username varchar(255),
	password varchar(255)
);
insert into admin values('niranjan','pass');
insert into admin values ('allen','pass');
insert into admin values('devan','pass');
insert into admin values ('anjana','pass');



drop table voters CASCADE;
drop table candidates CASCADE;
drop table party CASCADE;
drop table votes CASCADE;
drop table admin CASCADE;



SELECT * from voters;
SELECT * from candidates;
select * from votes;
select * from party;

INSERT INTO votes (voter_id)
SELECT voter_id
FROM voters
WHERE voted = true;

