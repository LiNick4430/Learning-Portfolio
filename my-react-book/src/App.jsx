import { useState, useEffect } from 'react'
import './App.css'

function App() {
  const DEFAULTBOOK = { id: 0, name: '', price: 0.0, amount: 0.0, pub: "true" };
  const [books, setbooks] = useState([]);/* 建立一個 空的 books, setbooks方法 來 replace */
  const [newBook, setNewBook] = useState(DEFAULTBOOK);

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
    // alert(JSON.stringify(newBook));
    console.log("新增: " + JSON.stringify(newBook));
    fetch("http://localhost:8080/api/book", {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newBook)
    })
      /* 1. 網路回應 */
      .then((response) => {
        if (!response.ok) {
          throw new Error("網路回應錯誤");
        }
        return response.json();
      })
      /* 2. 資料處理 */
      .then((jsonDate) => {
        console.log("新增回應: " + JSON.stringify(jsonDate));
        if (jsonDate.success) {
          console.log("新增成功")
          // 清空欄位
          setNewBook(DEFAULTBOOK);
          // 刷新資料 / 重新查詢資料
          fetchBooks();
        } else {
          console.log("新增失敗: " + jsonDate.message);
        }
      })
      /* 3. 錯誤處理 */
      .catch((error) => {
        console.log(error);
        alert(error);
      })
  }

  // 編輯書籍
  function editBook(book) {
    setNewBook({
      id: book.id,
      name: book.name,
      price: book.price,
      amount: book.amount,
      pub: book.pub
    });
  }

  // 更新書籍
  function updateBook() {
    fetch(`http://localhost:8080/api/book/${newBook.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newBook)
    })
      /* 1. 網路回應 */
      .then((response) => {
        if (!response.ok) {
          throw new Error("網路回應錯誤");
        }
        return response.json();
      })
      /* 2. 資料處理 */
      .then((jsonDate) => {
        console.log("更新回應: " + JSON.stringify(jsonDate));
        if (jsonDate.success) {
          console.log("更新成功")
          // 清空欄位
          setNewBook(DEFAULTBOOK);
          // 刷新資料 / 重新查詢資料
          fetchBooks();
        } else {
          console.log("更新失敗: " + jsonDate.message);
        }
      })
      /* 3. 錯誤處理 */
      .catch((error) => {
        console.log(error);
        alert(error);
      })
  }

  // 取消編輯
  function cancelEdit() {
    setNewBook(DEFAULTBOOK);
  }

  // 刪除書籍
  function deleteBook(book) {
    // 獲取 book's id
    const id = book.id;
    fetch(`http://localhost:8080/api/book/${id}`, {
      method: 'DELETE'
    })
      /* 1. 網路回應 */
      .then((response) => {
        if (!response.ok) {
          throw new Error("網路回應錯誤");
        }
        return response.json();
      })
      /* 2. 資料處理 */
      .then((jsonDate) => {
        console.log("刪除回應: " + JSON.stringify(jsonDate));
        if (jsonDate.success) {
          console.log("刪除成功")
          // 刷新資料 / 重新查詢資料
          fetchBooks();
        } else {
          console.log("刪除失敗: " + jsonDate.message);
        }
      })
      /* 3. 錯誤處理 */
      .catch((error) => {
        console.log(error);
        alert(error);
      })
  }

  return (
    <>
      <h1>My React 書籍列表</h1>
      <h2>
        {books.length} 筆
        <ul>
          {
            books.map((book) => {
              return (
                <li key={book.id}>{book.name} ${book.price} {book.amount}本 {book.pub === true ? "出版中" : "絕版中"}
                  &nbsp;
                  <button onClick={() => editBook(book)}>編輯</button>
                  &nbsp;
                  <button onClick={() => deleteBook(book)}>刪除</button>
                </li>
              )
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
      {
        newBook.id === 0 ?
          (<button onClick={() => addBook()}>新增書籍</button>) :
          (<>
            <button onClick={() => updateBook()}>更新書籍</button>
            <button onClick={() => cancelEdit()}>取消編輯</button>
          </>)
      }
    </>
  )
}

export default App
