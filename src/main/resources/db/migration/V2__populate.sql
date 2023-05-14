INSERT INTO roles (id, name) VALUES ('822ef4f8-7c1d-4c5d-9485-0e0a9fc379d4','ROLE_ADMIN');

INSERT INTO users (id, email, first_name, last_name, "password") 
VALUES ('4f77a61f-8a46-4199-ac9a-a9314dbe75cb', 'admin', 'admin', 'admin',
       '$2a$12$zZpdOgATJQce9b38E3EDkeWH2Eljxsk7IVaGYmSgRtrkNITgtAsUO');

INSERT INTO user_role_relation (role_id, user_id)
VALUES ('822ef4f8-7c1d-4c5d-9485-0e0a9fc379d4', '4f77a61f-8a46-4199-ac9a-a9314dbe75cb');