ALTER TABLE `admins` MODIFY COLUMN `password` varchar(90) NOT NULL DEFAULT 0;
ALTER TABLE `clients` MODIFY COLUMN `password` varchar(90) NOT NULL DEFAULT 0;