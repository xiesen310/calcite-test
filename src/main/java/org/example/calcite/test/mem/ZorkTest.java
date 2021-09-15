package org.example.calcite.test.mem;

import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import org.example.calcite.test.CalciteUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ZorkTest {

    public static void main(String[] args) throws SQLException {
        String ddl = "CREATE TABLE users(`id` String,`name` String)";
        // 构造Schema
        Schema hrs = new ZorkSchema(ddl);
        // 设置连接参数
        Properties info = new Properties();
        // SQL大小写不敏感
        info.setProperty("caseSensitive", "false");
        // 建立连接
        Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
        // 取得Calcite连接
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
        // 取得RootScheam RootSchema是所有Schema的父Schema
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        // 添加schema
        rootSchema.add("hr", hrs);

        query(connection);
        connection.close();
    }

    /**
     * 查询表
     *
     * @param connection
     * @throws SQLException
     */
    private static void query(Connection connection) throws SQLException {
        List<String> sqlList = new ArrayList<>();
        sqlList.add("select * from HR.metadata");
//        sqlList.add("select * from HR.EMPS");
//        sqlList.add("select e.empid as empid, e.name as employeeName, e.deptno as deptno, d.name as departmentName from HR.EMPS e join HR.DEPTS d on e.deptno = d.deptno");
        for (String sql : sqlList) {
            System.out.println("-----------------");
            System.out.println(sql);
            CalciteUtils.printResultSet(connection.createStatement().executeQuery(sql));
        }
    }
}
