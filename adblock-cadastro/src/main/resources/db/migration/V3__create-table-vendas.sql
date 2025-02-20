CREATE TABLE vendas
(
    id serial not null primary key,
   data                   timestamp,
   valor_total            float8,
   qtde_total             integer,
   id_empresa             bigint,
   observacao             varchar(255),
   id_condicoescomercias  bigint
);

   
CREATE TABLE itemvendas
(
    id serial not null primary key,
   qtde           integer,
   valor_total    float8,
   id_produto     bigint,
   id_venda       bigint,
   qtdlitros      integer,
   valordesconto  float8
);
