package com.example.demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/rag")
public class RagController {

	private final ChatClient chatClient;

	public RagController(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore)) // 這是 RAG 的靈魂
                .build();
    }
	
	@GetMapping("/ask")
	public Flux<String> generateWithRag(@RequestParam String message) {
		return chatClient.prompt()
				.user(message)
				.stream()
				.content();
	}
}
