/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.artemis.client.cdi.logger;

import javax.enterprise.inject.spi.ProcessBean;

import org.apache.activemq.artemis.logprocessor.CodeFactory;
import org.apache.activemq.artemis.logprocessor.annotation.LogBundle;
import org.apache.activemq.artemis.logprocessor.annotation.LogMessage;

/**
 * Logger code 57
 *
 * each message id must be 6 digits long starting with 57, the 3rd digit donates the level so
 *
 * INF0  1
 * WARN  2
 * DEBUG 3
 * ERROR 4
 * TRACE 5
 * FATAL 6
 *
 * so an INFO message would be 571000 to 571999
 */
@LogBundle(projectCode = "AMQ")
public interface ActiveMQCDILogger {

   ActiveMQCDILogger LOGGER = CodeFactory.getCodeClass(ActiveMQCDILogger.class, ActiveMQCDILogger.class.getPackage().getName());

   @LogMessage(id = 571000, value = "Discovered configuration class {}", level = LogMessage.Level.INFO)
   void discoveredConfiguration(ProcessBean<?> pb);

   @LogMessage(id = 571001, value = "Discovered client configuration class {}", level = LogMessage.Level.INFO)
   void discoveredClientConfiguration(ProcessBean<?> pb);

   @LogMessage(id = 573000, value = "Configuration found, not using built in configuration", level = LogMessage.Level.DEBUG)
   void notUsingDefaultConfiguration();

   @LogMessage(id = 573001, value = "Configuration found, not using built in configuration", level = LogMessage.Level.DEBUG)
   void notUsingDefaultClientConfiguration();
}
