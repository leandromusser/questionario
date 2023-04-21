package com.example.calango.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.calango.model.Questionario;
import com.example.calango.model.dto.QuestionarioDTO;
import com.example.calango.repositories.QuestaoRepository;
import com.example.calango.repositories.QuestionarioRepository;

@RestController
@RequestMapping("questionarios")
@CrossOrigin(origins = "*")
public class QuestionarioController {
	
	@Autowired
	private QuestionarioRepository repo;

	@Autowired
	private QuestaoRepository repoQuestao;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@GetMapping
	public List<QuestionarioDTO> findAll() {
		
		List<QuestionarioDTO> questionarios = new ArrayList<>();
		repo.findAll().forEach(questionario -> questionarios.add(modelMapper.map(questionario, QuestionarioDTO.class)));
		return questionarios;
	}
	
	@GetMapping("/{id}")
	public Optional<Questionario> findById (@PathVariable Integer id){
		
		return repo.findById(id);
	}
	
	@PostMapping
	public Questionario create(@RequestBody Questionario questionario) {

		questionario.getQuestoes().forEach(questao -> {
			if(questao.getId() == null)
				repoQuestao.save(questao);
		});
	
		return repo.save(questionario);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {

		Optional<Questionario> questionario = repo.findById(id);	
		if(questionario.isPresent()) {
			repo.deleteById(id);
		}

	}
	
}
