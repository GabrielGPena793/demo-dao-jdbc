package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entitites.Department;

import java.util.List;
import java.util.Scanner;

public class Program2 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== TEST 1: Department findAll ===");
        List<Department> departments = departmentDao.findAll();
        departments.forEach(System.out::println);

        System.out.println("\n=== TEST 2: Department findById ===");
        Department department = departmentDao.fingById(3);
        System.out.println(department);

//        System.out.println("\n=== TEST 3: Department insert ===");
//        Department department1 = new Department(null,"Music");
//        departmentDao.insert(department1);
//        System.out.println("Insert completed!");

        System.out.println("\n=== TEST 4: Department update ===");
        department.setName("Sports");
        departmentDao.update(department);
        System.out.println("upadate completed!");

        System.out.println("\n=== TEST 5: Department delete ===");
        System.out.print("Enter id for delete test: ");
        int id = scanner.nextInt();
        departmentDao.deleteById(id);
        System.out.println("delete completed");

    }
}
