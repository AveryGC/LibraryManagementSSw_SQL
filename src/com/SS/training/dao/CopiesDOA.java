package com.SS.training.dao;

import com.SS.training.entity.Copies;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CopiesDOA extends DAO<Copies> {
    public CopiesDOA(Connection conn) {super(conn);}

    @Override
    protected List<Copies> extractData(ResultSet rs) throws SQLException {
        List<Copies> copies = new ArrayList<>();
        while (rs.next()){
            Copies c = new Copies();
            BookDAO bdao = new BookDAO(conn);
            c.setBook(bdao.readByBookId(rs.getInt("bookId")));
            BranchDAO branch = new BranchDAO(conn);
            c.setBranch(branch.readByBranchIdEssentialData(rs.getInt("branchId")));
            c.setNoOfCopies(rs.getInt("noOfCopies"));
            copies.add(c);
        }
        return copies;
    }

    @Override
    protected List<Copies> extractEssentialData(ResultSet rs) throws SQLException {

        return extractData(rs);
    }

    @Override
    public Integer add(Copies object) throws SQLException {
        save("insert into tbl_book_copies (bookId, branchId, noOfCopies) values (?,?,?)",
            new Object[]{object.getBook().getBookId(),object.getBranch().getBranchId(),object.getNoOfCopies()});
        return null;
    }

    @Override
    public void update(Copies object) throws SQLException {
        save("update tbl_book_copies set noOfCopies = ? where bookId = ? && branchId = ?",
                new Object[]{object.getNoOfCopies(),object.getBook().getBookId(),object.getBranch().getBranchId()});
    }

    @Override
    public void delete(Copies object) throws SQLException {
        save("delete from tbl_book_copies where bookId = ? && branchId = ?",
                new Object[]{object.getBook().getBookId(),object.getBranch().getBranchId()});
    }

    @Override
    public List<Copies> read() throws SQLException {
        return read("select * from tbl_book_copies",null);
    }

    public List<Copies> readByBranchId(int branchId) throws SQLException {
        return readEssential("select * from tbl_book_copies where branchId = ?",new Object[]{branchId});
    }

    public List<Copies> readByBookId(int bookId) throws SQLException {
        return read("select * from tbl_books_copies where bookId = ?", new Object[]{bookId});
    }
}
