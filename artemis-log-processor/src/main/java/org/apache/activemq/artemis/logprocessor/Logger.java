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

package org.apache.activemq.artemis.logprocessor;

/** I don't want the log processor to depend on the logger itself.
 *  the reason for that is I would need to configure log4j to be used during compilation time on the javac.
 *  Instead I will just use a simple Logger controlled by a boolean property.
 *
 *  Set an env variable ARTEMIS_PROCESSOR_LOGGER=true to see this logger output
 */
public class Logger {

   private static final boolean active;

   static {
      boolean activeResult;
      {
         try {
            String activeEnv = System.getenv("ARTEMIS_PROCESSOR_LOGGER");
            if (activeEnv != null) {
               activeResult = Boolean.parseBoolean(activeEnv);
            } else {
               activeResult = false;
            }
         } catch (Exception e) {
            e.printStackTrace();
            activeResult = false;
         }
      }
      active = activeResult;
   }

   public static final Logger LOGGER = new Logger();

   private Logger() {
      System.out.println("Active = " + active);
   }

   public static Logger getLogger() {
      return LOGGER;
   }

   public void debug(String output) {
      if (active) {
         System.out.println(output);
      }
   }

   public boolean isDebug() {
      return active;
   }


}
