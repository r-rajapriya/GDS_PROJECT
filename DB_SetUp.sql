CREATE SCHEMA `gds_project` ;

CREATE TABLE `gds_project`.`user_master` (
  `user_id` VARCHAR(10) NOT NULL,
  `user_name` VARCHAR(60) NOT NULL,
  `password` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`user_id`));

CREATE TABLE `gds_project`.`session_master` (
  `session_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `session_name` VARCHAR(60) NOT NULL,
  `event_date` DATETIME NOT NULL,
  `session_status` VARCHAR(10) NOT NULL,
  `created_by` VARCHAR(10) NOT NULL,
  `start_date` DATETIME NOT NULL,
  `end_date` DATETIME NULL,
  `selected_restaurant` VARCHAR(60) NULL,
  `invite_all` VARCHAR(10) NULL,
  PRIMARY KEY (`session_id`),
  FOREIGN KEY (created_by) REFERENCES user_master(user_id),
  UNIQUE INDEX `session_name_UNIQUE` (`session_name` ASC));

CREATE TABLE `gds_project`.`user_choice` (
  `choice_id` VARCHAR(60) NOT NULL,    
  `user_id` VARCHAR(10) NOT NULL,
  `session_id` BIGINT UNSIGNED NOT NULL,
  `join_date` DATETIME NULL,
  `restaurant_name` VARCHAR(60) NULL, 
  `submit_date` DATETIME NULL,
  PRIMARY KEY (choice_id),
  FOREIGN KEY (user_id) REFERENCES user_master(user_id),
  FOREIGN KEY (session_id) REFERENCES session_master(session_id));

INSERT INTO `gds_project`.`user_master` (`user_id`, `user_name`, `password`) VALUES ('JOHN', 'JOHN MATHEW', '098f6bcd4621d373cade4e832627b4f6');
INSERT INTO `gds_project`.`user_master` (`user_id`, `user_name`, `password`) VALUES ('MARY', 'MARY DIANA', '098f6bcd4621d373cade4e832627b4f6');
INSERT INTO `gds_project`.`user_master` (`user_id`, `user_name`, `password`) VALUES ('ALEX', 'ALEX PETERSON', '098f6bcd4621d373cade4e832627b4f6');
INSERT INTO `gds_project`.`user_master` (`user_id`, `user_name`, `password`) VALUES ('ROSY', 'ROSY PREETHI', '098f6bcd4621d373cade4e832627b4f6');

commit;