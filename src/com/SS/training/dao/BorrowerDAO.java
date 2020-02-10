package com.SS.training.dao;

import com.SS.training.entity.Borrower;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowerDAO extends DAO<Borrower> {
    public BorrowerDAO(Connection conn) { super(conn);}

    @Override
    protected List<Borrower> extractData(ResultSet rs) throws SQLException {
        List<Borrower> borrowers = new ArrayList<>();
        while (rs.next()){
            Borrower b = new Borrower();
            b.setCardNo(rs.getInt("cardNo"));
            b.setName(rs.getString("name"));
            b.setAddress(rs.getString("address"));
            b.setPhone(rs.getString("phone"));
            //get loans
            LoanDAO ldao = new LoanDAO(conn);
            b.setLoans(ldao.readByCardNo(b.getCardNo()));
            borrowers.add(b);
        }
        return borrowers;
    }

    @Override
    protected List<Borrower> extractEssentialData(ResultSet rs) throws SQLException {
        List<Borrower> borrowers = new ArrayList<>();
        while (rs.next()){
            Borrower b = new Borrower();
            b.setCardNo(rs.getInt("cardNo"));
            b.setName(rs.getString("name"));
            b.setAddress(rs.getString("address"));
            b.setPhone(rs.getString("phone"));
            borrowers.add(b);
        }
        return borrowers;
    }

    @Override
    public Integer add(Borrower object) throws SQLException {
        return saveRecieveKey("insert into tbl_borrower (name, address, phone) values (?,?,?)",
                new Object[]{object.getName(),object.getAddress(),object.getPhone()});
    }

    @Override
    public void update(Borrower object) throws SQLException {
        save("update tbl_borrower set name= ?, address = ?, phone = ? where cardNo = ?",
                new Object[]{object.getName(),object.getAddress(),object.getPhone(),object.getCardNo()});
    }

    @Override
    public void delete(Borrower object) throws SQLException {
        save("delete from tbl_borrower where cardNo = ?", new Object[]{object.getCardNo()});
    }

    @Override
    public List<Borrower> read() throws SQLException {
        return read("select * from tbl_borrower",null);
    }

    public Borrower readByCardNo(int cardNo) throws SQLException{
        List<Borrower> b = read("select * from tbl_borrower where cardNo = ?", new Object[]{cardNo});
        return b.get(0);
    }

    public Borrower readByCardNoEssentialData(int cardNo) throws SQLException{
        List<Borrower> b = readEssential("select * from tbl_borrower where cardNo = ?", new Object[]{cardNo});
        return b.get(0);
    }

}
