USE finanziapp;

INSERT INTO Usuario (nombre, correo, contrasena)
VALUES 
('Camila Padilla', 'camila@gmail.com', '1234'),
('Juan Perez', 'juan@gmail.com', '1325');

INSERT INTO Categoria (nombreCategoria, tipo)
VALUES 
('Salario', 'Ingreso'),
('Freelance', 'Ingreso'),
('Alimentación', 'Gasto'),
('Transporte', 'Gasto'),
('Servicios', 'Gasto');

INSERT INTO Transaccion (monto, fecha, descripcion, idUsuario, idCategoria)
VALUES 
(2000000, '2026-01-01', 'Pago mensual', 1, 1),
(50000, '2026-01-02', 'Compra supermercado', 1, 3);

INSERT INTO Ingreso (idTransaccion, fuente)
VALUES 
(1, 'Empresa');

INSERT INTO Gasto (idTransaccion, tipoPago)
VALUES 
(2, 'Efectivo');

INSERT INTO Meta (nombreMeta, montoMeta, montoActual, fechaLimite, idUsuario)
VALUES 
('Comprar laptop', 3000000, 500000, '2026-12-31', 1);

INSERT INTO Presupuesto (montoInicial, montoActual, fechaCreacion, idUsuario)
VALUES 
(1000000, 850000, '2026-01-01', 1);

INSERT INTO Reporte (tipoReporte, fechaGeneracion, idUsuario)
VALUES 
('Mensual', '2026-01-05', 1);

INSERT INTO Alerta (mensaje, fechaAlerta, idUsuario)
VALUES 
('Has superado el 80% del presupuesto', '2026-01-10', 1);