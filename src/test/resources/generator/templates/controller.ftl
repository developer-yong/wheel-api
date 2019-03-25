package ${package}.controller;

import ${package}.controller.validator.PageParameter;
import ${package}.model.${className};
import ${package}.service.IService;
import ${package}.service.${className}Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/${mappingPath}")
public class ${className}Controller implements IController<${className}, PageParameter> {

    @Resource
    private ${className}Service ${variableName}Service;

    @Override
    public IService<${className}> createService() {
        return ${variableName}Service;
    }

    @RequestMapping("/save")
    public Map<String, Object> save(@Valid ${className} ${variableName}, BindingResult result) {
        return IController.super.save(${variableName}, result);
    }

    @RequestMapping("/delete")
    public Map<String, Object> delete(@RequestParam String ${variableName}Id) {
        return IController.super.delete(${variableName}Id);
    }

    @RequestMapping("/update")
    public Map<String, Object> update(@Valid ${className} ${variableName}, BindingResult result) {
        return IController.super.update(${variableName}, result);
    }

    @RequestMapping("/detail")
    public Map<String, Object> detail(String primaryKey) {
        return IController.super.detail(primaryKey);
    }

    @RequestMapping("/list")
    public Map<String, Object> list(@Valid PageParameter parameter, String keyword, BindingResult result) {
        return IController.super.list(parameter, keyword, result);
    }
}
