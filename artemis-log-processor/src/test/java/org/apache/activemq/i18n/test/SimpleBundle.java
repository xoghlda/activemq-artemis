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

package org.apache.activemq.i18n.test;

import org.apache.activemq.artemis.logprocessor.CodeFactory;
import org.apache.activemq.artemis.logprocessor.annotation.ArtemisBundle;
import org.apache.activemq.artemis.logprocessor.annotation.LogMessage;
import org.apache.activemq.artemis.logprocessor.annotation.Message;

@ArtemisBundle(projectCode = "TST")
public interface SimpleBundle {

   SimpleBundle MESSAGES = CodeFactory.getBundle(SimpleBundle.class);

   @Message(id = 1, value = "Test")
   String simpleTest();

   @Message(id = 2, value = "V{0}-{1}")
   String parameters(int value, String value2);

   @Message(id = 3, value = "EX")
   Exception someException();

   @Message(id = 4, value = "EX-{0}")
   Exception someExceptionParameter(String parameter);

   @LogMessage(id = 5, value = "This is a print!!!")
   void printMessage();

   @LogMessage(id = 6, value = "This is a print!!! {0}")
   void printMessage(int nr);
}
