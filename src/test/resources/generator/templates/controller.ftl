package ${package}.controller;

import ${package}.core.IController;
import ${package}.core.IService;
import ${package}.model.User;
import ${package}.parameter.${className}SelectParameter;
import ${package}.service.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/${mappingPath}")
public class ${className}Controller implements IController<${className}, ${className}SelectParameter> {

    @Resource
    private ${className}Service ${variableName}Service;

    @Override
    public IService<${className}, ${className}SelectParameter> createService() {
        return ${variableName}Service;
    }

    @RequestMapping("/save")
    public Map<String, Object> save(@Valid ${className} ${variableName}, BindingResult result) {
        return IController.super.save(${variableName}, result);
    }

    @RequestMapping("/delete")
    public Map<String, Object> delete(@RequestParam String... ${variableName}Id) {
        return IController.super.delete(${variableName}Id);
    }

    @RequestMapping("/update")
    public Map<String, Object> update(@Valid ${className} ${variableName}, BindingResult result) {
        return IController.super.update(${variableName}, result);
    }

    @RequestMapping("/detail")
    public Map<String, Object> detail(String ${variableName}Id) {
        return IController.super.detail(${variableName}Id);
    }

    @RequestMapping("/list")
    public Map<String, Object> list(@Valid ${className}SelectParameter> parameter, String keyword, BindingResult result) {
        return IController.super.list(parameter, keyword, result);
    }
}
