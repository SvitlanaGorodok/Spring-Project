INSERT INTO public.roles (id, name)
VALUES ('4e3c27be-76de-496a-bed2-fb2dcb71ab7a', 'ROLE_USER');
INSERT INTO public.roles (id, name)
VALUES ('072f378d-a7a8-44db-856a-4044668dfbe3', 'ROLE_ADMIN');

INSERT INTO public.users (id, email, password)
VALUES ('e9072bc9-9402-45eb-8e9a-cc088692879c', 'admin',
        '$2y$14$aaOEEYLC71E/hk8DlVetDuCRUmzsKrv4f699qCyakJB4.hYSML7n2');

INSERT INTO public.user_role_relation (role_id, user_id)
VALUES ('4e3c27be-76de-496a-bed2-fb2dcb71ab7a', 'e9072bc9-9402-45eb-8e9a-cc088692879c');
INSERT INTO public.user_role_relation (role_id, user_id)
VALUES ('072f378d-a7a8-44db-856a-4044668dfbe3', 'e9072bc9-9402-45eb-8e9a-cc088692879c');
