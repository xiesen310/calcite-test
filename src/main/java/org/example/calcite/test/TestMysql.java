package org.example.calcite.test;

import org.apache.calcite.adapter.jdbc.JdbcSchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class TestMysql {
    public static void main(String[] args) throws Exception {
        //初始化calcite connection
        Class.forName("org.apache.calcite.jdbc.Driver");
        Properties info = new Properties();
        info.setProperty("lex", "JAVA");
        Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);

        //添加mysql数据库作为数据源
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        Class.forName("com.mysql.jdbc.Driver");
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://192.168.1.222:3306/eladmin?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("Mysql@123");

        Schema schema = JdbcSchema.create(rootSchema, "eladmin", dataSource, null, "eladmin");
        rootSchema.add("eladmin", schema);
        //执行查询
        Statement statement = calciteConnection.createStatement();

        ResultSet resultSet = statement.executeQuery(
                "select * from eladmin.dept");
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            System.out.println(name);
        }
        System.out.println(resultSet);
        resultSet.close();
        statement.close();
        connection.close();
    }
}
