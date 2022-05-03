/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
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
import org.apache.activemq.artemis.logprocessor.annotation.GetLogger;
import org.apache.activemq.artemis.logprocessor.annotation.LogMessage;
import org.slf4j.Logger;

import javax.management.ObjectName;
import javax.security.auth.Subject;
import java.security.AccessController;
import java.security.Principal;
import java.util.Arrays;
import java.util.Set;

/**
 * Logger Code 60
 */
@ArtemisBundle(projectCode = "AMQ")
public interface AuditLogger {

   AuditLogger BASE_LOGGER = CodeFactory.getCodeClass(AuditLogger.class, "org.apache.activemq.audit.base");
   AuditLogger RESOURCE_LOGGER = CodeFactory.getCodeClass(AuditLogger.class, "org.apache.activemq.audit.resource");
   AuditLogger MESSAGE_LOGGER = CodeFactory.getCodeClass(AuditLogger.class, "org.apache.activemq.audit.message");

   ThreadLocal<String> remoteAddress = new ThreadLocal<>();

   ThreadLocal<Subject> currentCaller = new ThreadLocal<>();

   static boolean isAnyLoggingEnabled() {
      return isBaseLoggingEnabled() || isMessageLoggingEnabled() || isResourceLoggingEnabled();
   }

   @GetLogger
   Logger getLogger();

   static boolean isBaseLoggingEnabled() {
      return BASE_LOGGER.getLogger().isInfoEnabled();
   }

   static boolean isResourceLoggingEnabled() {
      return RESOURCE_LOGGER.getLogger().isInfoEnabled();
   }

   static boolean isMessageLoggingEnabled() {
      return MESSAGE_LOGGER.getLogger().isInfoEnabled();
   }

   /**
    * @return a String representing the "caller" in the format "user(role)@remoteAddress" using ThreadLocal values (if set)
    */
   static String getCaller() {
      Subject subject = Subject.getSubject(AccessController.getContext());
      if (subject == null) {
         subject = currentCaller.get();
      }
      return getCaller(subject, null);
   }

   /**
    * @param  subject       the Subject to be used instead of the corresponding ThreadLocal Subject
    * @param  remoteAddress the remote address to use; if null use the corresponding ThreadLocal remote address (if set)
    * @return               a String representing the "caller" in the format "user(role)@remoteAddress"
    */
   static String getCaller(Subject subject, String remoteAddress) {
      String user = "anonymous";
      String roles = "";
      String url = remoteAddress == null ? (AuditLogger.remoteAddress.get() == null ? "@unknown" : AuditLogger.remoteAddress.get()) : formatRemoteAddress(remoteAddress);
      if (subject != null) {
         Set<Principal> principals = subject.getPrincipals();
         for (Principal principal : principals) {
            if (principal.getClass().getName().endsWith("UserPrincipal")) {
               user = principal.getName();
            } else if (principal.getClass().getName().endsWith("RolePrincipal")) {
               roles = "(" + principal.getName() + ")";
            }
         }
      }
      return user + roles + url;
   }

   static void setCurrentCaller(Subject caller) {
      currentCaller.set(caller);
   }

   static void setRemoteAddress(String remoteAddress) {
      AuditLogger.remoteAddress.set(formatRemoteAddress(remoteAddress));
   }

   static String formatRemoteAddress(String remoteAddress) {
      String actualAddress;
      if (remoteAddress.startsWith("/")) {
         actualAddress = "@" + remoteAddress.substring(1);
      } else {
         actualAddress = "@" + remoteAddress;
      }
      return actualAddress;
   }

   static String getRemoteAddress() {
      return remoteAddress.get();
   }

   static String arrayToString(Object value) {
      if (value == null) return "";

      final String prefix = "with parameters: ";

      if (value instanceof long[]) {
         return prefix + Arrays.toString((long[])value);
      } else if (value instanceof int[]) {
         return prefix + Arrays.toString((int[])value);
      } else if (value instanceof char[]) {
         return prefix + Arrays.toString((char[])value);
      } else if (value instanceof byte[]) {
         return prefix + Arrays.toString((byte[])value);
      } else if (value instanceof float[]) {
         return prefix + Arrays.toString((float[])value);
      } else if (value instanceof short[]) {
         return prefix + Arrays.toString((short[])value);
      } else if (value instanceof double[]) {
         return prefix + Arrays.toString((double[])value);
      } else if (value instanceof boolean[]) {
         return prefix + Arrays.toString((boolean[])value);
      } else if (value instanceof Object[]) {
         return prefix + Arrays.toString((Object[])value);
      } else {
         return prefix + value.toString();
      }
   }

