INSERT INTO roles (id, name)
VALUES ('822ef4f8-7c1d-4c5d-9485-0e0a9fc379d4','ROLE_ADMIN');

INSERT INTO users (id, email, first_name, last_name, "password", role_id)
VALUES ('4f77a61f-8a46-4199-ac9a-a9314dbe75cb', 'admin', 'admin', 'admin',
       '$2a$12$zZpdOgATJQce9b38E3EDkeWH2Eljxsk7IVaGYmSgRtrkNITgtAsUO', '822ef4f8-7c1d-4c5d-9485-0e0a9fc379d4');

INSERT INTO manufacturers (id, name)
VALUES ('3a3c2995-e3bc-49f9-8935-7ab63e0ed85d', 'Taras Shevchenko'),
       ('628f8f9a-ad0f-49d2-92ce-a2e35429c367','Lesya Ukrainka'),
       ('b68e2bcf-a6f3-41d2-aaac-4d619be97b0f','Ivan Franko');

INSERT INTO products (id, name, price, manufacturer_id)
VALUES ('d63c2c3b-2e20-47eb-a439-738a9758ecce', 'Kobzar', 1000 ,'3a3c2995-e3bc-49f9-8935-7ab63e0ed85d'),
('059aa690-347c-4ede-9ebf-cf00415c955c', 'Lisova pisnia', 1000, '628f8f9a-ad0f-49d2-92ce-a2e35429c367'),
('a903cd2c-3113-4f47-bd60-bc560e1168dd', 'Farbovanyi lys', 1000, 'b68e2bcf-a6f3-41d2-aaac-4d619be97b0f');