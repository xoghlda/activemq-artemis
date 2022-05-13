/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.activemq.artemis.core.reload;

import java.util.logging.LogManager;

import org.apache.activemq.artemis.core.server.LoggingConfigurationFileReloader;
import org.apache.activemq.artemis.utils.ClassloadingUtil;
import org.apache.activemq.artemis.utils.SpawnedVMSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoggingConfigurationFileReloaderTest {

   private static final LogManager logManager = LogManager.getLogManager();
   private static final Logger root = LoggerFactory.getLogger("");
   private static final Logger test1 = LoggerFactory.getLogger("test1");
   private static final Logger test2 = LoggerFactory.getLogger("test2");
   private static final Logger test3 = LoggerFactory.getLogger("test3");

   public static void main(String[] args) {
      try {
         LoggingConfigurationFileReloaderTest test = new LoggingConfigurationFileReloaderTest();
         test.doTestA();
         System.exit(0);
      } catch (Throwable e) {
         System.exit(1);
      }
   }

   @Test
   public void testA() throws Exception {
      Process p = SpawnedVMSupport.spawnVM(LoggingConfigurationFileReloaderTest.class.getName());
      Assert.assertEquals(0, p.waitFor());
   }

   public void doTestA() throws Exception {

      /** This is making sure we won't mess with the configuration for other tests */
      validateInitialLoggers();

      LoggingConfigurationFileReloader loggingConfigurationFileReloader = new LoggingConfigurationFileReloader();
      loggingConfigurationFileReloader.reload(ClassloadingUtil.findResource("reload-logging-1.properties"));

      assertTrue(root.isErrorEnabled());
      assertTrue(root.isWarnEnabled());
      assertFalse(root.isInfoEnabled());
      assertFalse(root.isDebugEnabled());
      assertFalse(root.isTraceEnabled());

      assertTrue(test1.isErrorEnabled());
      assertTrue(test1.isWarnEnabled());
      assertTrue(test1.isInfoEnabled());
      assertTrue(test1.isDebugEnabled());
      assertTrue(test1.isTraceEnabled());

      assertTrue(test2.isErrorEnabled());
      assertFalse(test2.isWarnEnabled());
      assertFalse(test2.isInfoEnabled());
      assertFalse(test2.isDebugEnabled());
      assertFalse(test2.isTraceEnabled());

      loggingConfigurationFileReloader.reload(ClassloadingUtil.findResource("reload-logging-2.properties"));

      assertTrue(root.isErrorEnabled());
      assertFalse(root.isWarnEnabled());
      assertFalse(root.isInfoEnabled());
      assertFalse(root.isDebugEnabled());
      assertFalse(root.isTraceEnabled());

      assertTrue(test1.isErrorEnabled());
      assertTrue(test1.isWarnEnabled());
      assertFalse(test1.isInfoEnabled());
      assertFalse(test1.isDebugEnabled());
      assertFalse(test1.isTraceEnabled());

      assertTrue(test3.isErrorEnabled());
      assertTrue(test3.isWarnEnabled());
      assertTrue(test3.isInfoEnabled());
      assertTrue(test3.isDebugEnabled());
      assertFalse(test3.isTraceEnabled());
   }

   @Test
   public void testB() {
      validateInitialLoggers();
   }

   public void validateInitialLoggers() {
      // everything defaults to INFO
      assertTrue(root.isErrorEnabled());
      assertTrue(root.isWarnEnabled());
      assertFalse(root.isDebugEnabled());
      assertFalse(root.isTraceEnabled());

      assertTrue(test1.isErrorEnabled());
      assertTrue(test1.isWarnEnabled());
      assertFalse(test1.isDebugEnabled());
      assertFalse(test1.isTraceEnabled());

      assertTrue(test2.isErrorEnabled());
      assertTrue(test2.isWarnEnabled());
      assertFalse(test2.isDebugEnabled());
      assertFalse(test2.isTraceEnabled());
   }
}