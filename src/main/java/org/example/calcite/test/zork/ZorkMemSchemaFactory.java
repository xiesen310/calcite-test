package org.example.calcite.test.zork;

import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaFactory;
import org.apache.calcite.schema.SchemaPlus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZorkMemSchemaFactory implements SchemaFactory {

    /**
     * parentSchema 他的父节点，一般为root
     * name     数据库的名字，它在model中定义的
     * operand  也是在mode中定义的，是Map类型，用于传入自定义参数。
     */
    @Override
    public Schema create(SchemaPlus parentSchema, String name, Map<String, Object> operand) {

        try {
            List<ZorkMemTable> tableList = new ArrayList<>();

            ArrayList tables = (ArrayList) operand.get("tables");
            for (Object table : tables) {
                String ddl = (String) ((HashMap) table).get("ddl");
                String tableName = ddl.toUpperCase().split("CREATE TABLE")[1].split("\\(")[0].trim();
                ZorkMemTable zorkMemTable = new ZorkMemTable(tableName, ddl);
                tableList.add(zorkMemTable);
            }
            return new ZorkMemSchema(name, tableList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
