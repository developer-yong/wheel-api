package com.api.core;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.Valid;
import java.util.List;

/**
 * 接口控制基类
 *
 * @author coderyong
 */
public interface IController<M> {

    /**
     * 创建业务实现对象 继承自Service
     *
     * @return IService
     */
    IService<M> createService();


    /**
     * 添加记录接口控制
     *
     * @param m      记录字典对象
     * @param result 字段校验绑定结果对象
     */
    default Response<M> save(@Valid M m, BindingResult result) {
        IService<M> service = createService();
        //判断是否有错误验证信息
        if (result != null && result.hasErrors()) {
            FieldError error = (FieldError) result.getAllErrors().get(0);
            if (!error.getField().equals(service.getIdName(m))) {
                return Response.errorParameter(error.getDefaultMessage());
            }
        }
        //获取保存信息，如没有信息则保存成功
        String message = service.save(m);
        return StringUtils.isEmpty(message) ? Response.success(m) : Response.fail(message);
    }

    /**
     * 删除记录接口控制
     *
     * @param primaryKeys 记录主键数组
     */
    default Response<String> delete(String... primaryKeys) {
        if (ObjectUtils.isEmpty(primaryKeys)) {
            return Response.errorParameter();
        }
        //获取删除信息，如没有信息则删除成功
        String message = createService().delete(primaryKeys);
        return StringUtils.isEmpty(message) ? Response.success() : Response.fail(message);
    }

    /**
     * 修改记录接口控制
     *
     * @param m      记录字典对象
     * @param result 字段校验绑定结果对象
     */
    default Response<String> update(@Valid M m, BindingResult result) {
        IService<M> service = createService();
        String primaryKey = service.getIdName(m);
        if (StringUtils.isEmpty(primaryKey)) {
            return Response.errorParameter();
        }
        if (result != null && result.hasErrors()) {
            ObjectError error = result.getAllErrors().get(0);
            //更操作只验证不为空的数据
            if (!"NotNull".equals(error.getCode()) && !"NotEmpty".equals(error.getCode())
                    && !"NotBlank".equals(error.getCode())) {
                return Response.errorParameter(error.getDefaultMessage());
            }
        }
        //获取更新信息，如没有信息则更新成功
        String message = service.update(m);
        return StringUtils.isEmpty(message) ? Response.success() : Response.fail(message);
    }

    /**
     * 查询记录详情接口控制
     *
     * @param primaryKey 记录主键
     */
    default Response<M> detail(String primaryKey) {
        if (StringUtils.isEmpty(primaryKey)) {
            return Response.errorParameter();
        }
        //获取查询结果信息，如没结果为错误信息返回错误，否则返回查询结果数据
        M result = createService().findById(primaryKey);
        if (ObjectUtils.isEmpty(result) || result instanceof String) {
            return Response.fail((String) result);
        }
        return Response.success(result);
    }

    /**
     * 查询记录列表接口控制
     *
     * @param parameter 查询参数字段对象
     * @param result    字段校验绑定结果对象
     */
    default Response<List<M>> list(@Valid M parameter, BindingResult result) {
        IService<M> service = createService();
        //判断是否有错误验证信息
        if (result.hasErrors()) {
            ObjectError error = result.getAllErrors().get(0);
            //更操作只验证不为空的数据
            if (!"NotNull".equals(error.getCode()) && !"NotEmpty".equals(error.getCode())
                    && !"NotBlank".equals(error.getCode())) {
                return Response.errorParameter(error.getDefaultMessage());
            }
        }
        //获取查询结果信息，如没结果为错误信息返回错误，否则返回查询结果数据
        List<M> results = service.findListBy(parameter);
        int count = service.count(parameter);
        return Response.success(results, Math.max(count, results.size()));
    }
}
