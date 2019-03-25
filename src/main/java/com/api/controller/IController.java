package com.api.controller;

import com.api.common.Code;
import com.api.common.Response;
import com.api.model.annotation.JDBCField;
import com.api.service.IService;
import com.api.utils.MapUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author coderyong
 */
public interface IController<T, P> {

    /**
     * 创建数据操作对象 继承自Service
     *
     * @return IService
     */
    IService<T> createService();


    default Map<String, Object> save(@Valid T t, BindingResult result) {
        IService<T> service = createService();
        //判断是否有错误验证信息
        if (result != null && result.hasErrors()) {
            ObjectError error = result.getAllErrors().get(0);
            return Response.error(Code.create(Code.ERROR_PARA, error.getDefaultMessage()));
        }
        return service.save(t) ? Response.success() : Response.fail();
    }

    /**
     * 删除记录
     *
     * @param primaryKeys 主键数组
     */
    default Map<String, Object> delete(String... primaryKeys) {
        if (StringUtils.isEmpty(primaryKeys)) {
            return Response.error(Code.ERROR_PARA);
        }
        return createService().delete(primaryKeys) ? Response.success() : Response.fail();
    }

    /**
     * 更新记录
     *
     * @param t Model
     */
    default Map<String, Object> update(@Valid T t, BindingResult result) {
        Class<?> clazz = t.getClass();
        String primaryKey = "";
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.isAnnotationPresent(JDBCField.class)) {
                    JDBCField jdbcField = field.getAnnotation(JDBCField.class);
                    if (jdbcField.isIdentity()) {
                        primaryKey = (String) field.get(t);
                        break;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isEmpty(primaryKey)) {
            return Response.error(Code.ERROR_PARA);
        }
        if (result != null && result.hasErrors()) {
            ObjectError error = result.getAllErrors().get(0);
            if (!"NotNull".equals(error.getCode())) {
                return Response.error(Code.create(Code.ERROR_PARA, error.getDefaultMessage()));
            }
        }
        return createService().update(t) ? Response.success() : Response.fail();
    }

    /**
     * 查询详情信息
     *
     * @param primaryKey 主键
     */
    default Map<String, Object> detail(String primaryKey) {
        if (StringUtils.isEmpty(primaryKey)) {
            return Response.error(Code.ERROR_PARA);
        }
        T t = createService().findById(primaryKey);
        return t != null ? Response.success(t) : Response.fail();
    }

    /**
     * 查询列表信息
     *
     * @param parameter 查询参数
     * @param keyword   关键字
     * @param result    验证参数结果
     */
    default Map<String, Object> list(@Valid P parameter, String keyword, BindingResult result) {
        IService<T> service = createService();
        //判断是否有错误验证信息
        if (result.hasErrors()) {
            ObjectError error = result.getAllErrors().get(0);
            return Response.error(Code.create(Code.ERROR_PARA, error.getDefaultMessage()));
        }
        //将参数对象转为Map参数对象
        Map<String, Object> selectModel = parameter != null ? MapUtils.objectToMap(parameter) : new HashMap<>();
        selectModel.put("keyword", keyword);
        //查询List数据
        List<Map<String, Object>> tList = service.findListBy(selectModel);
        int count = service.count(selectModel);
        return tList != null ? Response.success(tList, count < tList.size() ? tList.size() : count) : Response.fail();
    }
}
