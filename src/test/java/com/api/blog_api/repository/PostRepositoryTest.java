package com.api.blog_api.repository;

import com.api.blog_api.model.PostModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository repository;

    @Test
    void deveSalvarERetorntarPost(){

        PostModel post = new PostModel("Eduardo", "Alunos +devs", "Os alunos do +devs são incríveis");

        PostModel postSalvo = repository.save(post);

        Assertions.assertThat(postSalvo).isNotNull();


    }
}
