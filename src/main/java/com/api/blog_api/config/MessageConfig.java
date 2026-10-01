package com.api.blog_api.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

@Configuration
public class MessageConfig {

    @Bean
    public MessageSource messageSource(){
        // Cria o objeto responsável por localizar
        // e carregar as mensagens da aplicação.
        var source = new ReloadableResourceBundleMessageSource();

        // Informa o nome-base e a localização dos arquivos
        // de mensagens dentro do projeto.
        //
        // O Spring irá procurar arquivos como:
        // messages.properties
        // messages_en.properties
        // messages_es.properties
        source.setBasename("classpath:messages");

        // Define UTF-8 como codificação dos arquivos.
        //
        // Isso permite utilizar corretamente caracteres
        // especiais da língua portuguesa, como:
        // á, é, í, ó, ú, ç, ã, õ
        source.setDefaultEncoding(StandardCharsets.UTF_8.name());

        // Impede que o Spring utilize automaticamente
        // o idioma configurado no sistema operacional
        // como alternativa.
        //
        // Dessa forma, trabalhamos com os Locales
        // definidos pela nossa aplicação.
        source.setFallbackToSystemLocale(false);

        // Retorna o MessageSource configurado para que
        // o Spring possa utilizá-lo na aplicação.
        return source;

    }
}
