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
package org.apache.activemq.artemis.protocol.amqp.logger;

import org.apache.activemq.artemis.logprocessor.CodeFactory;
import org.apache.activemq.artemis.logprocessor.annotation.LogBundle;
import org.apache.activemq.artemis.logprocessor.annotation.LogMessage;

/**
 * Logger Code 11
 *
 * each message id must be 6 digits long starting with 33, the 3rd digit donates the level so
 *
 * INF0  1
 * WARN  2
 * DEBUG 3
 * ERROR 4
 * TRACE 5
 * FATAL 6
 *
 * so an INFO message would be 111000 to 111999
 */

@LogBundle(projectCode = "AMQ")
public interface ActiveMQAMQPProtocolLogger {

   ActiveMQAMQPProtocolLogger LOGGER = CodeFactory.getCodeClass(ActiveMQAMQPProtocolLogger.class, ActiveMQAMQPProtocolLogger.class.getPackage().getName());

   @LogMessage(id = 111000, value = "Scheduled task can't be removed from scheduledPool.", level = LogMessage.Level.WARN)
   void cantRemovingScheduledTask();

   @LogMessage(id = 111001, value = "\n*******************************************************************************************************************************" +
      "\nCould not re-establish AMQP Server Connection {} on {} after {} retries" +
      "\n*******************************************************************************************************************************\n", level = LogMessage.Level.WARN)
   void retryConnectionFailed(String name, String hostAndPort, int currentRetry);

   @LogMessage(id = 111002, value = "\n*******************************************************************************************************************************" +
                                 "\nRetrying Server AMQP Connection {} on {} retry {} of {}" +
                                 "\n*******************************************************************************************************************************\n", level = LogMessage.Level.INFO)
   void retryConnection(String name, String hostAndPort, int currentRetry, int maxRetry);

   @LogMessage(id = 111003, value = "\n*******************************************************************************************************************************" +
      "\nConnected on Server AMQP Connection {} on {} after {} retries" +
      "\n*******************************************************************************************************************************\n", level = LogMessage.Level.INFO)
   void successReconnect(String name, String hostAndPort, int currentRetry);

   @LogMessage(id = 111004, value = "AddressFullPolicy clash on an anonymous producer between destinations {}(addressFullPolicy={}) and {}(addressFullPolicy={}). This could lead to semantic inconsistencies on your clients. Notice you could have other instances of this scenario however this message will only be logged once. log.debug output would show all instances of this event.", level = LogMessage.Level.WARN)
   void incompatibleAddressFullMessagePolicy(String oldAddress, String oldPolicy, String newAddress, String newPolicy);
}
