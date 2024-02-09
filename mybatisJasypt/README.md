## mybatis数据库框架整理 
### mybatis框架基本信息
> MyBatis框架是一个开源的数据持久层框架。它的内部封装了通过JDBC访问数据库的操作，支持普通的SQL查询、存储过程和高级映射，几乎消除了所有的JDBC代码和参数的手工设置以及结果集的检索。MyBatis作为持久层框架，其主要思想是将程序中的大量SQL语句剥离出来，配置在配置文件当中，实现SQL的灵活配置。这样做的好处是将SQL与程序代码分离，可以在不修改代码的情况下，直接在配置文件当中修改SQL。
 ORM（Object/Relational Mapping）即对象关系映射，是一种数据持久化技术。它在对象模型和关系型数据库直接建立起对应关系，并且提供一种机制，通过JavaBean对象去操作数据库表的数据

### springboot框架集成

> mybatis框架集成步骤：
>
> 1. 引入依赖
> 2. 配置文件设置（支持xml和yaml）
> 3. orm对应关系配置

```xml
<!-- 依赖包 -->
<!-- SpringBoot集成mybatis框架 -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
<!-- 阿里数据库连接池 -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.2.15</version>
</dependency>
<!-- 数据库依赖 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

```xml
<!-- mybatis全局配置 -->
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!-- 全局参数 -->
    <settings>
        <!-- 使全局的映射器启用或禁用缓存 -->
        <setting name="cacheEnabled"             value="true"   />
        <!-- 允许JDBC 支持自动生成主键 -->
        <setting name="useGeneratedKeys"         value="true"   />
        <!-- 配置默认的执行器.SIMPLE就是普通执行器;REUSE执行器会重用预处理语句(prepared statements);BATCH执行器将重用语句并执行批量更新 -->
        <setting name="defaultExecutorType"      value="SIMPLE" />
		<!-- 指定 MyBatis 所用日志的具体实现 -->
        <setting name="logImpl"                  value="SLF4J"  />
	</settings>
	
</configuration>
```

```xml
<!-- 依赖映射 -->
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.mybatis.dao.UserMapper">
    <!-- entity类型字段映射 -->
    <resultMap type="com.example.mybatis.entity.User" id="UserMap">
        <id property="userId" column="user_id"/>
        <result property="userName" column="user_name"/>
    </resultMap>
    <!-- dao层接口映射 -->
    <select id="findUser" resultMap="UserMap">
        select * from tb_user
    </select>
    ...
</mapper> 
<!-- 接口 -->
@Mapper
public interface UserMapper {

    @DataSource(value = DataSourceType.SLAVE)
    List<User> findUser();
}
<!-- 实体类型 -->
public class User{

    private String userId;

    private String userName;

    private String password;

    private Integer status;
}
```



### 注解实现多数据源切换

> 偶尔需求需要调用多个数据源，数据源切换可以通过注解更加优雅的切换。
>
> 仅适用mybatis数据库，jpa因为session一直持有同一个会出现无法切换的情况。

```java
/**
 * 自定义多数据源切换注解
 *
 * 优先级：先方法，后类，如果方法覆盖了类上的数据源类型，以方法的为准，否则以类上的为准
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource
{
    /**
     * 切换数据源名称
     */
    DataSourceType value() default DataSourceType.MASTER;
}
```

```java
/**
 * 动态配置数据源
 */
public class DynamicDataSourceContextHolder
{
    public static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    /**
     * 使用ThreadLocal维护变量，ThreadLocal为每个使用该变量的线程提供独立的变量副本，
     *  所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置数据源的变量
     */
    public static void setDataSourceType(String dsType)
    {
        log.info("切换到{}数据源", dsType);
        CONTEXT_HOLDER.set(dsType);
    }

    /**
     * 获得数据源的变量
     */
    public static String getDataSourceType()
    {
        String sourceType = CONTEXT_HOLDER.get();
        log.info("获取{}数据源", sourceType);
        return sourceType;
    }

    /**
     * 清空数据源变量
     */
    public static void clearDataSourceType()
    {
        CONTEXT_HOLDER.remove();
    }
}
```

```java
/**
 * 继承AbstractRoutingDataSource
 * spring执行determineCurrentLookupKey动态切换数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource
{
    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources)
    {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey()
    {
        String dataSourceType = DynamicDataSourceContextHolder.getDataSourceType();
        return dataSourceType;
    }
}

/** 切面会在方法执行前配置不同数据源 **/
 if (StringUtils.hasText(dataSource.value().name()))
 {
     //设置当前线程动态数据源
     DynamicDataSourceContextHolder.setDataSourceType(dataSource.value().name());
 }
```

```java
    /** 将数据源信息交给ioc容器管理 **/
    @Value("${spring.datasource.druid.slave.enabled}")
    private Boolean bool;

    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource() {
        DataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }

    /**
     * @param
     * @return
     * @ConfigurationProperties 通过配置文件进行绑定，然后将此Bean归还给容器 配置@Bean使用
     * @ConditionalOnProperty 该注解作用 havingValue根据这个值和配置文件比较 是否启动该配置
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
    public DataSource slaveDataSource() {
        DataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(DataSource masterDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
        if(bool){
            setDataSource(targetDataSources, DataSourceType.SLAVE.name(), "slaveDataSource");
        }
        return new DynamicDataSource(masterDataSource, targetDataSources);
    }

    /**
     * 设置数据源
     *
     * @param targetDataSources 备选数据源集合
     * @param sourceName        数据源名称
     * @param beanName          bean名称
     */
    public void setDataSource(Map<Object, Object> targetDataSources, String sourceName, String beanName) {
        try {
            DataSource dataSource = SpringUtils.getBean(beanName);
            targetDataSources.put(sourceName, dataSource);
        } catch (Exception e) {
            //改善ruoyi框架不捕捉异常信息的问题 注入value判断
            e.printStackTrace();
        }
    }
```



### mybatis事务管理

> MyBatis中的事务是指一系列数据库操作，这些操作要么全部执行成功，要么全部执行失败。如果操作过程中发生错误，所有对数据库的修改都将被回滚，即还原到最初状态。事务是确保数据一致性和完整性的关键机制之一。
>
> 在MyBatis中，我们可以通过三种方式来管理事务：
>
> 1. 编程式管理事务：在代码中显式开启、提交或回滚事务。
> 2. 声明式管理事务：通过AOP代理实现事务管理，可以让代码更简洁，更容易维护。
> 3. 注解式管理事务：通过注解方式管理事务，是声明式管理事务的一种扩展方式。

:cherry_blossom:详情点击：[mybatis框架集成](src%2Fmain%2Fjava%2Fcom%2Fexample%2Fmybatis%2FMybatisApplication.java)