   static void getRoutingTypes(Object source) {
      BASE_LOGGER.getRoutingTypes(getCaller(), source);
   }

  
   @LogMessage(id = 601000, value = "User {0} is getting routing type property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getRoutingTypes(String user, Object source, Object... args);

   static void getRoutingTypesAsJSON(Object source) {
      BASE_LOGGER.getRoutingTypesAsJSON(getCaller(), source);
   }

  
   @LogMessage(id = 601001, value = "User {0} is getting routing type property as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getRoutingTypesAsJSON(String user, Object source, Object... args);

   static void getQueueNames(Object source, Object... args) {
      BASE_LOGGER.getQueueNames(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601002, value = "User {0} is getting queue names on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getQueueNames(String user, Object source, Object... args);

   static void getBindingNames(Object source) {
      BASE_LOGGER.getBindingNames(getCaller(), source);
   }

  
   @LogMessage(id = 601003, value = "User {0} is getting binding names on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getBindingNames(String user, Object source, Object... args);

   static void getRoles(Object source, Object... args) {
      BASE_LOGGER.getRoles(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601004, value = "User {0} is getting roles on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getRoles(String user, Object source, Object... args);

   static void getRolesAsJSON(Object source, Object... args) {
      BASE_LOGGER.getRolesAsJSON(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601005, value = "User {0} is getting roles as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getRolesAsJSON(String user, Object source, Object... args);

   static void getNumberOfBytesPerPage(Object source) {
      BASE_LOGGER.getNumberOfBytesPerPage(getCaller(), source);
   }

  
   @LogMessage(id = 601006, value = "User {0} is getting number of bytes per page on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getNumberOfBytesPerPage(String user, Object source, Object... args);

   static void getAddressSize(Object source) {
      BASE_LOGGER.getAddressSize(getCaller(), source);
   }

  
   @LogMessage(id = 601007, value = "User {0} is getting address size on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getAddressSize(String user, Object source, Object... args);

   static void getNumberOfMessages(Object source) {
      BASE_LOGGER.getNumberOfMessages(getCaller(), source);
   }

  
   @LogMessage(id = 601008, value = "User {0} is getting number of messages on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getNumberOfMessages(String user, Object source, Object... args);

   static void isPaging(Object source) {
      BASE_LOGGER.isPaging(getCaller(), source);
   }

  
   @LogMessage(id = 601009, value = "User {0} is getting isPaging on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isPaging(String user, Object source, Object... args);

   static void getNumberOfPages(Object source) {
      BASE_LOGGER.getNumberOfPages(getCaller(), source);
   }

  
   @LogMessage(id = 601010, value = "User {0} is getting number of pages on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getNumberOfPages(String user, Object source, Object... args);

   static void getRoutedMessageCount(Object source) {
      BASE_LOGGER.getRoutedMessageCount(getCaller(), source);
   }

  
   @LogMessage(id = 601011, value = "User {0} is getting routed message count on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getRoutedMessageCount(String user, Object source, Object... args);

   static void getUnRoutedMessageCount(Object source) {
      BASE_LOGGER.getUnRoutedMessageCount(getCaller(), source);
   }

  
   @LogMessage(id = 601012, value = "User {0} is getting unrouted message count on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getUnRoutedMessageCount(String user, Object source, Object... args);

   static void sendMessageThroughManagement(Object source, Object... args) {
      BASE_LOGGER.sendMessageThroughManagement(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601013, value = "User {0} is sending a message on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void sendMessageThroughManagement(String user, Object source, Object... args);

   static void getName(Object source) {
      BASE_LOGGER.getName(getCaller(), source);
   }

  
   @LogMessage(id = 601014, value = "User {0} is getting name on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getName(String user, Object source, Object... args);

   static void getAddress(Object source) {
      BASE_LOGGER.getAddress(getCaller(), source);
   }

  
   @LogMessage(id = 601015, value = "User {0} is getting address on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getAddress(String user, Object source, Object... args);

   static void getFilter(Object source) {
      BASE_LOGGER.getFilter(getCaller(), source);
   }

  
   @LogMessage(id = 601016, value = "User {0} is getting filter on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getFilter(String user, Object source, Object... args);

   static void isDurable(Object source) {
      BASE_LOGGER.isDurable(getCaller(), source);
   }

  
   @LogMessage(id = 601017, value = "User {0} is getting durable property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isDurable(String user, Object source, Object... args);

   static void getMessageCount(Object source) {
      BASE_LOGGER.getMessageCount(getCaller(), source);
   }

  
   @LogMessage(id = 601018, value = "User {0} is getting message count on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getMessageCount(String user, Object source, Object... args);

   static void getMBeanInfo(Object source) {
      BASE_LOGGER.getMBeanInfo(getCaller(), source);
   }

  
   @LogMessage(id = 601019, value = "User {0} is getting mbean info on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getMBeanInfo(String user, Object source, Object... args);

   static void getFactoryClassName(Object source) {
      BASE_LOGGER.getFactoryClassName(getCaller(), source);
   }

  
   @LogMessage(id = 601020, value = "User {0} is getting factory class name on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getFactoryClassName(String user, Object source, Object... args);

   static void getParameters(Object source) {
      BASE_LOGGER.getParameters(getCaller(), source);
   }

  
   @LogMessage(id = 601021, value = "User {0} is getting parameters on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getParameters(String user, Object source, Object... args);

   static void reload(Object source) {
      BASE_LOGGER.reload(getCaller(), source);
   }

  
   @LogMessage(id = 601022, value = "User {0} is doing reload on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void reload(String user, Object source, Object... args);

   static void isStarted(Object source) {
      BASE_LOGGER.isStarted(getCaller(), source);
   }

  
   @LogMessage(id = 601023, value = "User {0} is querying isStarted on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isStarted(String user, Object source, Object... args);

   static void startAcceptor(Object source) {
      BASE_LOGGER.startAcceptor(getCaller(), source);
   }

  
   @LogMessage(id = 601024, value = "User {0} is starting an acceptor on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void startAcceptor(String user, Object source, Object... args);

   static void stopAcceptor(Object source) {
      BASE_LOGGER.stopAcceptor(getCaller(), source);
   }

  
   @LogMessage(id = 601025, value = "User {0} is stopping an acceptor on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void stopAcceptor(String user, Object source, Object... args);

   static void getVersion(Object source) {
      BASE_LOGGER.getVersion(getCaller(), source);
   }

  
   @LogMessage(id = 601026, value = "User {0} is getting version on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getVersion(String user, Object source, Object... args);

   static void isBackup(Object source) {
      BASE_LOGGER.isBackup(getCaller(), source);
   }

  
   @LogMessage(id = 601027, value = "User {0} is querying isBackup on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isBackup(String user, Object source, Object... args);

   static void isSharedStore(Object source) {
      BASE_LOGGER.isSharedStore(getCaller(), source);
   }

  
   @LogMessage(id = 601028, value = "User {0} is querying isSharedStore on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isSharedStore(String user, Object source, Object... args);

   static void getBindingsDirectory(Object source) {
      BASE_LOGGER.getBindingsDirectory(getCaller(), source);
   }

  
   @LogMessage(id = 601029, value = "User {0} is getting bindings directory on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getBindingsDirectory(String user, Object source, Object... args);

   static void getIncomingInterceptorClassNames(Object source) {
      BASE_LOGGER.getIncomingInterceptorClassNames(getCaller(), source);
   }

  
   @LogMessage(id = 601030, value = "User {0} is getting incoming interceptor class names on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getIncomingInterceptorClassNames(String user, Object source, Object... args);

   static void getOutgoingInterceptorClassNames(Object source) {
      BASE_LOGGER.getOutgoingInterceptorClassNames(getCaller(), source);
   }

  
   @LogMessage(id = 601031, value = "User {0} is getting outgoing interceptor class names on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getOutgoingInterceptorClassNames(String user, Object source, Object... args);

   static void getJournalBufferSize(Object source) {
      BASE_LOGGER.getJournalBufferSize(getCaller(), source);
   }

  
   @LogMessage(id = 601032, value = "User {0} is getting journal buffer size on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getJournalBufferSize(String user, Object source, Object... args);

   static void getJournalBufferTimeout(Object source) {
      BASE_LOGGER.getJournalBufferTimeout(getCaller(), source);
   }

  
   @LogMessage(id = 601033, value = "User {0} is getting journal buffer timeout on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getJournalBufferTimeout(String user, Object source, Object... args);

   static void setFailoverOnServerShutdown(Object source, Object... args) {
      BASE_LOGGER.setFailoverOnServerShutdown(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601034, value = "User {0} is setting failover on server shutdown on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void setFailoverOnServerShutdown(String user, Object source, Object... args);

   static void isFailoverOnServerShutdown(Object source) {
      BASE_LOGGER.isFailoverOnServerShutdown(getCaller(), source);
   }

  
   @LogMessage(id = 601035, value = "User {0} is querying is-failover-on-server-shutdown on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isFailoverOnServerShutdown(String user, Object source, Object... args);

   static void getJournalMaxIO(Object source) {
      BASE_LOGGER.getJournalMaxIO(getCaller(), source);
   }

  
   @LogMessage(id = 601036, value = "User {0} is getting journal's max io on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getJournalMaxIO(String user, Object source, Object... args);

   static void getJournalDirectory(Object source) {
      BASE_LOGGER.getJournalDirectory(getCaller(), source);
   }

  
   @LogMessage(id = 601037, value = "User {0} is getting journal directory on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getJournalDirectory(String user, Object source, Object... args);

   static void getJournalFileSize(Object source) {
      BASE_LOGGER.getJournalFileSize(getCaller(), source);
   }

  
   @LogMessage(id = 601038, value = "User {0} is getting journal file size on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getJournalFileSize(String user, Object source, Object... args);

   static void getJournalMinFiles(Object source) {
      BASE_LOGGER.getJournalMinFiles(getCaller(), source);
   }

  
   @LogMessage(id = 601039, value = "User {0} is getting journal min files on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getJournalMinFiles(String user, Object source, Object... args);

   static void getJournalCompactMinFiles(Object source) {
      BASE_LOGGER.getJournalCompactMinFiles(getCaller(), source);
   }

  
   @LogMessage(id = 601040, value = "User {0} is getting journal compact min files on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getJournalCompactMinFiles(String user, Object source, Object... args);

   static void getJournalCompactPercentage(Object source) {
      BASE_LOGGER.getJournalCompactPercentage(getCaller(), source);
   }

  
   @LogMessage(id = 601041, value = "User {0} is getting journal compact percentage on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getJournalCompactPercentage(String user, Object source, Object... args);

   static void isPersistenceEnabled(Object source) {
      BASE_LOGGER.isPersistenceEnabled(getCaller(), source);
   }

  
   @LogMessage(id = 601042, value = "User {0} is querying persistence enabled on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isPersistenceEnabled(String user, Object source, Object... args);

   static void getJournalType(Object source) {
      BASE_LOGGER.getJournalType(getCaller(), source);
   }

  
   @LogMessage(id = 601043, value = "User {0} is getting journal type on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getJournalType(String user, Object source, Object... args);

   static void getPagingDirectory(Object source) {
      BASE_LOGGER.getPagingDirectory(getCaller(), source);
   }

  
   @LogMessage(id = 601044, value = "User {0} is getting paging directory on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getPagingDirectory(String user, Object source, Object... args);

   static void getScheduledThreadPoolMaxSize(Object source) {
      BASE_LOGGER.getScheduledThreadPoolMaxSize(getCaller(), source);
   }

  
   @LogMessage(id = 601045, value = "User {0} is getting scheduled threadpool max size on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getScheduledThreadPoolMaxSize(String user, Object source, Object... args);

   static void getThreadPoolMaxSize(Object source) {
      BASE_LOGGER.getThreadPoolMaxSize(getCaller(), source);
   }

  
   @LogMessage(id = 601046, value = "User {0} is getting threadpool max size on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getThreadPoolMaxSize(String user, Object source, Object... args);

   static void getSecurityInvalidationInterval(Object source) {
      BASE_LOGGER.getSecurityInvalidationInterval(getCaller(), source);
   }

  
   @LogMessage(id = 601047, value = "User {0} is getting security invalidation interval on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getSecurityInvalidationInterval(String user, Object source, Object... args);

   static void isClustered(Object source) {
      BASE_LOGGER.isClustered(getCaller(), source);
   }

  
   @LogMessage(id = 601048, value = "User {0} is querying is-clustered on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isClustered(String user, Object source, Object... args);

   static void isCreateBindingsDir(Object source) {
      BASE_LOGGER.isCreateBindingsDir(getCaller(), source);
   }

  
   @LogMessage(id = 601049, value = "User {0} is querying is-create-bindings-dir on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isCreateBindingsDir(String user, Object source, Object... args);

   static void isCreateJournalDir(Object source) {
      BASE_LOGGER.isCreateJournalDir(getCaller(), source);
   }

  
   @LogMessage(id = 601050, value = "User {0} is querying is-create-journal-dir on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isCreateJournalDir(String user, Object source, Object... args);

   static void isJournalSyncNonTransactional(Object source) {
      BASE_LOGGER.isJournalSyncNonTransactional(getCaller(), source);
   }

  
   @LogMessage(id = 601051, value = "User {0} is querying is-journal-sync-non-transactional on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isJournalSyncNonTransactional(String user, Object source, Object... args);

   static void isJournalSyncTransactional(Object source) {
      BASE_LOGGER.isJournalSyncTransactional(getCaller(), source);
   }

  
   @LogMessage(id = 601052, value = "User {0} is querying is-journal-sync-transactional on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isJournalSyncTransactional(String user, Object source, Object... args);

   static void isSecurityEnabled(Object source) {
      BASE_LOGGER.isSecurityEnabled(getCaller(), source);
   }

  
   @LogMessage(id = 601053, value = "User {0} is querying is-security-enabled on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isSecurityEnabled(String user, Object source, Object... args);

   static void isAsyncConnectionExecutionEnabled(Object source) {
      BASE_LOGGER.isAsyncConnectionExecutionEnabled(getCaller(), source);
   }

  
   @LogMessage(id = 601054, value = "User {0} is query is-async-connection-execution-enabled on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isAsyncConnectionExecutionEnabled(String user, Object source, Object... args);

   static void getDiskScanPeriod(Object source) {
      BASE_LOGGER.getDiskScanPeriod(getCaller(), source);
   }

  
   @LogMessage(id = 601055, value = "User {0} is getting disk scan period on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getDiskScanPeriod(String user, Object source, Object... args);

   static void getMaxDiskUsage(Object source) {
      BASE_LOGGER.getMaxDiskUsage(getCaller(), source);
   }

  
   @LogMessage(id = 601056, value = "User {0} is getting max disk usage on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getMaxDiskUsage(String user, Object source, Object... args);

   static void getGlobalMaxSize(Object source) {
      BASE_LOGGER.getGlobalMaxSize(getCaller(), source);
   }

  
   @LogMessage(id = 601057, value = "User {0} is getting global max size on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getGlobalMaxSize(String user, Object source, Object... args);

   static void getAddressMemoryUsage(Object source) {
      BASE_LOGGER.getAddressMemoryUsage(getCaller(), source);
   }

  
   @LogMessage(id = 601058, value = "User {0} is getting address memory usage on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getAddressMemoryUsage(String user, Object source, Object... args);

   static void getAddressMemoryUsagePercentage(Object source) {
      BASE_LOGGER.getAddressMemoryUsagePercentage(getCaller(), source);
   }

  
   @LogMessage(id = 601059, value = "User {0} is getting address memory usage percentage on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getAddressMemoryUsagePercentage(String user, Object source, Object... args);

   static void freezeReplication(Object source) {
      BASE_LOGGER.freezeReplication(getCaller(), source);
   }

  
   @LogMessage(id = 601060, value = "User {0} is freezing replication on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void freezeReplication(String user, Object source, Object... args);

   static void createAddress(Object source, Object... args) {
      BASE_LOGGER.createAddress(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601061, value = "User {0} is creating an address on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void createAddress(String user, Object source, Object... args);

   static void updateAddress(Object source, Object... args) {
      BASE_LOGGER.updateAddress(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601062, value = "User {0} is updating an address on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void updateAddress(String user, Object source, Object... args);

   static void deleteAddress(Object source, Object... args) {
      BASE_LOGGER.deleteAddress(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601063, value = "User {0} is deleting an address on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void deleteAddress(String user, Object source, Object... args);

   static void deployQueue(Object source, Object... args) {
      BASE_LOGGER.deployQueue(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601064, value = "User {0} is creating a queue on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void deployQueue(String user, Object source, Object... args);

   static void createQueue(Object source, Subject user, String remoteAddress, Object... args) {
      RESOURCE_LOGGER.createQueue(getCaller(user, remoteAddress), source, arrayToString(args));
   }

  
   @LogMessage(id = 601065, value = "User {0} is creating a queue on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void createQueue(String user, Object source, Object... args);

   static void updateQueue(Object source, Object... args) {
      BASE_LOGGER.updateQueue(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601066, value = "User {0} is updating a queue on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void updateQueue(String user, Object source, Object... args);

   static void getClusterConnectionNames(Object source) {
      BASE_LOGGER.getClusterConnectionNames(getCaller(), source);
   }

  
   @LogMessage(id = 601067, value = "User {0} is getting cluster connection names on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getClusterConnectionNames(String user, Object source, Object... args);

   static void getUptime(Object source) {
      BASE_LOGGER.getUptime(getCaller(), source);
   }

  
   @LogMessage(id = 601068, value = "User {0} is getting uptime on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getUptime(String user, Object source, Object... args);

   static void getUptimeMillis(Object source) {
      BASE_LOGGER.getUptimeMillis(getCaller(), source);
   }

  
   @LogMessage(id = 601069, value = "User {0} is getting uptime in milliseconds on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getUptimeMillis(String user, Object source, Object... args);

   static void isReplicaSync(Object source) {
      BASE_LOGGER.isReplicaSync(getCaller(), source);
   }

  
   @LogMessage(id = 601070, value = "User {0} is querying is-replica-sync on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isReplicaSync(String user, Object source, Object... args);

   static void getAddressNames(Object source) {
      BASE_LOGGER.getAddressNames(getCaller(), source);
   }

  
   @LogMessage(id = 601071, value = "User {0} is getting address names on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getAddressNames(String user, Object source, Object... args);

   static void destroyQueue(Object source, Subject user, String remoteAddress, Object... args) {
      BASE_LOGGER.destroyQueue(getCaller(user, remoteAddress), source, arrayToString(args));
   }

  
   @LogMessage(id = 601072, value = "User {0} is deleting a queue on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void destroyQueue(String user, Object source, Object... args);

   static void getAddressInfo(Object source, Object... args) {
      BASE_LOGGER.getAddressInfo(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601073, value = "User {0} is getting address info on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getAddressInfo(String user, Object source, Object... args);

   static void listBindingsForAddress(Object source, Object... args) {
      BASE_LOGGER.listBindingsForAddress(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601074, value = "User {0} is listing bindings for address on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listBindingsForAddress(String user, Object source, Object... args);

   static void listAddresses(Object source, Object... args) {
      BASE_LOGGER.listAddresses(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601075, value = "User {0} is listing addresses on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listAddresses(String user, Object source, Object... args);

   static void getConnectionCount(Object source, Object... args) {
      BASE_LOGGER.getConnectionCount(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601076, value = "User {0} is getting connection count on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getConnectionCount(String user, Object source, Object... args);

   static void getTotalConnectionCount(Object source) {
      BASE_LOGGER.getTotalConnectionCount(getCaller(), source);
   }

  
   @LogMessage(id = 601077, value = "User {0} is getting total connection count on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getTotalConnectionCount(String user, Object source, Object... args);

   static void getTotalMessageCount(Object source) {
      BASE_LOGGER.getTotalMessageCount(getCaller(), source);
   }

  
   @LogMessage(id = 601078, value = "User {0} is getting total message count on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getTotalMessageCount(String user, Object source, Object... args);

   static void getTotalMessagesAdded(Object source) {
      BASE_LOGGER.getTotalMessagesAdded(getCaller(), source);
   }

  
   @LogMessage(id = 601079, value = "User {0} is getting total messages added on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getTotalMessagesAdded(String user, Object source, Object... args);

   static void getTotalMessagesAcknowledged(Object source) {
      BASE_LOGGER.getTotalMessagesAcknowledged(getCaller(), source);
   }

  
   @LogMessage(id = 601080, value = "User {0} is getting total messages acknowledged on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getTotalMessagesAcknowledged(String user, Object source, Object... args);

   static void getTotalConsumerCount(Object source) {
      BASE_LOGGER.getTotalConsumerCount(getCaller(), source);
   }

  
   @LogMessage(id = 601081, value = "User {0} is getting total consumer count on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getTotalConsumerCount(String user, Object source, Object... args);

   static void enableMessageCounters(Object source) {
      BASE_LOGGER.enableMessageCounters(getCaller(), source);
   }

  
   @LogMessage(id = 601082, value = "User {0} is enabling message counters on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void enableMessageCounters(String user, Object source, Object... args);

   static void disableMessageCounters(Object source) {
      BASE_LOGGER.disableMessageCounters(getCaller(), source);
   }

  
   @LogMessage(id = 601083, value = "User {0} is disabling message counters on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void disableMessageCounters(String user, Object source, Object... args);

   static void resetAllMessageCounters(Object source) {
      BASE_LOGGER.resetAllMessageCounters(getCaller(), source);
   }

  
   @LogMessage(id = 601084, value = "User {0} is resetting all message counters on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void resetAllMessageCounters(String user, Object source, Object... args);

   static void resetAllMessageCounterHistories(Object source) {
      BASE_LOGGER.resetAllMessageCounterHistories(getCaller(), source);
   }

  
   @LogMessage(id = 601085, value = "User {0} is resetting all message counter histories on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void resetAllMessageCounterHistories(String user, Object source, Object... args);

   static void isMessageCounterEnabled(Object source) {
      BASE_LOGGER.isMessageCounterEnabled(getCaller(), source);
   }

  
   @LogMessage(id = 601086, value = "User {0} is querying is-message-counter-enabled on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isMessageCounterEnabled(String user, Object source, Object... args);

   static void getMessageCounterSamplePeriod(Object source) {
      BASE_LOGGER.getMessageCounterSamplePeriod(getCaller(), source);
   }

  
   @LogMessage(id = 601087, value = "User {0} is getting message counter sample period on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getMessageCounterSamplePeriod(String user, Object source, Object... args);

   static void setMessageCounterSamplePeriod(Object source, Object... args) {
      BASE_LOGGER.setMessageCounterSamplePeriod(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601088, value = "User {0} is setting message counter sample period on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void setMessageCounterSamplePeriod(String user, Object source, Object... args);

   static void getMessageCounterMaxDayCount(Object source) {
      BASE_LOGGER.getMessageCounterMaxDayCount(getCaller(), source);
   }

  
   @LogMessage(id = 601089, value = "User {0} is getting message counter max day count on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getMessageCounterMaxDayCount(String user, Object source, Object... args);

   static void setMessageCounterMaxDayCount(Object source, Object... args) {
      BASE_LOGGER.setMessageCounterMaxDayCount(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601090, value = "User {0} is setting message counter max day count on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void setMessageCounterMaxDayCount(String user, Object source, Object... args);

   static void listPreparedTransactions(Object source) {
      BASE_LOGGER.listPreparedTransactions(getCaller(), source);
   }

  
   @LogMessage(id = 601091, value = "User {0} is listing prepared transactions on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listPreparedTransactions(String user, Object source, Object... args);

   static void listPreparedTransactionDetailsAsJSON(Object source) {
      BASE_LOGGER.listPreparedTransactionDetailsAsJSON(getCaller(), source);
   }

  
   @LogMessage(id = 601092, value = "User {0} is listing prepared transaction details as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listPreparedTransactionDetailsAsJSON(String user, Object source, Object... args);

   static void listPreparedTransactionDetailsAsHTML(Object source, Object... args) {
      BASE_LOGGER.listPreparedTransactionDetailsAsHTML(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601093, value = "User {0} is listing prepared transaction details as HTML on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listPreparedTransactionDetailsAsHTML(String user, Object source, Object... args);

   static void listHeuristicCommittedTransactions(Object source) {
      BASE_LOGGER.listHeuristicCommittedTransactions(getCaller(), source);
   }

  
   @LogMessage(id = 601094, value = "User {0} is listing heuristic committed transactions on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listHeuristicCommittedTransactions(String user, Object source, Object... args);

   static void listHeuristicRolledBackTransactions(Object source) {
      BASE_LOGGER.listHeuristicRolledBackTransactions(getCaller(), source);
   }

  
   @LogMessage(id = 601095, value = "User {0} is listing heuristic rolled back transactions on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listHeuristicRolledBackTransactions(String user, Object source, Object... args);

   static void commitPreparedTransaction(Object source, Object... args) {
      BASE_LOGGER.commitPreparedTransaction(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601096, value = "User {0} is commiting prepared transaction on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void commitPreparedTransaction(String user, Object source, Object... args);

   static void rollbackPreparedTransaction(Object source, Object... args) {
      BASE_LOGGER.rollbackPreparedTransaction(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601097, value = "User {0} is rolling back prepared transaction on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void rollbackPreparedTransaction(String user, Object source, Object... args);

   static void listRemoteAddresses(Object source, Object... args) {
      BASE_LOGGER.listRemoteAddresses(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601098, value = "User {0} is listing remote addresses on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listRemoteAddresses(String user, Object source, Object... args);

   static void closeConnectionsForAddress(Object source, Object... args) {
      BASE_LOGGER.closeConnectionsForAddress(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601099, value = "User {0} is closing connections for address on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void closeConnectionsForAddress(String user, Object source, Object... args);

   static void closeConsumerConnectionsForAddress(Object source, Object... args) {
      BASE_LOGGER.closeConsumerConnectionsForAddress(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601100, value = "User {0} is closing consumer connections for address on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void closeConsumerConnectionsForAddress(String user, Object source, Object... args);

   static void closeConnectionsForUser(Object source, Object... args) {
      BASE_LOGGER.closeConnectionsForUser(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601101, value = "User {0} is closing connections for user on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void closeConnectionsForUser(String user, Object source, Object... args);

   static void closeConnectionWithID(Object source, Object... args) {
      BASE_LOGGER.closeConnectionWithID(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601102, value = "User {0} is closing a connection by ID on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void closeConnectionWithID(String user, Object source, Object... args);

   static void closeSessionWithID(Object source, Object... args) {
      BASE_LOGGER.closeSessionWithID(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601103, value = "User {0} is closing session with id on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void closeSessionWithID(String user, Object source, Object... args);

   static void closeConsumerWithID(Object source, Object... args) {
      BASE_LOGGER.closeConsumerWithID(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601104, value = "User {0} is closing consumer with id on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void closeConsumerWithID(String user, Object source, Object... args);

   static void listConnectionIDs(Object source) {
      BASE_LOGGER.listConnectionIDs(getCaller(), source);
   }

  
   @LogMessage(id = 601105, value = "User {0} is listing connection IDs on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listConnectionIDs(String user, Object source, Object... args);

   static void listSessions(Object source, Object... args) {
      BASE_LOGGER.listSessions(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601106, value = "User {0} is listing sessions on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listSessions(String user, Object source, Object... args);

   static void listProducersInfoAsJSON(Object source) {
      BASE_LOGGER.listProducersInfoAsJSON(getCaller(), source);
   }

  
   @LogMessage(id = 601107, value = "User {0} is listing producers info as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listProducersInfoAsJSON(String user, Object source, Object... args);

   static void listConnections(Object source, Object... args) {
      BASE_LOGGER.listConnections(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601108, value = "User {0} is listing connections on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listConnections(String user, Object source, Object... args);

   static void listConsumers(Object source, Object... args) {
      BASE_LOGGER.listConsumers(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601109, value = "User {0} is listing consumers on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listConsumers(String user, Object source, Object... args);

   static void listQueues(Object source, Object... args) {
      BASE_LOGGER.listQueues(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601110, value = "User {0} is listing queues on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listQueues(String user, Object source, Object... args);

   static void listProducers(Object source, Object... args) {
      BASE_LOGGER.listProducers(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601111, value = "User {0} is listing producers on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listProducers(String user, Object source, Object... args);

   static void listConnectionsAsJSON(Object source) {
      BASE_LOGGER.listConnectionsAsJSON(getCaller(), source);
   }

  
   @LogMessage(id = 601112, value = "User {0} is listing connections as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listConnectionsAsJSON(String user, Object source, Object... args);

   static void listSessionsAsJSON(Object source, Object... args) {
      BASE_LOGGER.listSessionsAsJSON(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601113, value = "User {0} is listing sessions as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listSessionsAsJSON(String user, Object source, Object... args);

   static void listAllSessionsAsJSON(Object source) {
      BASE_LOGGER.listAllSessionsAsJSON(getCaller(), source);
   }

  
   @LogMessage(id = 601114, value = "User {0} is listing all sessions as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listAllSessionsAsJSON(String user, Object source, Object... args);

   static void listConsumersAsJSON(Object source, Object... args) {
      BASE_LOGGER.listConsumersAsJSON(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601115, value = "User {0} is listing consumers as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listConsumersAsJSON(String user, Object source, Object... args);

   static void listAllConsumersAsJSON(Object source) {
      BASE_LOGGER.listAllConsumersAsJSON(getCaller(), source);
   }

  
   @LogMessage(id = 601116, value = "User {0} is listing all consumers as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listAllConsumersAsJSON(String user, Object source, Object... args);

   static void getConnectors(Object source) {
      BASE_LOGGER.getConnectors(getCaller(), source);
   }

  
   @LogMessage(id = 601117, value = "User {0} is getting connectors on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getConnectors(String user, Object source, Object... args);

   static void getConnectorsAsJSON(Object source) {
      BASE_LOGGER.getConnectorsAsJSON(getCaller(), source);
   }

  
   @LogMessage(id = 601118, value = "User {0} is getting connectors as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getConnectorsAsJSON(String user, Object source, Object... args);

   static void addSecuritySettings(Object source, Object... args) {
      BASE_LOGGER.addSecuritySettings(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601119, value = "User {0} is adding security settings on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void addSecuritySettings(String user, Object source, Object... args);

   static void removeSecuritySettings(Object source, Object... args) {
      BASE_LOGGER.removeSecuritySettings(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601120, value = "User {0} is removing security settings on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void removeSecuritySettings(String user, Object source, Object... args);

   static void getAddressSettingsAsJSON(Object source, Object... args) {
      BASE_LOGGER.getAddressSettingsAsJSON(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601121, value = "User {0} is getting address settings as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getAddressSettingsAsJSON(String user, Object source, Object... args);

   static void addAddressSettings(Object source, Object... args) {
      BASE_LOGGER.addAddressSettings(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601122, value = "User {0} is adding addressSettings on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void addAddressSettings(String user, Object source, Object... args);

   static void removeAddressSettings(Object source, Object... args) {
      BASE_LOGGER.removeAddressSettings(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601123, value = "User {0} is removing address settings on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void removeAddressSettings(String user, Object source, Object... args);

   static void getDivertNames(Object source) {
      BASE_LOGGER.getDivertNames(getCaller(), source);
   }

  
   @LogMessage(id = 601124, value = "User {0} is getting divert names on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getDivertNames(String user, Object source, Object... args);

   static void createDivert(Object source, Object... args) {
      BASE_LOGGER.createDivert(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601125, value = "User {0} is creating a divert on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void createDivert(String user, Object source, Object... args);

   static void destroyDivert(Object source, Object... args) {
      BASE_LOGGER.destroyDivert(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601126, value = "User {0} is destroying a divert on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void destroyDivert(String user, Object source, Object... args);

   static void getBridgeNames(Object source) {
      BASE_LOGGER.getBridgeNames(getCaller(), source);
   }

  
   @LogMessage(id = 601127, value = "User {0} is getting bridge names on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getBridgeNames(String user, Object source, Object... args);

   static void createBridge(Object source, Object... args) {
      BASE_LOGGER.createBridge(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601128, value = "User {0} is creating a bridge on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void createBridge(String user, Object source, Object... args);

   static void destroyBridge(Object source, Object... args) {
      BASE_LOGGER.destroyBridge(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601129, value = "User {0} is destroying a bridge on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void destroyBridge(String user, Object source, Object... args);

   static void createConnectorService(Object source, Object... args) {
      BASE_LOGGER.createConnectorService(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601130, value = "User {0} is creating connector service on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void createConnectorService(String user, Object source, Object... args);

   static void destroyConnectorService(Object source, Object... args) {
      BASE_LOGGER.destroyConnectorService(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601131, value = "User {0} is destroying connector service on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void destroyConnectorService(String user, Object source, Object... args);

   static void getConnectorServices(Object source, Object... args) {
      BASE_LOGGER.getConnectorServices(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601132, value = "User {0} is getting connector services on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getConnectorServices(String user, Object source, Object... args);

   static void forceFailover(Object source) {
      BASE_LOGGER.forceFailover(getCaller(), source);
   }

  
   @LogMessage(id = 601133, value = "User {0} is forceing a failover on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void forceFailover(String user, Object source, Object... args);

   static void scaleDown(Object source, Object... args) {
      BASE_LOGGER.scaleDown(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601134, value = "User {0} is performing scale down on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void scaleDown(String user, Object source, Object... args);

   static void listNetworkTopology(Object source) {
      BASE_LOGGER.listNetworkTopology(getCaller(), source);
   }

  
   @LogMessage(id = 601135, value = "User {0} is listing network topology on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listNetworkTopology(String user, Object source, Object... args);

   static void removeNotificationListener(Object source, Object... args) {
      BASE_LOGGER.removeNotificationListener(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601136, value = "User {0} is removing notification listener on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void removeNotificationListener(String user, Object source, Object... args);

   static void addNotificationListener(Object source, Object... args) {
      BASE_LOGGER.addNotificationListener(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601137, value = "User {0} is adding notification listener on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void addNotificationListener(String user, Object source, Object... args);

   static void getNotificationInfo(Object source) {
      BASE_LOGGER.getNotificationInfo(getCaller(), source);
   }

  
   @LogMessage(id = 601138, value = "User {0} is getting notification info on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getNotificationInfo(String user, Object source, Object... args);

   static void getConnectionTTLOverride(Object source) {
      BASE_LOGGER.getConnectionTTLOverride(getCaller(), source);
   }

  
   @LogMessage(id = 601139, value = "User {0} is getting connection ttl override on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getConnectionTTLOverride(String user, Object source, Object... args);

   static void getIDCacheSize(Object source) {
      BASE_LOGGER.getIDCacheSize(getCaller(), source);
   }

  
   @LogMessage(id = 601140, value = "User {0} is getting ID cache size on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getIDCacheSize(String user, Object source, Object... args);

   static void getLargeMessagesDirectory(Object source) {
      BASE_LOGGER.getLargeMessagesDirectory(getCaller(), source);
   }

  
   @LogMessage(id = 601141, value = "User {0} is getting large message directory on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getLargeMessagesDirectory(String user, Object source, Object... args);

   static void getManagementAddress(Object source) {
      BASE_LOGGER.getManagementAddress(getCaller(), source);
   }

  
   @LogMessage(id = 601142, value = "User {0} is getting management address on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getManagementAddress(String user, Object source, Object... args);

   static void getNodeID(Object source) {
      BASE_LOGGER.getNodeID(getCaller(), source);
   }

  
   @LogMessage(id = 601143, value = "User {0} is getting node ID on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getNodeID(String user, Object source, Object... args);

   static void getManagementNotificationAddress(Object source) {
      BASE_LOGGER.getManagementNotificationAddress(getCaller(), source);
   }

  
   @LogMessage(id = 601144, value = "User {0} is getting management notification address on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getManagementNotificationAddress(String user, Object source, Object... args);

   static void getMessageExpiryScanPeriod(Object source) {
      BASE_LOGGER.getMessageExpiryScanPeriod(getCaller(), source);
   }

  
   @LogMessage(id = 601145, value = "User {0} is getting message expiry scan period on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getMessageExpiryScanPeriod(String user, Object source, Object... args);

   static void getMessageExpiryThreadPriority(Object source) {
      BASE_LOGGER.getMessageExpiryThreadPriority(getCaller(), source);
   }

  
   @LogMessage(id = 601146, value = "User {0} is getting message expiry thread priority on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getMessageExpiryThreadPriority(String user, Object source, Object... args);

   static void getTransactionTimeout(Object source) {
      BASE_LOGGER.getTransactionTimeout(getCaller(), source);
   }

  
   @LogMessage(id = 601147, value = "User {0} is getting transaction timeout on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getTransactionTimeout(String user, Object source, Object... args);

   static void getTransactionTimeoutScanPeriod(Object source) {
      BASE_LOGGER.getTransactionTimeoutScanPeriod(getCaller(), source);
   }

  
   @LogMessage(id = 601148, value = "User {0} is getting transaction timeout scan period on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getTransactionTimeoutScanPeriod(String user, Object source, Object... args);

   static void isPersistDeliveryCountBeforeDelivery(Object source) {
      BASE_LOGGER.isPersistDeliveryCountBeforeDelivery(getCaller(), source);
   }

  
   @LogMessage(id = 601149, value = "User {0} is querying is-persist-delivery-before-delivery on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isPersistDeliveryCountBeforeDelivery(String user, Object source, Object... args);

   static void isPersistIDCache(Object source) {
      BASE_LOGGER.isPersistIDCache(getCaller(), source);
   }

  
   @LogMessage(id = 601150, value = "User {0} is querying is-persist-id-cache on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isPersistIDCache(String user, Object source, Object... args);

   static void isWildcardRoutingEnabled(Object source) {
      BASE_LOGGER.isWildcardRoutingEnabled(getCaller(), source);
   }

  
   @LogMessage(id = 601151, value = "User {0} is querying is-wildcard-routing-enabled on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isWildcardRoutingEnabled(String user, Object source, Object... args);

   static void addUser(Object source, Object... args) {
      BASE_LOGGER.addUser(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601152, value = "User {0} is adding a user on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void addUser(String user, Object source, Object... args);

   static void listUser(Object source, Object... args) {
      BASE_LOGGER.listUser(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601153, value = "User {0} is listing a user on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listUser(String user, Object source, Object... args);

   static void removeUser(Object source, Object... args) {
      BASE_LOGGER.removeUser(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601154, value = "User {0} is removing a user on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void removeUser(String user, Object source, Object... args);

   static void resetUser(Object source, Object... args) {
      BASE_LOGGER.resetUser(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601155, value = "User {0} is resetting a user on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void resetUser(String user, Object source, Object... args);

   static void getUser(Object source) {
      BASE_LOGGER.getUser(getCaller(), source);
   }

  
   @LogMessage(id = 601156, value = "User {0} is getting user property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getUser(String user, Object source, Object... args);

   static void getRoutingType(Object source) {
      BASE_LOGGER.getRoutingType(getCaller(), source);
   }

  
   @LogMessage(id = 601157, value = "User {0} is getting routing type property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getRoutingType(String user, Object source, Object... args);

   static void isTemporary(Object source) {
      BASE_LOGGER.isTemporary(getCaller(), source);
   }

  
   @LogMessage(id = 601158, value = "User {0} is getting temporary property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isTemporary(String user, Object source, Object... args);

   static void getPersistentSize(Object source) {
      BASE_LOGGER.getPersistentSize(getCaller(), source);
   }

  
   @LogMessage(id = 601159, value = "User {0} is getting persistent size on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getPersistentSize(String user, Object source, Object... args);

   static void getDurableMessageCount(Object source) {
      BASE_LOGGER.getDurableMessageCount(getCaller(), source);
   }

  
   @LogMessage(id = 601160, value = "User {0} is getting durable message count on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getDurableMessageCount(String user, Object source, Object... args);

   static void getDurablePersistSize(Object source) {
      BASE_LOGGER.getDurablePersistSize(getCaller(), source);
   }

  
   @LogMessage(id = 601161, value = "User {0} is getting durable persist size on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getDurablePersistSize(String user, Object source, Object... args);

   static void getConsumerCount(Object source) {
      BASE_LOGGER.getConsumerCount(getCaller(), source);
   }

  
   @LogMessage(id = 601162, value = "User {0} is getting consumer count on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getConsumerCount(String user, Object source, Object... args);

   static void getDeliveringCount(Object source) {
      BASE_LOGGER.getDeliveringCount(getCaller(), source);
   }

  
   @LogMessage(id = 601163, value = "User {0} is getting delivering count on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getDeliveringCount(String user, Object source, Object... args);

   static void getDeliveringSize(Object source) {
      BASE_LOGGER.getDeliveringSize(getCaller(), source);
   }

  
   @LogMessage(id = 601164, value = "User {0} is getting delivering size on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getDeliveringSize(String user, Object source, Object... args);

   static void getDurableDeliveringCount(Object source) {
      BASE_LOGGER.getDurableDeliveringCount(getCaller(), source);
   }

  
   @LogMessage(id = 601165, value = "User {0} is getting durable delivering count on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getDurableDeliveringCount(String user, Object source, Object... args);

   static void getDurableDeliveringSize(Object source) {
      BASE_LOGGER.getDurableDeliveringSize(getCaller(), source);
   }

  
   @LogMessage(id = 601166, value = "User {0} is getting durable delivering size on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getDurableDeliveringSize(String user, Object source, Object... args);

   static void getMessagesAdded(Object source) {
      BASE_LOGGER.getMessagesAdded(getCaller(), source);
   }

  
   @LogMessage(id = 601167, value = "User {0} is getting messages added on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getMessagesAdded(String user, Object source, Object... args);

   static void getMessagesAcknowledged(Object source) {
      BASE_LOGGER.getMessagesAcknowledged(getCaller(), source);
   }

  
   @LogMessage(id = 601168, value = "User {0} is getting messages acknowledged on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getMessagesAcknowledged(String user, Object source, Object... args);

   static void getMessagesExpired(Object source) {
      BASE_LOGGER.getMessagesExpired(getCaller(), source);
   }

  
   @LogMessage(id = 601169, value = "User {0} is getting messages expired on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getMessagesExpired(String user, Object source, Object... args);

   static void getMessagesKilled(Object source) {
      BASE_LOGGER.getMessagesKilled(getCaller(), source);
   }

  
   @LogMessage(id = 601170, value = "User {0} is getting messages killed on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getMessagesKilled(String user, Object source, Object... args);

   static void getID(Object source) {
      BASE_LOGGER.getID(getCaller(), source);
   }

  
   @LogMessage(id = 601171, value = "User {0} is getting ID on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getID(String user, Object source, Object... args);

   static void getScheduledCount(Object source) {
      BASE_LOGGER.getScheduledCount(getCaller(), source);
   }

  
   @LogMessage(id = 601172, value = "User {0} is getting scheduled count on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getScheduledCount(String user, Object source, Object... args);

   static void getScheduledSize(Object source) {
      BASE_LOGGER.getScheduledSize(getCaller(), source);
   }

  
   @LogMessage(id = 601173, value = "User {0} is getting scheduled size on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getScheduledSize(String user, Object source, Object... args);

   static void getDurableScheduledCount(Object source) {
      BASE_LOGGER.getDurableScheduledCount(getCaller(), source);
   }

  
   @LogMessage(id = 601174, value = "User {0} is getting durable scheduled count on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getDurableScheduledCount(String user, Object source, Object... args);

   static void getDurableScheduledSize(Object source) {
      BASE_LOGGER.getDurableScheduledSize(getCaller(), source);
   }

  
   @LogMessage(id = 601175, value = "User {0} is getting durable scheduled size on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getDurableScheduledSize(String user, Object source, Object... args);

   static void getDeadLetterAddress(Object source) {
      BASE_LOGGER.getDeadLetterAddress(getCaller(), source);
   }

  
   @LogMessage(id = 601176, value = "User {0} is getting dead letter address on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getDeadLetterAddress(String user, Object source, Object... args);

   static void getExpiryAddress(Object source) {
      BASE_LOGGER.getExpiryAddress(getCaller(), source);
   }

  
   @LogMessage(id = 601177, value = "User {0} is getting expiry address on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getExpiryAddress(String user, Object source, Object... args);

   static void getMaxConsumers(Object source) {
      BASE_LOGGER.getMaxConsumers(getCaller(), source);
   }

  
   @LogMessage(id = 601178, value = "User {0} is getting max consumers on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getMaxConsumers(String user, Object source, Object... args);

   static void isPurgeOnNoConsumers(Object source) {
      BASE_LOGGER.isPurgeOnNoConsumers(getCaller(), source);
   }

  
   @LogMessage(id = 601179, value = "User {0} is getting purge-on-consumers property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isPurgeOnNoConsumers(String user, Object source, Object... args);

   static void isConfigurationManaged(Object source) {
      BASE_LOGGER.isConfigurationManaged(getCaller(), source);
   }

  
   @LogMessage(id = 601180, value = "User {0} is getting configuration-managed property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isConfigurationManaged(String user, Object source, Object... args);

   static void isExclusive(Object source) {
      BASE_LOGGER.isExclusive(getCaller(), source);
   }

  
   @LogMessage(id = 601181, value = "User {0} is getting exclusive property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isExclusive(String user, Object source, Object... args);

   static void isLastValue(Object source) {
      BASE_LOGGER.isLastValue(getCaller(), source);
   }

  
   @LogMessage(id = 601182, value = "User {0} is getting last-value property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isLastValue(String user, Object source, Object... args);

   static void listScheduledMessages(Object source) {
      BASE_LOGGER.listScheduledMessages(getCaller(), source);
   }

  
   @LogMessage(id = 601183, value = "User {0} is listing scheduled messages on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listScheduledMessages(String user, Object source, Object... args);

   static void listScheduledMessagesAsJSON(Object source) {
      BASE_LOGGER.listScheduledMessagesAsJSON(getCaller(), source);
   }

  
   @LogMessage(id = 601184, value = "User {0} is listing scheduled messages as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listScheduledMessagesAsJSON(String user, Object source, Object... args);

   static void listDeliveringMessages(Object source) {
      BASE_LOGGER.listDeliveringMessages(getCaller(), source);
   }

  
   @LogMessage(id = 601185, value = "User {0} is listing delivering messages on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listDeliveringMessages(String user, Object source, Object... args);

   static void listDeliveringMessagesAsJSON(Object source) {
      BASE_LOGGER.listDeliveringMessagesAsJSON(getCaller(), source);
   }

  
   @LogMessage(id = 601186, value = "User {0} is listing delivering messages as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listDeliveringMessagesAsJSON(String user, Object source, Object... args);

   static void listMessages(Object source, Object... args) {
      BASE_LOGGER.listMessages(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601187, value = "User {0} is listing messages on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listMessages(String user, Object source, Object... args);

   static void listMessagesAsJSON(Object source) {
      BASE_LOGGER.listMessagesAsJSON(getCaller(), source);
   }

  
   @LogMessage(id = 601188, value = "User {0} is listing messages as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listMessagesAsJSON(String user, Object source, Object... args);

   static void getFirstMessage(Object source) {
      BASE_LOGGER.getFirstMessage(getCaller(), source);
   }

  
   @LogMessage(id = 601189, value = "User {0} is getting first message on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getFirstMessage(String user, Object source, Object... args);

   static void getFirstMessageAsJSON(Object source) {
      BASE_LOGGER.getFirstMessageAsJSON(getCaller(), source);
   }

  
   @LogMessage(id = 601190, value = "User {0} is getting first message as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getFirstMessageAsJSON(String user, Object source, Object... args);

   static void getFirstMessageTimestamp(Object source) {
      BASE_LOGGER.getFirstMessageTimestamp(getCaller(), source);
   }

  
   @LogMessage(id = 601191, value = "User {0} is getting first message's timestamp on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getFirstMessageTimestamp(String user, Object source, Object... args);

   static void getFirstMessageAge(Object source) {
      BASE_LOGGER.getFirstMessageAge(getCaller(), source);
   }

  
   @LogMessage(id = 601192, value = "User {0} is getting first message's age on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getFirstMessageAge(String user, Object source, Object... args);

   static void countMessages(Object source, Object... args) {
      BASE_LOGGER.countMessages(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601193, value = "User {0} is counting messages on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void countMessages(String user, Object source, Object... args);

   static void countDeliveringMessages(Object source, Object... args) {
      BASE_LOGGER.countDeliveringMessages(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601194, value = "User {0} is counting delivery messages on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void countDeliveringMessages(String user, Object source, Object... args);

   static void removeMessage(Object source, Object... args) {
      BASE_LOGGER.removeMessage(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601195, value = "User {0} is removing a message on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void removeMessage(String user, Object source, Object... args);

   static void removeMessages(Object source, Object... args) {
      BASE_LOGGER.removeMessages(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601196, value = "User {0} is removing messages on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void removeMessages(String user, Object source, Object... args);

   static void expireMessage(Object source, Object... args) {
      BASE_LOGGER.expireMessage(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601197, value = "User {0} is expiring messages on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void expireMessage(String user, Object source, Object... args);

   static void expireMessages(Object source, Object... args) {
      BASE_LOGGER.expireMessages(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601198, value = "User {0} is expiring messages on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void expireMessages(String user, Object source, Object... args);

   static void retryMessage(Object source, Object... args) {
      BASE_LOGGER.retryMessage(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601199, value = "User {0} is retry sending message on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void retryMessage(String user, Object source, Object... args);

   static void retryMessages(Object source) {
      BASE_LOGGER.retryMessages(getCaller(), source);
   }

  
   @LogMessage(id = 601200, value = "User {0} is retry sending messages on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void retryMessages(String user, Object source, Object... args);

   static void moveMessage(Object source, Object... args) {
      BASE_LOGGER.moveMessage(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601201, value = "User {0} is moving a message to another queue on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void moveMessage(String user, Object source, Object... args);

   static void moveMessages(Object source, Object... args) {
      BASE_LOGGER.moveMessages(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601202, value = "User {0} is moving messages to another queue on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void moveMessages(String user, Object source, Object... args);

   static void sendMessagesToDeadLetterAddress(Object source, Object... args) {
      BASE_LOGGER.sendMessagesToDeadLetterAddress(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601203, value = "User {0} is sending messages to dead letter address on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void sendMessagesToDeadLetterAddress(String user, Object source, Object... args);

   static void sendMessageToDeadLetterAddress(Object source, Object... args) {
      BASE_LOGGER.sendMessageToDeadLetterAddress(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601204, value = "User {0} is sending messages to dead letter address on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void sendMessageToDeadLetterAddress(String user, Object source, Object... args);

   static void changeMessagesPriority(Object source, Object... args) {
      BASE_LOGGER.changeMessagesPriority(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601205, value = "User {0} is changing message's priority on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void changeMessagesPriority(String user, Object source, Object... args);

   static void changeMessagePriority(Object source, Object... args) {
      BASE_LOGGER.changeMessagePriority(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601206, value = "User {0} is changing a message's priority on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void changeMessagePriority(String user, Object source, Object... args);

   static void listMessageCounter(Object source) {
      BASE_LOGGER.listMessageCounter(getCaller(), source);
   }

  
   @LogMessage(id = 601207, value = "User {0} is listing message counter on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listMessageCounter(String user, Object source, Object... args);

   static void resetMessageCounter(Object source) {
      BASE_LOGGER.resetMessageCounter(getCaller(), source);
   }

  
   @LogMessage(id = 601208, value = "User {0} is resetting message counter on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void resetMessageCounter(String user, Object source, Object... args);

   static void listMessageCounterAsHTML(Object source) {
      BASE_LOGGER.listMessageCounterAsHTML(getCaller(), source);
   }

  
   @LogMessage(id = 601209, value = "User {0} is listing message counter as HTML on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listMessageCounterAsHTML(String user, Object source, Object... args);

   static void listMessageCounterHistory(Object source) {
      BASE_LOGGER.listMessageCounterHistory(getCaller(), source);
   }

  
   @LogMessage(id = 601210, value = "User {0} is listing message counter history on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listMessageCounterHistory(String user, Object source, Object... args);

   static void listMessageCounterHistoryAsHTML(Object source) {
      BASE_LOGGER.listMessageCounterHistoryAsHTML(getCaller(), source);
   }

  
   @LogMessage(id = 601211, value = "User {0} is listing message counter history as HTML on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listMessageCounterHistoryAsHTML(String user, Object source, Object... args);

   static void pause(Object source, Object... args) {
      BASE_LOGGER.pause(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601212, value = "User {0} is pausing on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void pause(String user, Object source, Object... args);

   static void resume(Object source) {
      BASE_LOGGER.resume(getCaller(), source);
   }

  
   @LogMessage(id = 601213, value = "User {0} is resuming on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void resume(String user, Object source, Object... args);

   static void isPaused(Object source) {
      BASE_LOGGER.isPaused(getCaller(), source);
   }

  
   @LogMessage(id = 601214, value = "User {0} is getting paused property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isPaused(String user, Object source, Object... args);

   static void browse(Object source, Object... args) {
      BASE_LOGGER.browse(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601215, value = "User {0} is browsing a queue on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void browse(String user, Object source, Object... args);

   static void flushExecutor(Object source) {
      BASE_LOGGER.flushExecutor(getCaller(), source);
   }

  
   @LogMessage(id = 601216, value = "User {0} is flushing executor on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void flushExecutor(String user, Object source, Object... args);

   static void resetAllGroups(Object source) {
      BASE_LOGGER.resetAllGroups(getCaller(), source);
   }

  
   @LogMessage(id = 601217, value = "User {0} is resetting all groups on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void resetAllGroups(String user, Object source, Object... args);

   static void resetGroup(Object source, Object... args) {
      BASE_LOGGER.resetGroup(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601218, value = "User {0} is resetting group on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void resetGroup(String user, Object source, Object... args);

   static void getGroupCount(Object source, Object... args) {
      BASE_LOGGER.getGroupCount(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601219, value = "User {0} is getting group count on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getGroupCount(String user, Object source, Object... args);

   static void listGroupsAsJSON(Object source) {
      BASE_LOGGER.listGroupsAsJSON(getCaller(), source);
   }

  
   @LogMessage(id = 601220, value = "User {0} is listing groups as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void listGroupsAsJSON(String user, Object source, Object... args);

   static void resetMessagesAdded(Object source) {
      BASE_LOGGER.resetMessagesAdded(getCaller(), source);
   }

  
   @LogMessage(id = 601221, value = "User {0} is resetting added messages on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void resetMessagesAdded(String user, Object source, Object... args);

   static void resetMessagesAcknowledged(Object source) {
      BASE_LOGGER.resetMessagesAcknowledged(getCaller(), source);
   }

  
   @LogMessage(id = 601222, value = "User {0} is resetting acknowledged messages on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void resetMessagesAcknowledged(String user, Object source, Object... args);

   static void resetMessagesExpired(Object source) {
      BASE_LOGGER.resetMessagesExpired(getCaller(), source);
   }

  
   @LogMessage(id = 601223, value = "User {0} is resetting expired messages on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void resetMessagesExpired(String user, Object source, Object... args);

   static void resetMessagesKilled(Object source) {
      BASE_LOGGER.resetMessagesKilled(getCaller(), source);
   }

  
   @LogMessage(id = 601224, value = "User {0} is resetting killed messages on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void resetMessagesKilled(String user, Object source, Object... args);

   static void getStaticConnectors(Object source) {
      BASE_LOGGER.getStaticConnectors(getCaller(), source);
   }

  
   @LogMessage(id = 601225, value = "User {0} is getting static connectors on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getStaticConnectors(String user, Object source, Object... args);

   static void getForwardingAddress(Object source) {
      BASE_LOGGER.getForwardingAddress(getCaller(), source);
   }

  
   @LogMessage(id = 601226, value = "User {0} is getting forwarding address on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getForwardingAddress(String user, Object source, Object... args);

   static void getQueueName(Object source) {
      BASE_LOGGER.getQueueName(getCaller(), source);
   }

  
   @LogMessage(id = 601227, value = "User {0} is getting the queue name on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getQueueName(String user, Object source, Object... args);

   static void getDiscoveryGroupName(Object source) {
      BASE_LOGGER.getDiscoveryGroupName(getCaller(), source);
   }

  
   @LogMessage(id = 601228, value = "User {0} is getting discovery group name on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getDiscoveryGroupName(String user, Object source, Object... args);

   static void getFilterString(Object source) {
      BASE_LOGGER.getFilterString(getCaller(), source);
   }

  
   @LogMessage(id = 601229, value = "User {0} is getting filter string on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getFilterString(String user, Object source, Object... args);

   static void getReconnectAttempts(Object source) {
      BASE_LOGGER.getReconnectAttempts(getCaller(), source);
   }

  
   @LogMessage(id = 601230, value = "User {0} is getting reconnect attempts on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getReconnectAttempts(String user, Object source, Object... args);

   static void getRetryInterval(Object source) {
      BASE_LOGGER.getRetryInterval(getCaller(), source);
   }

  
   @LogMessage(id = 601231, value = "User {0} is getting retry interval on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getRetryInterval(String user, Object source, Object... args);

   static void getRetryIntervalMultiplier(Object source) {
      BASE_LOGGER.getRetryIntervalMultiplier(getCaller(), source);
   }

  
   @LogMessage(id = 601232, value = "User {0} is getting retry interval multiplier on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getRetryIntervalMultiplier(String user, Object source, Object... args);

   static void getTransformerClassName(Object source) {
      BASE_LOGGER.getTransformerClassName(getCaller(), source);
   }

  
   @LogMessage(id = 601233, value = "User {0} is getting transformer class name on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getTransformerClassName(String user, Object source, Object... args);

   static void getTransformerPropertiesAsJSON(Object source) {
      BASE_LOGGER.getTransformerPropertiesAsJSON(getCaller(), source);
   }

  
   @LogMessage(id = 601234, value = "User {0} is getting transformer properties as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getTransformerPropertiesAsJSON(String user, Object source, Object... args);

   static void getTransformerProperties(Object source) {
      BASE_LOGGER.getTransformerProperties(getCaller(), source);
   }

  
   @LogMessage(id = 601235, value = "User {0} is getting transformer properties on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getTransformerProperties(String user, Object source, Object... args);

   static void isStartedBridge(Object source) {
      BASE_LOGGER.isStartedBridge(getCaller(), source);
   }

  
   @LogMessage(id = 601236, value = "User {0} is checking if bridge started on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isStartedBridge(String user, Object source, Object... args);

   static void isUseDuplicateDetection(Object source) {
      BASE_LOGGER.isUseDuplicateDetection(getCaller(), source);
   }

  
   @LogMessage(id = 601237, value = "User {0} is querying use duplicate detection on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isUseDuplicateDetection(String user, Object source, Object... args);

   static void isHA(Object source) {
      BASE_LOGGER.isHA(getCaller(), source);
   }

  
   @LogMessage(id = 601238, value = "User {0} is querying isHA on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isHA(String user, Object source, Object... args);

   static void startBridge(Object source) {
      BASE_LOGGER.startBridge(getCaller(), source);
   }

  
   @LogMessage(id = 601239, value = "User {0} is starting a bridge on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void startBridge(String user, Object source, Object... args);

   static void stopBridge(Object source) {
      BASE_LOGGER.stopBridge(getCaller(), source);
   }

  
   @LogMessage(id = 601240, value = "User {0} is stopping a bridge on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void stopBridge(String user, Object source, Object... args);

   static void getMessagesPendingAcknowledgement(Object source) {
      BASE_LOGGER.getMessagesPendingAcknowledgement(getCaller(), source);
   }

  
   @LogMessage(id = 601241, value = "User {0} is getting messages pending acknowledgement on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getMessagesPendingAcknowledgement(String user, Object source, Object... args);

   static void getMetrics(Object source) {
      BASE_LOGGER.getMetrics(getCaller(), source);
   }

  
   @LogMessage(id = 601242, value = "User {0} is getting metrics on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getMetrics(String user, Object source, Object... args);

   static void getBroadcastPeriod(Object source) {
      BASE_LOGGER.getBroadcastPeriod(getCaller(), source);
   }

  
   @LogMessage(id = 601243, value = "User {0} is getting broadcast period on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getBroadcastPeriod(String user, Object source, Object... args);

   static void getConnectorPairs(Object source) {
      BASE_LOGGER.getConnectorPairs(getCaller(), source);
   }

  
   @LogMessage(id = 601244, value = "User {0} is getting connector pairs on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getConnectorPairs(String user, Object source, Object... args);

   static void getConnectorPairsAsJSON(Object source) {
      BASE_LOGGER.getConnectorPairsAsJSON(getCaller(), source);
   }

  
   @LogMessage(id = 601245, value = "User {0} is getting connector pairs as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getConnectorPairsAsJSON(String user, Object source, Object... args);

   static void getGroupAddress(Object source) {
      BASE_LOGGER.getGroupAddress(getCaller(), source);
   }

  
   @LogMessage(id = 601246, value = "User {0} is getting group address on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getGroupAddress(String user, Object source, Object... args);

   static void getGroupPort(Object source) {
      BASE_LOGGER.getGroupPort(getCaller(), source);
   }

  
   @LogMessage(id = 601247, value = "User {0} is getting group port on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getGroupPort(String user, Object source, Object... args);

   static void getLocalBindPort(Object source) {
      BASE_LOGGER.getLocalBindPort(getCaller(), source);
   }

  
   @LogMessage(id = 601248, value = "User {0} is getting local binding port on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getLocalBindPort(String user, Object source, Object... args);

   static void startBroadcastGroup(Object source) {
      BASE_LOGGER.startBroadcastGroup(getCaller(), source);
   }

  
   @LogMessage(id = 601249, value = "User {0} is starting broadcasting group on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void startBroadcastGroup(String user, Object source, Object... args);

   static void stopBroadcastGroup(Object source) {
      BASE_LOGGER.stopBroadcastGroup(getCaller(), source);
   }

  
   @LogMessage(id = 601250, value = "User {0} is stopping broadcasting group on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void stopBroadcastGroup(String user, Object source, Object... args);

   static void getMaxHops(Object source) {
      BASE_LOGGER.getMaxHops(getCaller(), source);
   }

  
   @LogMessage(id = 601251, value = "User {0} is getting max hops on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getMaxHops(String user, Object source, Object... args);

   static void getStaticConnectorsAsJSON(Object source) {
      BASE_LOGGER.getStaticConnectorsAsJSON(getCaller(), source);
   }

  
   @LogMessage(id = 601252, value = "User {0} is geting static connectors as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getStaticConnectorsAsJSON(String user, Object source, Object... args);

   static void isDuplicateDetection(Object source) {
      BASE_LOGGER.isDuplicateDetection(getCaller(), source);
   }

  
   @LogMessage(id = 601253, value = "User {0} is querying use duplicate detection on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isDuplicateDetection(String user, Object source, Object... args);

   static void getMessageLoadBalancingType(Object source) {
      BASE_LOGGER.getMessageLoadBalancingType(getCaller(), source);
   }

  
   @LogMessage(id = 601254, value = "User {0} is getting message loadbalancing type on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getMessageLoadBalancingType(String user, Object source, Object... args);

   static void getTopology(Object source) {
      BASE_LOGGER.getTopology(getCaller(), source);
   }

  
   @LogMessage(id = 601255, value = "User {0} is getting topology on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getTopology(String user, Object source, Object... args);

   static void getNodes(Object source) {
      BASE_LOGGER.getNodes(getCaller(), source);
   }

  
   @LogMessage(id = 601256, value = "User {0} is getting nodes on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getNodes(String user, Object source, Object... args);

   static void startClusterConnection(Object source) {
      BASE_LOGGER.startClusterConnection(getCaller(), source);
   }

  
   @LogMessage(id = 601257, value = "User {0} is start cluster connection on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void startClusterConnection(String user, Object source, Object... args);

   static void stopClusterConnection(Object source) {
      BASE_LOGGER.stopClusterConnection(getCaller(), source);
   }

  
   @LogMessage(id = 601258, value = "User {0} is stop cluster connection on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void stopClusterConnection(String user, Object source, Object... args);

   static void getBridgeMetrics(Object source, Object... args) {
      BASE_LOGGER.getBridgeMetrics(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601259, value = "User {0} is getting bridge metrics on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getBridgeMetrics(String user, Object source, Object... args);

   static void getRoutingName(Object source) {
      BASE_LOGGER.getRoutingName(getCaller(), source);
   }

  
   @LogMessage(id = 601260, value = "User {0} is getting routing name on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getRoutingName(String user, Object source, Object... args);

   static void getUniqueName(Object source) {
      BASE_LOGGER.getUniqueName(getCaller(), source);
   }

  
   @LogMessage(id = 601261, value = "User {0} is getting unique name on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getUniqueName(String user, Object source, Object... args);

   static void serverSessionCreateAddress(Object source, Subject user, String remoteAddress, Object... args) {
      BASE_LOGGER.serverSessionCreateAddress2(getCaller(user, remoteAddress), source, arrayToString(args));
   }

  
   @LogMessage(id = 601262, value = "User {0} is creating address on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void serverSessionCreateAddress2(String user, Object source, Object... args);

   static void handleManagementMessage(Object source, Subject user, String remoteAddress, Object... args) {
      BASE_LOGGER.handleManagementMessage2(getCaller(user, remoteAddress), source, arrayToString(args));
   }

  
   @LogMessage(id = 601263, value = "User {0} is handling a management message on target resource {1} {2}", level = LogMessage.Level.INFO)
   void handleManagementMessage2(String user, Object source, Object... args);


   static void securityFailure(Exception cause) {
      BASE_LOGGER.securityFailure(getCaller(), cause);
   }

  
   @LogMessage(id = 601264, value = "User {0} gets security check failure", level = LogMessage.Level.INFO)
   void securityFailure(String user, Throwable cause);


   static void createCoreConsumer(Object source, Subject user, String remoteAddress, Object... args) {
      BASE_LOGGER.createCoreConsumer(getCaller(user, remoteAddress), source, arrayToString(args));
   }

  
   @LogMessage(id = 601265, value = "User {0} is creating a core consumer on target resource {1} {2}", level = LogMessage.Level.INFO)
   void createCoreConsumer(String user, Object source, Object... args);

   static void createSharedQueue(Object source, Subject user, String remoteAddress, Object... args) {
      BASE_LOGGER.createSharedQueue(getCaller(user, remoteAddress), source, arrayToString(args));
   }

  
   @LogMessage(id = 601266, value = "User {0} is creating a shared queue on target resource {1} {2}", level = LogMessage.Level.INFO)
   void createSharedQueue(String user, Object source, Object... args);

   static void createCoreSession(Object source, Subject user, String remoteAddress, Object... args) {
      BASE_LOGGER.createCoreSession(getCaller(user, remoteAddress), source, arrayToString(args));
   }

  
   @LogMessage(id = 601267, value = "User {0} is creating a core session on target resource {1} {2}", level = LogMessage.Level.INFO)
   void createCoreSession(String user, Object source, Object... args);

   static void getAcknowledgeAttempts(Object source) {
      BASE_LOGGER.getMessagesAcknowledged(getCaller(), source);
   }

  
   @LogMessage(id = 601269, value = "User {0} is getting messages acknowledged attempts on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getAcknowledgeAttempts(String user, Object source, Object... args);

   static void getRingSize(Object source, Object... args) {
      BASE_LOGGER.getRingSize(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601270, value = "User {0} is getting ring size on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getRingSize(String user, Object source, Object... args);


   static void isRetroactiveResource(Object source) {
      BASE_LOGGER.isRetroactiveResource(getCaller(), source);
   }

  
   @LogMessage(id = 601271, value = "User {0} is getting retroactiveResource property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isRetroactiveResource(String user, Object source, Object... args);

   static void getDiskStoreUsage(Object source) {
      BASE_LOGGER.getDiskStoreUsage(getCaller(), source);
   }

  
   @LogMessage(id = 601272, value = "User {0} is getting disk store usage on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getDiskStoreUsage(String user, Object source, Object... args);

   static void getDiskStoreUsagePercentage(Object source) {
      BASE_LOGGER.getDiskStoreUsagePercentage(getCaller(), source);
   }

  
   @LogMessage(id = 601273, value = "User {0} is getting disk store usage percentage on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getDiskStoreUsagePercentage(String user, Object source, Object... args);

   static void isGroupRebalance(Object source) {
      BASE_LOGGER.isGroupRebalance(getCaller(), source);
   }

  
   @LogMessage(id = 601274, value = "User {0} is getting group rebalance property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isGroupRebalance(String user, Object source, Object... args);

   static void getGroupBuckets(Object source) {
      BASE_LOGGER.getGroupBuckets(getCaller(), source);
   }

  
   @LogMessage(id = 601275, value = "User {0} is getting group buckets on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getGroupBuckets(String user, Object source, Object... args);

   static void getGroupFirstKey(Object source) {
      BASE_LOGGER.getGroupFirstKey(getCaller(), source);
   }

  
   @LogMessage(id = 601276, value = "User {0} is getting group first key on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getGroupFirstKey(String user, Object source, Object... args);

   static void getCurrentDuplicateIdCacheSize(Object source) {
      BASE_LOGGER.getCurrentDuplicateIdCacheSize(getCaller(), source);
   }

  
   @LogMessage(id = 601509, value = "User {0} is getting currentDuplicateIdCacheSize property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getCurrentDuplicateIdCacheSize(String user, Object source, Object... args);


   static void clearDuplicateIdCache(Object source) {
      BASE_LOGGER.clearDuplicateIdCache(getCaller(), source);
   }

  
   @LogMessage(id = 601510, value = "User {0} is clearing duplicate ID cache on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void clearDuplicateIdCache(String user, Object source, Object... args);


   static void getChannelName(Object source) {
      BASE_LOGGER.getChannelName(getCaller(), source);
   }

  
   @LogMessage(id = 601511, value = "User {0} is getting channelName property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getChannelName(String user, Object source, Object... args);

   static void getFileContents(Object source) {
      BASE_LOGGER.getFileContents(getCaller(), source);
   }

  
   @LogMessage(id = 601512, value = "User {0} is getting fileContents property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getFileContents(String user, Object source, Object... args);

   static void getFile(Object source) {
      BASE_LOGGER.getFile(getCaller(), source);
   }

  
   @LogMessage(id = 601513, value = "User {0} is getting file property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getFile(String user, Object source, Object... args);

   static void getPreparedTransactionMessageCount(Object source) {
      BASE_LOGGER.getPreparedTransactionMessageCount(getCaller(), source);
   }

  
   @LogMessage(id = 601514, value = "User {0} is getting preparedTransactionMessageCount property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getPreparedTransactionMessageCount(String user, Object source, Object... args);

   /*
    * This logger is for message production and consumption and is on the hot path so enabled independently
    *
    * */
   //hot path log using a different logger
   static void coreSendMessage(Subject user, String remoteAddress, String messageToString, Object context) {
      MESSAGE_LOGGER.logCoreSendMessage(getCaller(user, remoteAddress), messageToString, context);
   }

  
   @LogMessage(id = 601500, value = "User {0} is sending a message {1}, with Context: {2}", level = LogMessage.Level.INFO)
   void logCoreSendMessage(String user, String messageToString, Object context);

   //hot path log using a different logger
   static void coreConsumeMessage(Subject user, String remoteAddress, String queue, String message) {
      MESSAGE_LOGGER.consumeMessage(getCaller(user, remoteAddress), queue, message);
   }

  
   @LogMessage(id = 601501, value = "User {0} is consuming a message from {1}: {2}", level = LogMessage.Level.INFO)
   void consumeMessage(String user, String address, String message);

   //hot path log using a different logger
   static void coreAcknowledgeMessage(Subject user, String remoteAddress, String queue, String message) {
      MESSAGE_LOGGER.acknowledgeMessage(getCaller(user, remoteAddress), queue, message);
   }

  
   @LogMessage(id = 601502, value = "User {0} is acknowledging a message from {1}: {2}", level = LogMessage.Level.INFO)
   void acknowledgeMessage(String user, String queue, String message);

   /*
    * This logger is focused on user interaction from the console or thru resource specific functions in the management layer/JMX
    * */

   static void createAddressSuccess(String name, String routingTypes) {
      RESOURCE_LOGGER.createAddressSuccess(getCaller(), name, routingTypes);
   }

  
   @LogMessage(id = 601701, value = "User {0} successfully created Address: {1} with routing types {2}", level = LogMessage.Level.INFO)
   void createAddressSuccess(String user, String name, String routingTypes);

   static void createAddressFailure(String name, String routingTypes) {
      RESOURCE_LOGGER.createAddressFailure(getCaller(), name, routingTypes);
   }

  
   @LogMessage(id = 601702, value = "User {0} failed to created Address: {1} with routing types {2}", level = LogMessage.Level.INFO)
   void createAddressFailure(String user, String name, String routingTypes);

   static void updateAddressSuccess(String name, String routingTypes) {
      RESOURCE_LOGGER.updateAddressSuccess(getCaller(), name, routingTypes);
   }

  
   @LogMessage(id = 601703, value = "User {0} successfully updated Address: {1} with routing types {2}", level = LogMessage.Level.INFO)
   void updateAddressSuccess(String user, String name, String routingTypes);

   static void updateAddressFailure(String name, String routingTypes) {
      RESOURCE_LOGGER.updateAddressFailure(getCaller(), name, routingTypes);
   }

  
   @LogMessage(id = 601704, value = "User {0} successfully updated Address: {1} with routing types {2}", level = LogMessage.Level.INFO)
   void updateAddressFailure(String user, String name, String routingTypes);

   static void deleteAddressSuccess(String name) {
      RESOURCE_LOGGER.deleteAddressSuccess(getCaller(), name);
   }

  
   @LogMessage(id = 601705, value = "User {0} successfully deleted Address: {1}", level = LogMessage.Level.INFO)
   void deleteAddressSuccess(String user, String name);


   static void deleteAddressFailure(String name) {
      RESOURCE_LOGGER.deleteAddressFailure(getCaller(), name);
   }

  
   @LogMessage(id = 601706, value = "User {0} failed to deleted Address: {1}", level = LogMessage.Level.INFO)
   void deleteAddressFailure(String user, String name);

   static void createQueueSuccess(String name, String address, String routingType) {
      RESOURCE_LOGGER.createQueueSuccess(getCaller(), name, address, routingType);
   }

  
   @LogMessage(id = 601707, value = "User {0} successfully created Queue: {1} on Address: {2} with routing type {3}", level = LogMessage.Level.INFO)
   void createQueueSuccess(String user, String name, String address, String routingType);

   static void createQueueFailure(String name, String address, String routingType) {
      RESOURCE_LOGGER.createQueueFailure(getCaller(), name, address, routingType);
   }

  
   @LogMessage(id = 601708, value = "User {0} failed to create Queue: {1} on Address: {2} with routing type {3}", level = LogMessage.Level.INFO)
   void createQueueFailure(String user, String name, String address, String routingType);

   static void updateQueueSuccess(String name, String routingType) {
      RESOURCE_LOGGER.updateQueueSuccess(getCaller(), name, routingType);
   }

  
   @LogMessage(id = 601709, value = "User {0} successfully updated Queue: {1} with routing type {2}", level = LogMessage.Level.INFO)
   void updateQueueSuccess(String user, String name, String routingType);

   static void updateQueueFailure(String name, String routingType) {
      RESOURCE_LOGGER.updateQueueFailure(getCaller(), name, routingType);
   }

  
   @LogMessage(id = 601710, value = "User {0} failed to update Queue: {1} with routing type {2}", level = LogMessage.Level.INFO)
   void updateQueueFailure(String user, String name, String routingType);


   static void destroyQueueSuccess(String name) {
      RESOURCE_LOGGER.destroyQueueSuccess(getCaller(), name);
   }

  
   @LogMessage(id = 601711, value = "User {0} successfully deleted Queue: {1}", level = LogMessage.Level.INFO)
   void destroyQueueSuccess(String user, String name);

   static void destroyQueueFailure(String name) {
      RESOURCE_LOGGER.destroyQueueFailure(getCaller(), name);
   }

  
   @LogMessage(id = 601712, value = "User {0} failed to delete Queue: {1}", level = LogMessage.Level.INFO)
   void destroyQueueFailure(String user, String name);

   static void removeMessagesSuccess(int removed, String queue) {
      RESOURCE_LOGGER.removeMessagesSuccess(getCaller(), removed, queue);
   }

  
   @LogMessage(id = 601713, value = "User {0} has removed {1} messages from Queue: {2}", level = LogMessage.Level.INFO)
   void removeMessagesSuccess(String user, int removed, String queue);

   static void removeMessagesFailure(String queue) {
      RESOURCE_LOGGER.removeMessagesFailure(getCaller(), queue);
   }

  
   @LogMessage(id = 601714, value = "User {0} failed to remove messages from Queue: {1}", level = LogMessage.Level.INFO)
   void removeMessagesFailure(String user, String queue);

   static void userSuccesfullyAuthenticatedInAudit(Subject subject, String remoteAddress) {
      RESOURCE_LOGGER.userSuccesfullyAuthenticated(getCaller(subject, remoteAddress));
   }

   static void userSuccesfullyAuthenticatedInAudit(Subject subject) {
      userSuccesfullyAuthenticatedInAudit(subject, null);
   }

  
   @LogMessage(id = 601715, value = "User {0} successfully authenticated", level = LogMessage.Level.INFO)
   void userSuccesfullyAuthenticated(String caller);


   static void userFailedAuthenticationInAudit(String reason) {
      RESOURCE_LOGGER.userFailedAuthentication(getCaller(), reason);
   }

   static void userFailedAuthenticationInAudit(Subject subject, String reason) {
      RESOURCE_LOGGER.userFailedAuthentication(getCaller(subject, null), reason);
   }

  
   @LogMessage(id = 601716, value = "User {0} failed authentication, reason: {1}", level = LogMessage.Level.INFO)
   void userFailedAuthentication(String user, String reason);

   static void objectInvokedSuccessfully(ObjectName objectName, String operationName) {
      RESOURCE_LOGGER.objectInvokedSuccessfully(getCaller(), objectName, operationName);
   }

  
   @LogMessage(id = 601717, value = "User {0} accessed {2} on management object {1}", level = LogMessage.Level.INFO)
   void objectInvokedSuccessfully(String caller, ObjectName objectName, String operationName);


   static void objectInvokedFailure(ObjectName objectName, String operationName) {
      RESOURCE_LOGGER.objectInvokedFailure(getCaller(), objectName, operationName);
   }

  
   @LogMessage(id = 601718, value = "User {0} does not have correct role to access {2} on management object {1}", level = LogMessage.Level.INFO)
   void objectInvokedFailure(String caller, ObjectName objectName, String operationName);

   static void pauseQueueSuccess(String queueName) {
      RESOURCE_LOGGER.pauseQueueSuccess(getCaller(), queueName);
   }

  
   @LogMessage(id = 601719, value = "User {0} has paused queue {1}", level = LogMessage.Level.INFO)
   void pauseQueueSuccess(String user, String queueName);


   static void pauseQueueFailure(String queueName) {
      RESOURCE_LOGGER.pauseQueueFailure(getCaller(), queueName);
   }

  
   @LogMessage(id = 601720, value = "User {0} failed to pause queue {1}", level = LogMessage.Level.INFO)
   void pauseQueueFailure(String user, String queueName);


   static void resumeQueueSuccess(String queueName) {
      RESOURCE_LOGGER.resumeQueueSuccess(getCaller(), queueName);
   }

  
   @LogMessage(id = 601721, value = "User {0} has resumed queue {1}", level = LogMessage.Level.INFO)
   void resumeQueueSuccess(String user, String queueName);


   static void resumeQueueFailure(String queueName) {
      RESOURCE_LOGGER.pauseQueueFailure(getCaller(), queueName);
   }

  
   @LogMessage(id = 601722, value = "User {0} failed to resume queue {1}", level = LogMessage.Level.INFO)
   void resumeQueueFailure(String user, String queueName);

   static void sendMessageSuccess(String queueName, String user) {
      RESOURCE_LOGGER.sendMessageSuccess(getCaller(), queueName, user);
   }

  
   @LogMessage(id = 601723, value = "User {0} sent message to {1} as user {2}", level = LogMessage.Level.INFO)
   void sendMessageSuccess(String user, String queueName, String sendUser);

   static void sendMessageFailure(String queueName, String user) {
      RESOURCE_LOGGER.sendMessageFailure(getCaller(), queueName, user);
   }

  
   @LogMessage(id = 601724, value = "User {0} failed to send message to {1} as user {2}", level = LogMessage.Level.INFO)
   void sendMessageFailure(String user, String queueName, String sendUser);

   static void browseMessagesSuccess(String queueName, int numMessages) {
      RESOURCE_LOGGER.browseMessagesSuccess(getCaller(), queueName, numMessages);
   }

  
   @LogMessage(id = 601725, value = "User {0} browsed {2} messages from queue {1}", level = LogMessage.Level.INFO)
   void browseMessagesSuccess(String user, String queueName, int numMessages);

   static void browseMessagesFailure(String queueName) {
      RESOURCE_LOGGER.browseMessagesFailure(getCaller(), queueName);
   }

  
   @LogMessage(id = 601726, value = "User {0} failed to browse messages from queue {1}", level = LogMessage.Level.INFO)
   void browseMessagesFailure(String user, String queueName);

   static void updateDivert(Object source, Object... args) {
      BASE_LOGGER.updateDivert(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601727, value = "User {0} is updating a divert on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void updateDivert(String user, Object source, Object... args);

   static void isEnabled(Object source) {
      BASE_LOGGER.isEnabled(getCaller(), source);
   }

  
   @LogMessage(id = 601728, value = "User {0} is getting enabled property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isEnabled(String user, Object source, Object... args);

   static void disable(Object source, Object... args) {
      BASE_LOGGER.disable(getCaller(), source, arrayToString(args));
   }

  
   @LogMessage(id = 601729, value = "User {0} is disabling on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void disable(String user, Object source, Object... args);

   static void enable(Object source) {
      BASE_LOGGER.resume(getCaller(), source);
   }

  
   @LogMessage(id = 601730, value = "User {0} is enabling on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void enable(String user, Object source, Object... args);

   static void pauseAddressSuccess(String queueName) {
      RESOURCE_LOGGER.pauseAddressSuccess(getCaller(), queueName);
   }

  
   @LogMessage(id = 601731, value = "User {0} has paused address {1}", level = LogMessage.Level.INFO)
   void pauseAddressSuccess(String user, String queueName);


   static void pauseAddressFailure(String queueName) {
      RESOURCE_LOGGER.pauseAddressFailure(getCaller(), queueName);
   }

  
   @LogMessage(id = 601732, value = "User {0} failed to pause address {1}", level = LogMessage.Level.INFO)
   void pauseAddressFailure(String user, String queueName);


   static void resumeAddressSuccess(String queueName) {
      RESOURCE_LOGGER.resumeAddressSuccess(getCaller(), queueName);
   }

  
   @LogMessage(id = 601733, value = "User {0} has resumed address {1}", level = LogMessage.Level.INFO)
   void resumeAddressSuccess(String user, String queueName);


   static void resumeAddressFailure(String queueName) {
      RESOURCE_LOGGER.resumeAddressFailure(getCaller(), queueName);
   }

  
   @LogMessage(id = 601734, value = "User {0} failed to resume address {1}", level = LogMessage.Level.INFO)
   void resumeAddressFailure(String user, String queueName);

   static void isGroupRebalancePauseDispatch(Object source) {
      BASE_LOGGER.isGroupRebalancePauseDispatch(getCaller(), source);
   }

  
   @LogMessage(id = 601735, value = "User {0} is getting group rebalance pause dispatch property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isGroupRebalancePauseDispatch(String user, Object source, Object... args);

   static void getAuthenticationCacheSize(Object source) {
      BASE_LOGGER.getAuthenticationCacheSize(getCaller(), source);
   }

  
   @LogMessage(id = 601736, value = "User {0} is getting authentication cache size on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getAuthenticationCacheSize(String user, Object source, Object... args);

   static void getAuthorizationCacheSize(Object source) {
      BASE_LOGGER.getAuthorizationCacheSize(getCaller(), source);
   }

  
   @LogMessage(id = 601737, value = "User {0} is getting authorization cache size on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getAuthorizationCacheSize(String user, Object source, Object... args);

   static void listBrokerConnections() {
      BASE_LOGGER.listBrokerConnections(getCaller());
   }

  
   @LogMessage(id = 601738, value = "User {0} is requesting a list of broker connections", level = LogMessage.Level.INFO)
   void listBrokerConnections(String user);

   static void stopBrokerConnection(String name) {
      BASE_LOGGER.stopBrokerConnection(getCaller(), name);
   }

  
   @LogMessage(id = 601739, value = "User {0} is requesting to stop broker connection {1}", level = LogMessage.Level.INFO)
   void stopBrokerConnection(String user, String name);

   static void startBrokerConnection(String name) {
      BASE_LOGGER.startBrokerConnection(getCaller(), name);
   }

  
   @LogMessage(id = 601740, value = "User {0} is requesting to start broker connection {1}", level = LogMessage.Level.INFO)
   void startBrokerConnection(String user, String name);

   static void getAddressCount(Object source) {
      BASE_LOGGER.getAddressCount(getCaller(), source);
   }

  
   @LogMessage(id = 601741, value = "User {0} is getting address count on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getAddressCount(String user, Object source, Object... args);

   static void getQueueCount(Object source) {
      BASE_LOGGER.getQueueCount(getCaller(), source);
   }

  
   @LogMessage(id = 601742, value = "User {0} is getting the queue count on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getQueueCount(String user, Object source, Object... args);

   static void lastValueKey(Object source) {
      BASE_LOGGER.lastValueKey(getCaller(), source);
   }

  
   @LogMessage(id = 601743, value = "User {0} is getting last-value-key property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void lastValueKey(String user, Object source, Object... args);

   static void consumersBeforeDispatch(Object source) {
      BASE_LOGGER.consumersBeforeDispatch(getCaller(), source);
   }

  
   @LogMessage(id = 601744, value = "User {0} is getting consumers-before-dispatch property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void consumersBeforeDispatch(String user, Object source, Object... args);

   static void delayBeforeDispatch(Object source) {
      BASE_LOGGER.delayBeforeDispatch(getCaller(), source);
   }

  
   @LogMessage(id = 601745, value = "User {0} is getting delay-before-dispatch property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void delayBeforeDispatch(String user, Object source, Object... args);

   static void isInternal(Object source) {
      BASE_LOGGER.isInternal(getCaller(), source);
   }

  
   @LogMessage(id = 601746, value = "User {0} is getting internal property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isInternal(String user, Object source, Object... args);

   static void isAutoCreated(Object source) {
      BASE_LOGGER.isAutoCreated(getCaller(), source);
   }

  
   @LogMessage(id = 601747, value = "User {0} is getting auto-created property on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void isAutoCreated(String user, Object source, Object... args);

   static void getMaxRetryInterval(Object source) {
      BASE_LOGGER.getMaxRetryInterval(getCaller(), source);
   }

  
   @LogMessage(id = 601748, value = "User {0} is getting max retry interval on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getMaxRetryInterval(String user, Object source, Object... args);

   static void getActivationSequence(Object source) {
      BASE_LOGGER.getActivationSequence(getCaller(), source);
   }

  
   @LogMessage(id = 601749, value = "User {0} is getting activation sequence on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getActivationSequence(String user, Object source, Object... args);

   static void purge(Object source) {
      RESOURCE_LOGGER.purge(getCaller(), source);
   }

  
   @LogMessage(id = 601750, value = "User {0} is purging target resource: {1} {2}", level = LogMessage.Level.INFO)
   void purge(String user, Object source, Object... args);


   static void purgeAddressSuccess(String queueName) {
      RESOURCE_LOGGER.purgeAddressSuccess(getCaller(), queueName);
   }

  
   @LogMessage(id = 601751, value = "User {0} has purged address {1}", level = LogMessage.Level.INFO)
   void purgeAddressSuccess(String user, String queueName);


   static void purgeAddressFailure(String queueName) {
      RESOURCE_LOGGER.purgeAddressFailure(getCaller(), queueName);
   }

  
   @LogMessage(id = 601752, value = "User {0} failed to purge address {1}", level = LogMessage.Level.INFO)
   void purgeAddressFailure(String user, String queueName);

   static void getAddressLimitPercent(Object source) {
      BASE_LOGGER.getAddressLimitPercent(getCaller(), source);
   }

  
   @LogMessage(id = 601753, value = "User {0} is getting address limit %  on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getAddressLimitPercent(String user, Object source, Object... args);

   static void block(Object source) {
      BASE_LOGGER.block(getCaller(), source);
   }

  
   @LogMessage(id = 601754, value = "User {0} is blocking target resource: {1}", level = LogMessage.Level.INFO)
   void block(String user, Object source);

   static void unBlock(Object source) {
      BASE_LOGGER.unBlock(getCaller(), source);
   }

  
   @LogMessage(id = 601755, value = "User {0} is unblocking target resource: {1}", level = LogMessage.Level.INFO)
   void unBlock(String user, Object source);

   static void getAcceptors(Object source) {
      BASE_LOGGER.getAcceptors(getCaller(), source);
   }

  
   @LogMessage(id = 601756, value = "User {0} is getting acceptors on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getAcceptors(String user, Object source, Object... args);

   static void getAcceptorsAsJSON(Object source) {
      BASE_LOGGER.getAcceptorsAsJSON(getCaller(), source);
   }

  
   @LogMessage(id = 601757, value = "User {0} is getting acceptors as json on target resource: {1} {2}", level = LogMessage.Level.INFO)
   void getAcceptorsAsJSON(String user, Object source, Object... args);

   static void schedulePageCleanup(Object source) {
      BASE_LOGGER.schedulePageCleanup(getCaller(), source);
   }

   @LogMessage(level = Logger.Level.INFO)
   @Message(id = 601758, value = "User {0} is calling schedulePageCleanup on address: {1}", format = Message.Format.MESSAGE_FORMAT)
   void schedulePageCleanup(String user, Object address);
}
