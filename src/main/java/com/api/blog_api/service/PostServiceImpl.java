package com.api.blog_api.service;

import com.api.blog_api.dto.request.ComentarioRequestDto;
import com.api.blog_api.dto.response.ComentarioResponseDto;
import com.api.blog_api.exception.ComentarioNaoEncontradoException;
import com.api.blog_api.exception.PostNaoEncontradoException;
import com.api.blog_api.mapper.ComentarioMapper;
import com.api.blog_api.mapper.PostMapper;
import com.api.blog_api.model.ComentarioModel;
import com.api.blog_api.model.PostModel;
import com.api.blog_api.repository.ComentarioRepository;
import com.api.blog_api.repository.PostRepository;
import com.api.blog_api.dto.request.PostRequestDto;
import com.api.blog_api.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final ComentarioRepository comentarioRepository;
    private final ComentarioMapper comentarioMapper;
    private final MessageService messageService;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, ComentarioRepository comentarioRepository, ComentarioMapper comentarioMapper, MessageService messageService){
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.comentarioRepository = comentarioRepository;
        this.comentarioMapper = comentarioMapper;
        this.messageService = messageService;
    }

    /*@Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> findAll(){
        List<PostModel> posts = postRepository.findAll();
        List<PostResponseDto> dtos = new ArrayList<>();

        for (PostModel post : posts){
            dtos.add(postMapper.toDto(post));
        }
        return dtos;
    }*/

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAll(Pageable pageable){

        Page<PostModel> posts = postRepository.findAll(pageable);

        return posts.map(post -> {
            PostResponseDto dto = postMapper.toDto(post);
            return dto;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAll(Pageable pageable, String titulo){

        Page<PostModel> posts;
        if (titulo == null || titulo.isBlank()){
            posts = postRepository.findAll(pageable);
        } else {
            posts = postRepository.findByTituloContainingIgnoreCase(titulo, pageable);
        }

        /*List<PostResponseDto> response = new ArrayList<>();*/
        /*for (PostModel post : posts.getContent()){
            PostResponseDto dto = postMapper.toDto(post);
            response.add(dto);
        }*/

        List<PostResponseDto> response = posts.getContent().stream().map(postMapper::toDto).toList();

        return new PageImpl<>(response, pageable, posts.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDto findById(UUID id){
        /*Optional<PostModel> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()){
            throw new RuntimeException(("Post não encontrado com o ID: " + id));
        }

        PostModel post = optionalPost.get();
        return postMapper.toDto(post);*/

        PostModel post = postRepository.findById(id)
                .orElseThrow(() -> new PostNaoEncontradoException(messageService.getMessage("post.not.found", id)));

        return postMapper.toDto(post);
    }

    @Override
    @Transactional
    public PostResponseDto createPost(PostRequestDto dto){
        PostModel post = postMapper.toEntity(dto);
        PostModel saved = postRepository.save(post);
        return postMapper.toDto(saved);
    }

    @Override
    @Transactional
    public ComentarioResponseDto addComentario(UUID postId, ComentarioRequestDto dto){
        PostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNaoEncontradoException(messageService.getMessage("post.not.found", postId)));
        ComentarioModel comentario = new ComentarioModel(dto.comentario(), post);
        post.adicionarComentario(comentario);
        ComentarioModel saved = comentarioRepository.save(comentario);
        return comentarioMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void deletePost(UUID id){
        if (!postRepository.existsById(id)){
            throw new PostNaoEncontradoException(messageService.getMessage("post.not.found", id));
        }
        postRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PostResponseDto updatePost(UUID id, PostRequestDto dto){
        PostModel post = postRepository.findById(id)
                .orElseThrow(() -> new PostNaoEncontradoException(messageService.getMessage("post.not.found", id)));

        post.setTitulo(dto.titulo());
        post.setAutor(dto.autor());
        post.setTexto(dto.texto());

        PostModel updatedPost = postRepository.save(post);

        return postMapper.toDto(updatedPost);
    }

    @Override
    @Transactional
    public ComentarioResponseDto updateComentario(UUID comentarioId, ComentarioRequestDto dto){
        ComentarioModel comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new ComentarioNaoEncontradoException(messageService.getMessage("comentario.not.found", comentarioId)));

        comentario.setComentario(dto.comentario());

        ComentarioModel updated = comentarioRepository.save(comentario);

        return comentarioMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteComentario(UUID comentarioId){
        if (!comentarioRepository.existsById(comentarioId)){
            throw new ComentarioNaoEncontradoException(messageService.getMessage("comentario.not.found", comentarioId));
        }

        comentarioRepository.deleteById(comentarioId);
    }
}
