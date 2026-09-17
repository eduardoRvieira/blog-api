package com.api.blog_api.repository;

import com.api.blog_api.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostModel, UUID> {

}
