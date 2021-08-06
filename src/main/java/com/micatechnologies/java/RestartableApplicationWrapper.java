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

package com.micatechnologies.java;

import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Abstract application wrapper class which provides simple exit and restart functionality for the
 * implemented execution method, {@link #runWrappedApplication(String[])}.
 *
 * @author Mica Technologies
 * @version 2021.1
 * @since 2021.1
 */
public abstract class RestartableApplicationWrapper {

    /**
     * Status code returned on application exit which indicates that execution was completed
     * successfully.
     *
     * @since 2021.1
     */
    private static final int RESTARTABLE_APPLICATION_SUCCESS_EXIT_CODE = 0;

    /**
     * Name of the thread created to run the implemented wrapped application execution method,
     * {@link #runWrappedApplication(String[])}.
     *
     * @since 2021.1
     */
    private static final String RESTARTABLE_APPLICATION_THREAD_NAME = "restartable-main";

    /**
     * Boolean flag indicating if the wrapped application should be restarted (instead of stopped).
     *
     * @since 2021.1
     */
    private final AtomicBoolean applicationRestartRequested = new AtomicBoolean(false);

    /**
     * Countdown latch used to await wrapped application exit request before exiting main thread.
     *
     * @since 2021.1
     */
    private CountDownLatch applicationExitLatch;

    /**
     * Main {@link RestartableApplicationWrapper} execution method. This method executes the
     * restartable application wrapper thread with the implementation specified by
     * {@link #runWrappedApplication(String[])}.
     *
     * @param args application arguments
     * @since 2021.1
     */
    public void execute(String[] args, PrintStream logPrintStream) {
        // Try to execute, catching any exception to report as the application exit code.
        int executionExitCode = RESTARTABLE_APPLICATION_SUCCESS_EXIT_CODE;
        try {
            do {
                // Reset restart flag to false and create exit latch
                applicationRestartRequested.set(false);
                applicationExitLatch = new CountDownLatch(1);

                // Run app
                Thread appThread = new Thread(() -> runWrappedApplication(args));
                appThread.setName(RESTARTABLE_APPLICATION_THREAD_NAME);
                appThread.start();

                // Wait for exit latch
                applicationExitLatch.await();
                cleanupWrappedApplication();
                appThread.join();
            } while (applicationRestartRequested.get());
        } catch (Exception e) {
            // Store exception hash code for use as application exit code and print stack trace
            executionExitCode = e.hashCode();
            e.printStackTrace(logPrintStream);
        }
        System.exit(executionExitCode);
    }

    /**
     * Exits the wrapped application.
     *
     * @since 2021.1
     */
    public void exitWrappedApplication() {
        // Set restart flag to false
        applicationRestartRequested.set(false);

        // Release exit latch
        applicationExitLatch.countDown();
    }

    /**
     * Restarts the wrapped application.
     *
     * @since 2021.1
     */
    public void restartWrappedApplication() {
        // Set restart flag to false
        applicationRestartRequested.set(true);

        // Release exit latch
        applicationExitLatch.countDown();
    }

    /**
     * Abstract method which must be implemented to execute the wrapped application. In most
     * scenarios, this method will contain the previous contents of the application's {@code
     * public static void main(String[] args)} method. The application's {@code public static
     * void main(String[] args)} method should invoke the {@link RestartableApplicationWrapper} by
     * calling {@link #execute(String[], PrintStream)}.
     *
     * @param args application arguments
     * @since 2021.1
     */
    abstract void runWrappedApplication(String[] args);

    /**
     * Abstract method which must be implemented to cleanup the wrapped application. Object cleanup
     * or closing which is required prior to application exit should be performed in this method.
     * This method must be non-blocking and should not wait at any point of execution.
     *
     * @since 2021.1
     */
    abstract void cleanupWrappedApplication();

}
