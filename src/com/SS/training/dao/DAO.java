package com.SS.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class DAO<T> {
    protected static Connection conn= null;

    public DAO(Connection conn) {
        this.conn = conn;
    }

    protected void save(String sql, Object[] vals) throws SQLException {
        PreparedStatement  pstmt = conn.prepareStatement(sql);
        if(vals!=null){
            int index = 1;
            for(Object o : vals){
                pstmt.setObject(index,o);
                index++;
            }
        }
        pstmt.executeUpdate();
    }

    protected Integer saveRecieveKey(String sql, Object[] vals) throws SQLException {
        PreparedStatement  pstmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
        if(vals!=null){
            int index = 1;
            for(Object o : vals){
                pstmt.setObject(index,o);
                index++;
            }
        }
        ResultSet rs = pstmt.executeQuery();
        Integer key = null;
        while(rs.next()){
            key = rs.getInt(1);
        }
        return key;
    }

    protected List<T> read(String sql, Object[] vals) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if (vals!=null) {
            int index = 1;
            for(Object o : vals) {
                pstmt.setObject(index, o);
                index++;
            }
        }
        ResultSet rs = pstmt.executeQuery();
        return extractData(rs);
    }
    protected List<T> readEssential(String sql, Object[] vals) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if (vals!=null) {
            int index = 1;
            for(Object o : vals) {
                pstmt.setObject(index, o);
                index++;
            }
        }
        ResultSet rs = pstmt.executeQuery();
        return extractEssentialData(rs);
    }


    protected abstract List<T> extractData(ResultSet rs) throws SQLException;
    protected abstract List<T> extractEssentialData(ResultSet rs) throws SQLException;

    public abstract Integer add(T object)throws SQLException;
    public abstract void update(T object)throws SQLException;
    public abstract void delete(T object)throws SQLException;
    public abstract List<T> read() throws SQLException;

}
