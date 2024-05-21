package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

@RestController // anotações que diz para spring que essa é uma controladora de rotas e acesso aos metodos
@RequestMapping("/postagens") // rota para chegar nessa classe "insomnia"
@CrossOrigin(origins = "*", allowedHeaders = "*") // liberar o aceeso a outras maquinas / allowedHaeders = liberar passagem
public class PostagemController {

	@Autowired //injeção de dependecias - instaciar a classe postagemRepository
	private PostagemRepository postagemRepository;
	
	@GetMapping //defini o verbo HTTP que atende esse metodo
		public ResponseEntity<List<Postagem>> getAll(){
		
			//RepositoryEntity - Classe
			return ResponseEntity.ok(postagemRepository.findAll());
			// SELEC * FROM tb_postagens
		}

}
