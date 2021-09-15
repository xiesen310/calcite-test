package org.example.calcite.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DefaultCsvTest {

    private static Connection conn;

    public static void setup() throws SQLException {
        Properties config = new Properties();
        config.put("model", DefaultCsvTest.class.getClassLoader().getResource("csv_default.json").getPath());
        config.put("caseSensitive", "false");
        conn = DriverManager.getConnection("jdbc:calcite:", config);
    }


    public static void query() throws Exception {
        List<String> sqlList = new ArrayList<>();
        sqlList.add("select * from csv.csv_user");
        sqlList.add("select id, name || '_after_append' from csv_user");
        sqlList.add("select t.id,t.name,t2.age from csv_user t left join csv_detail t2 on t.id = t2.id");
        for (String sql : sqlList) {
            System.out.println("-----------------");
            System.out.println(sql);
            CalciteUtils.printResultSet(conn.createStatement().executeQuery(sql));
        }
    }


    public static void main(String[] args) throws Exception {
        setup();
        query();
    }


}
