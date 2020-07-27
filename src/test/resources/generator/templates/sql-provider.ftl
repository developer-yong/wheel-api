package ${package}.mapper.provider;

import ${package}.core.ISqlProvider;
import ${package}.model.${className};
import org.apache.ibatis.jdbc.SQL;

public class ${className}SqlProvider implements ISqlProvider<${className}> {

    /**
     * 查询单条${className}记录
     *
     * @param parameter 查询条件参数对象
     * @return 查询Sql
     */
    public String selectOne(${className} parameter) {
        if (parameter != null) {
            SQL selectSql = new SQL();
            selectSql.SELECT(createSelectSql()).FROM(tableName()).WHERE("${variableName}_id = ${variableId}");
            return selectSql.toString();
        }
        return "";
    }
}
