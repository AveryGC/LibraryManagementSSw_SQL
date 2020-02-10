package com.SS.training.dao;

import com.SS.training.entity.Genre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO extends DAO<Genre> {
    public GenreDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected List<Genre> extractData(ResultSet rs) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        while(rs.next()){
            Genre g = new Genre();
            g.setGenreID(rs.getInt("genre_id"));
            g.setGenreName(rs.getString("genre_name"));
            //get book list
            BookDAO bdao = new BookDAO(conn);
            g.setBooks(bdao.readBookGenreByBookId(g.getGenreID()));
            genres.add(g);
        }
        return genres;
    }

    @Override
    protected List<Genre> extractEssentialData(ResultSet rs) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        while(rs.next()){
            Genre g = new Genre();
            g.setGenreID(rs.getInt("genre_id"));
            g.setGenreName(rs.getString("genre_name"));
            genres.add(g);
        }
        return genres;
    }

    @Override
    public Integer add(Genre object) throws SQLException {
        return saveRecieveKey("insert into tbl_genre (genre_name) value (?)",
                new Object[]{object.getGenreName()});
    }

    @Override
    public void update(Genre object) throws SQLException {
        save("update tbl_genre set genre_name = ? where genre_id = ?",
                new Object[]{object.getGenreName(),object.getGenreID()});
    }

    @Override
    public void delete(Genre object) throws SQLException {
        save("delete from tbl_genre where genre_id = ?", new Object[]{object.getGenreID()});
    }

    @Override
    public List<Genre> read() throws SQLException {
        return read("select * from tbl_genre", null);
    }

    public List<Genre> readBookGenre(int bookId) throws SQLException {
        return readEssential("select genre_name, tg.genre_id as genre_id from tbl_genre as tg " +
                "join tbl_book_genres as tbg on tg.genre_id = tbg.genre_id " +
                "where bookId= ?",new Object[]{bookId});
    }

}
