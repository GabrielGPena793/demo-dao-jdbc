package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entitites.Department;
import model.entitites.Seller;

import java.util.List;

public class Program {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findyById ===");
        Seller seller = sellerDao.fingById(3);
        System.out.println(seller);

        System.out.println("\n=== TEST 2: seller findyByIdDepartment ===");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        list.forEach(System.out::println);

        System.out.println("\n=== TEST 3: seller findyAll ===");
        list = sellerDao.findAll();
        list.forEach(System.out::println);

    }
}
