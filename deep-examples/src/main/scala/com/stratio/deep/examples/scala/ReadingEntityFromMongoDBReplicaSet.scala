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
package com.stratio.deep.examples.scala

import com.stratio.deep.config.{DeepJobConfigFactory, IMongoDeepJobConfig}
import com.stratio.deep.context.DeepSparkContext
import com.stratio.deep.rdd.mongodb.MongoJavaRDD
import com.stratio.deep.testentity.MessageEntity
import com.stratio.deep.testutils.ContextProperties
import org.apache.log4j.Logger

/**
 * Example class to read an entity from a mongoDB replica set
 */
final object ReadingEntityFromMongoDBReplicaSet {
  def main(args: Array[String]) {
    doMain(args)
  }

  def doMain(args: Array[String]) {
    val job: String = "scala:readingEntityFromMongoDBReplicaSet"
    val host1: String = "localhost:47017"
    val host2: String = "localhost:47018"
    val host3: String = "localhost:47019"
    val database: String = "test"
    val inputCollection: String = "input"
    val replicaSet: String = "s1"
    val readPreference: String = "primaryPreferred"

    val p: ContextProperties = new ContextProperties(args)

    val deepContext: DeepSparkContext = new DeepSparkContext(p.getCluster, job, p.getSparkHome, p.getJars)

    val inputConfigEntity: IMongoDeepJobConfig[MessageEntity] = DeepJobConfigFactory.createMongoDB(classOf[MessageEntity]).host(host1).host(host2).host(host3).database(database).collection(inputCollection).replicaSet(replicaSet).readPreference(readPreference).initialize

    val inputRDDEntity: MongoJavaRDD[MessageEntity] = deepContext.mongoJavaRDD(inputConfigEntity)

    System.out.println("count : " + inputRDDEntity.cache.count)
    deepContext.stop
  }


}