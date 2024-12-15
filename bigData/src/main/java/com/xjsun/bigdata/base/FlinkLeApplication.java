package com.xjsun.bigdata.base;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author mrs
 */
public class FlinkLeApplication {
    public static void main(String[] args) throws Exception {
        String inputFile = "bigData/src/main/resources/word.txt";
        String outPutFile = "bigData/src/main/resources/wordResult.txt";
        ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
        //1. 读取数据
        File input = new File(inputFile);
        if(!input.exists()){
            boolean created = input.createNewFile();
            if(created){
                try (FileWriter writer = new FileWriter(input, false); BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
                    bufferedWriter.write("word count");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        DataSource<String> dataSource = executionEnvironment.readTextFile(inputFile);
        AggregateOperator<Tuple2<String, Integer>> sum = dataSource
                .flatMap(new Tokenizer())
                .groupBy(0)
                .sum(1);
        //5. 写出数据
        File file = new File(outPutFile);
        if(file.exists()){
            file.delete();
        }
        sum.writeAsCsv(outPutFile).setParallelism(1);
        //执行
        executionEnvironment.execute("word count batch process");
    }

    public static final class Tokenizer implements FlatMapFunction<String, Tuple2<String, Integer>> {

        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
            // 将行拆分成单词
            String[] tokens = value.toLowerCase().split(",");

            // 将每个单词转化为一个（单词，1）的元组
            for (String token : tokens) {
                if (token.length() > 0) {
                    out.collect(new Tuple2<>(token, 1));
                }
            }
        }
    }
}
