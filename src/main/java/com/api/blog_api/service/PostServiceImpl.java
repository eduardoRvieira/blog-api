package com.api.blog_api.service;

import com.api.blog_api.dto.request.ComentarioRequestDto;
import com.api.blog_api.dto.response.ComentarioResponseDto;
import com.api.blog_api.mapper.ComentarioMapper;
import com.api.blog_api.mapper.PostMapper;
import com.api.blog_api.model.ComentarioModel;
import com.api.blog_api.model.PostModel;
import com.api.blog_api.repository.ComentarioRepository;
import com.api.blog_api.repository.PostRepository;
import com.api.blog_api.dto.request.PostRequestDto;
import com.api.blog_api.dto.response.PostResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, ComentarioRepository comentarioRepository, ComentarioMapper comentarioMapper){
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.comentarioRepository = comentarioRepository;
        this.comentarioMapper = comentarioMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> findAll(){
        List<PostModel> posts = postRepository.findAll();
        List<PostResponseDto> dtos = new ArrayList<>();

        for (PostModel post : posts){
            dtos.add(postMapper.toDto(post));
        }
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDto findById(UUID id){
        Optional<PostModel> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()){
            throw new RuntimeException(("Post não encontrado com o ID: " + id));
        }

        PostModel post = optionalPost.get();
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
        Optional<PostModel> optionalPost = postRepository.findById(postId);
        PostModel post = optionalPost.get();
        ComentarioModel comentario = new ComentarioModel(dto.comentario(), post);
        post.adicionarComentario(comentario);
        ComentarioModel saved = comentarioRepository.save(comentario);
        return comentarioMapper.toDto(saved);
    }
}
