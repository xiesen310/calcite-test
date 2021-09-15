package org.example.calcite.test.customer;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;
import org.apache.calcite.util.Source;
import org.apache.calcite.util.Sources;

import java.net.URL;
import java.util.Map;

public class CsvSchema extends AbstractSchema {
    private Map<String, Table> tableMap;
    private String dataFile;
    private String ddl;

    Map<String, Object> operand;

    public CsvSchema(Map<String, Object> operand) {
        this.operand = operand;
        this.dataFile = String.valueOf(operand.get("dataFile"));
        this.ddl = String.valueOf(operand.get("ddl"));
    }

    @Override
    protected Map<String, Table> getTableMap() {
        URL url = Resources.getResource(dataFile);
        Source source = Sources.of(url);
        String tableName = this.ddl.toUpperCase().split("CREATE TABLE")[1].split("\\(")[0].trim();
        if (tableMap == null) {
            final ImmutableMap.Builder<String, Table> builder = ImmutableMap.builder();
            builder.put(tableName, new CsvTable(source,ddl));
            // 一个数据库有多个表名，这里初始化，大小写要注意了,TEST01是表名。
            tableMap = builder.build();
        }
        return tableMap;
    }
}
