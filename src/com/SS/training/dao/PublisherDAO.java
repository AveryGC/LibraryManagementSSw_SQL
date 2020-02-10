package com.SS.training.dao;

import com.SS.training.entity.Publisher;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublisherDAO extends DAO<Publisher> {
    public PublisherDAO(Connection conn) { super(conn);}

    @Override
    protected List<Publisher> extractData(ResultSet rs) throws SQLException {
        List<Publisher> p = new ArrayList<>();
        while(rs.next()){
            Publisher publisher = new Publisher();
            publisher.setPublisherId(rs.getInt("publisherId"));
            publisher.setPublisherName(rs.getString("publisherName"));
            publisher.setPublisherAddress(rs.getString("publisherAddress"));
            publisher.setPublisherPhone(rs.getString("publisherPhone"));
            BookDAO bdao = new BookDAO(conn);
            publisher.setBooks(bdao.readByPubId(publisher.getPublisherId()));
            p.add(publisher);
        }
        return p;
    }

    @Override
    protected List<Publisher> extractEssentialData(ResultSet rs) throws SQLException {
        List<Publisher> p = new ArrayList<>();
        while(rs.next()){
            Publisher publisher = new Publisher();
            publisher.setPublisherId(rs.getInt("publisherId"));
            publisher.setPublisherName(rs.getString("publisherName"));
            publisher.setPublisherAddress(rs.getString("publisherAddress"));
            publisher.setPublisherPhone(rs.getString("publisherPhone"));
            p.add(publisher);
        }
        return p;
    }

    @Override
    public Integer add(Publisher object) throws SQLException {
        return saveRecieveKey("insert into tbl_publisher (publisherName,publisherAddress,publisherPhone," +
                    "value (?,?,?)", new Object[]{object.getPublisherName(),object.getPublisherAddress(),object.getPublisherPhone()});
    }

    @Override
    public void update(Publisher object) throws SQLException {
        save("update tbl_publisher set publisherName = ? , publisherAddress = ? , publisherPhone = ? where publisherId = ?",
                new Object[]{object.getPublisherName(),object.getPublisherAddress(),object.getPublisherPhone(),object.getPublisherId()});
    }

    @Override
    public void delete(Publisher object) throws SQLException {
        save("delete from tbl_publisher where publisherId = ?",new Object[] {object.getPublisherId()});
    }

    @Override
    public List<Publisher> read() throws SQLException {
        return read("select * from tbl_publisher",null);
    }

    public Publisher readByID(int id) throws SQLException {
        List<Publisher> newPublisher =  read("select * from tbl_publisher where publisherId = ?",new Object[]{id});
        return newPublisher.get(0);
    }

    public Publisher readByIdEssentialData(int id) throws SQLException {
        List<Publisher> newPublisher =  readEssential("select * from tbl_publisher where publisherId = ?",new Object[]{id});
        return newPublisher.get(0);
    }

}
