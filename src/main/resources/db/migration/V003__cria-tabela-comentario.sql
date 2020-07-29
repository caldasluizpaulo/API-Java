CREATE table comentario(
	id               bigint NOT NULL AUTO_INCREMENT,
	ordem_servico_id bigint NOT NULL,
    descricao        text not null,
    data_envio       datetime not null,
	primary key (id)
);

ALTER TABLE comentario add constraint fk_comentario_ordem_servico
foreign key (ordem_servico_id) references ordem_servico (id)