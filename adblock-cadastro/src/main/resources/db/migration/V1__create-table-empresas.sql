create table empresas(

    id serial not null primary key,
    razao_social varchar(100) not null,
    email varchar(100) not null ,
    cnpj varchar(100) not null ,
    telefone varchar(100) not null ,
    rua varchar(100) not null,
    bairro varchar(100) not null,
    cidade varchar(100) not null,
    cep varchar(9) not null,
    numero varchar(20),
    inscricao varchar(100),
    contato varchar(100),
    estado char(2) not null ,
    id_funcionario  bigint
    );
