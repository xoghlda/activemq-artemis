/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.activemq.artemis.osgi;

import org.apache.activemq.artemis.logprocessor.CodeFactory;
import org.apache.activemq.artemis.logprocessor.annotation.Cause;
import org.apache.activemq.artemis.logprocessor.annotation.LogBundle;
import org.apache.activemq.artemis.logprocessor.annotation.LogMessage;

/**
 * Logger code 58
 *
 *
 * so an INFO message would be 581000 to 581999
 */
@LogBundle(projectCode = "AMQ")
public interface ActiveMQOsgiLogger {

   ActiveMQOsgiLogger LOGGER = CodeFactory.getCodeClass(ActiveMQOsgiLogger.class, ActiveMQOsgiLogger.class.getPackage().getName());

   @LogMessage(id = 581000, value = "Broker config {} found. Tracking protocols {}", level = LogMessage.Level.INFO)
   void brokerConfigFound(String name, String protocols);

   @LogMessage(id = 581001, value = "Required protocol {} was added for broker {}. {}", level = LogMessage.Level.INFO)
   void protocolWasAddedForBroker(String protocol, String name, String message);

   @LogMessage(id = 581002, value = "Required protocol {} was removed for broker {}. {}", level = LogMessage.Level.INFO)
   void protocolWasRemovedForBroker(String protocol, String name, String message);

   @LogMessage(id = 582000, value = "Error starting broker: {}", level = LogMessage.Level.WARN)
   void errorStartingBroker(String name, @Cause Exception e);

   @LogMessage(id = 582001, value = "Error stopping broker: {}", level = LogMessage.Level.WARN)
   void errorStoppingBroker(String name, @Cause Exception e);

   @LogMessage(id = 582002, value = "Error getting dataSource provider infos.", level = LogMessage.Level.WARN)
   void errorGettingDataSourceProviderInfo(@Cause Exception e);

}