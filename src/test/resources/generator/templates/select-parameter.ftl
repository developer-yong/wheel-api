package ${package}.parameter;

import ${package}.core.SelectParameter;

public class ${className}SelectParameter extends SelectParameter {

    private String ${variableName}Id;

    public ${className}SelectParameter() {
    }

    public ${className}SelectParameter(String ${variableName}Id) {
        this.${variableName}Id = ${variableName}Id;
    }

    public String get${className}Id() {
        return ${variableName}Id;
    }

    public void set${className}Id(String ${variableName}Id) {
        this.${variableName}Id = ${variableName}Id;
    }
}