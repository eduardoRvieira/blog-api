package com.api.blog_api.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tb_comentario")
public class ComentarioModel {

    private static final long serialversionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private LocalDate data;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String comentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostModel post;

    public ComentarioModel(){}

    public ComentarioModel(String comentario, PostModel post){
        this.comentario = comentario;
        this.post = post;
        this.data = LocalDate.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public PostModel getPost() {
        return post;
    }

    public void setPost(PostModel post) {
        this.post = post;
    }
}
