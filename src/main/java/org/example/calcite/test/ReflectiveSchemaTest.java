package org.example.calcite.test;

import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class ReflectiveSchemaTest {
    // table
    public static class People {
        // field
        public String id;
        public String name;

        public People(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    // table
    public static class Detail {
        // field
        public String id;
        public int age;

        public Detail(String id, int age) {
            this.id = id;
            this.age = age;
        }
    }

    // schema
    public static class JavaSchema {
        public final People[] people = new People[]{
                new People("1", "namea"),
                new People("2", "nameb"),
                new People("3", "namec")
        };

        public final Detail[] detail = new Detail[]{
                new Detail("1", 1),
                new Detail("2", 22),
                new Detail("3", 333)
        };
    }

    public static void main(String[] args) throws Exception {
        Class.forName("org.apache.calcite.jdbc.Driver");
        Properties info = new Properties();
        info.setProperty("lex", "JAVA");
        // 创建连接
        Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
        // SchemaPlus相当databases
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        // 创建schema, ReflectiveSchema内部通过放射会根据提供的schema创建table,field
        Schema schema = new ReflectiveSchema(new JavaSchema());
        // databases添加schema
        rootSchema.add("sc", schema);
        // 创建 Statement
        Statement statement = calciteConnection.createStatement();
        // 执行sql
        ResultSet resultSet = statement.executeQuery("" +
                "select p.id, p.name, d.age " +
                "from sc.people p left join sc.detail d  on p.id = d.id " +
                "where p.id >= 2" +
                "");

        CalciteUtils.printResultSet(resultSet);
        resultSet.close();
        statement.close();
        connection.close();
    }

}
