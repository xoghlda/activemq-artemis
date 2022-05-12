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
package org.apache.activemq.artemis.core.server.plugin.impl;

import org.apache.activemq.artemis.api.core.QueueConfiguration;
import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.core.config.BridgeConfiguration;
import org.apache.activemq.artemis.core.persistence.OperationContext;
import org.apache.activemq.artemis.core.postoffice.QueueBinding;
import org.apache.activemq.artemis.core.postoffice.RoutingStatus;
import org.apache.activemq.artemis.core.security.SecurityAuth;
import org.apache.activemq.artemis.core.server.MessageReference;
import org.apache.activemq.artemis.core.server.Queue;
import org.apache.activemq.artemis.core.server.RoutingContext;
import org.apache.activemq.artemis.core.server.ServerConsumer;
import org.apache.activemq.artemis.core.server.ServerSession;
import org.apache.activemq.artemis.core.server.cluster.Bridge;
import org.apache.activemq.artemis.core.server.impl.AckReason;
import org.apache.activemq.artemis.core.transaction.Transaction;
import org.apache.activemq.artemis.logprocessor.CodeFactory;
import org.apache.activemq.artemis.logprocessor.annotation.LogBundle;
import org.apache.activemq.artemis.logprocessor.annotation.LogMessage;
import org.apache.activemq.artemis.spi.core.protocol.RemotingConnection;
import org.apache.activemq.artemis.utils.critical.CriticalComponent;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.Logger;

/**
 * Logger Code 84
 */
@LogBundle(projectCode = "AMQ")
public interface LoggingActiveMQServerPluginLogger extends BasicLogger {

   /**
    * The LoggingPlugin logger.
    */
   LoggingActiveMQServerPluginLogger LOGGER = CodeFactory.getCodeClass(LoggingActiveMQServerPluginLogger.class, LoggingActiveMQServerPluginLogger.class.getPackage().getName());

   @LogMessage(id = 841000, value = "created connection: {0}", level = LogMessage.Level.INFO)
   void afterCreateConnection(RemotingConnection connection);

   @LogMessage(id = 841001, value = "destroyed connection: {0}", level = LogMessage.Level.INFO)
   void afterDestroyConnection(RemotingConnection connection);

   @LogMessage(id = 841002, value = "created session name: {0}, session connectionID: {1}", level = LogMessage.Level.INFO)
   void afterCreateSession(String sessionName, Object sesssionConnectionID);

   @LogMessage(id = 841003, value = "closed session with session name: {0}, failed: {1}", level = LogMessage.Level.INFO)
   void afterCloseSession(String sessionName, boolean sesssionConnectionID);

   @LogMessage(id = 841004, value = "added session metadata for session name : {0}, key: {1}, data: {2}", level = LogMessage.Level.INFO)
   void afterSessionMetadataAdded(String sessionName, String key, String data);

   @LogMessage(id = 841005, value = "created consumer with ID: {0}, with session name: {1}", level = LogMessage.Level.INFO)
   void afterCreateConsumer(String consumerID, String sessionID);

   @LogMessage(id = 841006, value = "closed consumer ID: {0}, with  consumer Session: {1}, failed: {2}", level = LogMessage.Level.INFO)
   void afterCloseConsumer(String consumerID, String sessionID, boolean failed);

   @LogMessage(id = 841007, value = "created queue: {0}", level = LogMessage.Level.INFO)
   void afterCreateQueue(Queue queue);

   @LogMessage(id = 841008, value = "destroyed queue: {0}, with args address: {1}, session: {2}, checkConsumerCount: {3}," + " removeConsumers: {4}, autoDeleteAddress: {5}", level = LogMessage.Level.INFO)
   void afterDestroyQueue(Queue queue,
                          SimpleString address,
                          SecurityAuth session,
                          boolean checkConsumerCount,
                          boolean removeConsumers,
                          boolean autoDeleteAddress);

   @LogMessage(id = 841009, value = "sent message with ID: {0}, session name: {1}, session connectionID: {2}, result: {3}", level = LogMessage.Level.INFO)
   void afterSend(String messageID, String sessionName, String sessionConnectionID, RoutingStatus result);

