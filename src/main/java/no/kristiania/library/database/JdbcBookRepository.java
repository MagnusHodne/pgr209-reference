package no.kristiania.library.database;

import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcBookRepository implements BookRepository {

    private final DataSource dataSource;

    @Inject
    public JdbcBookRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public void save(Book book) {
        try(var connection = dataSource.getConnection()) {
            var statement = connection.prepareStatement(
                    "INSERT INTO books (title, author) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.executeUpdate();

            try(var generatedKeys = statement.getGeneratedKeys()) {
                generatedKeys.next();
                book.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book retrieve(long id) {
        return null;
    }

    @Override
    public List<Book> listAll() {
        try(var connection = dataSource.getConnection()) {
            var statement = connection.prepareStatement("SELECT * FROM books");
            try(var rs = statement.executeQuery()) {
                var list = new ArrayList<Book>();
                while(rs.next()) {
                    var book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    list.add(book);
                }
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
