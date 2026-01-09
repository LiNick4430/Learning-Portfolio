package com.example.demo.service;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class GeminiService {

	private final GoogleGenAiChatModel chatModel;
	
	public GeminiService(GoogleGenAiChatModel chatModel) {
		this.chatModel = chatModel;
	}
	
	// 預設的 ASK 提問
	public String ask(String q) {
		return chatModel.call(q);
	}
	
	// 預設的 STREAM 提問
	public Flux<String> stream(String q) {
		return chatModel.stream(new Prompt(q))
				.map(chunk -> chunk.getResult().getOutput().getText());
	}
}
