/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.cloudwatch.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Date;
import java.util.Set;

import com.google.common.annotations.Beta;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

/**
 * Options use to get statistics for the specified metric.
 *
 * @see <a href="http://docs.amazonwebservices.com/AmazonCloudWatch/latest/APIReference/API_GetMetricStatistics.html" />
 *
 * @author Jeremy Whitlock
 */
@Beta
public class GetMetricStatistics {

   private final Set<Dimension> dimensions;
   private final Optional<Date> endTime;
   private final String metricName;
   private final String namespace;
   private final int period;
   private final Optional<Date> startTime;
   private final Set<Statistics> statistics;
   private final Optional<Unit> unit;

   /**
    * Private constructor to enforce using {@link Builder}.
    */
   protected GetMetricStatistics(Set<Dimension> dimensions, Date endTime, String metricName, String namespace, int period,
                               Date startTime, Set<Statistics> statistics, Unit unit) {
      this.dimensions = ImmutableSet.<Dimension>copyOf(checkNotNull(dimensions, "dimensions"));
      this.endTime = Optional.fromNullable(endTime);
      this.metricName = checkNotNull(metricName, "metricName");
      this.namespace = checkNotNull(namespace, "namespace");
      this.period = period;
      this.startTime = Optional.fromNullable(startTime);
      this.statistics = ImmutableSet.<Statistics>copyOf(checkNotNull(statistics, "statistics"));
      this.unit = Optional.fromNullable(unit);
   }

   /**
    * return the set of dimensions for this request
    */
   public Set<Dimension> getDimensions() {
      return dimensions;
   }

   /**
    * return the end time for this request
    */
   public Optional<Date> getEndTime() {
      return endTime;
   }

   /**
    * return the metric name for this request
    */
   public String getMetricName() {
      return metricName;
   }

   /**
    * return the namespace for this request
    */
   public String getNamespace() {
      return namespace;
   }

   /**
    * return the period for this request
    */
   public int getPeriod() {
      return period;
   }

   /**
    * return the start time for this request
    */
   public Optional<Date> getStartTime() {
      return startTime;
   }

   /**
    * return the statistics for this request
    */
   public Set<Statistics> getStatistics() {
      return statistics;
   }

   /**
    * return the unit for this request
    */
   public Optional<Unit> getUnit() {
      return unit;
   }

   /**
    * Returns a new builder. The generated builder is equivalent to the builder
    * created by the {@link Builder} constructor.
    */
   public static Builder builder() {
      return new Builder();
   }

   public static class Builder {

      // this builder is set to be additive on dimension calls, so make this mutable
      private Set<Dimension> dimensions = Sets.newLinkedHashSet();
      private Date endTime;
      private String metricName;
      private String namespace;
      private int period = 60;
      private Date startTime;
      // this builder is set to be additive on dimension calls, so make this mutable
      private Set<Statistics> statistics = Sets.newLinkedHashSet();
      private Unit unit;

      /**
       * Creates a new builder. The returned builder is equivalent to the builder
       * generated by {@link ListMetricsOptions#builder}.
       */
      public Builder() {}

      /**
       * A list of dimensions describing qualities of the metric.
       *
       * @param dimensions the dimensions describing the qualities of the metric
       *
       * @return this {@code Builder} object
       */
      public Builder dimensions(Set<Dimension> dimensions) {
         this.dimensions.addAll(checkNotNull(dimensions, "dimensions"));
         return this;
      }

      /**
       * A dimension describing qualities of the metric.
       *
       * @param dimension the dimension describing the qualities of the metric
       *
       * @return this {@code Builder} object
       */
      public Builder dimension(Dimension dimension) {
         this.dimensions.add(checkNotNull(dimension, "dimension"));
         return this;
      }

      /**
       * The time stamp to use for determining the last datapoint to return. The value specified is exclusive so
       * results will include datapoints up to the time stamp specified.
       *
       * @param endTime the timestamp to use for determining the last datapoint to return
       *
       * @return this {@code Builder} object
       */
      public Builder endTime(Date endTime) {
         this.endTime = endTime;
         return this;
      }

      /**
       * The name of the metric.
       *
       * @param metricName the metric name to filter against
       *
       * @return this {@code Builder} object
       */
      public Builder metricName(String metricName) {
         this.metricName = metricName;
         return this;
      }

      /**
       * The namespace of the metric.
       *
       * @param namespace the namespace to filter against
       *
       * @return this {@code Builder} object
       */
      public Builder namespace(String namespace) {
         this.namespace = namespace;
         return this;
      }

      /**
       * The granularity, in seconds, of the returned datapoints.
       *
       * @param period the granularity, in seconds, of the returned datapoints
       *
       * @return this {@code Builder} object
       */
      public Builder period(int period) {
         this.period = period;
         return this;
      }

      /**
       * The time stamp to use for determining the first datapoint to return. The value specified is inclusive so
       * results include datapoints with the time stamp specified.
       *
       * @param startTime The time stamp to use for determining the first datapoint to return
       *
       * @return this {@code Builder} object
       */
      public Builder startTime(Date startTime) {
         this.startTime = startTime;
         return this;
      }

      /**
       * The metric statistics to return.
       *
       * @param statistics the metric statistics to return.
       *
       * @return this {@code Builder} object
       */
      public Builder statistics(Set<Statistics> statistics) {
         this.statistics.addAll(checkNotNull(statistics, "statistics"));
         return this;
      }

      /**
       * The metric statistic to return.  (Can be called multiple times up to a maximum of 5 times.)
       *
       * @param statistic the metric statistic to return
       *
       * @return this {@code Builder} object
       */
      public Builder statistic(Statistics statistic) {
         this.statistics.add(checkNotNull(statistic, "statistic"));
         return this;
      }

       /**
        * The unit for the metric.
        *
        * @param unit the unit for the metric
        *
        * @return this {@code Builder} object
        */
      public Builder unit(Unit unit) {
         this.unit = unit;
         return this;
      }

      /**
       * Returns a newly-created {@code GetMetricStatisticsOptionsV2} based on the contents of
       * the {@code Builder}.
       */
      public GetMetricStatistics build() {
         return new GetMetricStatistics(dimensions, endTime, metricName, namespace, period, startTime,  statistics,
                                        unit);
      }

   }

}
