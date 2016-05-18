/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mine.spark;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.ServiceActivator;

import java.util.Arrays;
import java.util.List;

@EnableBinding(Processor.class)

public class Transformer {
    private static JavaStreamingContext ssc;
    private static JavaSparkContext jsc;
    private static Logger logger = LoggerFactory.getLogger(Transformer.class);

    /**
     * The name to include in the log message
     */

    public static JavaSparkContext getJsc() {
        return jsc;
    }

    public static void setJsc(JavaSparkContext jsc) {
        Transformer.jsc = jsc;
    }

    public static JavaStreamingContext getSsc() {
        return ssc;
    }

    public static void setSsc(JavaStreamingContext ssc) {
        Transformer.ssc = ssc;
    }


    @ServiceActivator(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public String transform(String payload) {

        if(payload.isEmpty()){
            return null;
        }
        List<String> list = getBlackList();
        JavaRDD<String> words = jsc.parallelize(Arrays.asList(payload.split(" ")));

//        words.foreach((VoidFunction<String>) s -> {
//            logger.warn(s);
//        });

        JavaRDD<String> ret = words.filter((org.apache.spark.api.java.function.Function<String, Boolean>) s -> {
            boolean isBlack = false;
            for (int i = 0; i < list.size(); i++) {
                if (s.contains(list.get(i))) {
                    isBlack = true;
                    break;
                }
            }
            return isBlack;
        });

//        ret.foreach((VoidFunction<String>) s -> {
//            logger.warn(s);
//        });

        if (ret.isEmpty()) {
            logger.warn("Filter: " + payload);
            return null;
        } else {
            logger.warn("Find: " + payload);
            return payload;
        }
    }


    public List<String> getBlackList() {
        List<String> blackList = Arrays.asList("zhangsan");
        return blackList;
    }
}
