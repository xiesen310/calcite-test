package org.example.calcite.test.zork;

import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.calcite.DataContext;
import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.linq4j.AbstractEnumerable;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Enumerator;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.ScannableTable;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.calcite.util.Pair;

import java.util.List;

@Data
public class ZorkMemTable extends AbstractTable implements ScannableTable {
    /**
     * 数据资源
     */
    private String name;
    private String ddl;

    public ZorkMemTable(String name, String ddl) {
        this.name = name;
        this.ddl = ddl;
    }

    /**
     * 获取字段类型
     */
    @Override
    public RelDataType getRowType(RelDataTypeFactory relDataTypeFactory) {
        JavaTypeFactory typeFactory = (JavaTypeFactory) relDataTypeFactory;

        List<String> names = Lists.newLinkedList();
        List<RelDataType> types = Lists.newLinkedList();
        String s = ddl.toUpperCase().split("\\(")[1];
        String substring = s.substring(0, s.length() - 1);
        List<String> lines = Lists.newArrayList(substring.split(","));
        lines.forEach(column -> {
            String c = column.trim();
            String name = c.split(" ")[0];
            String type = c.split(" ")[1];
            names.add(name);
            types.add(typeFactory.createSqlType(SqlTypeName.get(type)));
        });

        return typeFactory.createStructType(Pair.zip(names, types));
    }

    @Override
    public Enumerable<Object[]> scan(DataContext dataContext) {
        System.out.println("表名: " + this.getName());
        return new AbstractEnumerable<Object[]>() {
            @Override
            public Enumerator<Object[]> enumerator() {
                return new ZorkMemEnumerator<>();
            }
        };
    }
}