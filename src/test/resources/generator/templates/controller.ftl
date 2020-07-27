package ${package}.controller;

import ${package}.core.IController;
import ${package}.core.IService;
import ${package}.core.Parameter;
import ${package}.core.Response;
import ${package}.model.${className};
import ${package}.service.${className}Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * ${className}接口管理
 */
@RestController
@RequestMapping("/api/${mappingPath}")
public class ${className}Controller implements IController<${className}> {

    /**
     * ${className}业务逻辑处理实现对象
     */
    @Resource
    private ${className}Service ${variableName}Service;

    @Override
    public IService<${className}> createService() {
        return ${variableName}Service;
    }

    /**
     * 添加${className}
     *
     * @param ${variableName} ${className}数据实体对象
     * @param result 字段校验绑定结果对象
     * @return ${className}添加响应结果
     */
    @PostMapping("/save")
    public Response<${className}> save(@Valid ${className} ${variableName}, BindingResult result) {
        return IController.super.save(${variableName}, result);
    }

    /**
     * 删除${className}
     *
     * @param ${variableName}Id ${className}主键标识（支持数组）
     * @return ${className}删除响应结果
     */
    @GetMapping("/delete")
    public Response<String> delete(@RequestParam String... ${variableName}Id) {
        return IController.super.delete(${variableName}Id);
    }

    /**
     * 修改${className}
     *
     * @param ${variableName} ${className}数据实体对象
     * @param result 字段校验绑定结果对象
     * @return ${className}修改响应结果
     */
    @PostMapping("/update")
    public Response<String> update(@Valid ${className} ${variableName}, BindingResult result) {
        return IController.super.update(${variableName}, result);
    }

    /**
     * 查询${className}详情
     *
     * @param ${variableName}Id ${className}主键标识
     * @return ${className}详情查询响应结果
     */
    @GetMapping("/detail")
    public Response<${className}> detail(String ${variableName}Id) {
        return IController.super.detail(${variableName}Id);
    }

    /**
     * 查询${className}列表
     *
     * @param parameter 查询参数字段对象
     * @param result    字段校验绑定结果对象
     * @return ${className}列表查询响应结果
     */
    @GetMapping("/list")
    public Response<List<${className}>> list(@Valid ${className} parameter, BindingResult result) {
        return IController.super.list(parameter, result);
    }
}
