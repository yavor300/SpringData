import com.thoughtworks.qdox.model.expression.Add;
import entities.*;
import org.hibernate.mapping.ToOne;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Engine implements Runnable {
    private final EntityManager entityManager;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public void run() {
        //TODO Check Second Exercise By Removing The Comment Below
        //changeCasingExerciseTwo();

        //TODO Check Third Exercise By Removing The Comment Below
        //Scanner scanner = new Scanner(System.in);
        //String input = scanner.nextLine();
        //containsEmployeeExerciseThree(input);

        //TODO Check Fourth Exercise By Removing The Comment Below
        //employeesWithSalaryOverExerciseFour();

        //TODO Check Fifth Exercise By Removing The Comment Below
        //employeesFromDepartmentExerciseFive();

        //TODO Check Sixth Exercise By Removing The Comment Below
        //Scanner scanner = new Scanner(System.in);
        //String input = scanner.nextLine();
        //addNewAddressAndUpdateEmployeeExerciseSix(input);

        //TODO Check Seventh Exercise By Removing The Comment Below
        //addressesWithEmployeeCount();

        //TODO Check Eighth Exercise By Removing The Comment Below
        //Scanner scanner = new Scanner(System.in);
        //int id = Integer.parseInt(scanner.nextLine());
        //getEmployeeWithProject(id);

        //TODO Check Ninth Exercise By Removing The Comment Below
        //findLatestTenProjects();

        //TODO Check Tenth Exercise By Removing The Comment Below
        //increaseSalaries();

        //TODO Check Eleventh Exercise By Removing The Comment Below
        //Scanner scanner = new Scanner(System.in);
        //String pattern = scanner.nextLine();
        //findEmployeesByFirstName(pattern);

        //TODO Check Twelfth Exercise By Removing The Comment Below
        //employeesMaximumSalaries();

        //TODO Check Thirteenth Exercise By Removing The Comment Below
        //Scanner scanner = new Scanner(System.in);
        //String townName = scanner.nextLine();
        //removeTowns(townName);
    }

    private void changeCasingExerciseTwo() {
        List<Town> townsToDetach = this.entityManager.createQuery("SELECT t FROM Town t WHERE length(t.name) >= 5", Town.class)
                .getResultList();

        this.entityManager.getTransaction().begin();
        townsToDetach.forEach(this.entityManager::detach);
        townsToDetach.forEach(t -> t.setName(t.getName().toLowerCase()));
        townsToDetach.forEach(this.entityManager::merge);
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();
    }

    private void containsEmployeeExerciseThree(String input) {
        String[] employeeData = input.split("\\s+");

        try {
            Employee employee = this.entityManager.createQuery("SELECT e FROM Employee e WHERE e.firstName = :firstName and " +
                    "e.lastName = :lastName", Employee.class)
                    .setParameter("firstName", employeeData[0])
                    .setParameter("lastName", employeeData[1])
                    .getSingleResult();
            System.out.println("Yes");
        } catch (NoResultException exception) {
            System.out.println("No");
        }
    }

    private void employeesWithSalaryOverExerciseFour() {
        List<Employee> employees = this.entityManager
                .createQuery("SELECT e FROM Employee e WHERE e.salary > 50000 ", Employee.class)
                .getResultList();

        employees.forEach(e -> System.out.println(e.getFirstName()));

    }

    private void employeesFromDepartmentExerciseFive() {
            this.entityManager
                .createQuery("select e  from Employee e JOIN Department d on e.department.id = d.id " +
                        "where d.name = 'Research and Development' order by e.salary, e.id", Employee.class)
                .getResultList()
                    .forEach(e -> System.out.printf("%s %s from %s - $%.2f%n",
                            e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary()));
    }

    private void addNewAddressAndUpdateEmployeeExerciseSix(String lastName) {
        this.entityManager.getTransaction().begin();
        Address address = new Address();
        address.setText("Vitoshka 15");
        this.entityManager.persist(address);
        this.entityManager.getTransaction().commit();

        Employee employee = this.entityManager.createQuery("select e from Employee e where e.lastName = :lastName", Employee.class)
                .setParameter("lastName", lastName)
                .getSingleResult();

        this.entityManager.getTransaction().begin();
        this.entityManager.detach(employee);
        employee.setAddress(address);
        this.entityManager.merge(employee);
        this.entityManager.getTransaction().commit();
    }

    private void addressesWithEmployeeCount() {
        List<Address> addresses = this.entityManager
                .createQuery("select a from Address a join Employee e on e.address.id = a.id group by a.text order by count(e.id) desc", Address.class)
                .setMaxResults(10)
                .getResultList();

        for (Address a : addresses) {
            String townName;



            if (a.getTown() == null) {
                townName = "";
            } else {
                townName = a.getTown().getName();
            }


            System.out.printf("%s, %s - %d employees%n",
                    a.getText(), townName, a.getEmployees().size());
        }
    }

    private void getEmployeeWithProject(int id) {
        Employee employee = this.entityManager.createQuery("select e from Employee e where e.id = :id", Employee.class)
                .setParameter("id", id)
                .getSingleResult();

        System.out.printf("%s %s - %s%n", employee.getFirstName(), employee.getLastName(), employee.getJobTitle());

        List<Project> projects = employee.getProjects().stream().sorted(Comparator.comparing(Project::getName)).collect(Collectors.toList());
        for (Project project : projects) {
            System.out.println(project.getName());;
        }
    }

    private void findLatestTenProjects() {
        List<Project> projects = this.entityManager
                .createQuery("select p from Project p order by p.name, p.startDate desc ", Project.class)
                .setMaxResults(10)
                .getResultList();

        List<Project> sortedProjects = projects.stream().sorted(Comparator.comparing(Project::getName)).collect(Collectors.toList());
        for (Project project : sortedProjects) {
            System.out.printf("Project name: %s%n   Project Description: %s%n   Project Start Date: %s%n   Project End Date: %s%n",
                    project.getName(), project.getDescription(),project.getStartDate(), project.getEndDate());
        }
    }

    private void increaseSalaries() {
        List<Employee> employeesToUpdate = this.entityManager
                .createQuery("select e from Employee e where " +
                        "e.department.name = 'Engineering' or " +
                        "e.department.name = 'Tool Desing' or " +
                        "e.department.name = 'Marketing' or " +
                        "e.department.name = 'Information Services'", Employee.class)
                .getResultList();

        for (Employee employee : employeesToUpdate) {
            this.entityManager.getTransaction().begin();
            employee.setSalary(employee.getSalary().multiply(new BigDecimal("1.12")));
            this.entityManager.getTransaction().commit();
        }

        employeesToUpdate.forEach(e -> System.out.printf("%s %s ($%.2f)%n", e.getFirstName(), e.getLastName(), e.getSalary()));
    }

    private void findEmployeesByFirstName(String pattern) {
        List<Employee> employees = this.entityManager
                .createQuery("SELECT e FROM Employee e where e.firstName like :pattern", Employee.class)
                .setParameter("pattern", pattern + "%")
                .getResultList();

        employees.forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n", e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary()));
    }

    private void employeesMaximumSalaries() {
        List<Department> departments = this.entityManager
                .createQuery("SELECT d from Department d join Employee e on d.id = e.department.id group by d.name having max(e.salary) < 30000 or max(e.salary) > 70000", Department.class)
                .getResultList();

        for (Department department : departments) {
            double max = Double.MIN_VALUE;
            for (Employee employee : department.getEmployees()) {
                if (max < employee.getSalary().doubleValue()) {
                    max = employee.getSalary().doubleValue();
                }
            }
            System.out.printf("%s %.2f%n", department.getName(), max);
        }
    }

    private void removeTowns(String town) {
        List<Address> addresses = this.entityManager
                .createQuery("select a from Address a join Town t on t.id = a.town.id where t.name = :townName", Address.class)
                .setParameter("townName", town)
                .getResultList();

        for (Address address : addresses) {
            this.entityManager.getTransaction().begin();
            address.getEmployees().forEach(e -> e.setAddress(null));
            this.entityManager.getTransaction().commit();

            this.entityManager.getTransaction().begin();
            this.entityManager.remove(address);
            this.entityManager.getTransaction().commit();
        }

        Town townToRemove = this.entityManager
                .createQuery("select t from Town t where t.name = :townName", Town.class)
                .setParameter("townName", town)
                .getSingleResult();

        this.entityManager.getTransaction().begin();
        this.entityManager.remove(townToRemove);
        this.entityManager.getTransaction().commit();


        System.out.printf("%d address in %s deleted", addresses.size(), town);
    }
}
