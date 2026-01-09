package com.example.demo.config;

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

	@Bean
	public Client client() {
		Client client = Client.builder()
				.apiKey(System.getenv("GEMINI_API_KEY"))
				.build();

		/* 測試用 顯示可以使用的模型
		client.models.list(null).forEach(model -> {
			System.out.println("模型標記: " + model.name());
            System.out.println("顯示名稱: " + model.displayName());
            System.out.println("描述: " + model.description());
            System.out.println("--------------------------");
		});
		*/
		
		return client;
	}
	
	@Bean
	public GoogleGenAiChatModel geminiChatModel(Client client) {
		GoogleGenAiChatOptions options = GoogleGenAiChatOptions.builder()
				.model("gemini-2.5-flash")
				.temperature(0.7)
				.maxOutputTokens(1024)
				.build();
		
		return GoogleGenAiChatModel.builder()
				.genAiClient(client)
				.defaultOptions(options)
				.build();
	}
	
	@Bean
	public ChatClient chatClient(GoogleGenAiChatModel geminiChatModel) {
		return ChatClient.builder(geminiChatModel)
				.build();
	}
	
	@Bean
	public ChatMemory chatMemory() {
		return MessageWindowChatMemory.builder()
				.maxMessages(100)
				.build();
	}
	
}
