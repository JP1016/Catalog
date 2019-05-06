CREATE TABLE `Catalog` (
`id` INT NOT NULL AUTO_INCREMENT,
`user_id` INT NULL,
`order_code` VARCHAR(45) NULL,
`order_product` VARCHAR(45) NULL,
`order_description` VARCHAR(45) NULL,
PRIMARY KEY (`id`));

CREATE TABLE `User` (
`id` INT NOT NULL AUTO_INCREMENT,
`first_name` VARCHAR(45) NULL,
`last_name` VARCHAR(45) NULL,
`dob` DATETIME NULL,
PRIMARY KEY (`id`));
