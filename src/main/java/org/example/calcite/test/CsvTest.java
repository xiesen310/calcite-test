package org.example.calcite.test;

import org.apache.calcite.adapter.csv.CsvSchema;
import org.apache.calcite.adapter.csv.CsvTable;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;

import java.io.File;
import java.sql.*;
import java.util.Properties;

public class CsvTest {

    public static void main(String[] args) throws SQLException {
        // 构造Schema对象
        File file = new File("D:\\tmp\\calcite-test\\src\\main\\resources\\csv_dir");        // csv文件存放目录
        CsvSchema csvSchema = new CsvSchema(file, CsvTable.Flavor.SCANNABLE);    // 构建SCANNABLE类型的Schema
        // 设置连接参数
        Properties info = new Properties();
        // 设置大小写不敏感
        info.setProperty("caseSensitive", "false");
        // 建立连接
        Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
        // 取得 Calcite连接
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
        // 取得 RootScheam RootSchema是所有Schema的根
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        // 添加schema
        rootSchema.add("csv", csvSchema);
        // 编写SQL
        String sql = "select * from CSV.DEPTS";
        // 执行查询
        Statement statement = calciteConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        // 打印结果
        CalciteUtils.printResultSet(resultSet);

        resultSet.close();
        statement.close();
        connection.close();
    }
}
