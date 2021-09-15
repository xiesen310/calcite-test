package org.example.calcite.test.mem;

import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.calcite.util.Pair;
import org.apache.calcite.util.Source;

import java.util.ArrayList;
import java.util.List;

public class ZorkTable extends AbstractTable {
    protected final String ddl;

    public ZorkTable(String ddl) {
        this.ddl = ddl;
    }

    @Override
    public RelDataType getRowType(RelDataTypeFactory typeFactory) {
        final List<String> names = new ArrayList<>();
        final List<RelDataType> types = new ArrayList<>();
        String spt1 = ddl.split("\\(")[1];
        String substring = spt1.substring(0, spt1.length() - 1);
        String[] split = substring.split(",");
        for (String s : split) {
            String[] s1 = s.split(" ");
            names.add(s1[0]);
            final RelDataType type;
            String fieldType = s1[1];
            if (fieldType == null) {
                type = typeFactory.createSqlType(SqlTypeName.VARCHAR);
            } else {
                throw new RuntimeException("type 不匹配异常");
            }

            types.add(type);
        }
        return typeFactory.createStructType(Pair.zip(names, types));
    }

}
