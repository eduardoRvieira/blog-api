-- Exemplo de inserção de usuários com senha '12345' encriptada
INSERT INTO usuario(username, password, role, enabled)
VALUES('admin', '$2a$10$Y7VYLJQJnOdqs13qO2AvjeqJ8ZLmKPzCGzdHtn7aGSECzSvAGsKCy', 'ADMIN', true);

INSERT INTO usuario(username, password, role, enabled)
VALUES('usuario', '$2a$10$Y7VYLJQJnOdqs13qO2AvjeqJ8ZLmKPzCGzdHtn7aGSECzSvAGsKCy', 'USER', true);

