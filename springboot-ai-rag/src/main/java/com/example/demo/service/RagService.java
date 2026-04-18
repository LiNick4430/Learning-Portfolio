package com.example.demo.service;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RagService {

	private final VectorStore vectorStore;
	
	@Value("classpath:data/機械完整性管理程序參考手冊.pdf")
    private Resource pdfResource;
	
	public void initRagData() {
		// 1. 讀取PDF
		PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdfResource);
		
		// 2. 文字切片
		TokenTextSplitter splitter = new TokenTextSplitter();
		
		// 3. 轉換並存入向量資料庫
		List<Document> documents = splitter.apply(pdfReader.get());
		vectorStore.add(documents);
		
		System.out.println("====== RAG 知識庫初始化完成 ======");
	}
	
}
