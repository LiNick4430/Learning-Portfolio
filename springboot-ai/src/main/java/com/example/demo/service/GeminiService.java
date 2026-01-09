package com.example.demo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class GeminiService {

	private final ChatClient chatClient;

	public GeminiService(ChatClient chatClient) {
		this.chatClient = chatClient;
	}

	// 預設的 ASK 提問
	public String ask(String q) {
		return chatClient.prompt(q)
				.call()
				.content();
	}

	// 預設的 STREAM 提問
	public Flux<String> stream(String q) {
		return chatClient.prompt(q)
				.stream()
				.content();
	}
}
