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
package org.apache.activemq.artemis.journal;

import org.apache.activemq.artemis.core.journal.impl.JournalFile;
import org.apache.activemq.artemis.logprocessor.CodeFactory;
import org.apache.activemq.artemis.logprocessor.annotation.LogBundle;
import org.apache.activemq.artemis.logprocessor.annotation.LogMessage;

/**
 * Logger Code 14
 */
@LogBundle(projectCode = "AMQ")
public interface ActiveMQJournalLogger {

   /**
    * The journal logger.
    */
   ActiveMQJournalLogger LOGGER = CodeFactory.getCodeClass(ActiveMQJournalLogger.class, ActiveMQJournalLogger.class.getPackage().getName());

   @LogMessage(id = 141000, value = "*** running direct journal blast: {0}", level = LogMessage.Level.INFO)
   void runningJournalBlast(Integer numIts);

   @LogMessage(id = 141002, value = "starting thread for sync speed test", level = LogMessage.Level.INFO)
   void startingThread();

   @LogMessage(id = 141003, value = "Write rate = {0} bytes / sec or {1} MiB / sec", level = LogMessage.Level.INFO)
   void writeRate(Double rate, Long l);

   @LogMessage(id = 141004, value = "Flush rate = {0} flushes / sec", level = LogMessage.Level.INFO)
   void flushRate(Double rate);

   @LogMessage(id = 141005, value = "Check Data Files:", level = LogMessage.Level.INFO)
   void checkFiles();

   @LogMessage(id = 141006, value = "Sequence out of order on journal", level = LogMessage.Level.INFO)
   void seqOutOfOrder();

   @LogMessage(id = 141007, value = "Current File on the journal is <= the sequence file.getFileID={0} on the dataFiles" + "\nCurrentfile.getFileId={1} while the file.getFileID()={2}" + "\nIs same = ({3})", level = LogMessage.Level.INFO)
   void currentFile(Long fileID, Long id, Long fileFileID, Boolean b);

   @LogMessage(id = 141008, value = "Free File ID out of order", level = LogMessage.Level.INFO)
   void fileIdOutOfOrder();

   @LogMessage(id = 141009, value = "A Free File is less than the maximum data", level = LogMessage.Level.INFO)
   void fileTooSmall();

   @LogMessage(id = 141010, value = "Initialising JDBC data source {0} with properties {1}", level = LogMessage.Level.INFO)
   void initializingJdbcDataSource(String dataSourceClassName, String dataSourceProperties);

   @LogMessage(id = 142000, value = "You have a native library with a different version than expected", level = LogMessage.Level.WARN)
   void incompatibleNativeLibrary();

   @LogMessage(id = 142001, value = "Could not get lock after 60 seconds on closing Asynchronous File: {0}", level = LogMessage.Level.WARN)
   void couldNotGetLock(String fileName);

   @LogMessage(id = 142002, value = "Asynchronous File: {0} being finalized with opened state", level = LogMessage.Level.WARN)
   void fileFinalizedWhileOpen(String fileName);

   @LogMessage(id = 142003, value = "AIO Callback Error: {0}", level = LogMessage.Level.WARN)
   void callbackError(String error);

   @LogMessage(id = 142004, value = "Inconsistency during compacting: CommitRecord ID = {0} for an already committed transaction during compacting", level = LogMessage.Level.WARN)
   void inconsistencyDuringCompacting(Long transactionID);

   @LogMessage(id = 142005, value = "Inconsistency during compacting: Delete record being read on an existent record (id={0})", level = LogMessage.Level.WARN)
   void inconsistencyDuringCompactingDelete(Long recordID);

   @LogMessage(id = 142006, value = "Could not find add Record information for record {0} during compacting", level = LogMessage.Level.WARN)
   void compactingWithNoAddRecord(Long id);

   @LogMessage(id = 142007, value = "Can not find record {0} during compact replay", level = LogMessage.Level.WARN)
   void noRecordDuringCompactReplay(Long id);

   @LogMessage(id = 142008, value = "Could not remove file {0} from the list of data files", level = LogMessage.Level.WARN)
   void couldNotRemoveFile(JournalFile file);

   @LogMessage(id = 142009, value = "*******************************************************************************************************************************\nThe File Storage Attic is full, as the file {0}  does not have the configured size, and the file will be removed\n*******************************************************************************************************************************", level = LogMessage.Level.WARN)
   void deletingFile(JournalFile file);

   @LogMessage(id = 142010, value = "Failed to add file to opened files queue: {0}. This should NOT happen!", level = LogMessage.Level.WARN)
   void failedToAddFile(JournalFile nextOpenedFile);

   @LogMessage(id = 142011, value = "Error on reading compacting for {0}", level = LogMessage.Level.WARN)
   void compactReadError(JournalFile file);

   @LogMessage(id = 142012, value = "Couldn''t find tx={0} to merge after compacting", level = LogMessage.Level.WARN)
   void compactMergeError(Long id);

   @LogMessage(id = 142013, value = "Prepared transaction {0} was not considered completed, it will be ignored", level = LogMessage.Level.WARN)
   void preparedTXIncomplete(Long id);

   @LogMessage(id = 142014, value = "Transaction {0} is missing elements so the transaction is being ignored", level = LogMessage.Level.WARN)
   void txMissingElements(Long id);

   @LogMessage(id = 142015, value = "Uncommitted transaction with id {0} found and discarded", level = LogMessage.Level.WARN)
   void uncomittedTxFound(Long id);

