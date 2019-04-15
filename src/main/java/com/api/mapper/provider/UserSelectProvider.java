package com.api.mapper.provider;

import com.api.core.IProvider;
import com.api.model.User;
import com.api.parameter.UserSelectParameter;
import org.apache.ibatis.jdbc.SQL;

public class UserSelectProvider implements IProvider<User, UserSelectParameter> {

    /**
     * 查询单条记录
     *
     * @param parameter 查询需要的字段
     * @return 查询Sql
     */
    public String selectOne(UserSelectParameter parameter) {
        SQL sql = new SQL();
        sql.SELECT(createSelectSql()).FROM(tableName())
                .WHERE("user_id = " + parameter.getUserId());
        return sql.toString();
    }
}