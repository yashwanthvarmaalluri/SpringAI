package com.yash.SpringAIDemo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.template.TemplateRenderer;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Map;

@RestController
public class Moviecontroller {

    private ChatClient chatClient;


    public Moviecontroller(OpenAiChatModel openAiChatModel) {
        this.chatClient = ChatClient.create(openAiChatModel);
    }

    @GetMapping("movies")
    public List<String> getMovies(@RequestParam String text) {

        String message = """
                List of Top 5 movies of {text} and in the format
                {format}
                """;

        ListOutputConverter opCon = new ListOutputConverter(new DefaultConversionService());


        PromptTemplate promptTemplate = new PromptTemplate(message);
        Prompt prompt = promptTemplate.create(Map.of("text", text, "format", opCon.getFormat() ));
        return opCon.convert(chatClient.prompt(prompt).call().content());
    }

    @GetMapping("movies2")
    public List<String> getMovies2(@RequestParam String text) {
        ListOutputConverter opCon = new ListOutputConverter(new DefaultConversionService());

        String message = "List of Top 5 movies of " + text + "\n" + opCon.getFormat();

        Prompt prompt = new Prompt(message);

        return opCon.convert(chatClient.prompt(prompt).call().content());
    }

    @GetMapping("movie")
    public Movie getMovie(@RequestParam String hero) {
              BeanOutputConverter<Movie> opCon = new BeanOutputConverter<>(Movie.class);
        String message = "best  movie of " + hero  + opCon.getFormat();

        Prompt prompt = new Prompt(message);

        return opCon.convert(chatClient.prompt(prompt).call().content());


    }

    @GetMapping("moviesList")
    public List<Movie> getMovieList(@RequestParam String hero) {
        BeanOutputConverter<List<Movie>> opCon = new BeanOutputConverter<>(
                new ParameterizedTypeReference<List<Movie>>() {
                }
        );
        String message = "Really existing Top 5 movies of " + hero  + opCon.getFormat();

        Prompt prompt = new Prompt(message);

        return opCon.convert(chatClient.prompt(prompt).call().content());


    }

}
