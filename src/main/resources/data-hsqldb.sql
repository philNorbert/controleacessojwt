/*
INSERT INTO authority (id,name) VALUES (NEXT VALUE FOR authority_seq,'Cadastro');
INSERT INTO role (id,name) VALUES (NEXT VALUE FOR role_seq,'ROLE_ADMIN');
INSERT INTO role (id,name) VALUES (NEXT VALUE FOR role_seq,'ROLE_DEFAULT');
INSERT INTO user (id,username,password, first_name,last_name,email,enabled,last_password_reset_date)
VALUES (NEXT VALUE FOR user_seq, 'admin', 'admin', 'Admin', 'Admin', 'admin@admin.com.br', TRUE, CURRENT_TIMESTAMP);
INSERT INTO user_role (USER_ID, ROLE_ID) VALUES ('1', '1');
--INSERT INTO role_authority (ROLE_ID, AUTHORITY_ID) VALUES ('1', '1');
*/
