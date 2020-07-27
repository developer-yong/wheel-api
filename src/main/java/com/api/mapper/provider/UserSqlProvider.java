package com.api.mapper.provider;

import com.api.core.ISqlProvider;
import com.api.model.User;
import org.apache.ibatis.jdbc.SQL;

public class UserSqlProvider implements ISqlProvider<User> {

    /**
     * 查询单条User记录
     *
     * @param parameter 查询条件参数对象
     * @return 查询Sql
     */
    public String selectOne(User parameter) {
        if (parameter != null) {
            SQL selectSql = new SQL();
            selectSql.SELECT(createSelectSql()).FROM(tableName()).WHERE("user_id = #{userId}");
            return selectSql.toString();
        }
        return "";
    }
}
