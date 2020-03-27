
CREATE TABLE usuario (
  codigo SERIAL NOT NULL,
  nome CHARACTER VARYING(255) NOT NULL,
  email CHARACTER VARYING(255) NOT NULL,
  "login" CHARACTER VARYING(45) NOT NULL,
  senha CHARACTER VARYING(45) NOT NULL,
  telefone CHARACTER VARYING(45),
  data_nascimento DATE,
  estado INTEGER,
  verificacao CHARACTER VARYING(45),
  CONSTRAINT "usuario_pkey" PRIMARY KEY (codigo),
  CONSTRAINT unicos UNIQUE (login, email)
);


CREATE TABLE interface (
  codigo SERIAL NOT NULL,
  interface_codigo INTEGER, --pai necessario ajustar no MER---<
  descricao_completa CHARACTER VARYING(255) NOT NULL,
  descricao_curta CHARACTER VARYING(45) NOT NULL,
  posicao_menu INTEGER NOT NULL,
  url CHARACTER VARYING(255),
  icone CHARACTER VARYING(255),
  cor CHARACTER VARYING(45),
  CONSTRAINT "interface_pkey" PRIMARY KEY (codigo),
  CONSTRAINT "ref_interface_to_interface" FOREIGN KEY (interface_codigo)
      REFERENCES interface (codigo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE tipo_permissao (
  codigo SERIAL NOT NULL,
  descricao CHARACTER VARYING(255) NOT NULL,
  CONSTRAINT "tipo_permissao_pkey" PRIMARY KEY (codigo)
);

CREATE TABLE permissao (
  codigo SERIAL NOT NULL,
  tipo_permissao_codigo INTEGER NOT NULL,
  interface_codigo INTEGER NOT NULL,
  descricao CHARACTER VARYING(255),
  CONSTRAINT "permissao_pkey" PRIMARY KEY (codigo),
  CONSTRAINT "ref_permissao_to_tipo_permissao" FOREIGN KEY (tipo_permissao_codigo)
      REFERENCES tipo_permissao (codigo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "ref_permissao_to_interface" FOREIGN KEY (interface_codigo)
      REFERENCES interface (codigo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE usuario_permissao (
  permissao_codigo INTEGER NOT NULL,
  usuario_codigo INTEGER NOT NULL,
  CONSTRAINT "usuario_permissao_pkey" PRIMARY KEY (permissao_codigo, usuario_codigo),
  CONSTRAINT "ref_usuario_permissao_permissao_setor" FOREIGN KEY (permissao_codigo)
      REFERENCES permissao (codigo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "ref_usuario_permissao_to_usuario" FOREIGN KEY (usuario_codigo)
      REFERENCES usuario (codigo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);



