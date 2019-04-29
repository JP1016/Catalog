CREATE TABLE `Catalog` (
`id` INT NOT NULL,
`user_id` INT NULL,
`order_code` VARCHAR(45) NULL,
`order_product` VARCHAR(45) NULL,
`order_description` VARCHAR(45) NULL,
PRIMARY KEY (`id`));
