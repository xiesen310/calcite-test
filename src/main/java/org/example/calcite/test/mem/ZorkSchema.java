package org.example.calcite.test.mem;

import com.google.common.collect.ImmutableMap;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;

import java.util.Map;

public class ZorkSchema extends AbstractSchema {
    private String ddl;
    private Map<String, Table> tableMap;

    public ZorkSchema(String ddl) {
        super();
        this.ddl = ddl;
    }

    @Override
    protected Map<String, Table> getTableMap() {

        if (tableMap == null) {
            tableMap = createTableMap();
        }
        return tableMap;
    }


    private Map<String, Table> createTableMap() {
        final ImmutableMap.Builder<String, Table> builder = ImmutableMap.builder();
        if (ddl != null) {
            /**
             * 获取表名,构造 table
             */
            String[] tables = ddl.split("CREATE TABLE");
            String tableName = tables[1].split("\\(")[0];

            final Table table = new ZorkTable(ddl);
            builder.put(tableName, table);
        }
        return builder.build();
    }
}
