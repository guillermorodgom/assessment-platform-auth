-- Credenciales seed (todas usan password123):
--   admin      / password123  (ADMIN)
--   candidato1 / password123  (CANDIDATO)
--   candidato2 / password123  (CANDIDATO)

INSERT INTO usuarios (username, password, email, nombre_completo) VALUES ('admin', '$2a$10$M0pf.89UgE/aBWVf2aXwp.x0sPmLtPjU9JPD0na7VSCn8yWNl60ma', 'admin@assessment.com', 'Administrador');
INSERT INTO user_roles (usuario_id, rol) VALUES (1, 'ADMIN');

INSERT INTO usuarios (username, password, email, nombre_completo) VALUES ('candidato1', '$2a$10$M0pf.89UgE/aBWVf2aXwp.x0sPmLtPjU9JPD0na7VSCn8yWNl60ma', 'candidato1@test.com', 'Juan Perez');
INSERT INTO user_roles (usuario_id, rol) VALUES (2, 'CANDIDATO');

INSERT INTO usuarios (username, password, email, nombre_completo) VALUES ('candidato2', '$2a$10$M0pf.89UgE/aBWVf2aXwp.x0sPmLtPjU9JPD0na7VSCn8yWNl60ma', 'candidato2@test.com', 'Maria Garcia');
INSERT INTO user_roles (usuario_id, rol) VALUES (3, 'CANDIDATO');
