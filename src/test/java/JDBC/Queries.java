package JDBC;

import org.testng.annotations.Test;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Queries extends DatabaseHelper {


    // 1. List all employees in department D001.
    // - D001 departmanındaki tüm çalışanları listele.
    @Test
    public void Task1() throws SQLException {

        String query1 = "select * from employees";
        ArrayList<ArrayList<String>> employees = getListData(query1);

        for (int i = 0; i < employees.size(); i++) {

            for (int j = 0; j < employees.get(i).size(); j++) {
                System.out.print(employees.get(i).get(j).toString() + " ");
            }
            System.out.println();
        }
    }

    // 2. List all employees in 'Human Resources' department.
    // - 'İnsan Kaynakları' departmanındaki tüm çalışanları listele.
    @Test(groups = {"EmployeeQueries"})
    public void listEmployeesInHumanResources() {
        String query =
                "SELECT e.*, d.dept_name " +
                        "FROM employees e " +
                        "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                        "JOIN departments d ON de.dept_no = d.dept_no " +
                        "WHERE d.dept_name = 'Human Resources' " +
                        "LIMIT 100;";

        try {
            System.out.println("Employees in Human Resources Department:");
            executeEmployeeQuery(query);
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    // 3. Calculate the average salary of all employees
    // - Tüm çalışanların ortalama maaşını hesapla.
    @Test
    public void Query3() {
        String query3 = " SELECT emp_no, AVG(salary) FROM employees.salaries group by emp_no";
        try {
            System.out.println("Average salary of all employees:");
            executeEmployeeQuery(query3);
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }


    // 4. Calculate the average salary of all employees with gender "M"
    // - "Erkek" cinsiyetindeki tüm çalışanların ortalama maaşını hesapla.
    @Test
    public void Task4() throws SQLException {
        String sorgu2 = "select count(gender) as toplam_erkek_personel,sum(salary)/count(salary) as ortalama_maas from (select gender as gender, salary as salary from employees left join salaries on employees.emp_no = salaries.emp_no where gender like 'M') as ort";

        ArrayList<ArrayList<String>> line = getListData(sorgu2);

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

    // 5. Calculate the average salary of all employees with gender "F"
    // - "Kadın" cinsiyetindeki tüm çalışanların ortalama maaşını hesapla.
    @Test
    public void Task5() throws SQLException {
        String sorgu2 = "select count(gender) as toplam_kadin_personel,sum(salary)/count(salary) as ortalama_maas from (select gender as gender, salary as salary from employees left join salaries on employees.emp_no = salaries.emp_no where gender like 'F') as ort";

        ArrayList<ArrayList<String>> line = getListData(sorgu2);

        for (int i = 0; i < line.size(); i++) {
            for (int j = 0; j < line.get(i).size(); j++) {
                System.out.print(line.get(i).get(j).toString() + " ");
            }
            System.out.println();
        }
    }

    // 6. List all employees in the "Sales" department with a salary greater than 70,000.
    // - Maaşı 70.000'den yüksek olan "Satış" departmanındaki tüm çalışanları listele
    @Test(groups = {"EmployeeQueries", "SalaryQueries"})
    public void listEmployeesInSalesDepartmentWithHighSalary() {

        String query =
                "select e.*, s.salary " +
                        "from employees e " +
                        "join dept_emp de on e.emp_no = de.emp_no " +
                        "join departments d on de.dept_no = d.dept_no " +
                        "join salaries s on e.emp_no = s.emp_no " +
                        "where d.dept_name = 'Sales' " +
                        "and s.salary > 70000 " +
                        "and s.to_date = '9999-01-01' " +
                        "limit 100;";

        try {
            System.out.println("Employees in Sales Department with Salary > $70,000:");
            executeEmployeeQuery(query);
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }


    // 7. This query retrieves employees who have salaries between 50000 and 100000.
    @Test
    public void Query7() {
        String query =
                "select e.*, s.salary " +
                        "from employees e " +
                        "join salaries s on e.emp_no = s.emp_no " +
                        "where s.salary between 50000 and 100000 " +
                        "and s.to_date = '9999-01-01' " +
                        "limit 100;";

        try {
            System.out.println("Employees with Salary between $50,000 and $100,000:");
            executeEmployeeQuery(query);
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
    // - Bu sorgu, maaşı 50.000 ile 100.000 arasında olan çalışanları getirir.


    /// İBRAHİM CAN
    /// ↓↓↓
    /// QUERIES 8 and 9
    // 8. Calculate the average salary for each department (by department number or department name)
    // - Her departmanın ortalama maaşını hesapla (departman numarasına veya departman adına göre).
    @Test(groups = {"AvgSalaries"})
    public void getDepartmentAverageSalaries() {

        String query =
                "select " +
                        "de.dept_no as departman_no, " +
                        "d.dept_name as departman_adi, " +
                        "avg(s.salary) as ortalama_maas " +
                        "from dept_emp de " +
                        "join salaries s on de.emp_no = s.emp_no " +
                        "join departments d on de.dept_no = d.dept_no " +
                        "where s.to_date = '9999-01-01' " +
                        "group by de.dept_no, d.dept_name " +
                        "order by ortalama_maas desc";
        try {
            System.out.println();
            System.out.println("\t\t\t Department Average Salaries");
            System.out.println("============================================================");
            executeEmployeeQuery(query);
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    // 9. Calculate the average salary for each department, including department names
    // - Departman adlarını da içeren her departmanın ortalama maaşını hesapla.
    @Test(groups = {"EachAvgSalaries"})
    public void averageSalaryOfEachDepartment() {

        String query =
                "select " +
                        "`d`.`dept_no` as departman_numarasi, " +
                        "`d`.`dept_name` as departman_adi, " +
                        "AVG(`s`.`salary`) as ortalama_maas " +
                        "from `employees` e " +
                        "join `dept_emp` de on e.emp_no = de.emp_no " +
                        "join `salaries` s on e.emp_no = s.emp_no " +
                        "join `departments` d on de.dept_no = d.dept_no " +
                        "group by d.dept_no, d.dept_name " +
                        "order by ortalama_maas desc";
        try {
            System.out.println();
            System.out.println("\t\t\tAverage Salary Of Each Department ");
            System.out.println("=======================================================");
            executeEmployeeQuery(query);
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    /// /// ↑↑↑ Over ↑↑↑ ///

    // 10. Find all salary changes for employee with emp. no '10102'
    // - '10102' iş numarasına sahip çalışanın tüm maaş değişikliklerini bul.
    @Test
    public void Task10() throws SQLException {

        String query1 = "select emp_no,salary from salaries where emp_no like '10102'";
        ArrayList<ArrayList<String>> employee10102 = getListData(query1);

        for (int i = 0; i < employee10102.size(); i++) {

            for (int j = 0; j < employee10102.get(i).size(); j++) {
                System.out.print(employee10102.get(i).get(j).toString() + " ");
            }
            System.out.println();
        }
    }
    // 11. Find the salary increases for employee with employee number '10102' (using the to_date column
    // in salaries)


    @Test

    public void TC11() throws SQLException {

        String query13 = "select * " +
                "from salaries " +
                "where salaries.emp_no='10102';";

        try {
            System.out.println("8 row(s) returned");
            executeEmployeeQuery(query13);

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());

        }
    }


    // - Maaş numarası '10102' olan çalışanın maaş artışlarını bul ('to_date' sütununu kullanarak).
    // 12. Find the employee with the highest salary
    // - En yüksek maaşa sahip çalışanı bul.
    @Test(groups = {"SalaryQueries"})
    public void findEmployeeWithHighestSalary() {

        String query =
                "select e.*, s.salary " +
                        "from employees e " +
                        "join salaries s on e.emp_no = s.emp_no " +
                        "where s.to_date = '9999-01-01' " +
                        "order by s.salary desc " +
                        "limit 1;";

        try {
            System.out.println("Employee with the Highest Salary:");
            executeEmployeeQuery(query);
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    // 13. Find the latest salaries for each employee
    // - Her çalışanın en son maaşlarını bul.

    @Test
    public void TC13() throws SQLException {

        String query13 = "select emp_no, salary " +
                "from salaries " +
                "where to_date = '9999-01-01'";


        try {
            System.out.println("2000 row(s) returned");
            executeEmployeeQuery(query13);

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());

        }
    }


    // 14. List the first name, last name, and highest salary of employees in the "Sales" department.
    // Order the list by highest salary descending and only show the employee with the highest salary.
    // - "Satış" departmanındaki çalışanların adını, soyadını ve en yüksek maaşını listele.

    @Test
    public void TC14() throws SQLException {
        String query =
                "select e.first_name, e.last_name, max(s.salary) " +
                        "from employees e " +
                        "join dept_emp de on e.emp_no = de.emp_no " +
                        "join departments d on de.dept_no = d.dept_no " +
                        "join salaries s ON e.emp_no = s.emp_no " +
                        "where d.dept_name = 'Sales' " +
                        "group by  e.first_name, e.last_name, e.emp_no " +
                        "order by max(s.salary) desc " +
                        "limit 1;";

        try {
            System.out.println("Sales departmanında en yüksek maaşı alan çalışan:");
            executeEmployeeQuery(query);
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    // Listeyi en yüksek maaşa göre azalan şekilde sırala ve sadece en yüksek maaşa sahip çalışanı
    // göster.
    // 15. Find the Employee with the Highest Salary Average in the Research Department
    // - Araştırma Departmanındaki Ortalama En Yüksek Maaşlı Çalışanı Bul
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

        ArrayList<ArrayList<String>> sonuc = DatabaseHelper.getListData(sorgu);

        // Başlıklar
        System.out.printf("%-15s %-15s %-20s%n", "Ortalama Maaş", "Ad", "Soyad");
        System.out.println("----------------------------------------------------------");

        // Satırları yazdır
        for (ArrayList<String> satir : sonuc) {
            System.out.printf("%-15s %-15s %-20s%n", satir.get(0), satir.get(1), satir.get(2));
        }
    }
    // 16. For each department, identify the employee with the highest single salary ever recorded. List the
    // department name, employee's first name, last name, and the peak salary amount. Order the results
    // by the peak salary in descending order.
    // - Her departman için, kaydedilmiş en yüksek tek maaşı belirle. Departman adını, çalışanın adını,
    // soyadını ve en yüksek maaş tutarını listele. Sonuçları en yüksek maaşa göre azalan şekilde sırala.

    @Test
    public void TC16() throws SQLException {
        String query =
                "SELECT d.dept_name AS department, e.first_name, e.last_name, s.salary AS max_salary " +
                        "FROM employees e " +
                        "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                        "JOIN departments d ON de.dept_no = d.dept_no " +
                        "JOIN salaries s ON e.emp_no = s.emp_no " +
                        "WHERE (d.dept_no, s.salary) IN ( " +
                        "    SELECT de.dept_no, MAX(s.salary) " +
                        "    FROM dept_emp de " +
                        "    JOIN salaries s ON de.emp_no = s.emp_no " +
                        "    GROUP BY de.dept_no " +
                        ") " +
                        "ORDER BY max_salary DESC;";

        try {
            System.out.println("Her departmandaki en yüksek maaşı alan çalışanlar:");
            executeEmployeeQuery(query);
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    // 17. Identify the employees in each department who have the highest average salary. List the
    // department name, employee's first name, last name, and the average salary. Order the results by
    // average salary in descending order, showing only those with the highest average salary within their
    // department.
    // techno.study @techno.study @Techno_Study
    // | T A S K s | One week |
    // - Her departmandaki en yüksek ortalama maaşa sahip çalışanları belirle. Departman adını,
    // çalışanın adını, soyadını ve ortalama maaşı listele. Sonuçları departmanlarına göre azalan şekilde
    // sırala, sadece kendi departmanlarında en yüksek ortalama maaşa sahip olanları göster.

    @Test
    public void TC17() throws SQLException {

        String sorgu = """
                select 
                    sonuc.departman_no, 
                    departments.dept_name as departman_adi, 
                    sonuc.calisan_no, 
                    employees.first_name as ad, 
                    employees.last_name as soyad, 
                    sonuc.ortalama_maas 
                from ( 
                    select 
                        dept_emp.dept_no as departman_no, 
                        salaries.emp_no as calisan_no, 
                        avg(salaries.salary) as ortalama_maas 
                    from dept_emp 
                    join salaries on dept_emp.emp_no = salaries.emp_no 
                    group by dept_emp.dept_no, salaries.emp_no 
                ) as sonuc 
                join ( 
                    select 
                        departman_no, 
                        max(ortalama_maas) as en_yuksek_ortalama 
                    from ( 
                        select 
                            dept_emp.dept_no as departman_no, 
                            salaries.emp_no as calisan_no, 
                            avg(salaries.salary) as ortalama_maas 
                        from dept_emp 
                        join salaries on dept_emp.emp_no = salaries.emp_no 
                        group by dept_emp.dept_no, salaries.emp_no 
                    ) as ortalama_tablosu 
                    group by departman_no 
                ) as en_yuksek_tablosu 
                on sonuc.departman_no = en_yuksek_tablosu.departman_no 
                   and sonuc.ortalama_maas = en_yuksek_tablosu.en_yuksek_ortalama 
                join employees on sonuc.calisan_no = employees.emp_no 
                join departments on sonuc.departman_no = departments.dept_no 
                order by departman_adi desc;
                """;

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sorgu);

        System.out.printf("%-20s %-20s %-20s %-16s%n", "Department", "First Name", "Last Name", "Average Salary");
        System.out.println("------------------------------------------------------------------------");

        while (rs.next()) {
            String department = rs.getString("departman_adi");
            String firstName = rs.getString("ad");
            String lastName = rs.getString("soyad");
            double avgSalary = rs.getDouble("ortalama_maas");
            System.out.printf("%-20s %-20s %-20s %-16.2f%n", department, firstName, lastName, avgSalary);
        }
    }

    // 18. List the names, last names, and hire dates in alphabetical order of all employees hired before
    // January 01, 1990.
    // - 1990-01-01 tarihinden önce işe alınan tüm çalışanların adlarını, soyadlarını ve işe alınma
    // tarihlerini alfabetik sırayla listele.
    @Test(groups = {"EmployeeQueries"})
    public void listEmployeesHiredBefore1990() {
        String query =
                "SELECT first_name, last_name, hire_date " +
                        "FROM employees " +
                        "WHERE hire_date < '1990-01-01' " +
                        "ORDER BY hire_date ASC " +
                        "LIMIT 100;";

        try {
            System.out.println("Employees hired before 1990:");
            executeEmployeeQuery(query);
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    // 19. List the names, last names, hire dates of all employees hired between January 01, 1985 and
    // December 31, 1989, sorted by hire date.
    // - 1985-01-01 ile 1989-12-31 tarihleri arasında işe alınan tüm çalışanların adlarını, soyadlarını ve işe
    // alınma tarihlerini işe alınma tarihine göre sıralı olarak listele.
    @Test
    public void Query19() {
        String query =
                "select first_name, last_name, hire_date " +
                        "from employees " +
                        "where hire_date between '1985-01-01' and '1989-12-31' " +
                        "order by hire_date asc;";

        try {
            System.out.println("Employees hired between 1985 and 1989:");
            executeEmployeeQuery(query);
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    // 20. List the names, last names, hire dates, and salaries of all employees in the Sales department who
    // were hired between January 01, 1985 and December 31, 1989, sorted by salary in descending order.
    // - 1985-01-01 ile 1989-12-31 tarihleri arasında işe alınan Satış departmanındaki tüm çalışanların
    // adlarını, soyadlarını, işe alınma tarihlerini ve maaşlarını, maaşa göre azalan şekilde sıralı olarak
    // listele.
    @Test
    public void Query20() {
        String query20 = " select e.emp_no,e.first_name, e.last_name, e. hire_date, s.salary, d.dept_name\n" +
                " from employees e inner join salaries s on e.emp_no=s.emp_no\n" +
                " join dept_emp de on s.emp_no=de.emp_no inner join departments d on de.dept_no=d.dept_no where d.dept_name='Sales'and\n" +
                " e.hire_date  BETWEEN '1985-01-01' AND '1989-12-31' ORDER BY s.salary desc;";
        try {
            System.out.println("List of those were hired between January 01, 1985 and December 31, 1989:");
            executeEmployeeQuery(query20);
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }


    // 21.
    // -- a: Find the count of male employees (179973)
    // -- Erkek çalışanların sayısını bulun (179973)
    // -- b: Determine the count of female employees (120050)
    // -- Kadın çalışanların sayısını belirleyin (120050)
    // -- c: Find the number of male and female employees by grouping:
    // -- Gruplandırarak erkek ve kadın çalışanların sayısını bulun:
    // -- d: Calculate the total number of employees in the company (300023)
    // -- Şirketteki toplam çalışan sayısını hesaplayın (300023)
    // 22.
    // -- a: Find out how many employees have unique first names (1275)
    // -- Kaç çalışanın benzersiz ilk adı olduğunu bulun (1275)
    // -- b: Identify the number of distinct department names (9)
    // -- Farklı bölüm adlarının sayısını belirleyin (9)
    // 23. List the number of employees in each department
    // -- Her bölümdeki çalışan sayısını listele
    // 24. List all employees hired within the last 5 years from February 20, 1990
    // -- 1990-02-20 tarihinden sonraki son 5 yıl içinde işe alınan tüm çalışanları listele
    // 25. List the information (employee number, date of birth, first name, last name, gender, hire date) of
    // the employee named "Annemarie Redmiles".
    // -- "Annemarie Redmiles" adlı çalışanın bilgilerini (çalışan numarası, doğum tarihi, ilk adı, soyadı,
    // cinsiyet, işe alınma tarihi) listele.
    // 26. List all information (employee number, date of birth, first name, last name, gender, hire date,
    // salary, department, and title) for the employee named "Annemarie Redmiles".
    // -- "Annemarie Redmiles" adlı çalışanın tüm bilgilerini (çalışan numarası, doğum tarihi, ilk adı,
    // soyadı, cinsiyet, işe alınma tarihi, maaş, departman ve unvan) listele.
    // 27. List all employees and managers in the D005 department
    // -- D005 bölümündeki tüm çalışanları ve yöneticileri listele
    // 28. List all employees hired after '1994-02-24' and earning more than 50,000
    // -- '1994-02-24' tarihinden sonra işe alınan ve 50.000'den fazla kazanan tüm çalışanları listele
    // techno.study @techno.study @Techno_Study
    // | T A S K s | One week |
    // 29. List all employees working in the "Sales" department with the title "Manager"
    // -- "Satış" bölümünde "Yönetici" unvanıyla çalışan tüm çalışanları listele
    // 30. Find the department where employee with '10102' has worked the longest
    // -- '10102' numaralı çalışanın en uzun süre çalıştığı departmanı bul
    // 31. Find the highest paid employee in department D004
    // -- D004 bölümünde en yüksek maaş alan çalışanı bulun
    // 32. Find the entire position history for employee with emp. no '10102'
    // -- '10102' numaralı çalışanın tüm pozisyon geçmişini bulun
    // 33. Finding the average "employee age"
    // -- Ortalama "çalışan yaşını" bulma
    // 34. Finding the number of employees per department
    // -- Bölüm başına çalışan sayısını bulma
    // 35. Finding the managerial history of employee with ID (emp. no) 110022
    // -- 110022 numaralı çalışanın yönetim geçmişini bulma
    // 36. Find the duration of employment for each employee
    // -- Her çalışanın istihdam süresini bulma
    // 37. Find the latest title information for each employee
    // -- Her çalışanın en son unvan bilgisini bulma
    // 38. Find the first and last names of managers in department 'D005'
    // -- 'D005' bölümünde yöneticilerin adını ve soyadını bulma
    // 39. Sort employees by their birth dates
    // -- Çalışanları doğum tarihlerine göre sıralama
    // 40. List employees hired in April 1992
    // -- Nisan 1992'de işe alınan çalışanları listeleme
    // 41. Find all departments that employee '10102' has worked in.
    // -- '10102' numaralı çalışanın çalıştığı tüm bölümleri bulma.

}
