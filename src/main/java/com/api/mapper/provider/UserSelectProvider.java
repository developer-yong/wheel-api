package com.api.mapper.provider;

import com.api.core.IProvider;
import com.api.model.User;
import com.api.parameter.UserSelectParameter;
import org.apache.ibatis.jdbc.SQL;

public class UserSelectProvider implements IProvider<User, UserSelectParameter> {

    /**
     * 表名
     *
     * @return 表名
     */
    @Override
    public String tableName() {
        return "user";
    }

    /**
     * 主键在Model中的变量名
     *
     * @return 变量名
     */
    @Override
    public String primaryKeyInModelName() {
        return "userId";
    }

    /**
     * 查询单条记录
     *
     * @param userSelectParameter 查询需要的字段
     * @return 查询Sql
     */
    public String selectOne(UserSelectParameter userSelectParameter) {
        SQL sql = new SQL();
        sql.SELECT(createSelectSql()).FROM(tableName())
                .WHERE(createJDBCName(primaryKeyInModelName()) + " = #{" + primaryKeyInModelName() + "}");
        return sql.toString();
    }
}