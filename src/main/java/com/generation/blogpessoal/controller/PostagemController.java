package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

import jakarta.validation.Valid;

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

	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id) {
		
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
			return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
		}
	
	@PostMapping
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(postagemRepository.save(postagem));
		
	}
	
	@PutMapping
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem){
		return postagemRepository.findById(postagem.getId())
				.map(resposta-> ResponseEntity.status(HttpStatus.OK)
				.body(postagemRepository.save(postagem)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

		@DeleteMapping("/{id}")
		public void delete(@PathVariable Long id) {
			Optional<Postagem> postagem = postagemRepository.findById(id);
			
			if(postagem.isEmpty())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			
			postagemRepository.deleteById(id);
	}
	
}
	

