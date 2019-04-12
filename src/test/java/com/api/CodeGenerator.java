package com.api;

import freemarker.template.TemplateExceptionHandler;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mybatis.generator.config.PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS;
import static org.mybatis.generator.config.PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE;

/**
 * 代码生成器，自动生成Model、Mapper、Service、Controller简化开发。
 */
public class CodeGenerator extends DefaultCommentGenerator {

    //JDBC配置
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/demo?useSSL=false";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "db123456";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    //项目在硬盘上的基础路径
    private static final String PATH_PROJECT = System.getProperty("user.dir");
    //模板位置
    private static final String PATH_TEMPLATE = PATH_PROJECT + "/src/test/resources/generator/templates";

    //java文件路径
    private static final String JAVA_PATH = "/src/main/java";

    //包名路径
    private static final String PACKAGE_NAME = "com.api";
    //生成的Model存放路径
    private static final String PACKAGE_MODEL = PACKAGE_NAME + ".model";
    //生成的Mapper存放路径
    private static final String PACKAGE_MAPPER = PACKAGE_NAME + ".mapper";
    //生成的ServiceImpl实现存放路径
    private static final String PACKAGE_PROVIDER = PACKAGE_NAME + ".mapper.provider";
    //生成的Controller存放路径
    private static final String PACKAGE_CONTROLLER = PACKAGE_NAME + ".controller";
    //生成的Service存放路径
    private static final String PACKAGE_SERVICE = PACKAGE_NAME + ".service";
    //生成的ServiceImpl实现存放路径
    private static final String PACKAGE_SERVICE_IMPL = PACKAGE_NAME + ".service.impl";
    //生成的ServiceImpl实现存放路径
    private static final String PACKAGE_SELECT_PARAMETER = PACKAGE_NAME + ".parameter";

    //如果表名前带有数据库名需设置此字段，否则设置为空
    private static final String DB_NAME = "workorder";
    //设置是否根据表名忽略字段前缀
    private static final boolean IGNORE_FIELD_PREFIX = true;

    private static final Context sContext = new Context(ModelType.FLAT);

    public static void main(String[] args) {
        generator("user");
    }

    private static void generator(String... tableNames) {
        for (String tableName : tableNames) {
            String modelName = getCamelString(tableName.replace(DB_NAME + "_", ""), true);
            genModel(tableName, modelName);
            String mappingPath = tableName.replace(DB_NAME + "_", "").replaceAll("_", "/");
            genController(modelName, mappingPath);
            genService(modelName);
            genServiceImpl(modelName);
            genMapper(modelName);
            genProvider(tableName, modelName);
            genSelectParameter(modelName);
        }
    }

    /**
     * 初始化 MybatisGenerator 配置
     */
    private static void init() {
        //上下文id，用于在生成错误时提示
        sContext.setId(CodeGenerator.class.getSimpleName());
        sContext.setTargetRuntime("MyBatis3Simple");
        //指明数据库的用于标记数据库对象名的符号，比如ORACLE就是双引号，MYSQL默认是`反引号；
        sContext.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        sContext.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

        CommentGeneratorConfiguration commentConfiguration = new CommentGeneratorConfiguration();
        commentConfiguration.setConfigurationType(CodeGenerator.class.getName());
        //去掉注释
        commentConfiguration.addProperty(COMMENT_GENERATOR_SUPPRESS_DATE, "true");
        commentConfiguration.addProperty(COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS, "true");
        sContext.setCommentGeneratorConfiguration(commentConfiguration);

        //设置JDBC连接配置信息
        JDBCConnectionConfiguration jdbcConfiguration = new JDBCConnectionConfiguration();
        jdbcConfiguration.setConnectionURL(JDBC_URL);
        jdbcConfiguration.setUserId(JDBC_USERNAME);
        jdbcConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        sContext.setJdbcConnectionConfiguration(jdbcConfiguration);

        //设置Model配置信息
        JavaModelGeneratorConfiguration modelConfiguration = new JavaModelGeneratorConfiguration();
        modelConfiguration.setTargetProject(PATH_PROJECT + JAVA_PATH);
        modelConfiguration.setTargetPackage(PACKAGE_MODEL);
        sContext.setJavaModelGeneratorConfiguration(modelConfiguration);
    }

