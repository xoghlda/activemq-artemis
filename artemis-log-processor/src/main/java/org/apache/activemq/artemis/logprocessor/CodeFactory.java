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

import java.lang.reflect.Field;
import java.security.PrivilegedAction;

import static java.security.AccessController.doPrivileged;

public class CodeFactory {

   public static <T> T getBundle(final Class<T> type) {
      return doPrivileged(new PrivilegedAction<T>() {
         public T run() {
            try {
               String className = type.getName() + "_impl";
               System.out.println("Loading [" + className + "]");
               Class messageClass = Class.forName(className, true, type.getClassLoader()).asSubclass(type);

               Field field = messageClass.getField("INSTANCE");

               return type.cast(field.get(null));
            } catch (ClassNotFoundException e) {
               return null;
            } catch (NoSuchFieldException e) {
               throw new IllegalStateException(e.getMessage(), e);
            } catch (IllegalAccessException e) {
               e.printStackTrace();
               throw new IllegalStateException(e.getMessage(), e);
            }
         }
      });
   }

   public static <T> T getMessageLogger(Class<T> type) {
      return getBundle(type);
   }

}
