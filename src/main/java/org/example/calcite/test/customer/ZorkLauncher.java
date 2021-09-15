package org.example.calcite.test.customer;

import com.alibaba.fastjson.JSONObject;
import org.example.calcite.test.CalciteUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ZorkLauncher {
    public static void main(String[] args) {
        Scanner scanner = null;
        while (true) {
            try {
                String execSql = null;
                scanner = new Scanner(System.in);
                System.err.println(">> 请输入 JSON 配置描述信息: ");
                String model = scanner.nextLine();
                System.out.println(model);

                if (!confValidate(model)) {
                    continue;
                }

                System.err.println(">> 请输入业务 SQL 语句: ");
                execSql = scanner.nextLine();
                System.out.println(execSql);

                if (!sqlValidate(execSql)) {
                    System.err.println("SQL 语句格式不正确,任务启动失败,请重新构建.");
                    continue;
                }

                exec(model, execSql);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * sql 语句校验
     *
     * @param sql SQL
     * @return
     */
    private static boolean sqlValidate(String sql) {
        boolean flag = sql.toUpperCase().startsWith("SELECT");
        if (!flag) {
            System.err.println("JSON 配置文件或业务 SQL 语句错误.");
        }
        return flag;
    }

    /**
     * 校验配置文件
     *
     * @param model JSON 配置描述信息
     * @return
     */
    private static boolean confValidate(String model) {
        boolean flag = false;
        try {
            if (null != model && model.trim().length() > 0) {
                JSONObject jsonObject = JSONObject.parseObject(model);
            }
            flag = true;
        } catch (Exception e) {
            System.err.println("解析 JSON 配置描述信息失败,请重新输入正确的配置信息.");
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 指定业务逻辑
     *
     * @param model   json 格式配置描述信息
     * @param execSql 需要执行的 SQL
     */
    private static void exec(String model, String execSql) {
        Connection connection = null;
        Statement statement = null;
        try {
//            String model = ResUtil.getResourceAsString("model.json");
            connection = DriverManager.getConnection("jdbc:calcite:model=inline:" + model);
            statement = connection.createStatement();
            query(statement, execSql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 执行查询操作
     *
     * @param statement
     * @param execSql
     * @throws Exception
     */
    private static void query(Statement statement, String execSql) throws Exception {
        System.out.println("----------------------------------");
//        execSql = "SELECT * FROM TEST.USERS";
        System.out.println(execSql);
        CalciteUtils.printResultSet(statement.executeQuery(execSql));
    }
}
