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
package org.apache.activemq.artemis.core.persistence.impl.journal.codec;

import org.apache.activemq.artemis.api.core.ActiveMQBuffer;
import org.apache.activemq.artemis.core.journal.EncodingSupport;
import org.apache.activemq.artemis.core.paging.cursor.PagePosition;
import org.apache.activemq.artemis.core.paging.cursor.impl.PagePositionImpl;
import org.apache.activemq.artemis.utils.DataConstants;

public class CursorAckRecordEncoding implements EncodingSupport {

   public CursorAckRecordEncoding(final long queueID, final PagePosition position) {
      this.queueID = queueID;
      this.position = position;
   }

   public CursorAckRecordEncoding() {
   }

   @Override
   public String toString() {
      return "CursorAckRecordEncoding [queueID=" + queueID + ", position=" + position + "]";
   }

   public long queueID;

   public PagePosition position;

   @Override
   public int getEncodeSize() {
      return DataConstants.SIZE_LONG + DataConstants.SIZE_LONG + DataConstants.SIZE_INT;
   }

   @Override
   public void encode(ActiveMQBuffer buffer) {
      buffer.writeLong(queueID);
      buffer.writeLong(position.getPageNr());
      buffer.writeInt(position.getMessageNr());
   }

   @Override
   public void decode(ActiveMQBuffer buffer) {
      queueID = buffer.readLong();
      long pageNR = buffer.readLong();
      int messageNR = buffer.readInt();
      this.position = new PagePositionImpl(pageNR, messageNR);
   }
}
