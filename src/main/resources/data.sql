INSERT INTO `warehouses` (`id`, `warehouse_code`) VALUES ('1', 'WAREHOUSE_TESTE');

INSERT INTO `sections` (`id`, `section_code`) VALUES ('1', "ELETRONICOS");
INSERT INTO `sections` (`id`, `section_code`) VALUES ('2', "FRESCOS");
INSERT INTO `sections` (`id`, `section_code`) VALUES ('3', "ROUPAS");

INSERT INTO `warehouse_section` (`warehouse_id`, `section_id`, `id`) VALUES ('1', '1', '1');
INSERT INTO `warehouse_section` (`warehouse_id`, `section_id`, `id`) VALUES ('1', '2', '2');
INSERT INTO `warehouse_section` (`warehouse_id`, `section_id`, `id`) VALUES ('1', '3', '3');

INSERT INTO `fresh_products` (`id`, `product_id`,`min_temp`, `manufacturing_date`, `manufacturing_time`, `due_date`)
VALUES ('1','CARNE', '-10.0', curdate(), curtime(), '2021-07-25');
INSERT INTO `fresh_products` (`id`, `product_id`,`min_temp`, `manufacturing_date`, `manufacturing_time`, `due_date`)
VALUES ('2', 'QUEIJO', '5.0', curdate(), curtime(), '2021-08-25');
INSERT INTO `fresh_products` (`id`, `product_id`, `min_temp`, `manufacturing_date`, `manufacturing_time`, `due_date`)
VALUES ('3','IOGURTE', '1.0', curdate(), curtime(), '2021-07-15');

INSERT INTO `stocks` (`id`, `init_quantity`, `cur_quantity`, `cur_temp`, `product_id`, `warehouse_section_id`)
VALUES('1', '100', '50','-5.0','1', '2');
INSERT INTO `stocks` (`id`, `init_quantity`, `cur_quantity`, `cur_temp`, `product_id`, `warehouse_section_id`)
VALUES('2', '30', '20','3.0','2', '2');
INSERT INTO `stocks` (`id`, `init_quantity`, `cur_quantity`, `cur_temp`, `product_id`, `warehouse_section_id`)
VALUES('3', '10', '5','0.0','3', '2');

INSERT INTO `user_roles` (`id`, `role_code`) VALUES ('1', 'ADMIN');
INSERT INTO `user_roles` (`id`, `role_code`) VALUES ('2', 'BUYER');
INSERT INTO `user_roles` (`id`, `role_code`) VALUES ('3', 'SELLER');
INSERT INTO `user_roles` (`id`, `role_code`) VALUES ('4', 'REP');

INSERT INTO `country_houses` (`id`, `country`, `name`) VALUES ('1', 'Argentina', 'Casa central de Argentina');
INSERT INTO `country_houses` (`id`, `country`, `name`) VALUES ('2', 'Chile', 'Casa central de Chile');
INSERT INTO `country_houses` (`id`, `country`, `name`) VALUES ('3', 'Uruguay', 'Casa central de Uruguay');
INSERT INTO `country_houses` (`id`, `country`, `name`) VALUES ('4', 'Colombia', 'Casa central de Colombia');

INSERT INTO `user_accounts` (`id`, `username`, `password`, `role_id`, `country_house_id`) VALUES ('1', 'testAdmin', 'teste1000', '1', '1');
INSERT INTO `user_accounts` (`id`, `username`, `password`, `role_id`, `country_house_id`) VALUES ('2', 'testBuyer', 'teste1000', '2', '2');
INSERT INTO `user_accounts` (`id`, `username`, `password`, `role_id`, `country_house_id`) VALUES ('3', 'testSeller', 'teste1000', '3', '3');
INSERT INTO `user_accounts` (`id`, `username`, `password`, `role_id`, `country_house_id`) VALUES ('4', 'testRep', 'teste1000', '4', '4');