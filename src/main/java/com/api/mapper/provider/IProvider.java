package com.api.mapper.provider;

import com.api.utils.SqlUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * @author coderyong
 */
public interface IProvider {

    /**
     * 创建查询条件Sql
     *
     * @param selectModel 查询需要的字段
     * @return 查询条件Sql
     */
    String createWhereSql(Map<String, Object> selectModel);

    /**
     * 创建FormSql
     *
     * @return Sql字符串
     */
    String createFromSql();

    /**
     * 查询记录列表
     *
     * @param selectModel 查询需要的字段
     * @return 查询Sql
     */
    default String createListSql(SQL sql, Map<String, Object> selectModel) {
        sql.FROM(createFromSql());
        sql.WHERE(createWhereSql(selectModel));
        int page = selectModel.get("page") != null ? (int) selectModel.get("page") : 0;
        int size = selectModel.get("size") != null ? (int) selectModel.get("size") : 0;
        return sql.toString() + SqlUtils.getPageSql(page, size);
    }

    /**
     * 查询记录列表计数
     *
     * @param selectModel 查询需要的字段
     * @return 查询Sql
     */
    default String count(Map<String, Object> selectModel) {
        SQL sql = new SQL();
        sql.SELECT("COUNT(*)");
        sql.FROM(createFromSql());
        sql.WHERE(createWhereSql(selectModel));
        return sql.toString();
    }
}
