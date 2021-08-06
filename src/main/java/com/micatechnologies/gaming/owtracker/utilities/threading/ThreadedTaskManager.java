/*
 * Copyright (c) 2021 Mica Technologies
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.micatechnologies.gaming.owtracker.utilities.threading;

import com.google.common.util.concurrent.AtomicDouble;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class for managing the multi-threaded execution of multiple tasks which track progress and report their result.
 *
 * @param <V> return type for results
 *
 * @author Mica Technologies
 * @version 1.0
 * @since 2021.3
 */
public abstract class ThreadedTaskManager< V >
{

    /**
     * List of {@link ThreadedTask}s which must be executed by this {@link ThreadedTaskManager}.
     *
     * @since 1.0
     */
    private final List< ThreadedTask< V > > taskList;

    /**
     * Title used for showing progress updates.
     *
     * @since 1.0
     */
    private final String progressTitle;

    /**
     * Counter used for tracking the total progress of tasks in this {@link ThreadedTaskManager}.
     *
     * @since 1.0
     */
    private final AtomicDouble totalProgress = new AtomicDouble();

    /**
     * Executor service used to invoke and complete each task in {@link #taskList}.
     *
     * @since 1.0
     */
    private final ExecutorService executorService = Executors.newFixedThreadPool( getThreadCount() );

    /**
     * Constructor for {@link ThreadedTaskManager} which requires the list of {@link ThreadedTask}s to complete, and the
     * title to display during progress updates.
     *
     * @param taskList      list of {@link ThreadedTask}s to complete
     * @param progressTitle title to display during progress updates
     *
     * @since 1.0
     */
    public ThreadedTaskManager( List< ThreadedTask< V > > taskList, String progressTitle ) {
        this.taskList = taskList;
        this.progressTitle = progressTitle;
    }

    /**
     * Method to start the asynchronous execution of the task list.
     *
     * @return list of future task results
     *
     * @since 1.0
     */
    public List< CompletableFuture< V > > start() {
        // Set task parent managers
        taskList.forEach( vThreadedTask -> vThreadedTask.setParentMultiThreadedMultiTaskManager( this ) );

        // Start tasks and get futures
        List< CompletableFuture< V > > completableFutureList = new ArrayList<>();
        taskList.forEach( vThreadedTask -> completableFutureList.add(
                CompletableFuture.supplyAsync( vThreadedTask, executorService ) ) );
        return completableFutureList;
    }

    /**
     * Method to start the asynchronous execution of the task list and wait for its completion.
     *
     * @return list of task results
     *
     * @since 1.0
     */
    public List< V > startAndAwait() throws ExecutionException, InterruptedException {
        List< CompletableFuture< V > > startAsyncResult = start();
        List< V > resultList = new ArrayList<>();
        for ( CompletableFuture< V > taskResult : startAsyncResult ) {
            resultList.add( taskResult.get() );
        }
        return resultList;
    }

    /**
     * Stops execution by shutting down the executor service which backs each task.
     *
     * @since 1.0
     */
    public void stop() {
        executorService.shutdownNow();
    }

    /**
     * Method for processing updated progress information from executing {@link ThreadedTask}s.
     *
     * @param progressText progress detail text
     * @param progress     progress value
     *
     * @since 1.0
     */
    protected void recieveThreadProgress( String progressText, double progress ) {
        // Divide progress by number of tasks in list
        double actualProgress = progress / ( double ) ( taskList.size() );

        // Add to total progress counter
        final double newTotalProgress = totalProgress.addAndGet( actualProgress );

        // Call progress update method
        onProgressUpdate( progressTitle, progressText, newTotalProgress );
    }

    /**
     * Method which returns an integer indicating the number of threads which the executor should use. This method may
     * be overridden by implementations.
     *
     * @return executor service thread count
     *
     * @since 1.0
     */
    protected int getThreadCount() {
        int threadCount = 3;
        try {
            // Get system and hardware information
            SystemInfo si = new SystemInfo();
            HardwareAbstractionLayer hal = si.getHardware();

            // Get core count
            threadCount = hal.getProcessor().getLogicalProcessorCount();
        }
        catch ( Exception e ) {
            Logger.logWarningSilent(
                    "Unable to detect number of CPU cores available on system. Using default launcher thread count of" +
                            " " +
                            threadCount +
                            "." );
            Logger.logThrowable( e );
        }
        return threadCount;
    }

    /**
     * Method which must update the user (via GUI, console, etc) with the specified progress information, including
     * title, detail text and value (0.0 - 1.0).
     *
     * @param progressTitle title to display during progress update
     * @param progressText  detail text to display during progress update
     * @param progress      total progress value of {@link ThreadedTask}s (0.0 - 1.0)
     *
     * @since 1.0
     */
    abstract void onProgressUpdate( String progressTitle, String progressText, double progress );
}
