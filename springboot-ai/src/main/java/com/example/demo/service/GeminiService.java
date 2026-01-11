package com.example.demo.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class GeminiService {

	private final ChatClient chatClient;
	private final ChatMemory chatMemory;

	public GeminiService(ChatClient chatClient, ChatMemory chatMemory) {
		this.chatClient = chatClient;
		this.chatMemory = chatMemory;
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

	// 有記憶的 ASK
	public String askWithMemory(String conversationId, String q) {
		// 分類 對話記憶ID
		if (conversationId == null || conversationId.isEmpty()) {
			conversationId = ChatMemory.DEFAULT_CONVERSATION_ID;
		}

		// 1. 將使用者問題 存入對話記憶
		chatMemory.add(conversationId, new UserMessage(q));

		// 2. 從 memory 中取出訊息組成 Prompt
		List<Message> messageInMemory = chatMemory.get(conversationId);
		Prompt prompt = new Prompt(messageInMemory);

		// 3. 呼叫模型
		ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

		// 4. 將 AI 回應透過 AssistantMessage 存入 memory
		String aiText = response.getResult().getOutput().getText();
		chatMemory.add(conversationId, new AssistantMessage(aiText));

		return aiText;
	}

	// 有記憶的 STREAM
	public Flux<String> streamWithMemory(String conversationId, String q) {
		// 分類 對話記憶ID
		if (conversationId == null || conversationId.isEmpty()) {
			conversationId = ChatMemory.DEFAULT_CONVERSATION_ID;
		}
		
		/* 舊版回答
		final String finalConversationId = conversationId;

		// 1. 將使用者問題 存入對話記憶
		chatMemory.add(finalConversationId, new UserMessage(q));

		// 2. 從 memory 中取出訊息組成 Prompt
		List<Message> messageInMemory = chatMemory.get(finalConversationId);
		Prompt prompt = new Prompt(messageInMemory);

		// 3. AI 完整回答
		StringBuilder fullAnswer = new StringBuilder();
		
		
		return chatClient.prompt(prompt)
				.stream()
				.content()
				.map(chunk -> {
					fullAnswer.append(chunk);
					return chunk;
				})
				.doOnComplete(() -> {
					chatMemory.add(finalConversationId, new AssistantMessage(fullAnswer.toString()));
				});
				*/
		
		// 新版本回答 使用 advisors
		return chatClient.prompt()
				.user(q)
				.advisors(MessageChatMemoryAdvisor.builder(chatMemory).conversationId(conversationId).build())
				.stream()
				.content();
	}
}
