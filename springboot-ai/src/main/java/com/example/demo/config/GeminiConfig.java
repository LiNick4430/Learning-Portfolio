package com.example.demo.config;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.genai.Client;

@Configuration
public class GeminiConfig {

	// 模型名稱
	private final List<String> modelNames = List.of(
			"gemma-3-12b-it",
			"gemma-3-27b-it",
			"gemini-2.5-flash-lite"
			);
	
	// 是否顯示 模型名稱(檢測用)
	private final Boolean isShowModelNames = false;
	
	@Bean
	Client client() {
		Client client = Client.builder()
				.apiKey(System.getenv("GEMINI_API_KEY"))
				.build();

		// 測試用 顯示可以使用的模型
		if (isShowModelNames) {
			System.out.println("====== 正在檢索可用模型列表 ======");
			client.models.list(null).forEach(model -> {
				System.out.println("模型標記: " + model.name());
	            System.out.println("顯示名稱: " + model.displayName());
	            System.out.println("--------------------------");
			});
		}
		
		return client;
	}
	
	@Bean
	GoogleGenAiChatModel geminiChatModel(Client client) {
		GoogleGenAiChatOptions options = GoogleGenAiChatOptions.builder()
				.model(modelNames.get(1))	// 模型名稱
				.temperature(0.7)			// 模型溫度
				.maxOutputTokens(4096)		// 回傳最大數量
				.build();
		
		return GoogleGenAiChatModel.builder()
				.genAiClient(client)
				.defaultOptions(options)
				.build();
	}
	
	@Bean
	ChatClient chatClient(GoogleGenAiChatModel geminiChatModel) {
		return ChatClient.builder(geminiChatModel)
				.build();
	}
	
	@Bean
	ChatMemory chatMemory() {
		return MessageWindowChatMemory.builder()
				.maxMessages(100)
				.build();
	}
	
}
