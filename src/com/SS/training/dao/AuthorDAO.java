package com.SS.training.dao;

import com.SS.training.entity.Author;
import com.SS.training.entity.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO extends DAO<Author> {
    public AuthorDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected List<Author> extractData(ResultSet rs) throws SQLException {
        List<Author> a = new ArrayList<>();
        while(rs.next()){
            Author author= new Author();
            author.setAuthorID(rs.getInt("authorId"));
            author.setAuthorName(rs.getString("authorName"));
            //set book list
            BookDAO bdao = new BookDAO(conn);
            author.setBooks(bdao.readBookAuthorByAuthorId(author.getAuthorID()));
            a.add(author);
            }
        return a;
    }

    @Override
    protected List<Author> extractEssentialData(ResultSet rs) throws SQLException {
        List<Author> a = new ArrayList<>();
        while(rs.next()) {
            Author author = new Author();
            author.setAuthorID(rs.getInt("authorId"));
            author.setAuthorName(rs.getString("authorName"));
            a.add(author);
        }
        return a;
    }

    @Override
    public Integer add(Author object) throws SQLException {
        return saveRecieveKey("Insert into tbl_author (authorName) value (?)", new Object[]{object.getAuthorName()});
    }

    @Override
    public void update(Author object) throws SQLException {
        save("update tbl_author set authorName = ? where authorId = ?", new Object[]{object.getAuthorName(),object.getAuthorID()});
    }

    @Override
    public void delete(Author object) throws SQLException {
        save("delete from tbl_author where authorID = ?", new Object[]{object.getAuthorID()});
    }

    @Override
    public List<Author> read() throws SQLException {
        return read("select * from tbl_author", null);
    }

    public void addToBookAuthor(Author author, Book book) throws SQLException {
        save("Insert into tbl_book_authors (bookId, AuthorId) values (?,?)", new Object[]{book.getBookId(),author.getAuthorID()});
    }

    public Author readByID(Author object) throws SQLException{
        List<Author> newAuthor = read("select * from tbl_author where authorId = ?",new Object[]{object.getAuthorID()});
        return newAuthor.get(0);
    }

    public List<Author> readBookAuthorByBookId(int bookId) throws SQLException {
        return readEssential("select ta.authorName as authorName, ta.authorId as authorId from tbl_book_authors as tba " +
                            "join tbl_author as ta on tba.authorId = ta.authorId " +
                            "where bookId = ?",new Object[]{bookId});
    }

}
