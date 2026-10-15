package com.api.blog_api.controller;

import com.api.blog_api.dto.request.ComentarioRequestDto;
import com.api.blog_api.dto.request.PostRequestDto;
import com.api.blog_api.dto.response.ComentarioResponseDto;
import com.api.blog_api.dto.response.PostResponseDto;
import com.api.blog_api.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Posts e comentários", description = "Operações do Blog API")
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "Lista posts com paginação")
    @GetMapping("/posts")
    public ResponseEntity<Page<PostResponseDto>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "data") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) String titulo) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(postService.findAll(pageable, titulo));
    }

    @Operation(summary = "Busca post por ID")
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable UUID id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @Operation(summary = "Cria post")
    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody @Valid PostRequestDto dto) {
        PostResponseDto created = postService.createPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Adiciona comentário em post pela ID")
    @PostMapping("/posts/{postId}/comentarios")
    public ResponseEntity<ComentarioResponseDto> createComentario(
            @PathVariable UUID postId,
            @RequestBody @Valid ComentarioRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.addComentario(postId, dto));
    }

    @Operation(summary = "Deleta post pelo ID")
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza post pelo ID")
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable UUID id, @RequestBody @Valid PostRequestDto dto) {
        PostResponseDto updatedPost = postService.updatePost(id, dto);
        return ResponseEntity.ok(updatedPost);
    }

    @Operation(summary = "Atualiza comentário pelo ID")
    @PutMapping("/comentarios/{comentarioId}")
    public ResponseEntity<ComentarioResponseDto> updateComentario(
            @PathVariable UUID comentarioId,
            @RequestBody @Valid ComentarioRequestDto dto) {
        ComentarioResponseDto updated = postService.updateComentario(comentarioId, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Deleta comentário pelo ID")
    @DeleteMapping("/comentarios/{comentarioId}")
    public ResponseEntity<Void> deleteComentario(@PathVariable UUID comentarioId) {
        postService.deleteComentario(comentarioId);
        return ResponseEntity.noContent().build();
    }
}
