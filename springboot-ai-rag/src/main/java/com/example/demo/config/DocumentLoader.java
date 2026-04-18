package com.example.demo.config;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DocumentLoader implements CommandLineRunner{

	private final VectorStore vectorStore;

	@Override
	public void run(String... args) throws Exception {
		
		// 讀取資料 存入 List<Document>
		List<Document> pdfDocs = Stream.of(
				new PagePdfDocumentReader(new ClassPathResource("data/機械完整性管理程序參考手冊.pdf")).read()
				)
				.flatMap(List::stream)
				.collect(Collectors.toList());
		
		// 存入書庫
		vectorStore.add(pdfDocs);
		System.out.println("====== RAG 知識庫初始化完成 ======");

	}

}
