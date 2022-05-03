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
package org.apache.activemq.artemis.logs;

import org.apache.activemq.artemis.logprocessor.CodeFactory;
import org.apache.activemq.artemis.logprocessor.annotation.ArtemisBundle;
import org.apache.activemq.artemis.logprocessor.annotation.LogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logger Code 20
 *
 * each message id must be 6 digits long starting with 20, the 3rd digit donates the level so
 *
 * INF0  1
 * WARN  2
 * DEBUG 3
 * ERROR 4
 * TRACE 5
 * FATAL 6
 *
 * so an INFO message would be 201000 to 201999
 */
@ArtemisBundle(projectCode = "AMQ")
public interface ActiveMQUtilLogger {

   /**
    * The default logger.
    */
   ActiveMQUtilLogger LOGGER = CodeFactory.getMessageLogger(ActiveMQUtilLogger.class);

   @LogMessage(id = 201000, value = "Network is healthy, starting service {0}")
   void startingService(String component);

   @LogMessage(id = 201001, value = "Network is unhealthy, stopping service {0}")
   void stoppingService(String component);

   @LogMessage(id = 202000, value = "Missing privileges to set Thread Context Class Loader on Thread Factory. Using current Thread Context Class Loader")
   void missingPrivsForClassloader();

   @LogMessage(id = 202001, value = "{0} is a loopback address and will be discarded.")
   void addressloopback(String address);

   @LogMessage(id = 202002, value = "Ping Address {0} wasn't reacheable.")
   void addressWasntReacheable(String address);

   @LogMessage(id = 202003, value = "Ping Url {0} wasn't reacheable.")
   void urlWasntReacheable(String url);

   @LogMessage(id = 202004, value = "Error starting component {0} ")
   void errorStartingComponent(Exception e, String component);

   @LogMessage(id = 202005, value = "Error stopping component {0} ")
   void errorStoppingComponent(Exception e, String component);

   @LogMessage(id = 202006, value = "Failed to check Url {0}.")
   void failedToCheckURL(Exception e, String url);

   @LogMessage(id = 202007, value = "Failed to check Address {0}.")
   void failedToCheckAddress(Exception e, String address);

   @LogMessage(id = 202008, value = "Failed to check Address list {0}.")
   void failedToParseAddressList(Exception e, String addressList);

   @LogMessage(id = 202009, value = "Failed to check Url list {0}.")
   void failedToParseUrlList(Exception e, String urlList);

   @LogMessage(id = 202010, value = "Failed to set NIC {0}.")
   void failedToSetNIC(Exception e, String nic);

   @LogMessage(id = 202011, value = "Failed to read from stream {0}.")
   void failedToReadFromStream(String stream);

   @LogMessage(id = 202012, value = "Object cannot be serialized.")
   void failedToSerializeObject(Exception e);

   @LogMessage(id = 202013, value = "Unable to deserialize object.")
   void failedToDeserializeObject(Exception e);

   @LogMessage(id = 202014, value = "Unable to encode byte array into Base64 notation.")
   void failedToEncodeByteArrayToBase64Notation(Exception e);

   @LogMessage(id = 202015, value = "Failed to clean up file {0}")
   void failedToCleanupFile(String file);

   @LogMessage(id = 202016, value = "Could not list files to clean up in {0}")
   void failedListFilesToCleanup(String path);
}
