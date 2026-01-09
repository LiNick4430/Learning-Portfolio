package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.GeminiService;

@RestController
@RequestMapping("/gemini")
public class GeminiController {

	private final GeminiService geminiService;
	
	public GeminiController(GeminiService geminiService) {
		this.geminiService = geminiService;
	}
	
	@GetMapping("/ask")
	public String Chat(@RequestParam String q) {
		return geminiService.ask(q);
	}
	
}
