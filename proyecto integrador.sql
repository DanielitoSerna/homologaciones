CREATE TABLE tbl_estado
(
  id_estado numeric(10,0) NOT NULL,
  descripcion character varying(20) NOT NULL,
  CONSTRAINT tbl_estado_pkey PRIMARY KEY (id_estado)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tbl_estado
  OWNER TO homologa;

CREATE TABLE tbl_roles
(
  cod_rol numeric(10,0) NOT NULL,
  nombre_rol character varying(20) NOT NULL,
  descripcion character varying(200) DEFAULT NULL::character varying,
  id_estado numeric(10,0) NOT NULL,
  CONSTRAINT tblroles_pkey PRIMARY KEY (cod_rol),
  CONSTRAINT fk_estado_banco FOREIGN KEY (id_estado)
      REFERENCES tbl_estado (id_estado) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tbl_roles
  OWNER TO homologa;
  
CREATE TABLE tbl_usuario
(
  usuario character varying(20) NOT NULL,
  contrasena character varying(20) NOT NULL,
  correo character varying(30) NOT NULL,
  cod_rol numeric(10,0) NOT NULL,
  id_estado numeric(10,0) NOT NULL,
  CONSTRAINT tbl_usuario_pkey PRIMARY KEY (usuario),
  CONSTRAINT fk_id_rol FOREIGN KEY (cod_rol)
      REFERENCES tbl_roles (cod_rol) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_estado_banco FOREIGN KEY (id_estado)
      REFERENCES tbl_estado (id_estado) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tbl_usuario
  OWNER TO homologa;
  

CREATE TABLE tbl_universidad
(
	id_universidad serial NOT NULL,
	nombre_universidad character varying(200) NOT NULL,
	acreditada character varying(2) NOT NULL,
	nit character varying(20) NOT NULL, 
	id_estado numeric(10,0) NOT NULL,
	CONSTRAINT tbl_universidad_pkey PRIMARY KEY (id_universidad),
	CONSTRAINT fk_estado_banco FOREIGN KEY (id_estado)
      REFERENCES tbl_estado (id_estado) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tbl_universidad
  OWNER TO homologa;
  
  
CREATE TABLE tbl_programas
(
	id_programa serial NOT NULL,
	codigo_interno character varying(20) NOT NULL,
	nombre_programa character varying(200) NOT NULL,
	facultad_pertenece character varying(2) NOT NULL,
	id_universidad integer NOT NULL,
	id_estado numeric(10,0) NOT NULL,
	CONSTRAINT tbl_programas_pkey PRIMARY KEY (id_programa),
	CONSTRAINT fk_id_u FOREIGN KEY (id_universidad)
      REFERENCES tbl_universidad (id_universidad) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT fk_estado_banco FOREIGN KEY (id_estado)
      REFERENCES tbl_estado (id_estado) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tbl_programas
  OWNER TO homologa;
  
  
CREATE TABLE tbl_materias
(
	id_materia serial NOT NULL,
	codigo_interno character varying(20) NOT NULL,
	nombre_materia character varying(200) NOT NULL,
	id_programa integer NOT NULL,
	id_estado numeric(10,0) NOT NULL,
	CONSTRAINT tbl_materias_pkey PRIMARY KEY (id_materia),
	CONSTRAINT fk_id_programa FOREIGN KEY (id_programa)
      REFERENCES tbl_programas (id_programa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT fk_estado_banco FOREIGN KEY (id_estado)
      REFERENCES tbl_estado (id_estado) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tbl_programas
  OWNER TO homologa;
  
  
CREATE TABLE tbl_homologacion
  (id_homologa numeric(10,0) NOT NULL,
  universidad_origen integer NOT NULL,
  programa_origen integer NOT NULL,
  materia_origen integer NOT NULL,
  universidad_destino integer NOT NULL,
  programa_destino integer NOT NULL,
  materia_destino integer NOT NULL,
  CONSTRAINT tbl_homologa_pkey PRIMARY KEY (id_homologa),
  CONSTRAINT fk_id_u1 FOREIGN KEY (universidad_origen)
      REFERENCES tbl_universidad (id_universidad) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_id_u2 FOREIGN KEY (universidad_destino)
      REFERENCES tbl_universidad (id_universidad) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_id_programa1 FOREIGN KEY (programa_destino)
      REFERENCES tbl_programas (id_programa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_id_programa2 FOREIGN KEY (programa_origen)
      REFERENCES tbl_programas (id_programa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_id_materia1 FOREIGN KEY (materia_origen)
      REFERENCES tbl_materias (id_materia) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_id_materia2 FOREIGN KEY (materia_destino)
      REFERENCES tbl_materias (id_materia) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tbl_homologacion
  OWNER TO homologa;