const chatBox = document.getElementById("chat-box");
const questionInput = document.getElementById("question");
let timerId = null;

// 啟動 載入動畫 
function startLoading() {
	let sec = 0;
	
	// 建立專門存放 loading 文字 的 span 方便清除
	const loader = document.createElement("span");
	loader.id = "current-loader";
	loader.style.color = "gray";
	loader.style.display = "block";
	
	loader.innerText = `AI 思考中... 已過 0 秒`;
	chatBox.appendChild(loader);
	
	timerId = setInterval(() => {
		sec++;
		loader.innerText = `AI 思考中... 已過 ${sec} 秒`;
	}, 1000);
	
	return loader;
}

// 停止 載入動畫 
function stopLoading() {
	if(timerId) {
		clearInterval(timerId);
		timerId = null;
	}
	
	const loader = document.getElementById("current-loader");
	if(loader) loader.remove();
}


// 一次性回應 呼叫 /gemini/ask
function ask() {
	const question = questionInput.value.trim();
	
	if(!question) return;
	
	chatBox.innerText += `You: ${question}\n`;
	questionInput.value = ''; 
	
	// 開始 讀取動畫
	startLoading();
	
	fetch(`http://localhost:8080/gemini/memory/ask?q=${encodeURIComponent(question)}`)
		.then(res => res.text())
		.then(answer => {
			stopLoading();	// 停止讀取動畫
			chatBox.innerText += `AI: ${answer}\n\n`;
			chatBox.scrollTop = chatBox.scrollHeight;
		})
		.catch(err => {
			chatBox.innerText += "錯誤呼叫\n";
			chatBox.scrollTop = chatBox.scrollHeight;
		})
}

// 串流回應 呼叫 /gemini/stream
function stream() {
	const question = questionInput.value.trim();
		
	if(!question) return;
	
	chatBox.innerText += `You: ${question}\n`;
	questionInput.value = ''; 
	
	// 開始 讀取動畫
	startLoading();
	
	// 建立 SSE(Server-Sent Events) 連線
	const eventSource = new EventSource(`http://localhost:8080/gemini/memory/stream?q=${encodeURIComponent(question)}`);
	
	let isFirstChunk = true;
	
	// 都後端送出一個事件資料(chunk)時, 就會觸發 onmessage
	eventSource.onmessage = function(event) {
		if(isFirstChunk) {
			stopLoading();	// 停止讀取動畫
			// 先在 chatBox 上印出 AI: 當作前綴字
			chatBox.innerText += 'AI: ';
			isFirstChunk = false;
		}
		
		chatBox.innerText += event.data;
		chatBox.scrollTop = chatBox.scrollHeight;
	};
	
	// 當連線失敗或後端關閉時就會觸發 onerror
	eventSource.onerror = function(err) {
		
		// 關閉 EventSource 連線
		eventSource.close();
		
		chatBox.innerText += "\n[Stream end]\n";
		chatBox.scrollTop = chatBox.scrollHeight;
	};
}