/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.activemq.artemis.rest;

import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.logprocessor.CodeFactory;
import org.apache.activemq.artemis.rest.queue.push.xml.XmlLink;
import org.apache.activemq.artemis.logprocessor.annotation.Cause;
import org.apache.activemq.artemis.logprocessor.annotation.LogBundle;
import org.apache.activemq.artemis.logprocessor.annotation.LogMessage;

/**
 * Logger Code 19
 * so an INFO message would be 191000 to 191999
 */
@LogBundle(projectCode = "AMQ")
public interface ActiveMQRestLogger {

   ActiveMQRestLogger LOGGER = CodeFactory.getCodeClass(ActiveMQRestLogger.class, ActiveMQRestLogger.class.getPackage().getName());

   @LogMessage(id = 181000, value = "Loading REST push store from: {}", level = LogMessage.Level.INFO)
   void loadingRestStore(String path);

   @LogMessage(id = 181001, value = "adding REST push registration: {}", level = LogMessage.Level.INFO)
   void addingPushRegistration(String id);

   @LogMessage(id = 181002, value = "Push consumer started for: {}", level = LogMessage.Level.INFO)
   void startingPushConsumer(XmlLink link);

   @LogMessage(id = 182000, value = "shutdown REST consumer because of timeout for: {}", level = LogMessage.Level.WARN)
   void shutdownRestConsumer(String id);

   @LogMessage(id = 182001, value = "shutdown REST subscription because of timeout for: {}", level = LogMessage.Level.WARN)
   void shutdownRestSubscription(String id);

   @LogMessage(id = 182002, value = "Failed to push message to {}", level = LogMessage.Level.WARN)
   void failedToPushMessageToUri(String uri, @Cause Exception e);

   @LogMessage(id = 182003, value = "Failed to build Message from object", level = LogMessage.Level.WARN)
   void failedToBuildMessageFromObject(@Cause Exception e);

   @LogMessage(id = 182004, value = "REST configuration parameter '{}' is deprecated. Use '{}' instead.", level = LogMessage.Level.WARN)
   void deprecatedConfiguration(String oldConfigParameter, String newConfigParameter);

   @LogMessage(id = 184000, value = "Failed to load push store {}, it is probably corrupted", level = LogMessage.Level.ERROR)
   void errorLoadingStore(@Cause Exception e, String name);

   @LogMessage(id = 184001, value = "Error updating store", level = LogMessage.Level.ERROR)
   void errorUpdatingStore(@Cause Exception e);

   @LogMessage(id = 184002, value = "Failed to push message to {} disabling push registration...", level = LogMessage.Level.ERROR)
   void errorPushingMessage(XmlLink link);

   @LogMessage(id = 184003, value = "Error deleting Subscriber queue", level = LogMessage.Level.ERROR)
   void errorDeletingSubscriberQueue(@Cause ActiveMQException e);
}
