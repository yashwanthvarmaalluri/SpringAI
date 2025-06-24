package com.yash.SpringAIDemo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.template.TemplateRenderer;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @GetMapping("movies")
//    public List<String> getMovies(@RequestParam String text) {
//
//        String message = """
//                List of Top 5 movies of {text} and in the format
//                {format}
//                """;
//
//        ListOutputConverter opCon = new ListOutputConverter(new DefaultConversionService());
//
//        Object renderer = null;
//        PromptTemplate template =
//                new PromptTemplate(message,Map.of("text",text,"format",opCon.getFormat()));
//
//        Prompt prompt = template.create();
//        return opCon.convert(chatClient.prompt(prompt).call().content());
//    }
// Not working

    @GetMapping("movies")
    public List<String> getMovies(@RequestParam String text) {
        ListOutputConverter opCon = new ListOutputConverter(new DefaultConversionService());

        // Manually format the full prompt without using PromptTemplate variables
        String message = "List of Top 5 movies of " + text + "\n" + opCon.getFormat();

        Prompt prompt = new Prompt(message);

        return opCon.convert(chatClient.prompt(prompt).call().content());
    }

}
