const chatBox = document.getElementById("chat-box");
const questionInput = document.getElementById("question");

// 一次性回應 呼叫 /gemini/ask
function ask() {
	const question = questionInput.value.trim();
	
	if(!question) return;
	
	chatBox.innerText += `You: ${question}\n`;
	questionInput.value = ''; 
	
	fetch(`http://localhost:8080/gemini/ask?q=${encodeURIComponent(question)}`)
		.then(res => res.text())
		.then(answer => {
			chatBox.innerText += `AI: ${answer}\n\n`;
			chatBox.scrollTo(0, chatBox.scrollHeight);
		})
		.catch(err => {
			chatBox.innerText += "錯誤呼叫\n";
			chatBox.scrollTo(0, chatBox.scrollHeight);
		})
}

// 串流回應 呼叫 /gemini/stream
function stream() {
	const question = questionInput.value.trim();
		
	if(!question) return;
	
	chatBox.innerText += `You: ${question}\n`;
	questionInput.value = ''; 
	
	// 建立 SSE(Server-Sent Events) 連線
	const eventSource = new EventSource(`http://localhost:8080/gemini/stream?q=${encodeURIComponent(question)}`);
	
	// 先在 chatBox 上印出 AI: 當作前綴字
	chatBox.innerText += 'AI: ';
	
	// 都後端送出一個事件資料(chunk)時, 就會觸發 onmessage
	eventSource.onmessage = function(event) {
		chatBox.innerText += event.data;
		chatBox.scrollTo(0, chatBox.scrollHeight);
	};
	
	// 當連線失敗或後端關閉時就會觸發 onerror
	eventSource.onerror = function(err) {
		
		// 關閉 EventSource 連線
		eventSource.close();
		
		chatBox.innerText += "\n[Stream end]\n";
		chatBox.scrollTo(0, chatBox.scrollHeight);
	};
}