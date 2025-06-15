INSERT INTO categorias (nombre)
VALUES
    ('Tecnología'),
    ('Libros'),
    ('Electrodomésticos');

INSERT INTO carreras (nombre, codigo)
VALUES
    ('Ingeniería de Sistemas', 'IS01'),
    ('Administración de Empresas', 'AE01');

INSERT INTO cursos (nombre, codigo)
VALUES
    ('Estructuras de Datos', 'CS201'),
    ('Contabilidad Financiera', 'AF101'),
    ('Programación Avanzada', 'CS301');

INSERT INTO cursos_carreras (id_curso, id_carrera)
VALUES
    (1, 1), -- Estructuras de Datos + Ing. Sistemas
    (3, 1), -- Programación Avanzada + Ing. Sistemas
    (2, 2); -- Contabilidad Financiera + Administración

INSERT INTO roles (nombre) VALUES ('ADMIN');
INSERT INTO roles (nombre) VALUES ('USER');
INSERT INTO roles (nombre) VALUES ('SELLER');