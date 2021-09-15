# calcite

Apache Calcite 是一款开源SQL解析工具, 可以将各种SQL语句解析成抽象语法术AST(Abstract Syntax Tree), 之后通过操作AST就可以把SQL中所要表达的算法与关系体现在具体代码之中。

Calcite的生前为Optiq(也为Farrago), 为Java语言编写, 通过十多年的发展, 在2013年成为Apache旗下顶级项目，并还在持续发展中, 该项目的创始人为Julian Hyde, 其拥有多年的SQL引擎开发经验,
目前在Hortonworks工作, 主要负责Calcite项目的开发与维护。

目前, 使用Calcite作为SQL解析与处理引擎有Hive、Drill、Flink、Phoenix和Storm，可以肯定的是还会有越来越多的数据处理引擎采用Calcite作为SQL解析工具。

## calcite 功能

* SQL 解析
* SQL 校验
* 查询优化
* SQL 生成器
* 数据连接

## calcite 概念

* `Catelog`: 主要定义 SQL 语义相关的元数据与命名空间。
* `SQL parser`: 主要是把 SQ L转化成 AST.
* `SQL validator`: 通过 Catalog 来校证AST.
* `Query optimizer`: 将 AST 转化成物理执行计划、优化物理执行计划.
* `SQL generator`: 反向将物理执行计划转化成 SQL 语句.
* `Schema`: 主要定义schema与表的集合，schema 并不是强制一定需要的，比如说有两张同名的表T1, T2，就需要schema要区分这两张表，如A.T1, B.T1.
* `Table`: 对应关系数据库的表，代表一类数据，在 calcite 中由 RelDataType定义.
* `RelDataType`: 代表表的数据定义，如表的数据列名称、类型等.

## calcite 如何使用

1. 构建外部 Schema
2. 建立连接,获取Calcite 连接
3. 添加 Schema
4. 编写 SQL
5. 执行 SQL
6. 处理结果集





