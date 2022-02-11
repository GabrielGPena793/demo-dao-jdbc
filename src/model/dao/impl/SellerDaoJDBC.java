package model.dao.impl;

import db.DB;
import model.dao.SellerDao;
import model.entitites.Department;
import model.entitites.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("""
                    SELECT seller.*,department.Name as DepName
                    FROM seller INNER JOIN department
                    ON seller.DepartmentId = department.Id
                    WHERE DepartmentId = ?
                    ORDER BY Name
                    """);

            st.setInt(1,department.getId());
            rs = st.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer,Department> map = new HashMap<>();

            while (rs.next()){

                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = instatiateSeller(rs, dep);
                sellers.add(seller);
            }
            return sellers;

        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }

        return null;
    }
}
