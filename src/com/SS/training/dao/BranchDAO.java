package com.SS.training.dao;

import com.SS.training.entity.Branch;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BranchDAO extends DAO<Branch> {

    public BranchDAO(Connection conn) {super(conn);}

    @Override
    protected List<Branch> extractData(ResultSet rs) throws SQLException {
        List<Branch> branches = new ArrayList<>();
        while(rs.next()){
            Branch b = new Branch();
            b.setBranchId(rs.getInt("branchId"));
            b.setBranchName(rs.getString("branchName"));
            b.setBranchAddress(rs.getString("branchAddress"));
            CopiesDOA cdoa = new CopiesDOA(conn);
            b.setCopies(cdoa.readByBranchId(b.getBranchId()));
            //setup loans
            LoanDAO ldao = new LoanDAO(conn);
            b.setLoans(ldao.readByBranchId(b.getBranchId()));
            branches.add(b);
        }
        return branches;
    }

    @Override
    protected List<Branch> extractEssentialData(ResultSet rs) throws SQLException {
        List<Branch> branches = new ArrayList<>();
        while(rs.next()){
            Branch b = new Branch();
            b.setBranchId(rs.getInt("branchId"));
            b.setBranchName(rs.getString("branchName"));
            b.setBranchAddress(rs.getString("branchAddress"));
            branches.add(b);
        }
        return branches;
    }


    @Override
    public Integer add(Branch object) throws SQLException {
        return saveRecieveKey("insert into tbl_library_branch (branchName, branchAddress) values (?,?)",
                new Object[]{object.getBranchName(),object.getBranchAddress()});
    }

    @Override
    public void update(Branch object) throws SQLException {
        save("update tbl_library_branch set branchName = ? , branchAddress = ? where branchId = ?",
                new Object[]{object.getBranchName(),object.getBranchAddress(),object.getBranchId()});
    }

    @Override
    public void delete(Branch object) throws SQLException {
        save("delete from tbl_library_branch where branchId = ?", new Object[]{object.getBranchId()});
    }

    @Override
    public List<Branch> read() throws SQLException {
        return read("select * from tbl_library_branch", null);
    }

     public Branch readByBranchId(int branchId) throws SQLException {
        List<Branch> b = read("select * from tbl_library_branch where branchId = ?", new Object[]{branchId});
        return b.get(0);
     }

    public Branch readByBranchIdEssentialData(int branchId) throws SQLException {
        List<Branch> b = readEssential("select * from tbl_library_branch where branchId = ?", new Object[]{branchId});
        return b.get(0);
    }
}
