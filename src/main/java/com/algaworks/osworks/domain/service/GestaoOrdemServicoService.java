package com.algaworks.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.osworks.api.model.Comentario;
import com.algaworks.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.osworks.domain.exception.NegocioException;
import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.model.OrdemServico;
import com.algaworks.osworks.domain.model.StatusOrdemServico;
import com.algaworks.osworks.domain.repository.ClienteRepository;
import com.algaworks.osworks.domain.repository.ComentarioRepository;
import com.algaworks.osworks.domain.repository.OrdemServicoRespository;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	private OrdemServicoRespository ordermServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRespository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	
	
	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId); 
		
		//ordemServico.setStatus(StatusOrdemServico.FINALIZADA);
		ordemServico.finalizar();
		
		ordermServicoRepository.save(ordemServico);
	}


	
	
	public OrdemServico criar(OrdemServico ordemServico) {
		
		Cliente cliente ;
		cliente = clienteRespository.findById(ordemServico.getCliente().getId()).orElseThrow(  () -> new NegocioException("Cliente não encontrado") );
		
		ordemServico.setCliente(cliente);
				
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordermServicoRepository.save(ordemServico);  
		}
	
		
	/**
	 *   *** CRIAR COMENTARIO DA ORDEM DE SERVICO ***
	 *   Não criamos um CadastroComentarioServico por Comentarios tem uma forte influencia com OrdemServiço
	 * @param ordemServicoId
	 * @param descricao
	 * @return Comentario
	 */
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {

		OrdemServico ordemServico = buscar(ordemServicoId); 
		
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);  
		}
	
	
	
	private OrdemServico buscar(Long ordemServicoId) {
		return   ordermServicoRepository.findById(ordemServicoId)
				.orElseThrow(    () -> new EntidadeNaoEncontradaException("Ordem de Serviço não Encontrada")   );
		}
	
	
	
}
