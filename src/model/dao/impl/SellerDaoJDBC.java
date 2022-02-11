package model.dao.impl;

import db.DB;
import model.dao.SellerDao;
import model.entitites.Department;
import model.entitites.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller fingById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    """
                            SELECT seller.*, department.Name as DepName
                            FROM seller INNER JOIN department
                            ON seller.DepartmentId = department.Id
                            WHERE seller.Id = ?
                            """
            );
            st.setInt(1, id);
            rs = st.executeQuery();
            //começa numa posição vazia, usamos rs.next() para ir para as proximas linhas da tabela
            if (rs.next()){
                Department dep = instantiateDepartment(rs);
                Seller seller = instatiateSeller(rs, dep);
                return seller;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

        return null;
    }

    //instaciando o seller a partir dos dados do banco
    private Seller instatiateSeller(ResultSet rs, Department dep) throws SQLException {

        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setEmail(rs.getString("Email"));
        seller.setBaseSalary(rs.getDouble("BaseSalary"));
        seller.setBirthDate(rs.getDate("BirthDate"));
        seller.setDepartment(dep);

        return seller;
    }

    //instaciando o Department a partir dos dados do banco
    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }
}
