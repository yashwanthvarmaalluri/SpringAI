package com.yash.SpringAIDemo;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Component
public class DataInitializer {

    @Autowired
    private VectorStore vectorStore;

//    @PostConstruct
    public void init(){

        TextReader textReader = new TextReader(new ClassPathResource("product_details.txt"));
        TokenTextSplitter splitter = new TokenTextSplitter(100,30,50,500,false);
        List<Document> documents = splitter.split(textReader.get());

        vectorStore.add(documents);

    }

}
