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

package com.stratio.deep.aerospike.config;

import java.util.List;

import org.apache.hadoop.conf.Configuration;

import scala.Tuple2;
import scala.Tuple3;

/**
 * Defines the public methods that each Stratio Deep Aerospike configuration object should implement.
 *
 * @param <T>
 */
public interface IAerospikeDeepJobConfig<T> {

    /**
     * Aerospike's set name.
     *
     * @param set
     * @return
     */
    IAerospikeDeepJobConfig<T> set(String set);

    /**
     * Aerospike's namespace.
     *
     * @param namespace
     * @return
     */
    IAerospikeDeepJobConfig<T> namespace(String namespace);

    /**
     * Returns Aerospike's namespace.
     *
     * @return
     */
    String getNamespace();

    /**
     * Returns Aerospike's set.
     *
     * @return
     */
    String getSet();

    /**
     * Aerospike's bin name.
     *
     * @param bin
     * @return
     */
    IAerospikeDeepJobConfig<T> bin(String bin);

    /**
     * Returns Aerospike's bin.
     *
     * @return
     */
    String getBin();

    /**
     * @return the hadoop configuration object if the concrete implementation has one, null otherwise.
     */
    Configuration getHadoopConfiguration();

    /**
     * Sets the list of available Aerospike ports.
     *
     * @param port
     * @return
     */
    IAerospikeDeepJobConfig<T> port(List<Integer> port);

    /**
     * Sets the operation to perform in Aerospike.
     *
     * @param operation the operation to perform in aerospike.
     * @return
     */
    IAerospikeDeepJobConfig<T> operation(String operation);

    /**
     * Returns Aerospike's configured operation.
     *
     * @return
     */
    String getOperation();

    /**
     * Sets an equality filter for querying Aerospike.
     *
     * @param filter Equality filter value.
     * @return
     */
    IAerospikeDeepJobConfig<T> equalsFilter(Tuple2<String, Object> filter);

    /**
     * Returns the configured Aerospike's equality filter.
     *
     * @return
     */
    Tuple2<String, Object> getEqualsFilter();

    /**
     * Sets a numrange filter for querying Aerospike.
     *
     * @param filter
     * @return
     */
    IAerospikeDeepJobConfig<T> numrangeFilter(Tuple3<String, Object, Object> filter);

    /**
     * Returns the configured Aerospike's numrange filter.
     *
     * @return
     */
    Tuple3<String, Object, Object> getNumrangeFilter();

}