   @LogMessage(id = 841010, value = "routed message with ID: {0}, result: {1}", level = LogMessage.Level.INFO)
   void afterMessageRoute(String messageID, RoutingStatus result);

   @LogMessage(id = 841011, value = "delivered message with message ID: {0}, consumer info UNAVAILABLE", level = LogMessage.Level.INFO)
   void afterDeliverNoConsumer(String messageID);

   @LogMessage(id = 841012, value = "delivered message with message ID: {0}, to consumer on address: {1}, queue: {2}," + " consumer sessionID: {3}, consumerID: {4}", level = LogMessage.Level.INFO)
   void afterDeliver(String messageID,
                     SimpleString queueAddress,
                     SimpleString queueName,
                     String consumerSessionID,
                     long consumerID);

   @LogMessage(id = 841013, value = "expired message: {0}, messageExpiryAddress: {1}", level = LogMessage.Level.INFO)
   void messageExpired(MessageReference message, SimpleString messageExpiryAddress);

   @LogMessage(id = 841014, value = "acknowledged message ID: {0}, messageRef sessionID: {1}, with messageRef consumerID: {2}, messageRef QueueName: {3}," + "  with ackReason: {4}", level = LogMessage.Level.INFO)
   void messageAcknowledged(String messageID, String sessionID, String consumerID, String queueName, AckReason reason);

   @LogMessage(id = 841015, value = "deployed bridge: {0}", level = LogMessage.Level.INFO)
   void afterDeployBridge(Bridge config);

   @LogMessage(id = 841016, value = "criticalFailure called with criticalComponent: {0}", level = LogMessage.Level.INFO)
   void criticalFailure(CriticalComponent components);

   @LogMessage(id = 841017, value = "error sending message with ID: {0}, session name: {1}, session connectionID: {2}," + " exception: {3}", level = LogMessage.Level.INFO)
   void onSendError(String messageID, String sessionName, String sessionConnectionID, Exception e);

   @LogMessage(id = 841018, value = "error routing message with ID: {0}, exception: {1}", level = LogMessage.Level.INFO)
   void onMessageRouteError(String messageID, Exception e);

   //DEBUG messages

   @LogMessage(id = 843000, value = "beforeCreateSession called with name: {0}, username: {1}, minLargeMessageSize: {2}, connection: {3}," + " autoCommitSends: {4}, autoCommitAcks: {5}, preAcknowledge: {6}, xa: {7}, publicAddress: {8}, context: {9}", level = LogMessage.Level.INFO)
   void beforeCreateSession(String name,
                            String username,
                            int minLargeMessageSize,
                            RemotingConnection connection,
                            boolean autoCommitSends,
                            boolean autoCommitAcks,
                            boolean preAcknowledge,
                            boolean xa,
                            String publicAddress,
                            OperationContext context);

   @LogMessage(id = 843001, value = "beforeCloseSession called with session name : {0}, session: {1}, failed: {2}", level = LogMessage.Level.INFO)
   void beforeCloseSession(String sessionName, ServerSession session, boolean failed);

   @LogMessage(id = 843002, value = "beforeSessionMetadataAdded called with session name: {0} , session: {1}, key: {2}," + " data: {3}", level = LogMessage.Level.INFO)
   void beforeSessionMetadataAdded(String sessionName, ServerSession session, String key, String data);

   @LogMessage(id = 843003, value = "added session metadata for session name : {0}, session: {1}, key: {2}, data: {3}", level = LogMessage.Level.INFO)
   void afterSessionMetadataAddedDetails(String sessionName, ServerSession session, String key, String data);

   @LogMessage(id = 843004, value = "beforeCreateConsumer called with ConsumerID: {0}, QueueBinding: {1}, filterString: {2}," + " browseOnly: {3}, supportLargeMessage: {4}", level = LogMessage.Level.INFO)
   void beforeCreateConsumer(String consumerID,
                             QueueBinding queueBinding,
                             SimpleString filterString,
                             boolean browseOnly,
                             boolean supportLargeMessage);

   @LogMessage(id = 843005, value = "beforeCloseConsumer called with consumer: {0}, consumer sessionID: {1}, failed: {2}", level = LogMessage.Level.INFO)
   void beforeCloseConsumer(ServerConsumer consumer, String sessionID, boolean failed);

