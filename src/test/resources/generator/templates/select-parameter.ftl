package ${package}.service;

import ${package}.core.IService;
import ${package}.model.${className};
import ${package}.parameter.${className}SelectParameter;

public interface ${className}Service extends IService<${className}, ${className}SelectParameter> {

}

package ${package}.parameter;

import ${package}.core.SelectParameter;

public class ${className}SelectParameter extends SelectParameter {

    private String ${variableName}Id;

    public String get${className}Id() {
        return ${variableName}Id;
    }

    public void set${className}Id(String ${variableName}Id) {
        this.${variableName}Id = ${variableName}Id;
    }
}