    /**
     * 通过数据表配置生成 Model 和 Mapper 类
     * <dependency>
     * <groupId>org.mybatis.generator</groupId>
     * <artifactId>mybatis-generator-core</artifactId>
     * <version>1.3.5</version>
     * <scope>test</scope>
     * </dependency>
     *
     * @param tableName 数据表名称
     * @link https://www.cnblogs.com/maanshancss/p/6027999.html
     */
    private static void genModel(String tableName, String modelName) {
        init();
        //添加数据表配置信息
        sContext.addTableConfiguration(createTableConfiguration(tableName, modelName));

        List<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration config = new Configuration();
            config.addContext(sContext);
            config.validate();

            DefaultShellCallback callback = new DefaultShellCallback(true);
            warnings = new ArrayList<>();
            generator = new MyBatisGenerator(config, callback, new ArrayList<>());
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }

        List<GeneratedJavaFile> files = generator.getGeneratedJavaFiles();
        if (files.isEmpty()) {
            throw new RuntimeException("生成Model和Mapper失败：" + warnings);
        } else {
            for (GeneratedJavaFile file : files) {
                System.out.println(file.getFileName() + " 已生成");
            }
        }
    }

    /**
     * 创建Model数据表Model 和 Mapper自动生成相关配置
     *
     * @param tableName 表名
     * @param modelName Model名
     * @return TableConfiguration
     */
    private static TableConfiguration createTableConfiguration(String tableName, String modelName) {
        TableConfiguration configuration = new TableConfiguration(sContext);
        configuration.setTableName(tableName);
        //设置Model类名
        configuration.setDomainObjectName(modelName);

        //根据表名忽略字段前缀
        if (IGNORE_FIELD_PREFIX) {
            ColumnRenamingRule renamingRule = new ColumnRenamingRule();
            //设置忽略字段前缀
            renamingRule.setSearchString("^" + tableName + (tableName.contains("_") ? "_" : ""));
        }

        configuration.setInsertStatementEnabled(false);
        configuration.setUpdateByPrimaryKeyStatementEnabled(false);
        configuration.setCountByExampleStatementEnabled(false);
        configuration.setUpdateByExampleStatementEnabled(false);
        configuration.setDeleteByExampleStatementEnabled(false);
        configuration.setSelectByExampleStatementEnabled(false);
        configuration.setWildcardEscapingEnabled(true);
        return configuration;
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (!StringUtils.isEmpty(introspectedColumn.getRemarks())) {
            StringBuilder sb = new StringBuilder();
            field.addJavaDocLine("/**");
            sb.append(" * ");
            sb.append(introspectedColumn.getRemarks());
            field.addJavaDocLine(sb.toString().replace("\n", " "));
            field.addJavaDocLine(" */");
        }
    }

    /**
     * 生成指定 Model 的 Provider 类
     *
     * @param modelName 指定Model类名
     */
    private static void genProvider(String tableName, String modelName) {
        Map<String, Object> model = new HashMap<>();
        model.put("tableName", tableName);
        model.put("className", modelName);
        model.put("variableName", getCamelString(modelName, false));
        model.put("package", PACKAGE_NAME);

        freemarkerGenerator(model, PACKAGE_PROVIDER, modelName + "SelectProvider.java", "select-provider.ftl");
    }

    /**
     * 生成指定 Model 的 Mapper 类
     *
     * @param modelName 指定Model类名
     */
    private static void genMapper(String modelName) {
        Map<String, Object> model = new HashMap<>();
        model.put("className", modelName);
        model.put("variableName", getCamelString(modelName, false));
        model.put("package", PACKAGE_NAME);

        freemarkerGenerator(model, PACKAGE_MAPPER, modelName + "Mapper.java", "mapper.ftl");
    }

    /**
     * 生成指定 Model 的 Service 类
     *
     * @param modelName 指定Model类名
     */
    private static void genService(String modelName) {
        Map<String, Object> model = new HashMap<>();
        model.put("className", modelName);
        model.put("variableName", getCamelString(modelName, false));
        model.put("package", PACKAGE_NAME);

        freemarkerGenerator(model, PACKAGE_SERVICE, modelName + "Service.java", "service.ftl");
    }

    /**
     * 生成指定 Model 的 ServiceImpl 类
     *
     * @param modelName 指定Model类名
     */
    private static void genServiceImpl(String modelName) {
        Map<String, Object> model = new HashMap<>();
        model.put("className", modelName);
        model.put("variableName", getCamelString(modelName, false));
        model.put("package", PACKAGE_NAME);

        freemarkerGenerator(model, PACKAGE_SERVICE_IMPL, modelName + "ServiceImpl.java", "service-impl.ftl");
    }

    /**
     * 生成指定 Model 的 SelectParameter 类
     *
     * @param modelName 指定Model类名
     */
    private static void genSelectParameter(String modelName) {
        Map<String, Object> model = new HashMap<>();
        model.put("className", modelName);
        model.put("variableName", getCamelString(modelName, false));
        model.put("package", PACKAGE_NAME);

        freemarkerGenerator(model, PACKAGE_SELECT_PARAMETER, modelName + "SelectParameter.java", "select-parameter.ftl");
    }

    /**
     * 生成指定 Model、MappingPath 的 Controller
     *
     * @param modelName   指定Model类名
     * @param mappingPath 指定Controller请求路径
     */
    private static void genController(String modelName, String mappingPath) {
        Map<String, Object> model = new HashMap<>();
        model.put("mappingPath", mappingPath);
        model.put("className", modelName);
        model.put("variableName", getCamelString(modelName, false));
        model.put("package", PACKAGE_NAME);

        freemarkerGenerator(model, PACKAGE_CONTROLLER, modelName + "Controller.java", "controller.ftl");
    }

    /**
     * 创建 freemarker 代码生成器
     * <!-- https://mvnrepository.com/artifact/org.freemarker/freemarker -->
     * <dependency>
     * <groupId>org.freemarker</groupId>
     * <artifactId>freemarker</artifactId>
     * <version>2.3.28</version>
     * <scope>test</scope>
     * </dependency>
     *
     * @return freemarker Configuration
     * @throws IOException IO操作异常
     */
    private static freemarker.template.Configuration createConfiguration() throws IOException {
        freemarker.template.Configuration config = new freemarker.template
                .Configuration(freemarker.template.Configuration.VERSION_2_3_28);
        config.setDirectoryForTemplateLoading(new File(PATH_TEMPLATE));
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return config;
    }

    /**
     * freemarker自动生成
     *
     * @param model           模板需要字段
     * @param filePackagePath 指定生成文件所在包路径
     * @param fileName        指定文件名
     * @param templateName    指定模板名
     */
    private static void freemarkerGenerator(Map<String, Object> model, String filePackagePath, String fileName, String templateName) {
        try {
            freemarker.template.Configuration config = createConfiguration();

            File file = new File(PATH_PROJECT + JAVA_PATH + packageConvertPath(filePackagePath) + fileName);
            if (file.getParentFile().exists() || file.getParentFile().mkdirs()) {
                config.getTemplate(templateName).process(model, new FileWriter(file));
                if (file.exists()) {
                    System.out.println(fileName + " 已生成");
                }
            }
        } catch (Exception e) {
            System.out.println(fileName + "生成失败");
            e.printStackTrace();
        }
    }

    /**
     * 包名转文件路径
     *
     * @param packageName 包名
     * @return 文件路径
     */
    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

    /**
     * 获取驼峰式字符串
     *
     * @param str                     原字符串
     * @param firstCharacterUppercase 是否首字母大写
     * @return 驼峰式字符串
     */
    private static String getCamelString(String str, boolean firstCharacterUppercase) {
        StringBuilder sb = new StringBuilder();

        boolean nextUpperCase = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            switch (c) {
                case '_':
                case '-':
                case ' ':
                case '/':
                    if (sb.length() > 0) {
                        nextUpperCase = true;
                    }
                    break;

                default:
                    if (nextUpperCase) {
                        sb.append(Character.toUpperCase(c));
                        nextUpperCase = false;
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                    break;
            }
        }
        if (firstCharacterUppercase) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }
        return sb.toString();
    }
}
