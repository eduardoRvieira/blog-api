package com.api.blog_api.service;

import com.api.blog_api.request.PostRequestDto;
import com.api.blog_api.response.PostResponseDto;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<PostResponseDto> findAll();
    PostResponseDto findById(UUID id);
    PostResponseDto createPost(PostRequestDto dto);
}
