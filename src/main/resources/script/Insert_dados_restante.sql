-- RODAR DEPOIS DE SUBIR O SISTEMA E CHAMAR TODOS OS METODOS QUE POPULA A BASE --

INSERT INTO cliente (id, longitude, latitude) VALUES (1000,'-51.15231602','-30.0416058');
ALTER SEQUENCE saeq_cliente RESTART WITH 1000;

INSERT INTO motoboy (id, longitude, latitude) VALUES (1000,'-51.17648844','-30.12000696');
ALTER SEQUENCE saeq_motoboy RESTART WITH 1000;
