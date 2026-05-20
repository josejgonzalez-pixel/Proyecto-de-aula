USE finanziapp;

CREATE TABLE Usuario (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL
);

CREATE TABLE Categoria (
    idCategoria INT AUTO_INCREMENT PRIMARY KEY,
    nombreCategoria VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL
);

CREATE TABLE Transaccion (
    idTransaccion INT AUTO_INCREMENT PRIMARY KEY,
    monto DOUBLE NOT NULL,
    fecha DATE NOT NULL,
    descripcion VARCHAR(255),
    idUsuario INT NOT NULL,
    idCategoria INT NOT NULL,

    CONSTRAINT fk_transaccion_usuario
    FOREIGN KEY (idUsuario)
    REFERENCES Usuario(idUsuario),

    CONSTRAINT fk_transaccion_categoria
    FOREIGN KEY (idCategoria)
    REFERENCES Categoria(idCategoria)
);

CREATE TABLE Ingreso (
    idTransaccion INT PRIMARY KEY,
    fuente VARCHAR(100),

    CONSTRAINT fk_ingreso_transaccion
    FOREIGN KEY (idTransaccion)
    REFERENCES Transaccion(idTransaccion)
    ON DELETE CASCADE
);

CREATE TABLE Gasto (
    idTransaccion INT PRIMARY KEY,
    tipoPago VARCHAR(50),

    CONSTRAINT fk_gasto_transaccion
    FOREIGN KEY (idTransaccion)
    REFERENCES Transaccion(idTransaccion)
    ON DELETE CASCADE
);

CREATE TABLE Meta (
    idMeta INT AUTO_INCREMENT PRIMARY KEY,
    nombreMeta VARCHAR(100) NOT NULL,
    montoMeta DOUBLE NOT NULL,
    montoActual DOUBLE DEFAULT 0,
    fechaLimite DATE,
    idUsuario INT NOT NULL,

    CONSTRAINT fk_meta_usuario
    FOREIGN KEY (idUsuario)
    REFERENCES Usuario(idUsuario)
);

CREATE TABLE Presupuesto (
    idPresupuesto INT AUTO_INCREMENT PRIMARY KEY,
    montoInicial DOUBLE NOT NULL,
    montoActual DOUBLE NOT NULL,
    fechaCreacion DATE NOT NULL,
    idUsuario INT NOT NULL,

    CONSTRAINT fk_presupuesto_usuario
    FOREIGN KEY (idUsuario)
    REFERENCES Usuario(idUsuario)
);

CREATE TABLE Reporte (
    idReporte INT AUTO_INCREMENT PRIMARY KEY,
    tipoReporte VARCHAR(100),
    fechaGeneracion DATE,
    idUsuario INT NOT NULL,

    CONSTRAINT fk_reporte_usuario
    FOREIGN KEY (idUsuario)
    REFERENCES Usuario(idUsuario)
);

CREATE TABLE Alerta (
    idAlerta INT AUTO_INCREMENT PRIMARY KEY,
    mensaje VARCHAR(255) NOT NULL,
    fechaAlerta DATE NOT NULL,
    idUsuario INT NOT NULL,

    CONSTRAINT fk_alerta_usuario
    FOREIGN KEY (idUsuario)
    REFERENCES Usuario(idUsuario)
);