package JDBC;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;

public class TC15 extends DatabaseHelper {

    @Test
    public void TC15() throws SQLException {

        String sorgu =
                "select employees.emp_no, employees.first_name, employees.last_name, avg(salaries.salary) as ortalama_maas \n" +
                        "from employees \n" +
                        "JOIN dept_emp ON employees.emp_no = dept_emp.emp_no \n" +
                        "JOIN salaries ON employees.emp_no = salaries.emp_no \n" +
                        "where dept_emp.dept_no = 'd008' \n" +
                        "group by employees.emp_no, employees.first_name, employees.last_name \n" +
                        "order by ortalama_maas desc \n" +
                        "limit 1;";

        ArrayList<ArrayList<String>> sonuc =DatabaseHelper.getListData(sorgu);

        // Başlıklar
        System.out.printf("%-15s %-15s %-20s%n", "Ortalama Maaş", "Ad", "Soyad");
        System.out.println("----------------------------------------------------------");

        // Satırları yazdır
        for (ArrayList<String> satir : sonuc) {
            System.out.printf("%-15s %-15s %-20s%n", satir.get(0), satir.get(1), satir.get(2));
        }
    }

}
