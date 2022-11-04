import { useEffect, useState } from "react";
import "./App.css";

function App() {
  const [books, setBooks] = useState([]);

  return (
    <div className="App">
      <ListBooks books={books} setBooks={setBooks} />
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
  return (
    <ul>
      {books.map((book, index) => (
        <li key={index}>
          Book: {book.title}, Author: {book.author}
        </li>
      ))}
    </ul>
  );
}

export default App;
