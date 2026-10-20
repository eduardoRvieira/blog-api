package com.api.blog_api.repository;

import com.api.blog_api.model.PostModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository repository;

    @Test
    void deveSalvarERetorntarPost(){

        PostModel post = new PostModel("Eduardo", "Alunos +devs", "Os alunos são do +Devs2Blu");

        PostModel postSalvo = repository.save(post);

        Assertions.assertThat(postSalvo).isNotNull();
    }

    @Test
    void deveListarPorTitulo() {

        PostModel post1 = new PostModel("Eduardo", "Alunos do +devs", "Os alunos são do +devs");
        PostModel post2 = new PostModel("Eduardo", "Estudos +devs", "Estudos de Java");
        PostModel post3 = new PostModel("Eduardo", "Frequência +devs", "Frequência dos alunos");

        repository.save(post1);
        repository.save(post2);
        repository.save(post3);

        Page<PostModel> listaPosts = repository.findByTituloContainingIgnoreCase("+devs",
                PageRequest.of(0, 10));

        Assertions.assertThat(listaPosts).hasSize(3);
    }
}
