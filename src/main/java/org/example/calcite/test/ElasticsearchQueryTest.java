package org.example.calcite.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.calcite.adapter.elasticsearch.ElasticsearchSchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.sql.*;
import java.util.Properties;

public class ElasticsearchQueryTest {

    public static void main(String[] args) throws Exception {
        // 构造Schema
        RestClient restClient = RestClient
                .builder(new HttpHost("192.168.70.25", 9200))
                .build();

        ElasticsearchSchema esSchema = new ElasticsearchSchema(restClient, new ObjectMapper(), "teachers");
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
        rootSchema.add("es", esSchema);
        // 编写SQL
        String sql = "select * from ES.TEACHERS";
        // 执行查询
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        // 打印结果
        CalciteUtils.printResultSet(resultSet);

        resultSet.close();
        statement.close();
        connection.close();
    }
}
