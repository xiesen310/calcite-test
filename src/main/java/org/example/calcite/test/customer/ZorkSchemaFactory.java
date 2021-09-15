package org.example.calcite.test.customer;

import com.google.common.io.Resources;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaFactory;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.util.Source;
import org.apache.calcite.util.Sources;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义 CsvSchemaFactory
 */
public class ZorkSchemaFactory implements SchemaFactory {

    /**
     * parentSchema 他的父节点，一般为root
     * name     数据库的名字，它在model中定义的
     * operand  也是在mode中定义的，是Map类型，用于传入自定义参数。
     */
    @Override
    public Schema create(SchemaPlus parentSchema, String name, Map<String, Object> operand) {

        try {
            List<ZorkTable> tableList = new ArrayList<>();

            ArrayList tables = (ArrayList) operand.get("tables");
            for (Object table : tables) {
                String ddl = (String) ((HashMap) table).get("ddl");
                String dataFile = (String) ((HashMap) table).get("dataFile");
                URL url = Resources.getResource(dataFile);
                Source source = Sources.of(url);
                ZorkTable csvTable = new ZorkTable(source,ddl);
                tableList.add(csvTable);
            }
            return new ZorkSchema(name, tableList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
