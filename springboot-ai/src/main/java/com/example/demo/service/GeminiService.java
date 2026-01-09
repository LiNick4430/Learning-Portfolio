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
				.map(chunk -> {
					// 1. 檢查 chunk 是否存在
					if (chunk == null || chunk.getResult() == null) {
						return "";
					}

					// 2. 檢查 output 是否存在
					if (chunk.getResult().getOutput() == null) {
						return "";
					}

					// 3. 檢查 text 是否存在
					String text = chunk.getResult().getOutput().getText();
					return (text != null) ? text : "";
				})
				.filter(text -> !text.isEmpty())	// 當有文字 才傳遞到前端
				.onErrorResume(e -> {
					System.out.println("串流中斷警報" + e.getMessage());
					return Flux.empty();	// 發生錯誤時優雅結束，不要崩潰
				});	
	}
}
