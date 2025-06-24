package com.yash.SpringAIDemo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageGenController {
    private ChatClient chatClient;
    private OpenAiImageModel openAiImageModel;

    public ImageGenController(OpenAiImageModel openAiImageModel, OpenAiChatModel openAiChatModel) {
        this.openAiImageModel = openAiImageModel;
        this.chatClient = ChatClient.create(openAiChatModel);
    }

    @GetMapping("image/{query}")
    public String generateImage(@PathVariable String query) {
        ImagePrompt imagePrompt = new ImagePrompt(query, OpenAiImageOptions.builder()
                .quality("hd")
                .height(1024)
                .width(1024)
                .style("natural")
                .build());
        ImageResponse response = openAiImageModel.call(imagePrompt);

        return response.getResult().getOutput().getUrl();
    }
}
