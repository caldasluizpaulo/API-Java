package com.algaworks.osworks.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.repository.ClienteRepository;
import com.algaworks.osworks.domain.service.CadastroClienteService;


@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private CadastroClienteService cadastroClienteService;
		
	@Autowired
	private ClienteRepository clienteRepository;
	
	// Como seria usando o EntityManager mas, não ire usar essa tecenica, irei utilizar o Hibernate,
	// usando a classe ClienteRepository no pacote repository.
	//@PersistenceContext
	//private EntityManager manager;
	
	
	
	@GetMapping //     *** ROTA PARA LISTAR CLIENTES ***
	public List<Cliente> lista() {
		//return manager.createQuery("from Cliente", Cliente.class).getResultList();
			return clienteRepository.findAll();
		//return clienteRepository.findByNome("Luiz Paulo Caldas de Faria");
		//return clienteRepository.findByNomeContaining("Luiz Paulo Caldas");
	}


	
		
	// *** ROTA PARA BUSCAR CLIENTE POR ID ***
	@GetMapping("/{clienteId}") 
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
 		
		Optional<Cliente> cliente =	clienteRepository.findById(clienteId);
	
 		if (cliente.isPresent()) {
 			return ResponseEntity.ok(cliente.get());
 		}else{
 			return ResponseEntity.notFound().build();
 		}
 	}

	
	
	 /**  *** ROTA PARA CRIAR CLIENTE ***
	 * O pacote controller tem autonomia de realizar essa criação do Cliente utilizando o ClienteRepository mas,
	 * O ClienteController realizou validações no proprio metodo e depois passou essa responsabilidade para o 
	 * pacote service para realizar a persistencia utilizando o ClienteRepository
	 * @author Luiz Paulo Caldas
	 * @param Clinte da Requisição, juntamente com o ID vindo do PathVariable.
	 * @return Salva a estancia de cliente no banco e retorna o cliente criado.
	 */
	@PostMapping 
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
	// return clienteRepository.save(cliente);
	return cadastroClienteService.salvar(cliente);
	}
	//
	
	


	//ROTA PARA ATUALIZAR CLIENTE POR ID
	@PutMapping("/{clienteId}")  
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente){
				
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		cliente.setId(clienteId);
		//cliente = clienteRepository.save(cliente);
			cliente = cadastroClienteService.salvar(cliente);
		return ResponseEntity.ok(cliente);
	}

	
	
	
	//  *** ROTA PARA DELETAR CLIENTE POR IR ***
	@DeleteMapping("/{clienteId}") 
	public ResponseEntity<Void> remover(@PathVariable Long clienteId){
		
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		//clienteRepository.deleteById(clienteId);
		cadastroClienteService.excluir(clienteId);
	return ResponseEntity.noContent().build();
	}

	
}
