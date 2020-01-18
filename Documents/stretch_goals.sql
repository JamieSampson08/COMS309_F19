SET SQL_SAFE_UPDATES = 0;
SET foreign_key_checks = 0;

drop table if exists user;
drop table if exists event;
drop table if exists user_event;
drop table if exists location;
drop table if exists mastery;
drop table if exists friends;

CREATE TABLE user (
	user_id INT AUTO_INCREMENT, 
	first_name VARCHAR(100), 
	last_name VARCHAR(100), 
	email VARCHAR(100),
	mastery_name varchar(30),

	PRIMARY KEY (user_id),
	KEY fk_mastery (mastery_name),
	CONSTRAINT fk_mastery FOREIGN KEY (mastery_name) references mastery(name)
);
CREATE TABLE event (
	event_id INT AUTO_INCREMENT, 
	name VARCHAR(100), 
	description VARCHAR(1000), 
	start_date_time TIMESTAMP, 
	end_date_time TIMESTAMP,
	category varchar(50),
	location_address varchar(50),
	location_zip_code int,

	PRIMARY KEY (event_id),
	KEY fk_location(location_address, location_zip_code),
	CONSTRAINT fk_location FOREIGN KEY (location_address, location_zip_code) references location(address, zip_code)
);
CREATE TABLE user_event (
	user_event_id INT AUTO_INCREMENT,
	user_id INT NOT NULL, 
	event_id INT NOT NULL, 
	is_admin BIT, 
	is_checked_in BIT, 
	PRIMARY KEY (user_event_id),
	KEY fk_user (user_id),
	KEY fk_event (event_id),
	CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user (user_id),
	CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES event (event_id)

);

CREATE TABLE location (
    -- street number & route
    address varchar(50),
    -- political
    state varchar(30),
    -- locality
    city varchar(50),
    -- postal code
    zip_code int,

    PRIMARY KEY (address, zip_code)
);

CREATE TABLE mastery (
  name varchar(30),
  level int,
  points int,

  PRIMARY KEY(name)
);

CREATE TABLE friends (
    user_one INT NOT NULL,
    user_two INT NOT NULL,

    PRIMARY KEY (user_one, user_two),
    FOREIGN KEY (user_one) references user(user_id),
    FOREIGN KEY (user_two) references user(user_id)
);

SHOW TABLES