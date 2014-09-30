/*
 * Copyright 2014, Stratio.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stratio.deep.examples.java.es;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.spark.rdd.RDD;

import com.stratio.deep.commons.config.DeepJobConfig;
import com.stratio.deep.commons.config.ExtractorConfig;
import com.stratio.deep.commons.extractor.utils.ExtractorConstants;
import com.stratio.deep.commons.rdd.IExtractor;
import com.stratio.deep.core.context.DeepSparkContext;
import com.stratio.deep.extractor.ESEntityExtractor;
import com.stratio.deep.testentity.WordCount;
import com.stratio.deep.utils.ContextProperties;

/**
 * Created by dgomez on 31/08/14.
 */
public final class GroupingEntityWithES {

    private static final Logger LOG = Logger.getLogger(GroupingEntityWithES.class);

    private static Long counts;

    private GroupingEntityWithES() {
    }

    public static void main(String[] args) {
        doMain(args);
    }

    public static void doMain(String[] args) {
        String job = "java:groupingEntityWithES";
        String host = "localhost:9200";
        String database = "entity/output";
        String index = "book";
        String type = "test";
        String databaseOutput = "entity/output";

        // Creating the Deep Context where args are Spark Master and Job Name
        ContextProperties p = new ContextProperties(args);
        DeepSparkContext deepContext = new DeepSparkContext(p.getCluster(), job, p.getSparkHome(), p.getJars());

        // Creating a configuration for the Extractor and initialize it
        DeepJobConfig<WordCount> config = new DeepJobConfig<>(new ExtractorConfig(WordCount.class));

        Map<String, Serializable> values = new HashMap<>();

        values.put(ExtractorConstants.DATABASE, database);
        values.put(ExtractorConstants.HOST, host);

        config.setExtractorImplClass((Class<? extends IExtractor<WordCount>>) ESEntityExtractor.class);
        config.setEntityClass(WordCount.class);

        config.setValues(values);

        // Creating the RDD
        RDD<WordCount> rdd = deepContext.createRDD(config);

        counts = rdd.count();
        WordCount[] collection = (WordCount[]) rdd.collect();
        LOG.info("-------------------------   Num of rows: " + counts + " ------------------------------");
        LOG.info("-------------------------   Num of Columns: " + collection.length + " ------------------------------");
        LOG.info("-------------------------   Element Canto: " + collection[0].getWord()
                + " ------------------------------");

        //
        // JavaRDD<String> words =rdd.toJavaRDD().flatMap(new FlatMapFunction<BookEntity, String>() {
        // @Override
        // public Iterable<String> call(BookEntity bookEntity) throws Exception {
        //
        // List<String> words = new ArrayList<>();
        // for (CantoEntity canto : bookEntity.getCantoEntities()){
        // words.addAll(Arrays.asList(canto.getText().split(" ")));
        // }
        // return words;
        // }
        // });
        //
        //
        // JavaPairRDD<String, Integer> wordCount = words.mapToPair(new PairFunction<String, String, Integer>() {
        // @Override
        // public Tuple2<String, Integer> call(String s) throws Exception {
        // return new Tuple2<String, Integer>(s,1);
        // }
        // });
        //
        //
        // JavaPairRDD<String, Integer> wordCountReduced = wordCount.reduceByKey(new Function2<Integer, Integer,
        // Integer>() {
        // @Override
        // public Integer call(Integer integer, Integer integer2) throws Exception {
        // return integer + integer2;
        // }
        // });
        //
        // JavaRDD<WordCount> outputRDD = wordCountReduced.map(new Function<Tuple2<String, Integer>, WordCount>() {
        // @Override
        // public WordCount call(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
        // return new WordCount(stringIntegerTuple2._1(), stringIntegerTuple2._2());
        // }
        // });
        //
        //
        // ExtractorConfig<WordCount> outputConfigEntity = new ExtractorConfig(WordCount.class);
        // outputConfigEntity.putValue(ExtractorConstants.HOST, host).putValue(ExtractorConstants.DATABASE, database);
        // outputConfigEntity.setExtractorImplClass(ESEntityExtractor.class);
        //
        // deepContext.saveRDD(outputRDD.rdd(),outputConfigEntity);
        //

        deepContext.stop();

    }

}
