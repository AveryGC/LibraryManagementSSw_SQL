package com.SS.training.dao;

import com.SS.training.entity.Loans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO extends DAO<Loans> {
    public LoanDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected List<Loans> extractData(ResultSet rs) throws SQLException {
        List<Loans> loans = new ArrayList<>();
        while (rs.next()){
            Loans l = new Loans();
            BookDAO bdao = new BookDAO(conn);
            l.setBook(bdao.readByBookId(rs.getInt("bookId")));
            BranchDAO ranch = new BranchDAO(conn);
            l.setBranch(ranch.readByBranchIdEssentialData(rs.getInt("branchId")));
            BorrowerDAO rowdao = new BorrowerDAO(conn);
            l.setBorrower(rowdao.readByCardNoEssentialData(rs.getInt("cardNo")));
            l.setDateIn(rs.getDate("dateIn"));
            l.setDateOut(rs.getDate("dateOut"));
            l.setDueDate(rs.getDate("dueDate"));
            loans.add(l);
        }
        return loans;
    }

    @Override
    protected List<Loans> extractEssentialData(ResultSet rs) throws SQLException {
        return extractData(rs);
    }

    @Override
    public Integer add(Loans object) throws SQLException {
        save("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate, dateIn) values (?,?,?,?,?,?)",
                new Object[]{object.getBook().getBookId(),object.getBranch().getBranchId(),object.getBorrower().getCardNo(),object.getDateOut(),object.getDueDate(),object.getDateIn()});
        return null;
    }

    @Override
    public void update(Loans object) throws SQLException {
        save("update tbl_book_loans set dateOut = ?, dateIn=?, dueDate = ? where bookId = ? && branchId = ? && cardNo = ?",
                new Object[]{object.getDateOut(),object.getDateIn(),object.getDueDate(),object.getBook().getBookId(),object.getBranch().getBranchId(),object.getBorrower().getCardNo()});

    }

    @Override
    public void delete(Loans object) throws SQLException {
        save("delete from tbl_book_loans where bookId = ? && cardNo = ? && and branchId = ?",
                new Object[]{object.getBook().getBookId(),object.getBorrower().getCardNo(),object.getBranch().getBranchId()});
    }

    @Override
    public List<Loans> read() throws SQLException {
        return read("select * from tbl_book_loans", null);
    }

    public List <Loans> readByCardNo(int cardNo) throws SQLException {
        return readEssential("select * from tbl_book_loans where cardNo = ? && dateIn is null", new Object[]{cardNo} );
    }

    public List <Loans> readByBranchId(int branchId) throws SQLException {
        return readEssential("select * from tbl_book_loans where branchId = ?", new Object[]{branchId} );
    }

    public List <Loans> readByBookId(int bookId) throws SQLException {
        return readEssential("select * from tbl_book_loans where bookId = ?", new Object[]{bookId} );
    }
}
