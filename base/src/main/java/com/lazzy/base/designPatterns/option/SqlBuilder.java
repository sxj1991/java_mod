package com.lazzy.base.designPatterns.option;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Option模式是一个强大的工具，能够帮助我们写出更健壮、更清晰的代码。但关键是要在合适的场景下使用它，并遵循最佳实践。
 * 建议：
 * 先在特定模块试点使用
 * 制定清晰的使用规范
 * 通过代码评审确保正确使用
 * 收集团队反馈持续改进
 */
public class SqlBuilder {
    private StringBuilder sql;
    private List<Object> params;
    private String baseSelect;

    public SqlBuilder(String baseSelect) {
        this.sql = new StringBuilder();
        this.params = new ArrayList<>();
        this.baseSelect = baseSelect;
    }

    public <T> SqlBuilder option(Optional<T> option, Function<T, String> mapper) {
        option.ifPresent(value -> {
            if (sql.length() == 0) {
                sql.append(" WHERE ");
            } else {
                sql.append(" AND ");
            }
            sql.append(mapper.apply(value));
        });
        return this;
    }

    public <T> SqlBuilder optionWithParam(Optional<T> option, String sqlPart) {
        option.ifPresent(value -> {
            if (sql.length() == 0) {
                sql.append(" WHERE ");
            } else {
                sql.append(" AND ");
            }
            sql.append(sqlPart);
            params.add(value);
        });
        return this;
    }

    public SqlBuilder when(boolean condition, String sqlPart) {
        if (condition) {
            if (sql.length() == 0) {
                sql.append(" WHERE ");
            } else {
                sql.append(" AND ");
            }
            sql.append(sqlPart);
        }
        return this;
    }

    public <T> SqlBuilder optionWithPredicate(Optional<T> option, Predicate<T> predicate, String sqlPart) {
        option.filter(predicate).ifPresent(value -> {
            if (sql.length() == 0) {
                sql.append(" WHERE ");
            } else {
                sql.append(" AND ");
            }
            sql.append(sqlPart);
            params.add(value);
        });
        return this;
    }

    public String buildSql() {
        return baseSelect + sql.toString();
    }

    public List<Object> getParams() {
        return params;
    }

    public static void main(String[] args) {
        // 模拟一些查询参数
        Optional<String> name = Optional.of("张三");
        Optional<Integer> age = Optional.of(25);
        Optional<String> city = Optional.empty();

        SqlBuilder builder = new SqlBuilder("SELECT * FROM users");

        builder.option(name, (v) -> "name = " + v)
                .optionWithParam(age, "age > ?")
                .when(false, "city = ?");

        String sql = builder.buildSql();
        List<Object> params = builder.getParams();

        System.out.println("SQL: " + sql);
        System.out.println("Parameters: " + params);
    }
}