   @LogMessage(id = 843006, value = "beforeCreateQueue called with queueConfig: {0}", level = LogMessage.Level.INFO)
   void beforeCreateQueue(QueueConfiguration queueConfig);

   @LogMessage(id = 843007, value = "beforeDestroyQueue called with queueName: {0}, session: {1}, checkConsumerCount: {2}," + " removeConsumers: {3}, autoDeleteAddress: {4}", level = LogMessage.Level.INFO)
   void beforeDestroyQueue(SimpleString queueName,
                           SecurityAuth session,
                           boolean checkConsumerCount,
                           boolean removeConsumers,
                           boolean autoDeleteAddress);

   @LogMessage(id = 843008, value = "beforeSend called with message: {0}, tx: {1}, session: {2}, direct: {3}," + " noAutoCreateQueue: {4}", level = LogMessage.Level.INFO)
   void beforeSend(org.apache.activemq.artemis.api.core.Message message,
                   Transaction tx,
                   ServerSession session,
                   boolean direct,
                   boolean noAutoCreateQueue);

   @LogMessage(id = 843009, value = "message ID: {0}, message {1}, session name: {2} with tx: {3}, session: {4}, direct: {5}," + " noAutoCreateQueue: {6}", level = LogMessage.Level.INFO)
   void afterSendDetails(String messageID,
                         org.apache.activemq.artemis.api.core.Message message,
                         String sessionName,
                         Transaction tx,
                         ServerSession session,
                         boolean direct,
                         boolean noAutoCreateQueue);

   @LogMessage(id = 843010, value = "beforeMessageRoute called with message: {0}, context: {1}, direct: {2}, rejectDuplicates: {3}", level = LogMessage.Level.INFO)
   void beforeMessageRoute(org.apache.activemq.artemis.api.core.Message message,
                           RoutingContext context,
                           boolean direct,
                           boolean rejectDuplicates);

   @LogMessage(id = 843011, value = "afterMessageRoute message: {0}, with context: {1}, direct: {2}, rejectDuplicates: {3}", level = LogMessage.Level.INFO)
   void afterMessageRouteDetails(org.apache.activemq.artemis.api.core.Message message,
                                 RoutingContext context,
                                 boolean direct,
                                 boolean rejectDuplicates);

   @LogMessage(id = 843012, value = "beforeDeliver called with consumer: {0}, reference: {1}", level = LogMessage.Level.INFO)
   void beforeDeliver(ServerConsumer consumer, MessageReference reference);

   @LogMessage(id = 843013, value = "delivered message with message ID: {0} to consumer on address: {1}, queue: {2}, consumer sessionID: {3}," + " consumerID: {4}, full message reference: {5}, full consumer: {6}", level = LogMessage.Level.INFO)
   void afterDeliverDetails(String messageID,
                            SimpleString queueAddress,
                            SimpleString queueName,
                            String consumerSessionID,
                            long consumerID,
                            MessageReference reference,
                            ServerConsumer consumer);

   @LogMessage(id = 843014, value = "acknowledged message: {0}, with ackReason: {1}", level = LogMessage.Level.INFO)
   void messageAcknowledgedDetails(MessageReference ref, AckReason reason);

   @LogMessage(id = 843015, value = "beforeDeployBridge called with bridgeConfiguration: {0}", level = LogMessage.Level.INFO)
   void beforeDeployBridge(BridgeConfiguration config);

   @LogMessage(id = 843016, value = "onSendError message ID: {0}, message {1}, session name: {2} with tx: {3}, session: {4}, direct: {5}," + " noAutoCreateQueue: {6}", level = LogMessage.Level.INFO)
   void onSendErrorDetails(String messageID,
                           org.apache.activemq.artemis.api.core.Message message,
                           String sessionName,
                           Transaction tx,
                           ServerSession session,
                           boolean direct,
                           boolean noAutoCreateQueue);

   @LogMessage(id = 843017, value = "onMessageRouteError message: {0}, with context: {1}, direct: {2}, rejectDuplicates: {3}", level = LogMessage.Level.INFO)
   void onMessageRouteErrorDetails(org.apache.activemq.artemis.api.core.Message message,
                                   RoutingContext context,
                                   boolean direct,
                                   boolean rejectDuplicates);

}
