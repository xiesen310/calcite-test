package org.example.calcite.test.customer;

import org.example.calcite.test.CalciteUtils;
import org.example.calcite.test.ResUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * CustomerClientTest
 */
public class CustomerClientTest {
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
        ResultSet resultSet = statement.executeQuery("select * from TEST_CSV.USERS");
        CalciteUtils.printResultSet(resultSet);
    }
}
