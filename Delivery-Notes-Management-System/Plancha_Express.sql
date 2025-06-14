CREATE TABLE CLIENTES (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50),
    telefono VARCHAR (10),
    direccion VARCHAR(100)
);

CREATE TABLE SERVICIOS (
    id INT PRIMARY KEY AUTO_INCREMENT,
    descripción VARCHAR(200),
    precio float
);

CREATE TABLE USUARIOS (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50),
    pass VARCHAR(8)
);

CREATE TABLE NOTASREMISION (
    folio INT PRIMARY KEY AUTO_INCREMENT,
    cliente_id INT,
    servicio_id INT,
	usuario_id INT,
	fechaRecepcion datetime, 
	fechaEntrega datetime,
    FOREIGN KEY (cliente_id) REFERENCES CLIENTES(id),
    FOREIGN KEY (servicio_id) REFERENCES SERVICIOS(id),
    FOREIGN KEY (usuario_id) REFERENCES USUARIOS(id)
);

CREATE TABLE NOTA_SERVICIO (
    id INT PRIMARY KEY AUTO_INCREMENT,
    servicio_id INT,
    notaremision_folio INT,
    cant INT(2),
    detalle VARCHAR(100),
    precio float,
    perdidas float,
    FOREIGN KEY (servicio_id) REFERENCES SERVICIOS(id),
    FOREIGN KEY (notaremision_folio) REFERENCES NOTASREMISION(folio)
);

INSERT INTO USUARIOS(id, nombre, pass)
VALUES(1, 'admin', 'admin');

