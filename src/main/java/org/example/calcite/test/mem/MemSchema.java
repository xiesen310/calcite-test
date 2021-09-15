package org.example.calcite.test.mem;

public class MemSchema {
    public static class Employee {
        public final int empid;
        public final String name;
        public final int deptno;

        public Employee(int empid, String name, int deptno) {
            this.empid = empid;
            this.name = name;
            this.deptno = deptno;
        }
    }

    public static class Department {
        public final String name;
        public final int deptno;

        public Department(int deptno, String name) {
            this.name = name;
            this.deptno = deptno;
        }
    }

    /**
     * 初始化emps表
     */
    public final Employee[] emps = {
            new Employee(100, "John Sue", 1),
            new Employee(200, "Machine Den", 2),
            new Employee(150, "Jack Ma", 1),
            new Employee(250, "Silver Pony", 3)};
    /**
     * 初始化depts表
     */
    public final Department[] depts = {
            new Department(1, "dev"),
            new Department(2, "market"),
            new Department(3, "management")};

}
