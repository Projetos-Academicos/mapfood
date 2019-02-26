--RODAR DEPOIS DE SUBIR O SISTEMA E CHAMAR TODOS OS METODOS QUE POPULA A BASE 

ALTER SEQUENCE seq_cliente RESTART WITH 1000;

ALTER SEQUENCE seq_motoboy RESTART WITH 1000;

ALTER SEQUENCE seq_cidade RESTART WITH 3;

ALTER SEQUENCE seq_estabelecimento RESTART WITH 1000;

ALTER SEQUENCE seq_produto RESTART WITH 1000;

--ALTERAÇÕES PARA TESTES REAIS COM LOCALIZAÇÕES EM RECIFE

update estabelecimento set nome = 'Passira', latitude = '-7.9453', longitude = '-35.5856', cidade_id = 3 where id = 20

update produto set cidade_id = 3, estabelecimento_id = 20 where id = 25;
update produto set cidade_id = 3, estabelecimento_id = 20 where id = 5;
update produto set cidade_id = 3, estabelecimento_id = 20 where id = 80;
update produto set cidade_id = 3, estabelecimento_id = 20 where id = 256;
update produto set cidade_id = 3, estabelecimento_id = 20 where id = 815;
update produto set cidade_id = 3, estabelecimento_id = 20 where id = 95;

update cliente set latitude = '-8.05428', longitude = '-34.8813' where id = 5
update cliente set latitude = '-8.0364389', longitude = '-34.8924' where id = 150;	
update cliente set latitude = '-8.0369583', longitude = '-34.8917222' where id = 600;
update cliente set latitude = '-8.0372111', longitude = '-34.9035861' where id = 487;

update motoboy set latitude = '-8.0369639', longitude = '-34.8925111' where id = 164;
update motoboy set latitude = '-8.0339643', longitude = '-34.9053644' where id = 405;
update motoboy set latitude = '-8.0523944', longitude = '-34.9086833' where id = 19;
 
