package JDBC;

import org.testng.annotations.Test;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static JDBC.DatabaseHelper.*;

public class DatabaseTesting {

    @Test
    public void Task1() throws SQLException {

        String query1 = "select * from employees";
        List<List<String>> employees = getDataList(query1);

        for (int i = 0; i < employees.size(); i++) {

            for (int j = 0; j < employees.get(i).size(); j++) {
                System.out.print(employees.get(i).get(j).toString() + " ");
            }
            System.out.println();
        }
    }

    @Test
    public void Task4() throws SQLException {
        String sorgu2 = "select count(gender) as toplam_erkek_personel,sum(salary)/count(salary) as ortalama_maas from (select gender as gender, salary as salary from employees left join salaries on employees.emp_no = salaries.emp_no where gender like 'M') as ort";

        List<List<String>> line = getDataList(sorgu2);

        /* rs null döndürğü için şimdilik yorum olarak bıraktım sonra bakılabilir veya silinebilir
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rsmd.getColumnCount();
        System.out.println("kolonSayisi = " + columnNumber);
        for (int i = 1; i <= columnNumber; i++) {
            System.out.println(rsmd.getColumnName(i)+" "+ rsmd.getColumnTypeName(i));
        }
         */

        for (int i = 0; i < line.size(); i++) {
            for (int j = 0; j < line.get(i).size(); j++) {
                System.out.print(line.get(i).get(j).toString() + " ");
            }
            System.out.println();
        }
    }

}