   @LogMessage(id = 142016, value = "Could not stop compactor executor after 120 seconds", level = LogMessage.Level.WARN)
   void couldNotStopCompactor();

   @LogMessage(id = 142017, value = "Could not stop journal executor after 60 seconds", level = LogMessage.Level.WARN)
   void couldNotStopJournalExecutor();

   @LogMessage(id = 142018, value = "Temporary files were left unattended after a crash on journal directory, deleting invalid files now", level = LogMessage.Level.WARN)
   void tempFilesLeftOpen();

   @LogMessage(id = 142019, value = "Deleting orphaned file {0}", level = LogMessage.Level.WARN)
   void deletingOrphanedFile(String fileToDelete);

   @LogMessage(id = 142020, value = "Could not get lock after 60 seconds on closing Asynchronous File: {0}", level = LogMessage.Level.WARN)
   void errorClosingFile(String fileToDelete);

   @LogMessage(id = 142021, value = "Error on IO callback, {0}", level = LogMessage.Level.WARN)
   void errorOnIOCallback(String errorMessage);

   @LogMessage(id = 142022, value = "Timed out on AIO poller shutdown", level = LogMessage.Level.WARN)
   void timeoutOnPollerShutdown(Exception e);

   @LogMessage(id = 142023, value = "Executor on file {0} couldn''t complete its tasks in 60 seconds.", level = LogMessage.Level.WARN)
   void couldNotCompleteTask(Exception e, String name);

   @LogMessage(id = 142024, value = "Error completing callback", level = LogMessage.Level.WARN)
   void errorCompletingCallback(Throwable e);

   @LogMessage(id = 142025, value = "Error calling onError callback", level = LogMessage.Level.WARN)
   void errorCallingErrorCallback(Throwable e);

   @LogMessage(id = 142026, value = "Timed out on AIO writer shutdown", level = LogMessage.Level.WARN)
   void timeoutOnWriterShutdown(Throwable e);

   @LogMessage(id = 142027, value = "Error on writing data! {0} code - {1}", level = LogMessage.Level.WARN)
   void errorWritingData(Throwable e, String errorMessage, Integer errorCode);

   @LogMessage(id = 142028, value = "Error replaying pending commands after compacting", level = LogMessage.Level.WARN)
   void errorReplayingCommands(Throwable e);

   @LogMessage(id = 142029, value = "Error closing file", level = LogMessage.Level.WARN)
   void errorClosingFile(Throwable e);

   @LogMessage(id = 142030, value = "Could not open a file in 60 Seconds", level = LogMessage.Level.WARN)
   void errorOpeningFile(Throwable e);

   @LogMessage(id = 142031, value = "Error retrieving ID part of the file name {0}", level = LogMessage.Level.WARN)
   void errorRetrievingID(Throwable e, String fileName);

   @LogMessage(id = 142032, value = "Error reading journal file", level = LogMessage.Level.WARN)
   void errorReadingFile(Throwable e);

   @LogMessage(id = 142033, value = "Error reinitializing file {0}", level = LogMessage.Level.WARN)
   void errorReinitializingFile(Throwable e, JournalFile file);

   @LogMessage(id = 142034, value = "Exception on submitting write", level = LogMessage.Level.WARN)
   void errorSubmittingWrite(Throwable e);

   @LogMessage(id = 142035, value = "Could not stop journal append executor after 60 seconds", level = LogMessage.Level.WARN)
   void couldNotStopJournalAppendExecutor();

   @LogMessage(id = 144000, value = "Failed to delete file {0}", level = LogMessage.Level.ERROR)
   void errorDeletingFile(Object e);

   @LogMessage(id = 144001, value = "Error starting poller", level = LogMessage.Level.ERROR)
   void errorStartingPoller(Exception e);

   @LogMessage(id = 144002, value = "Error pushing opened file", level = LogMessage.Level.ERROR)
   void errorPushingFile(Exception e);

   @LogMessage(id = 144003, value = "Error compacting", level = LogMessage.Level.ERROR)
   void errorCompacting(Throwable e);

   @LogMessage(id = 144004, value = "Error scheduling compacting", level = LogMessage.Level.ERROR)
   void errorSchedulingCompacting(Throwable e);

   @LogMessage(id = 144005, value = "Failed to performance blast", level = LogMessage.Level.ERROR)
   void failedToPerfBlast(Throwable e);

   @LogMessage(id = 144006, value = "IOError code {0}, {1}", level = LogMessage.Level.ERROR)
   void ioError(int errorCode, String errorMessage);

   @LogMessage(id = 144007, value = "Ignoring journal file {0}: file is shorter then minimum header size. This file is being removed.", level = LogMessage.Level.WARN)
   void ignoringShortFile(String fileName);

   @LogMessage(id = 144008, value = "*******************************************************************************************************************************\nFile {0}: was moved under attic, please review it and remove it.\n*******************************************************************************************************************************", level = LogMessage.Level.WARN)
   void movingFileToAttic(String fileName);

   @LogMessage(id = 144009, value = "Could not get a file in {0} seconds, System will retry the open but you may see increased latency in your system", level = LogMessage.Level.WARN)
   void cantOpenFileTimeout(long timeout);

   @LogMessage(id = 144010, value = "Critical IO Exception happened: {0}", level = LogMessage.Level.WARN)
   void criticalIO(String message, Exception error);

}
