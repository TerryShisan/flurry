package org.mine.findblacklist;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.springframework.xd.spark.streaming.SparkConfig;
import org.springframework.xd.spark.streaming.java.Processor;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@SuppressWarnings({"serial"})
public class FindBlackList implements Processor<JavaDStream<String>, JavaDStream<String>> {
    List<String> blackList = new ArrayList(Arrays.asList("zhangsan", "lisi"));
    public static final String fileName = "/opt/tmp/blacklist.txt";

    @Override
    /**
     * 黑名单的内容不过滤，其他的过滤
     * 返回值为false时过滤
     */
    public JavaDStream<String> process(JavaDStream<String> input) {
        JavaDStream<String> ret = input.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String s) throws Exception {
                boolean isBlack = false;
                for (int i = 0; i < blackList.size(); i++) {
                    if (s.contains(blackList.get(i))) {
                        isBlack = true;
                        break;
                    }
                }
                return isBlack;
            }
        });

        return ret;
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
