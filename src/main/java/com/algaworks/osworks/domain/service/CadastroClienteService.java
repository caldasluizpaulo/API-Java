package com.algaworks.osworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.osworks.domain.exception.NegocioException;
import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService {

	@Autowired
	private ClienteRepository clienteRespository;
	
	
	/**Método para Criar um cliente utilizando o pacote Service
	 * O pacote controller tinha autonomia de realizar essa criação do Cliente utilizando o ClienteRepository mas,
	 * O ClienteController preferiu passar essa responsabilidade para o pacote service para realizar algum tipo
	 * de validações antes de realmente salvar.
	 * @author Luiz Paulo Caldas
	 * @param Objeto Cliente
	 * @return Salva a estancia de cliente no banco e retorna o cliente criado.
	 */
	public Cliente salvar(Cliente cliente) {
		Cliente clienteExistente = clienteRespository.findByEmail(cliente.getEmail());
		
		if (clienteExistente != null  && !clienteExistente.equals(cliente)){
			throw new NegocioException("Já existe umu cliente cadastrado com este email.");
		}
		
		return clienteRespository.save(cliente);
	}
	
	
	
	/**Método para Excluir um cliente utilizando o pacote Service
	 * O pacote controller tinha autonomia de realizar a exclusão do Cliente utilizando o ClienteRepository mas,
	 * O ClienteController preferiu passar essa responsabilidade para o pacote service para realizar algum tipo
	 * de validações antes de realmente salvar, que no caso atual, não houve validações.
	 * @author Luiz Paulo Caldas
	 * @param Objeto Cliente
	 * @return Salva a estancia de cliente no banco e retorna o cliente criado.
	 */
	public void excluir(Long clienteId) {
		clienteRespository.deleteById(clienteId);
	}
	
}
