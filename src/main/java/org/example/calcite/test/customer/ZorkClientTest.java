package org.example.calcite.test.customer;

import org.example.calcite.test.CalciteUtils;
import org.example.calcite.test.ResUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * CustomerClientTest
 */
public class ZorkClientTest {
    /**
     * 测试的时候用字符串 defaultSchema 默认数据库 name 数据库名称 type custom factory
     * 请求接收类，该类会实例化Schema也就是数据库类，Schema会实例化Table实现类，Table会实例化数据类。
     * operand 动态参数，ScheamFactory的create方法会接收到这里的数据
     */
    public static void main(String[] args) {
        try {
            String model = ResUtil.getResourceAsString("model.json");
            Connection connection = DriverManager.getConnection("jdbc:calcite:model=inline:" + model);
            Statement statement = connection.createStatement();
            query(statement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * CSV文件读取
     *
     * @param statement
     * @throws Exception
     */
    public static void query(Statement statement) throws Exception {
        List<String> sqlList = new ArrayList<>();
        sqlList.add("select * from TEST.USERS");
        sqlList.add("select * from TEST.DEPT");
        sqlList.add("select * from TEST.DEPT GROUP BY ID,NAME");
        sqlList.add("select u.ID , u.NAME,d.ID,d.NAME from USERS u left join DEPT d on u.DEPT_ID = d.ID");

        sqlList.add("select * from XS.DEPT");
        sqlList.add("select * from XS.USERS");
        for (String sql : sqlList) {
            System.out.println("----------------------------------");
            System.out.println(sql);
            CalciteUtils.printResultSet(statement.executeQuery(sql));
        }
    }
}
