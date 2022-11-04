import { useEffect, useState } from "react";
import "./App.css";

function App() {
  const [books, setBooks] = useState([]);

  return (
    <div className="App">
      <AddBook setBooks={setBooks} />
      <ListBooks books={books} setBooks={setBooks} />
    </div>
  );
}

function AddBook({ setBooks }: { setBooks: any }) {
  const [title, setTitle] = useState("");
  const [author, setAuthor] = useState("");
  const [loading, setLoading] = useState(false);

  async function handleSubmit(e: any) {
    e.preventDefault();
    setLoading(true);
    await fetch("/api/books", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ title, author }),
    });
    window.location.reload();
  }

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <h3>Add book</h3>
        <div>
          <label>Author</label>
          <input type="text" onChange={(e) => setAuthor(e.target.value)} />
        </div>
        <div>
          <label>Title</label>
          <input type="text" onChange={(e) => setTitle(e.target.value)} />
        </div>
        <button>Submit</button>
      </form>
    </div>
  );
}

function ListBooks({ books, setBooks }: { books: any[]; setBooks: any }) {
  const [loading, setLoading] = useState(true);
  // @ts-ignore
  useEffect(async () => {
    const response = await fetch("/api/books");
    const books = await response.json();
    setBooks(books);
    setLoading(false);
  }, []);
  if (loading) {
    return <div>Loading...</div>;
  }
  return (
    <div>
      <h3>Books</h3>
      <ul>
        {books.map((book, index) => (
          <li key={index}>
            Book: {book.title}, Author: {book.author}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;
