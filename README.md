# redis-db-cache
Redis database cache CURD operations

#sample sql script

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(5) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(200) NOT NULL,
  `user_role` varchar(100) NOT NULL,
  PRIMARY KEY (`user_id`)
);
