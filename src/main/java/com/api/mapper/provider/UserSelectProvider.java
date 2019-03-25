package com.api.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class UserSelectProvider implements IProvider {

    private String tableName = "user";

    /**
     * 创建FormSql
     *
     * @return Sql字符串
     */
    public String createFromSql() {
        return tableName;
    }

    /**
     * 创建查询条件Sql
     *
     * @param selectModel 查询需要的字段
     * @return 查询条件Sql
     */
    public String createWhereSql(Map<String, Object> selectModel) {

        return "1=1";
    }

    /**
     * 查询单条记录
     *
     * @param selectModel 查询需要的字段
     * @return 查询Sql
     */
    public String selectOne(Map<String, Object> selectModel) {
        SQL sql = new SQL();
        sql.SELECT("*").FROM(tableName).WHERE("1=1");
        return sql.toString();
    }


    /**
     * 查询记录列表
     *
     * @param selectModel 查询需要的字段
     * @return 查询Sql
     */
    public String selectList(Map<String, Object> selectModel) {
        SQL sql = new SQL();
        sql.SELECT("*");
        return IProvider.super.createListSql(sql, selectModel);
    }
}