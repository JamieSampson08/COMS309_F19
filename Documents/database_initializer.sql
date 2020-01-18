SET SQL_SAFE_UPDATES = 0;
SET foreign_key_checks = 0;

drop table if exists user;
drop table if exists event;
drop table if exists user_event;
drop table if exists mastery;

CREATE TABLE user (
	user_id INT AUTO_INCREMENT,
	first_name VARCHAR(100),
	last_name VARCHAR(100),
	email VARCHAR(100),
	profile_file_name VARCHAR(100),
	points INT,
	PRIMARY KEY (user_id)
);
CREATE TABLE event (
	event_id INT AUTO_INCREMENT,
	name VARCHAR(100),
	description VARCHAR(1000),
	start_date_time TIMESTAMP,
	end_date_time TIMESTAMP,
	location VARCHAR(100),
	PRIMARY KEY (event_id)
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
CREATE TABLE mastery (
    mastery_id INT AUTO_INCREMENT,
    mastery_name VARCHAR(100) NOT NULL,
    points_needed INT NOT NULL,
    max_points INT NOT NULL,
    register_points INT NOT NULL,
    check_in_points INT NOT NULL,
    create_event_points INT NOT NULL,
    PRIMARY KEY(mastery_id)
);
SHOW TABLES