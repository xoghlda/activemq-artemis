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
package org.apache.activemq.artemis.core.protocol.stomp;

import org.apache.activemq.artemis.logprocessor.CodeFactory;
import org.apache.activemq.artemis.logprocessor.annotation.Cause;
import org.apache.activemq.artemis.logprocessor.annotation.LogBundle;
import org.apache.activemq.artemis.logprocessor.annotation.LogMessage;

/**
 * Logger Code 33
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
 * so an INFO message would be 331000 to 331999
 */

@LogBundle(projectCode = "AMQ")
public interface ActiveMQStompProtocolLogger {

   ActiveMQStompProtocolLogger LOGGER = CodeFactory.getCodeClass(ActiveMQStompProtocolLogger.class, ActiveMQStompProtocolLogger.class.getPackage().getName());

   @LogMessage(id = 332068, value = "connection closed {}", level = LogMessage.Level.WARN)
   void connectionClosed(StompConnection connection);

   @LogMessage(id = 332069, value = "Sent ERROR frame to STOMP client {}: {}", level = LogMessage.Level.WARN)
   void sentErrorToClient(String address, String message);

   @LogMessage(id = 334023, value = "Unable to send frame {}", level = LogMessage.Level.ERROR)
   void errorSendingFrame(StompFrame frame, @Cause Exception e);
}
