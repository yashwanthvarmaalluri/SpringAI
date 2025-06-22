package com.yash.SpringAIDemo;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAIController {

    private OpenAiChatModel openAiChatModel;

    public OpenAIController(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    @GetMapping("api/{message}")
    public String GetResponse(@PathVariable String message) {
        String response = openAiChatModel.call(message);
        System.out.println("Hello how r u i am good");
        return response;
    }
}
