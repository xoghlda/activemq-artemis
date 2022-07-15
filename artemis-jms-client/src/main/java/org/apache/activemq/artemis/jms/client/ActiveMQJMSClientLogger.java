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
package org.apache.activemq.artemis.jms.client;

import org.apache.activemq.artemis.logprocessor.CodeFactory;
import org.apache.activemq.artemis.logprocessor.annotation.Cause;
import org.apache.activemq.artemis.logprocessor.annotation.LogBundle;
import org.apache.activemq.artemis.logprocessor.annotation.LogMessage;

/**
 * Logger Code 13
 *
 * each message id must be 6 digits long starting with 13, the 3rd digit donates the level so
 *
 * INF0  1
 * WARN  2
 * DEBUG 3
 * ERROR 4
 * TRACE 5
 * FATAL 6
 *
 * so an INFO message would be 131000 to 131999
 */
@LogBundle(projectCode = "AMQ")
public interface ActiveMQJMSClientLogger {

   ActiveMQJMSClientLogger LOGGER = CodeFactory.getCodeClass(ActiveMQJMSClientLogger.class, ActiveMQJMSClientLogger.class.getPackage().getName());

   @LogMessage(id = 132000, value = "I'm closing a JMS connection you left open. Please make sure you close all JMS connections explicitly before letting them go out of scope! see stacktrace to find out where it was created", level = LogMessage.Level.WARN)
   void connectionLeftOpen(@Cause Exception e);

   @LogMessage(id = 132001, value = "Unhandled exception thrown from onMessage", level = LogMessage.Level.WARN)
   void onMessageError(@Cause Exception e);

   @LogMessage(id = 134000, value = "Failed to call JMS exception listener", level = LogMessage.Level.ERROR)
   void errorCallingExcListener(@Cause Exception e);

   @LogMessage(id = 134002, value = "Queue Browser failed to create message {}", level = LogMessage.Level.ERROR)
   void errorCreatingMessage(String messageToString, @Cause Throwable e);

   @LogMessage(id = 134003, value = "Message Listener failed to prepare message for receipt, message={}", level = LogMessage.Level.ERROR)
   void errorPreparingMessageForReceipt(String messagetoString, @Cause Throwable e);

   @LogMessage(id = 134004, value = "Message Listener failed to process message", level = LogMessage.Level.ERROR)
   void errorProcessingMessage(@Cause Throwable e);

   @LogMessage(id = 134005, value = "Message Listener failed to recover session", level = LogMessage.Level.ERROR)
   void errorRecoveringSession(@Cause Throwable e);

   @LogMessage(id = 134006, value = "Failed to call Failover listener", level = LogMessage.Level.ERROR)
   void errorCallingFailoverListener(@Cause Exception e);

}
