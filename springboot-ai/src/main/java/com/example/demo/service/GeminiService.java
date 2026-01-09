package com.example.demo.service;

import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.stereotype.Service;

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
}
