/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.openstack.glance.v1_0.functions.internal;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.openstack.glance.v1_0.options.ListImageOptions.Builder.marker;

import java.beans.ConstructorProperties;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.collect.IterableWithMarker;
import org.jclouds.collect.internal.CallerArg0ToPagedIterable;
import org.jclouds.http.functions.ParseJson;
import org.jclouds.json.Json;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.glance.v1_0.domain.ImageDetails;
import org.jclouds.openstack.glance.v1_0.features.ImageApi;
import org.jclouds.openstack.glance.v1_0.functions.internal.ParseImageDetails.Images;
import org.jclouds.openstack.keystone.v2_0.domain.PaginatedCollection;
import org.jclouds.openstack.v2_0.domain.Link;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.inject.TypeLiteral;

/**
 * boiler plate until we determine a better way
 * 
 * @author Adrian Cole
 */
@Beta
@Singleton
public class ParseImageDetails extends ParseJson<Images<? extends ImageDetails>> {
   static class Images<T extends ImageDetails> extends PaginatedCollection<T> {

      @ConstructorProperties({ "images", "images_links" })
      protected Images(Iterable<T> images, Iterable<Link> images_links) {
         super(images, images_links);
      }

   }

   @Inject
   public ParseImageDetails(Json json) {
      super(json, new TypeLiteral<Images<? extends ImageDetails>>() {
      });
   }

   public static class ToPagedIterable extends CallerArg0ToPagedIterable<ImageDetails, ToPagedIterable> {

      private final GlanceApi api;

      @Inject
      protected ToPagedIterable(GlanceApi api) {
         this.api = checkNotNull(api, "api");
      }

      @Override
      protected Function<Object, IterableWithMarker<ImageDetails>> markerToNextForCallingArg0(final String zone) {
         final ImageApi imageApi = api.getImageApiForZone(zone);
         return new Function<Object, IterableWithMarker<ImageDetails>>() {

            @SuppressWarnings("unchecked")
            @Override
            public IterableWithMarker<ImageDetails> apply(Object input) {
               return IterableWithMarker.class.cast(imageApi.listInDetail(marker(input.toString())));
            }

            @Override
            public String toString() {
               return "listInDetail()";
            }
         };
      }

   }

}
