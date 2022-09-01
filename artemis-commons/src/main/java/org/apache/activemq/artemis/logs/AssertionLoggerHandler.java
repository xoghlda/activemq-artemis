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

import java.util.logging.Level;


public class AssertionLoggerHandler {

   public void close() throws SecurityException {
   }

   public static boolean hasLevel(Level level) {
      return false;
   }

   public static boolean findText(long mstimeout, String... text) {
      return false;
   }

   /**
    * Find a line that contains the parameters passed as an argument
    *
    * @param text
    * @return
    */
   public static boolean findText(final String... text) {
      return false;
   }

   public static int countText(final String... text) {
      return 0;
   }

   public static boolean matchText(final String pattern) {
      return false;
   }

   public static final void clear() {
   }

   public static final void startCapture() {
   }

   /**
    *
    * @param individualMessages enables counting individual messages.
    */
   public static final void startCapture(boolean individualMessages) {
   }

   public static final void stopCapture() {
   }
}
