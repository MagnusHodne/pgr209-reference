package no.kristiania.library;

public class Book {
    private final int id;
    private String title;
    private String author;

    public Book(
            int id,
            String title,
            String author
    ) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
