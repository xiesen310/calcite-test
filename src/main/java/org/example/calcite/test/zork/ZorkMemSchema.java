package org.example.calcite.test.zork;

import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;

import java.util.List;
import java.util.Map;

@Data
public class ZorkMemSchema extends AbstractSchema {
    private Map<String, Table> tableMap;
    /**
     * schema 名称
     */
    private String name;

    /**
     * table信息
     */
    private List<ZorkMemTable> tableList;

    public ZorkMemSchema(String name, List<ZorkMemTable> tableList) {
        this.name = name;
        this.tableList = tableList;
    }


    @Override
    protected Map<String, Table> getTableMap() {
        Map<String, Table> tableMap = Maps.newHashMap();
        for (ZorkMemTable table : this.tableList) {
            tableMap.put(table.getName(), table);
        }
        return tableMap;
    }
}
