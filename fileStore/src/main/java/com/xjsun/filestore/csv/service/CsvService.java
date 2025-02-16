package com.xjsun.filestore.csv.service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import com.xjsun.filestore.csv.entity.User;

import java.io.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.nio.file.Paths;

public class CsvService {
    // 基础路径：项目根目录/store/file/
    private static final String BASE_PATH = "fileStore/file/";

    /**
     * 读取CSV文件并转换为User对象列表
     * @param fileName 输入文件名（自动拼接基础路径）
     * @return 包含所有User对象的列表
     * @throws IOException 文件不存在或读取错误时抛出
     * @throws CsvException CSV格式不匹配时抛出
     */
    public static List<User> readUsers(String fileName) throws IOException, CsvException {
        try (Reader reader = new FileReader(getFullPath(fileName))) {
            CsvToBean<User> csvToBean = new CsvToBeanBuilder<User>(reader)
                    .withType(User.class)          // 设置目标类
                    .withIgnoreLeadingWhiteSpace(true) // 忽略前导空格
                    .withSkipLines(1)             // 跳过CSV标题行
                    .build();
            return csvToBean.parse();             // 执行解析操作
        }
    }

    /**
     * 将User对象列表写入CSV文件
     * @param fileName 输出文件名（自动拼接基础路径）
     * @param users 要写入的User对象列表
     * @throws Exception 包含多种可能的I/O和CSV格式异常
     */
    public static void writeUsers(String fileName, List<User> users) throws Exception {
        try (Writer writer = new FileWriter(getFullPath(fileName))) {
            // 配置CSV写入器
            StatefulBeanToCsv<User> beanToCsv = new StatefulBeanToCsvBuilder<User>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER) // 不添加引号
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)   // 使用逗号分隔
                    .build();

            // 手动写入标题行（保证顺序与字段注解一致）
            writer.write("索引,用户名,性别,注册时间\n");
            
            // 批量写入数据
            beanToCsv.write(users);
        }
    }

    /**
     * 构建完整文件路径（跨平台兼容）
     * @param fileName 文件名
     * @return 完整路径字符串
     */
    private static String getFullPath(String fileName) {
        return Paths.get(BASE_PATH, fileName).toString();
    }

    public static void main(String[] args) {
        try {
            // 读取数据
            List<User> users = CsvService.readUsers("user.csv");
            // 更新数据
            User user = users.get(0);
            user.setRegisterDate(new Date());
            CsvService.writeUsers("user.csv", Collections.singletonList(user));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}