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
CREATE FUNCTION check_constituency() RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM candidates c JOIN voters v ON c.constituency = v.constituency WHERE c.candidate_id = NEW.candidate_id AND v.voter_id = NEW.voter_id) THEN
        RAISE EXCEPTION 'Voter and candidate must have the same constituency';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_constituency_trigger
BEFORE INSERT ON votes
FOR EACH ROW
EXECUTE FUNCTION check_constituency();

INSERT INTO votes (voter_id)
SELECT voter_id
FROM voters
WHERE voted = true;

CREATE TABLE vote_count (
    candidate_id varchar (255),
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


INSERT into vote_count VALUES
('1',3, 'West','2022-12-16','14:00'),
('2',5, 'East','2022-12-16','14:01'),
('3',6, 'North','2022-12-17','14:02'),
('4',6, 'South','2022-12-17','14:03'),
('5',1, 'East','2022-12-16','14:01'),
('6',2, 'North','2022-12-17','14:02'),
('7',3, 'West','2022-12-16','14:00'),
('8',1, 'South','2022-12-17','14:03'),
('9',3, 'West','2022-12-16','14:00'),
('10',2, 'South','2022-12-17','14:03'),
('11',3, 'North','2022-12-17','14:02'),
('12',2, 'East','2022-12-16','14:01');


insert into admin values('niranjan','pass');
insert into admin values ('allen','pass');
insert into admin values('devan','pass');
insert into admin values ('anjana','pass');

select * from voters where voted = TRUE;

INSERT INTO voters (voter_id, name, password, age, constituency, email, phone, voted)
VALUES
(1, 'Shawn James','1', 30, 'West', 'shawnj@example.com', '+1234567896', FALSE),
(2, 'Vishnu Sharma','2', 40, 'East', 'vishnus@example.com', '+0987654327',FALSE),
(3, 'Anandu Suresh','3', 50, 'North', 'anandus@example.com', '+1171121111',TRUE),
(4, 'Rakesh Roshan','4', 27, 'South', 'rakeshr@example.com', '+1115121111',TRUE),
(5, 'Karthik Sajeev','5', 19, 'East', 'karthig@example.com', '+1111121111',FALSE),
(6, 'Kavya Krishna','6', 27, 'West', 'kavyak@example.com', '+111113111',TRUE),
(7, 'Aswin Suresh','7', 53, 'South', 'aswins@example.com', '+1111121911',TRUE),
(8, 'James Samuel','8', 33 , 'South', 'samueljames@example.com', '+1234522556',TRUE),
(9, 'Vishal Sharma','9', 29, 'West', 'vishals@example.com', '+0987654337',FALSE),
(10, 'Ananthu Suresh','10', 39, 'North', 'ananthuuu@example.com', '+9171121111',TRUE),
(11, 'Ajmal  Roshan','11', 27, 'North', 'ajumal@example.com', '+1195125511',TRUE),
(12, 'Karthik Surya','12', 19, 'East', 'karthiksuryag@example.com', '+1111121961',FALSE),
(13, 'Gopalakrishnan','13', 27, 'North', 'gopalk@example.com', '+221113111',TRUE),
(14, 'Aswin krishna','14', 53, 'East', 'aswinsk@example.com', '+2311121911',TRUE),
(15, 'James Vardy','15', 60 , 'South', 'vardy@example.com', '+1324567896',FALSE),
(16, 'Jayashankar M','16', 44, 'East', 'jayashankar@example.com', '+0958964327',FALSE),
(17, 'Suresh Babu','17', 36, 'East', 'suresh@example.com', '+1171121456',TRUE),
(18, 'Samuel Jhon','18', 24, 'West', 'samuel@example.com', '+1115124561',TRUE),
(19, 'Sanjeev Ravikumar','19', 49, 'East', 'sanjeev@example.com', '+1111456111',FALSE),
(20, 'Aparna Balamurali','20', 26, 'North', 'balamurali@example.com', '+122213111',FALSE),
(21, 'Kuttikrishnan','21', 63, 'South', 'kutti@example.com', '+1221121911',TRUE),
(22, 'Rahul Pandit','22', 34, 'West', 'rahul@example.com', '+9134567896',TRUE),
(23, 'Anjana A','23', 19, 'North', 'anjaa@example.com', '+9987652327',TRUE),
(24, 'Allen Kammanghat','24', 21, 'North', 'allen@example.com', '+1115121581',TRUE),
(25, 'Niranjan Nambiar','25', 20, 'East', 'ninja@example.com', '+1111125631',FALSE),
(26, 'Sajith S','26', 53, 'West', 'sajiettan@example.com', '+984113111',TRUE),
(27, 'Anil Kumar','27', 53, 'North', 'anil@example.com', '+1764121911',TRUE),
(28, 'Nivin Pauly','28', 44, 'West', 'nivii@example.com', '+1235794896',FALSE),
(29, 'Sreekumar ','29', 40, 'East', 'sree@example.com', '+9495864327',TRUE),
(30, 'Balashekar','30', 50, 'North', 'bala@example.com', '+1171125871',TRUE),
(31, 'Ranjith S','31', 27, 'South', 'ranju@example.com', '+7485121111',FALSE),
(32, 'Beena A','32', 41, 'East', 'beena@example.com', '+6611121111',TRUE),
(33, 'Divya Sajith','33', 27, 'West', 'divyak@example.com', '+666113111',TRUE),
(34, 'Tony Croos','34', 35, 'South', 'croos@example.com', '+7711121911',TRUE),
(35, 'Lijo Jose','35', 36, 'West', 'lijoj@example.com', '+1234567896',FALSE),
(36, 'Tinu Papachan','36', 31, 'East', 'tinu@example.com', '+7654309827',TRUE),
(37, 'Lokesh Kanagaraj','37', 29 , 'North', 'lokii@example.com', '+1171120981',TRUE),
(38, 'Gothum ','38', 27, 'South', 'gouthuu@example.com', '+0985121111',FALSE),
(39, 'Karthik Subbaraj','39', 19  , 'East', 'subbarajg@example.com', '+1110981098',FALSE),
(40, 'Anjali Menon','40', 27  , 'West', 'anjali@example.com', '+111113098',TRUE),
(41, 'Vipin Das','41', 53,  'South', 'vipin@example.com', '+0981121911',TRUE),
(42, 'Dileesh Pothan','42', 30, 'West', 'dileesh@example.com', '+0984098896',TRUE),
(43, 'Shyam Pushkaran','43', 40, 'East', 'shyam@example.com', '+0987654327',TRUE),
(44, 'Fahad Fassil','44', 50,  'North', 'fafa@example.com', '+0980981111',TRUE),
(45, 'Martin Prakat','45', 27 , 'South', 'martin@example.com', '+8515120981',FALSE),
(46, 'Geetu Mohandas','46', 19, 'East', 'geetu@example.com', '+1109821098',TRUE),
(47, 'Mani Ratnam','47', 27, 'West', 'mani@example.com', '+110983111',TRUE),
(48, 'Mari Selvaraj','48', 53 , 'South', 'selva@example.com', '+1098121991',FALSE),
(49, 'Mahesh Narayanan','49', 30, 'West', 'mahesh@example.com', '+1234097896',FALSE),
(50, 'Kamal','50', 40, 'East', 'kamal@example.com', '+0987640927',FALSE),
(51, 'Sibi Malayil','51', 50, 'North', 'sibi@example.com', '+1171124091',TRUE),
(52, 'Jeethu Joseph','52', 27, 'South', 'jeethu@example.com', '+1115124091',TRUE),
(53, 'Amal Neerad','53', 19, 'East', 'amal@example.com', '+1409121111',FALSE),
(54, 'Anwar Rasheed','54', 27, 'West', 'anwar@example.com', '+140913111',TRUE),
(55, 'Basil Joseph','55', 53, 'South', 'basiii@example.com', '+1140921911',FALSE),
(56, 'John Smith', '56',35 , 'West', 'johns@example.com', '+1234567891',FALSE),
(57, 'Emily Davis','57', 25,'North', 'emilyd@example.com', '+0987654328',FALSE),
(58, 'Jacob Wilson','58', 45,'East', 'jacobw@example.com', '+1171121112',TRUE),
(59, 'Michael Brown','59', 32, 'South', 'michaelb@example.com', '+1115121112',TRUE),
(60, 'Devanandan SP','60', 50 , 'South', 'devan@example.com', '+9371121411',TRUE);

INSERT INTO candidates VALUES
(1, 'Devan SP', 1, 'West', 'devansp@example.com', '1234567890'),
(7, 'Durga Lakshmi', 3, 'West', 'durgal@example.com', '0987654325'),
(9, 'Pournami P', 2, 'West', 'pournami@example.com', '0987654925'),
(4, 'Niranjan S Nambiar', 2, 'South', 'niranjans@example.com', '0987654322'),
(10, 'Remya P', 1, 'South', 'remyap@example.com', '0987654221'),
(8, 'Archith Ramesh', 3, 'South', 'archithr@example.com', '0984454322'),
(11, 'Surya Narayanan', 2, 'North', 'suryanl@example.com', '4987654325'),
(3, 'Allen Kammanghat', 3, 'North', 'allenk@example.com', '1111111111'),
(6, 'Bharath  Krishna', 1,'North','bharathv@example.com', '0987654324'),
(12, 'Aiswarya Lakshmi', 3, 'East', 'aiswaryal@example.com', '5987654325'),
(2, 'Anjana A Menon', 2, 'East', 'anjana@example.com', '0987654321'),
(5, 'Joe V', 1, 'East', 'joev@example.com', '0987654323');

INSERT INTO votes VALUES
(1,'3','11','2022-12-12', '13:30'),
(2, '4' ,'4' ,'2022-12-12', '13:30'),
(3, '6' ,'1' ,'2022-12-12', '13:31'),
(4, '7' ,'10' ,'2022-12-12', '13:32'),
(5, '8' ,'8' ,'2022-12-12', '13:33'),
(6, '10' ,'3' ,'2022-12-12', '13:34'),
(7, '11' ,'6' ,'2022-12-12', '13:35'),
(8, '13' ,'11' ,'2022-12-12', '13:36'),
(9, '14' ,'12' ,'2022-12-12', '13:37'),
(10, '17' ,'2' ,'2022-12-12', '13:38'),
(11, '18' ,'1' ,'2022-12-12', '13:39'),
(12, '21' ,'4' ,'2022-12-12', '13:40'),
(13, '22' ,'7' ,'2022-12-12', '13:41'),
(14, '23' ,'3' ,'2022-12-12', '13:42'),
(15, '24' ,'3' ,'2022-12-12', '13:43'),
(16, '26' ,'9' ,'2022-12-12', '13:44'),
(17, '27' ,'3' ,'2022-12-12', '13:45'),
(18, '29' ,'12' ,'2022-12-13', '13:30'),
(19, '30' ,'11' ,'2022-12-13', '13:31'),
(20, '32' ,'2' ,'2022-12-13', '13:32'),
(21, '33' ,'9' ,'2022-12-13', '13:33'),
(22, '34' ,'4' ,'2022-12-13', '13:34'),
(23,'36' ,'2' ,'2022-12-13', '13:35'),
(24, '37' ,'3' ,'2022-12-13', '13:36'),
(25, '40','1' ,'2022-12-13', '13:37'),
(26, '41' ,'10' ,'2022-12-13', '13:38'),
(27, '42' ,'7' ,'2022-12-13', '13:39'),
(28, '43' ,'5','2022-12-13', '13:40'),
(29,'44' ,'3' ,'2022-12-13', '13:41'),
(30, '46' ,'2' ,'2022-12-13', '13:42'),
(31, '47' ,'7' ,'2022-12-13', '13:43'),
(32, '51' ,'6' ,'2022-12-13', '13:44'),
(33, '52' ,'4' ,'2022-12-13', '13:45'),
(34, '54' ,'9' ,'2022-12-13', '13:46'),
(35, '58' ,'2' ,'2022-12-13', '13:47'),
(36, '59' ,'4' ,'2022-12-13', '13:48'),
(37,'60','4','2022-12-13', '13:49');


INSERT INTO party VALUES
(1,'Democratic', 'Devan SP', 'West', 'Pen'),
(2, 'Republican', 'Pournami P', 'North', 'Flag'),
(3, 'Independent','Durga Lakshmi','South', 'Elephant');


drop table voters CASCADE;
drop table candidates CASCADE;
drop table party CASCADE;
drop table votes CASCADE;
drop table admin CASCADE;
drop table vote_count CASCADE ;



SELECT * from voters;
SELECT * from candidates;
select * from votes;
select * from party;
select * from vote_count;










SELECT
    constituency,
    SUM(age) AS total_age,
    AVG(age) AS average_age,
    MIN(age) AS minimum_age,
    MAX(age) AS maximum_age
FROM voters
GROUP BY constituency
HAVING AVG(age) >= 25;

--**********************************************************************


SELECT *
FROM voters
ORDER BY age ASC;

--******************************************************
SELECT c.candidate_id, c.name, p.party_name, v.name
FROM candidates c
JOIN party p ON c.party_id = p.party_id
JOIN voters v ON c.constituency = v.constituency
WHERE c.constituency = 'North'

SELECT v.voter_id, v.name, c.name
FROM voters v
LEFT OUTER JOIN candidates c ON v.constituency = c.constituency
WHERE v.voted = true


--**************************************************************

SELECT v.voter_id, v.name, c.name
FROM voters v
JOIN candidates c ON v.constituency = c.constituency
WHERE v.voted = true AND c.party_id = 1

SELECT c.candidate_id, c.name, p.party_name, v.name
FROM candidates c
JOIN party p ON c.party_id = p.party_id
JOIN voters v ON c.constituency = v.constituency
WHERE NOT c.constituency = 'North'


--*************************************************************


SELECT v.voter_id, v.age + 5 as age_in_5_years
FROM voters v
SELECT v.voter_id, v.age - 5 as age_5_years_ago
FROM voters v
SELECT v.voter_id, v.age * 2 as double_age
FROM voters v
SELECT v.voter_id, v.age / 2 as half_age
FROM voters v
SELECT v.voter_id, v.age % 2 as remainder_when_divided_by_2
FROM voters v

--****************************************************************8

SELECT c.candidate_id, c.name
FROM candidates c
WHERE c.name LIKE 'A%'

SELECT c.candidate_id, c.name
FROM candidates c
WHERE c.name ILIKE '%john%'

SELECT c.candidate_id, c.name || ' (' || c.constituency || ')' as full_name
FROM candidates c

SELECT c.candidate_id, c.name, LENGTH(c.name) as name_length
FROM candidates c

SELECT c.candidate_id, UPPER(c.name) as upper_case_name, LOWER(c.name) as lower_case_name
FROM candidates c


--*******************************************************************************

SELECT c.candidate_id, c.name, to_char(c.constituency, 'FM99999') as formatted_constituency
FROM candidates c

SELECT v.voter_id, v.name, extract(month from v.age) as birth_month
FROM voters v

SELECT v.voter_id, v.name, to_char(v.age, 'FM9999') || ' (' || extract(year from v.age) || ')' as formatted_age
FROM voters v


--***************************************************************************************************************************


SELECT v.voter_id, v.name, v.age
FROM voters v
WHERE v.age BETWEEN 18 AND 25

SELECT v.voter_id, v.name, v.constituency
FROM voters v
WHERE v.constituency IN ('Constituency A', 'Constituency B', 'Constituency C')

SELECT v.voter_id, v.name, v.age
FROM voters v
WHERE v.age NOT BETWEEN 18 AND 25

SELECT v.voter_id, v.name, v.constituency
FROM voters v
WHERE v.constituency NOT IN ('Constituency A', 'Constituency B', 'Constituency C')

--*********************************************************************************************

-- Retrieve the list of all voters who have voted and not voted
WITH voted AS (
  SELECT voter_id, name, constituency, email, phone, 'voted' AS vote_status
  FROM voters
  WHERE voted = true
), not_voted AS (
  SELECT voter_id, name, constituency, email, phone, 'not voted' AS vote_status
  FROM voters
  WHERE voted = false
)
-- Use UNION to combine the results from both queries into one table
SELECT voter_id, name, constituency, email, phone, vote_status
FROM voted
UNION
SELECT voter_id, name, constituency, email, phone, vote_status
FROM not_voted;

--***********************************************************************************************

-- Retrieve the list of all candidates and their parties
-- where the candidate has received at least one vote
SELECT c.candidate_id, c.name, c.party_id, p.party_name
FROM candidates c
JOIN party p ON c.party_id = p.party_id
WHERE EXISTS (
  SELECT 1
  FROM votes v
  WHERE v.candidate_id = c.candidate_id
);

-- Retrieve the list of all candidates and their parties
-- where the candidate has not received any votes
SELECT c.candidate_id, c.name, c.party_id, p.party_name
FROM candidates c
JOIN party p ON c.party_id = p.party_id
WHERE NOT EXISTS (
  SELECT 1
  FROM votes v
  WHERE v.candidate_id = c.candidate_id
);

-- Retrieve the list of all candidates who received votes from voters older than 30
SELECT c.candidate_id, c.name
FROM candidates c
WHERE EXISTS (
  SELECT 1
  FROM votes v
  JOIN voters vo ON v.voter_id = vo.voter_id
  WHERE v.candidate_id = c.candidate_id
  AND vo.age > 30
);

-- Retrieve the list of all candidates who received votes from all voters older than 30
SELECT c.candidate_id, c.name
FROM candidates c
WHERE NOT EXISTS (
  SELECT 1
  FROM voters vo
  WHERE vo.age <= 30
  AND NOT EXISTS (
    SELECT 1
    FROM votes v
    WHERE v.candidate_id = c.candidate_id
    AND v.voter_id = vo.voter_id
  )
);



















