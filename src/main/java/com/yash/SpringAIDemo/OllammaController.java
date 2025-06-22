//package com.yash.SpringAIDemo;
//
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
//import org.springframework.ai.chat.memory.ChatMemory;
//import org.springframework.ai.chat.memory.MessageWindowChatMemory;
//import org.springframework.ai.chat.model.ChatResponse;
//import org.springframework.ai.ollama.OllamaChatModel;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class OllammaController {
//
//    private ChatClient chatClient;
//
//    public OllammaController(OllamaChatModel chatModel) {
//        this.chatClient = ChatClient.create(chatModel);
//    }
//
//    ChatMemory chatMemory = MessageWindowChatMemory.builder().build();

//    public OllammaController(ChatClient.Builder builder) {
//        this.chatClient = builder
//                .defaultAdvisors(MessageChatMemoryAdvisor
//                        .builder(chatMemory)
//                        .build())
//                .build();
//
//    }
//
//    @GetMapping("api/{message}")
//    public ResponseEntity<String> GetResponse(@PathVariable String message) {
//        ChatResponse chatResponse = chatClient
//                .prompt(message)
//                .call().chatResponse();
//
//        System.out.println("Model is  " + chatResponse.getMetadata().getModel());
//
//        String response = chatResponse
//                .getResult()
//                .getOutput()
//                .getText();
//
//
//        return ResponseEntity.ok(response);
//    }
//}