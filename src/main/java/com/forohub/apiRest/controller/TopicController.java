package com.forohub.apiRest.controller;

import com.forohub.apiRest.domain.dto.TopicDTO;
import com.forohub.apiRest.domain.model.Topic;
import com.forohub.apiRest.domain.repostory.TopicRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;



import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequestMapping("/topics")
@SecurityRequirement(name = "bearer-key")
public class TopicController {
    @Autowired
    private TopicRepository topicoRepository;

    @PostMapping
    public ResponseEntity<TopicDTO> registrarTopico(@RequestBody @Valid TopicDTO topicoDTO) {
        Topic topico = new Topic(topicoDTO);
        Topic topicoGuardado = topicoRepository.save(topico);
        TopicDTO topicoGuardadoDTO = convertirADTO(topicoGuardado);
        URI location = URI.create("/topics/" + topicoGuardadoDTO.id());
        return ResponseEntity.created(location).body(topicoGuardadoDTO);
    }

    @GetMapping
    public ResponseEntity<Page<TopicDTO>> listarTopicos(@PageableDefault(size = 5, page = 0, direction = ASC) Pageable paginacion) {
        Page<TopicDTO> topicos = SearchTopicos(paginacion, null);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/title")
    public ResponseEntity<Page<TopicDTO>> listarTopicosPorTitulo(@PageableDefault(size = 5, page = 0, direction = ASC, sort = {"title"}) Pageable paginacion) {
        Page<TopicDTO> topicos = SearchTopicos(paginacion, "title");
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/course")
    public ResponseEntity<Page<TopicDTO>> listarTopicosPorCurso(@PageableDefault(size = 5, page = 0, direction = ASC, sort = {"course"}) Pageable paginacion) {
        Page<TopicDTO> topicos =SearchTopicos(paginacion, "course");
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/create_date")
    public ResponseEntity<Page<TopicDTO>> listarTopicosPorFecha(@PageableDefault(size = 5, page = 0, direction = ASC, sort = {"createDate"}) Pageable paginacion) {
        Page<TopicDTO> topicos = SearchTopicos(paginacion, "createDate");
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/year")
    public ResponseEntity<Page<TopicDTO>> listarTopicosPorAnio(@PageableDefault(size = 5, page = 0, direction = ASC) Pageable paginacion) {
        Page<TopicDTO> topicos = searchTopicsByYear(paginacion);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDTO> mostrarTopico(@PathVariable Long id) {
        Optional<Topic> topicoOptional = topicoRepository.findById(id);
        return topicoOptional.map(topico -> ResponseEntity.ok(convertirADTO(topico)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Optional<TopicDTO>> actualizarTopico(@PathVariable Long id, @RequestBody @Valid TopicDTO topicoDTO) {
        Optional<Topic> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()) {
            Topic topico = topicoOptional.get();
            topico.setTitle(topicoDTO.title());
            topico.setMessage(topicoDTO.message());
            topico.setCreateDate(topicoDTO.createDate());
            topico.setStatus(topicoDTO.status());
            topico.setAuthor(topicoDTO.author());
            topico.setCourse(topicoDTO.course());
            topico.setAnswers(topicoDTO.answers());
            topicoRepository.save(topico);
            return ResponseEntity.ok(Optional.of(convertirADTO(topico)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        if (topicoRepository.existsById(id)) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            // throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Topico no encontrado");
            return ResponseEntity.notFound().build();
        }
    }

    private TopicDTO convertirADTO(Topic topico) {
        return new TopicDTO(
                topico.getId(),
                topico.getTitle(),
                topico.getMessage(),
                topico.getCreateDate(),
                topico.getStatus(),
                topico.getAuthor(),
                topico.getCourse(),
                topico.getAnswers()
        );
    }

    private Page<TopicDTO> SearchTopicos(Pageable paginacion, String sortBy) {
        if (sortBy != null) {
            paginacion = PageRequest.of(paginacion.getPageNumber(), paginacion.getPageSize(), ASC, sortBy);
        }
        return topicoRepository.findAll(paginacion).map(this::convertirADTO);
    }

    private Page<TopicDTO> searchTopicsByYear(Pageable paginacion) {
        List<Topic> topicos = topicoRepository.findAll();
        topicos.sort((t1, t2) -> {
            String[] fecha1 = t1.getCreateDate().split("/");
            String[] fecha2 = t2.getCreateDate().split("/");
            return Integer.compare(Integer.parseInt(fecha1[2]), Integer.parseInt(fecha2[2]));
        });
        return new PageImpl<>(topicos.stream().map(this::convertirADTO).collect(Collectors.toList()));
    }
}
