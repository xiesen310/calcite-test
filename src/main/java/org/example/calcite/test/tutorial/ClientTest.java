package org.example.calcite.test.tutorial;


import com.alibaba.fastjson.JSON;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import org.example.calcite.test.CalciteUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class ClientTest {
/*    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
            .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
            .configure(JsonParser.Feature.ALLOW_COMMENTS, true);*/


   /* public static void main(String[] args) throws SQLException, IOException {
        String uri = "D:\\tmp\\calcite-test\\src\\main\\resources\\tutorial\\model.json";
        JsonRoot root = JSON_MAPPER.readValue(new File(uri), JsonRoot.class);
        System.out.println(root);



    }*/


    public void createTableFactoryConnection() throws SQLException {
        Properties info = new Properties();
        String model = "D:\\tmp\\calcite-test\\src\\main\\resources\\tutorial\\model.json";
        info.put("model", model);
        info.setProperty("caseSensitive", "false");
        Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
        String schema = calciteConnection.getSchema();
        System.out.println(schema);
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        TutorialSchemaFactory csvSchema = new TutorialSchemaFactory();

        Map<String, Object> operand = new HashMap<>();
        ArrayList arrayList = new ArrayList();
        Map<String, String> map1 = new HashMap<>();
        map1.put("ddl", "D:\\tmp\\calcite-test\\src\\main\\resources\\tutorial\\user\\user.json");
        map1.put("data", "D:\\tmp\\calcite-test\\src\\main\\resources\\tutorial\\user\\user.txt");
        arrayList.add(map1);
        Map<String, String> map2 = new HashMap<>();
        map2.put("ddl", "D:\\tmp\\calcite-test\\src\\main\\resources\\tutorial\\role\\role.json");
        map2.put("data", "D:\\tmp\\calcite-test\\src\\main\\resources\\tutorial\\role\\role.txt");
        arrayList.add(map2);
        operand.put("tables", arrayList);
        operand.put("modelUri", "D:\\tmp\\calcite-test\\src\\main\\resources\\tutorial\\model.json");
        operand.put("baseDirectory", "D:\\tmp\\calcite-test\\src\\main\\resources\\tutorial");
        Schema tutorial = csvSchema.create(rootSchema, "tutorial", operand);
        rootSchema.add("tutorial", tutorial);

//        String sql = "select name from tutorial.user_info";
//         执行查询
//        Statement statement = calciteConnection.createStatement();

//        ResultSet resultSet = statement.executeQuery(sql);
        // 打印结果
//        CalciteUtils.printResultSet(resultSet);

//        resultSet.close();
//        statement.close();
        connection.close();
    }

    public static void main(String[] args) throws SQLException {
        new ClientTest().createTableFactoryConnection();

    }
}
