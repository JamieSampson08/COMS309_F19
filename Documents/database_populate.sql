INSERT INTO `eventXpert`.`user` (`user_id`, `first_name`, `last_name`, `email`, `points`, `profile_file_name`) VALUES ('1', 'jamie', 'sampson', 'lilpii991@gmail.com', 100, 'cat_avatar.png');
INSERT INTO `eventXpert`.`user` (`user_id`, `first_name`, `last_name`, `email`, `points`, `profile_file_name`) VALUES ('2', 'eventxpert', 'eventxpert', 'eventxpert333@gmail.com', 0, 'cat_avatar.png');
INSERT INTO `eventXpert`.`user` (`user_id`, `first_name`, `last_name`, `email`, `points`, `profile_file_name`) VALUES ('3', 'Hill', 'Megan', 'meganhill.iowa@gmail.com', 1000, 'cat_avatar.png');

INSERT INTO `eventXpert`.`event` (`event_id`, `name`, `description`, `start_date_time`, `end_date_time`, `location`) VALUES ('1', 'thing1', 'descript2', '2019-10-07 17:31:00', '2019-10-10 20:31:00', 'thing3');
INSERT INTO `eventXpert`.`event` (`event_id`, `name`, `description`, `start_date_time`, `end_date_time`, `location`) VALUES ('2', 'dino', 'apple', '2019-10-07 17:31:00', '2019-10-10 20:31:00', 'thing3');

-- user 2 is admin of event 1
INSERT INTO `eventXpert`.`user_event` (`user_event_id`, `user_id`, `event_id`, `is_admin`, `is_checked_in`) VALUES ('1', '2', '1', b'1', b'0');
-- user 1 is admin of event 2
INSERT INTO `eventXpert`.`user_event` (`user_event_id`, `user_id`, `event_id`, `is_admin`, `is_checked_in`) VALUES ('2', '1', '2', b'1', b'0');

INSERT INTO `eventXpert`.`mastery` (`mastery_name`, `points_needed`, `max_points`, `register_points`, `check_in_points`, `create_event_points`) VALUES ('novice', 0, 99, 10, 10, 10);
INSERT INTO `eventXpert`.`mastery` (`mastery_name`, `points_needed`, `max_points`, `register_points`, `check_in_points`, `create_event_points`) VALUES ('apprentice', 100, 499, 15, 15, 15);
INSERT INTO `eventXpert`.`mastery` (`mastery_name`, `points_needed`, `max_points`, `register_points`, `check_in_points`, `create_event_points`) VALUES ('master', 500, 999, 20, 20, 20);
INSERT INTO `eventXpert`.`mastery` (`mastery_name`, `points_needed`, `max_points`, `register_points`, `check_in_points`, `create_event_points`) VALUES ('eventXpert', 1000, 99999, 30, 30, 30);