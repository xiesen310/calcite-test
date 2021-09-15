package org.example.calcite.test;

import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class ReflectiveSchemaTestMap {
    // table
    public static class People {
        // field
        public String id;
        public String name;
        public Map<String, String> dept;

        public People(String id, String name, Map<String, String> dept) {
            this.id = id;
            this.name = name;
            this.dept = dept;
        }
    }

    // schema
    public static class JavaSchema {
        public final People[] people = genData();

        public final String[] deptSet = new String[]{
                "研发部",
                "测试部",
                "设计部"
        };


        /**
         * 生产模拟数据
         *
         * @return {@link People[]}
         */
        private People[] genData() {
            People[] results = new People[10];
            for (int i = 0; i < 10; i++) {
                Map<String, String> dept = new HashMap<>();
                dept.put("name", deptSet[new Random().nextInt(deptSet.length - 1)]);
                dept.put("no", String.valueOf(new Random().nextInt(10)));
                People p = new People("" + i, "name" + i, dept);
                results[i] = p;
            }
            return results;
        }
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
        ResultSet resultSet = statement.executeQuery("select id,name,dept['name'] from sc.people");

        CalciteUtils.printResultSet(resultSet);
        resultSet.close();
        statement.close();
        connection.close();
    }

}
