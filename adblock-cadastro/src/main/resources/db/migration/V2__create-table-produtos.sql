CREATE TABLE produtos(

   id serial not null primary key,
   produto    varchar(100),
   embalagem  varchar(100),
   preco      float8,
   qtdlitro   integer,
   risco      varchar(100),
   id_funcionario  bigint

);