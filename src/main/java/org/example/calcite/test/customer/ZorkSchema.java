package org.example.calcite.test.customer;

import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;

import java.util.List;
import java.util.Map;

@Data
public class ZorkSchema extends AbstractSchema {
    private Map<String, Table> tableMap;
    /**
     * schema 名称
     */
    private String name;

    /**
     * table信息
     */
    private List<ZorkTable> tableList;

    public ZorkSchema(String name, List<ZorkTable> tableList) {
        this.name = name;
        this.tableList = tableList;
    }


    @Override
    protected Map<String, Table> getTableMap() {
        Map<String, Table> tableMap = Maps.newHashMap();
        for (ZorkTable table : this.tableList) {
            String ddl = table.getDdl();
            String tableName = ddl.toUpperCase().split("CREATE TABLE")[1].split("\\(")[0].trim();
            tableMap.put(tableName, table);
        }
        return tableMap;
    }
}
