package chapter_4;

import java.time.LocalDate;

public class EmployeeTest {
  public static void main(String[] args) {
    Employee[] staff = new Employee[3];
    staff[0] = new Employee("Carl Cracker", 75000, 1987, 12, 15);
    staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
    staff[2] = new Employee("Tony Tester", 40000, 1990, 3, 15);

    for (int i = 0; i < staff.length; i++) {
      staff[i].raiseSalary(5);
    }
    for (int i = 0; i < staff.length; i++) {
      System.out.println("name=" + staff[i].getName() + ",salary=" + staff[i].getSalary() + ",hireDay=" + staff[i].getHireDay());
    }
  }
}

class Employee {
  private String name;
  private double salary;
  private LocalDate hireDay;

  public Employee() {
  }

  public Employee(String name, double salary, int year, int month, int day) {
    this.name = name;
    this.salary = salary;
    this.hireDay = LocalDate.of(year, month, day);
  }

  public String getName() {
    return name;
  }

  public double getSalary() {
    return salary;
  }

  public LocalDate getHireDay() {
    return hireDay;
  }

  public void raiseSalary(double byPercent) {
    double raise = salary * byPercent / 100;
    salary += raise;
  }
}
