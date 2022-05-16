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

import org.apache.activemq.artemis.logprocessor.annotation.GetLogger;
import org.apache.activemq.artemis.logprocessor.annotation.LogBundle;
import org.apache.activemq.artemis.logprocessor.annotation.LogMessage;
import org.apache.activemq.artemis.logprocessor.annotation.Message;
import org.slf4j.Logger;

@LogBundle(projectCode = "TST")
public interface SimpleBundle {

   SimpleBundle MESSAGES = CodeFactory.getCodeClass(SimpleBundle.class);

   @Message(id = 1, value = "Test")
   String simpleTest();

   @Message(id = 2, value = "V{}-{}")
   String parameters(int value, String value2);

   @Message(id = 3, value = "EX")
   Exception someException();

   @Message(id = 4, value = "EX-{}")
   Exception someExceptionParameter(String parameter);

   @LogMessage(id = 5, value = "This is a print!!!", level = LogMessage.Level.WARN)
   void printMessage();

   @LogMessage(id = 6, value = "This is a print!!! {}", level = LogMessage.Level.WARN)
   void printMessage(int nr);

   @LogMessage(id = 7, value = "multi\nLine\nMessage", level = LogMessage.Level.WARN)
   void multiLines();

   @Message(id = 8, value = "EX{}")
   MyException someExceptionWithCause(String message, Exception myCause);

   @Message(id = 9, value = "{} {} {} {}")
   String abcd(String a, String b, String c, String d);

   @Message(id = 10, value = "{} {} {} {}")
   String objectsAbcd(MyObject a, MyObject b, MyObject c, MyObject d);

   @GetLogger
   Logger getLogger();
}
