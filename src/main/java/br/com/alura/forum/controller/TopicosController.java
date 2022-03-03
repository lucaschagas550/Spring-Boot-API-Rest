package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.DetalheDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    //@RequestMapping(value = "/topicos", method = RequestMethod.GET)
    @GetMapping
    @Cacheable(value = "listaDeTopicos")
    public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso, @PageableDefault(page = 0, size = 25, sort = "id") Pageable paginacao) {

        if (nomeCurso == null) {
            Page<Topico> topicos = topicoRepository.findAll(paginacao);
            return TopicoDto.converter(topicos);
        }

        Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
        return TopicoDto.converter(topicos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DetalheDoTopicoDto> detalhar(@PathVariable Long id){
        Optional<Topico> topico = topicoRepository.findById(id);
        return topico.isPresent() ? ResponseEntity.ok(new DetalheDoTopicoDto(topico.get())) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder){
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @PutMapping("/{id}")
    @Transactional //Avisa o spring que é para ele commitar a transação no final deste metodo
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form){
        Optional<Topico> optional = topicoRepository.findById(id);
        if(optional.isPresent()){
            Topico topico = form.atualizar(id,topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }

        return ResponseEntity.notFound().build();
        //Ao final da transação JPA ja detectado que houve alteraçao nas propriedades e ela ja atualiza o banco de dados
    }

    @DeleteMapping({"/{id}"})
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Topico> optional = topicoRepository.findById(id);
        if(optional.isPresent()){
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
