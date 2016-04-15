package org.mine.findblacklist;


import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.xd.spark.streaming.SparkConfig;
import org.springframework.xd.spark.streaming.java.Processor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@SuppressWarnings({"serial"})
public class FindBlackList implements Processor<JavaDStream<String>, JavaDStream<String>> {
    List<String> blackList = Arrays.asList("zhangsan", "lisi");
    public static final String fileName = "/opt/tmp/blacklist.txt";

    @Value("${blackName}")
    private String blackName;


    @Value("${listFileName}")
    private String blackFileName;


    @Override
    /**
     * 黑名单的内容不过滤，其他的过滤
     * 返回值为false时过滤
     */
    public JavaDStream<String> process(JavaDStream<String> input) {
        List<String> list = getBlackListByNames();
        JavaDStream<String> ret = input.filter((Function<String, Boolean>) s -> {
            boolean isBlack = false;
            for (int i = 0; i < list.size(); i++) {
                if (s.contains(list.get(i))) {
                    isBlack = true;
                    break;
                }
            }
            return isBlack;
        });

        return ret;
    }

    public List<String> getBlackListByNames() {
        List<String> listAllName = new ArrayList<>();

        /**
         * 解析参数中的黑名单
         */
        List<String> listByName = null;
        if (!blackName.isEmpty()) {
            listByName = Arrays.asList(blackName.split(","));
        }
        if (listByName != null) {
            listAllName.addAll(listByName);
        }

        /**
         * 解析参数中文件的黑名单
         */
        List<String> listByFile = getBlackListByFile();
        if (listByFile != null) {
            listAllName.addAll(listByFile);
        }
        System.out.println("3" + listAllName);
        return listAllName;
    }

    private List<String> getBlackListByFile() {
        File file = new File(blackFileName);
        List<String> list = null;
        byte[] content = new byte[((Long) file.length()).intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(content);
            in.close();
            String con = new String(content);
            list = Arrays.asList(con.replace("\r", "").split("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    @SparkConfig
    public Properties getSparkConfigProperties() {
        Properties props = new Properties();
        // Any specific Spark configuration properties would go here.
        // These properties always get the highest precedence
//        props.setProperty("spark.driver.allowMultipleContexts","true");
        props.setProperty(SPARK_MASTER_URL_PROP, "local[4]");

        return props;
    }
}
