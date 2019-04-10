package com.api.core;

import com.api.core.utils.MapUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.Valid;
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
        //获取保存信息，如没有信息则保存成功
        String message = service.save(t);
        return StringUtils.isEmpty(message) ? Response.success(t) : Response.fail(message);
    }

    /**
     * 删除记录
     *
     * @param primaryKeys 主键数组
     */
    default Map<String, Object> delete(String... primaryKeys) {
        if (ObjectUtils.isEmpty(primaryKeys)) {
            return Response.error(Code.ERROR_PARA);
        }
        //获取删除信息，如没有信息则删除成功
        String message = createService().delete(primaryKeys);
        return StringUtils.isEmpty(message) ? Response.success() : Response.fail(message);
    }

    /**
     * 更新记录
     *
     * @param t Model
     */
    default Map<String, Object> update(@Valid T t, BindingResult result) {
        String primaryKey = IService.getPrimaryKey(t.getClass());
        if (StringUtils.isEmpty(primaryKey)) {
            return Response.error(Code.ERROR_PARA);
        }
        if (result != null && result.hasErrors()) {
            ObjectError error = result.getAllErrors().get(0);
            //更操作只验证不为空的数据
            if (!"NotNull".equals(error.getCode()) && !"NotEmpty".equals(error.getCode())) {
                return Response.error(Code.create(Code.ERROR_PARA, error.getDefaultMessage()));
            }
        }
        //获取更新信息，如没有信息则更新成功
        String message = createService().update(t);
        return StringUtils.isEmpty(message) ? Response.success() : Response.fail();
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
        //获取查询结果信息，如没结果为错误信息返回错误，否则返回查询结果数据
        Object result = createService().findById(primaryKey);
        if (ObjectUtils.isEmpty(result) || result instanceof String) {
            return Response.fail((String) result);
        }
        return Response.success(result);
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
        //获取查询结果信息，如没结果为错误信息返回错误，否则返回查询结果数据
        Object resultData = service.findListBy(selectModel);
        if (ObjectUtils.isEmpty(result) || resultData instanceof String) {
            return Response.fail((String) resultData);
        }
        int count = service.count(selectModel);
        return Response.success(resultData, count < ((List) resultData).size() ? ((List) resultData).size() : count);
    }
}
