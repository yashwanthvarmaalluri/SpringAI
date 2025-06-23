package com.yash.SpringAIDemo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class OpenAIController {

    private ChatClient chatClient;

    @Autowired
    private EmbeddingModel embeddingModel;

//    public OpenAIController(OpenAiChatModel openAiChatModel) {
//        this.chatClient = ChatClient.create(openAiChatModel);
//    }

    ChatMemory chatMemory = MessageWindowChatMemory.builder().build();

    public OpenAIController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor
                        .builder(chatMemory)
                        .build())
                .build();

    }

    @GetMapping("api/{message}")
    public ResponseEntity<String> GetResponse(@PathVariable String message) {
        ChatResponse chatResponse = chatClient
                .prompt(message)
                .call().chatResponse();

        System.out.println("Model is  " + chatResponse.getMetadata().getModel());

        String response = chatResponse
                .getResult()
                .getOutput()
                .getText();


        return ResponseEntity.ok(response);
    }

    @PostMapping("api/recommend")
    public String recommend(@RequestParam String type , @RequestParam String year , @RequestParam String lang) {

        String template = """
                            I want you to recommend a movie of genre {type} , released around the year {year} and of language {lang}.
                            
                            the format of your response should be 
                            
                            1.title
                            2.duration
                            3.cast
                            4.crew
                            5. imdb rating
                       
                            
                            """ ;
        PromptTemplate promptTemplate = new PromptTemplate(template);
        Prompt prompt = promptTemplate.create(Map.of("type", type, "year", year, "lang", lang));


        String response = chatClient
                .prompt(prompt)
                .call()
                .content();
        return response;
    }

    @PostMapping("/api/embedding")
    public float[] getEmbeddings(@RequestParam String text){

        return embeddingModel.embed(text);

    }
}