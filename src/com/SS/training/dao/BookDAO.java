package com.SS.training.dao;

import com.SS.training.entity.Author;
import com.SS.training.entity.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO extends DAO<Book> {
    public BookDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected List<Book> extractData(ResultSet rs) throws SQLException {
        List<Book> books = new ArrayList<>();
        while(rs.next()){
            Book b = new Book();
            b.setBookId(rs.getInt("bookId"));
            b.setTitle(rs.getString("title"));
            //set publisher
            PublisherDAO pdao = new PublisherDAO(conn);
            b.setPublisher(pdao.readByIdEssentialData(rs.getInt("pubId")));
//            //set author list
            AuthorDAO adao = new AuthorDAO(conn);
            b.setAuthors(adao.readBookAuthorByBookId(b.getBookId()));
            //set genre list
            GenreDAO gdao = new GenreDAO(conn);
            b.setGenres(gdao.readBookGenre(b.getBookId()));
            books.add(b);
        }
        return books;
    }

    @Override
    protected List<Book> extractEssentialData(ResultSet rs) throws SQLException {
        List<Book> books = new ArrayList<>();
        while(rs.next()){
            Book b = new Book();
            b.setBookId(rs.getInt("bookId"));
            b.setTitle(rs.getString("title"));
            //set publisher
            PublisherDAO pdao = new PublisherDAO(conn);
            b.setPublisher(pdao.readByIdEssentialData(rs.getInt("pubID")));
            books.add(b);
        }
        return books;
    }

    @Override
    public Integer add(Book object) throws SQLException {
        return saveRecieveKey("insert into tbl_book (bookId, title, pubId) values (?,?,?)",
                 new Object[]{object.getBookId(),object.getTitle(),object.getPublisher().getPublisherId()});
    }

    @Override
    public void update(Book object) throws SQLException {
        save("update tbl_book set title = ? pubId = ? where bookId=?",
                new Object[]{object.getTitle(),object.getPublisher().getPublisherId(),object.getBookId()});
    }

    @Override
    public void delete(Book object) throws SQLException {
        save("delete from tbl_book where bookId = ?", new Object[]{object.getBookId()});
    }

    @Override
    public List<Book> read() throws SQLException {
        return read("select * from tbl_book", null);
    }

    public Book readByBookId(int id) throws SQLException {
        List<Book> books = read("select * from tbl_book where bookId= ?", new Object[]{id});
        return  books.get(0);
    }

    public List<Book> readByPubId(int id) throws SQLException {
        return readEssential("select * from tbl_book where pubId= ?", new Object[]{id});
    }

    public void addToBookAuthor(Author author, Book book) throws SQLException {
        save("Insert into tbl_book_authors (bookId, AuthorId) values (?,?)", new Object[]{book.getBookId(),author.getAuthorID()});
    }

    public List<Book> readBookAuthorByAuthorId(int authorId) throws SQLException {
        return readEssential("select tb.bookId as bookId, title, pubId from tbl_book_authors as tba " +
                            "join tbl_book as tb on tba.bookId = tb.bookId " +
                            "where authorId = ?", new Object[]{authorId});
    }

    public List<Book> readBookGenreByBookId(int genreId) throws SQLException {
        return readEssential("select  tb.bookId as bookId, tb.title, tb.pubId  from tbl_book as tb " +
                "join tbl_book_genres as tbg on tb.bookId = tbg.bookId " +
                "where genre_id = ?", new Object[]{genreId});
    }

}
