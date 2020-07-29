package com.algaworks.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.osworks.api.model.OrdemServicoInput;
import com.algaworks.osworks.api.model.OrdemServicoModel;
import com.algaworks.osworks.domain.model.OrdemServico;
import com.algaworks.osworks.domain.repository.OrdemServicoRespository;
import com.algaworks.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	
	@Autowired
	private OrdemServicoRespository ordemSercivoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
		
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) //Respostsa de Sucesso quando cria
	public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoInput ordemServicoInput) {
		OrdemServico ordermServico = toEntity(ordemServicoInput);
		return toModel(gestaoOrdemServico.criar(ordermServico));
	} 

	
	
	
	
	@GetMapping //   
	public List<OrdemServicoModel> listar(){
		return toCollectionModel(ordemSercivoRepository.findAll());
	}
	
	
	
	
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long ordemServicoId){
		
		Optional<OrdemServico> ordemServico =  ordemSercivoRepository.findById(ordemServicoId);
		
			if (ordemServico.isPresent()) {
				OrdemServicoModel ordemServicoModel = toModel(ordemServico.get());
				return ResponseEntity.ok(ordemServicoModel);
			}
			
			return ResponseEntity.notFound().build();
	}
	
	
	
	
	
	
		
	private OrdemServicoModel toModel(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoModel.class);	 
	}
	
	
	private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordensServico) {
		return  ordensServico.stream().map(ordemServico -> toModel(ordemServico))
				.collect(Collectors.toList());		 
	}
	
	private OrdemServico toEntity(OrdemServicoInput ordemServicoInput) {
		return modelMapper.map(ordemServicoInput, OrdemServico.class);
				
	}
	
	
	
}
