import { useState, useEffect } from 'react'
import './App.css'

function App() {
  const [books, setbooks] = useState([]);/* 建立一個 空的 books, setbooks方法 來 replace */
  const [newBook, setNewBook] = useState({ name: '', price: 0.0, amount: 0.0, pub: "true" });

  /* useEffect(lambda表達式, [變數]), []內 變數 改變會執行 lambda, 當[] 為空值時 則 載入網頁時執行*/
  useEffect(() => {
    fetchBooks();
  }, [])

  // 取得所有書籍
  function fetchBooks() {
    fetch("http://localhost:8080/api/books")
      /* 1. 網路回應 */
      .then((response) => {
        if (!response.ok) {
          throw new Error("網路回應錯誤");
        }
        return response.json();
      })
      /* 2. 資料處理 */
      .then((jsonDate) => {
        setbooks(jsonDate.data);  // 資料透過 setbooks 注入
      })
      /* 3. 錯誤處理 */
      .catch((error) => {
        console.log(error);
        alert(error);
      })
  }

  // 新增書籍
  function addBook() {
    alert(JSON.stringify(newBook))
  }

  return (
    <>
      <h1>My React 書籍列表</h1>
      <h2>
        {books.length} 筆
        <ul>
          {
            books.map((book) => {
              return (<li key={book.id}>{book.name} ${book.price} {book.amount}本 {book.pub === true ? "出版中" : "絕版中"}</li>)
            })
          }
        </ul>
      </h2>

      <h1>My React 書籍新增</h1>
      書名: <input type='text' value={newBook.name} onChange={e => setNewBook({ ...newBook, name: e.target.value })} /> <p />
      價格: <input type='number' value={newBook.price} onChange={e => setNewBook({ ...newBook, price: e.target.value })} /> <p />
      數量: <input type='number' value={newBook.amount} onChange={e => setNewBook({ ...newBook, amount: e.target.value })} /> <p />
      出版: <select value={newBook.pub ? "true" : "false"} onChange={e => setNewBook({ ...newBook, pub: e.target.value === "true" })} >
        <option value="true">出版中</option>
        <option value="false">絕版中</option>
      </select> <p />
      <button onClick={addBook}>新增書籍</button>
    </>
  )
}

export default App
