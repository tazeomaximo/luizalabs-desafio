USE desafioluizalabs;

SET character_set_client = utf8;
SET character_set_connection = utf8;
SET character_set_results = utf8;
SET collation_connection = utf8_general_ci;

CREATE TABLE cliente (
        id_cliente int AUTO_INCREMENT,
    email VARCHAR(150) NOT NULL,
    nome VARCHAR(150) NOT NULL,
        PRIMARY KEY (id_cliente)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;;

ALTER TABLE cliente
ADD UNIQUE INDEX cliente_email_unique (email ASC) ;

CREATE TABLE produto_favorito(
        id_cliente int not null,
        id_produto varchar(60) not null,
        PRIMARY KEY (id_cliente, id_produto),
        FOREIGN KEY(id_cliente) REFERENCES cliente(id_cliente)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;;


