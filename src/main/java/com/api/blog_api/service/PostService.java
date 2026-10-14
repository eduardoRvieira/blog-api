package com.api.blog_api.service;

import com.api.blog_api.dto.request.ComentarioRequestDto;
import com.api.blog_api.dto.request.PostRequestDto;
import com.api.blog_api.dto.response.ComentarioResponseDto;
import com.api.blog_api.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PostService {
    // List<PostResponseDto> findAll();
    Page<PostResponseDto> findAll(Pageable pageable);
    Page<PostResponseDto> findAll(Pageable pageable, String titulo);
    PostResponseDto findById(UUID id);
    PostResponseDto createPost(PostRequestDto dto);
    ComentarioResponseDto addComentario(UUID postId, ComentarioRequestDto dto);
    void deletePost(UUID id);
    PostResponseDto updatePost(UUID id, PostRequestDto dto);
    void deleteComentario(UUID id);
    ComentarioResponseDto updateComentario(UUID id, ComentarioRequestDto dto);
}
