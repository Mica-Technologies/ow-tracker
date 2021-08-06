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

package com.micatechnologies.gaming.owtracker;

import com.micatechnologies.gaming.owtracker.consts.localization.LocalizationManager;
import com.micatechnologies.gaming.owtracker.gui.OWTrackerGuiController;
import com.micatechnologies.gaming.owtracker.consts.ApplicationConstants;
import com.micatechnologies.gaming.owtracker.consts.LocalPathConstants;
import com.micatechnologies.gaming.owtracker.files.LocalPathManager;
import com.micatechnologies.gaming.owtracker.utilities.DiscordRpcUtility;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Main application class, referred to as the application core. This class handles the startup and shutdown of the
 * application and applicable systems such as logging and rich presence.
 *
 * @author Mica Technologies
 * @version 2021.1
 * @since 2021.1
 */
public class AppCore {

    /**
     * Launcher application restart flag. This flag must be true for the application to start. If this flag is set when
     * the launcher closes, it will restart.
     */
    private static boolean restartFlag = true;

    private static CountDownLatch exitLatch;

    /**
     * Launcher application main method/entry point.
     *
     * @param args launcher arguments
     * @since 1.0
     */
    public static void main(String[] args) {
        while (restartFlag) {
            // Reset restart flag to false
            restartFlag = false;
            exitLatch = new CountDownLatch(1);

            // Apply system properties
            applySystemProperties();

            // Configure logging
            configureLogger();

            // DO GUI STUFF

            // Wait for exit latch
            try {
                exitLatch.await();
            } catch (InterruptedException e) {
                Logger.logError("The main thread was interrupted before receiving an exit signal. The application " +
                        "will be unable to restart!");
                Logger.logThrowable(e);
            }
        }
    }

    /**
     * Configure the launcher application to use the logging utility class for output to file and console.
     *
     * @since 2.0
     */
    public static void configureLogger() {
        BasicConfigurator.configure();
        Timestamp logTimeStamp = new Timestamp(System.currentTimeMillis());
        File logFile = SynchronizedFileManager.getSynchronizedFile(LocalPathManager.getLauncherLogFolderPath() +
                File.separator +
                ApplicationConstants.LAUNCHER_APPLICATION_NAME_TRIMMED +
                "_" +
                LocalPathConstants.LOG_FILE_NAME_DATE_FORMAT
                        .format(logTimeStamp) +
                LocalPathConstants.LOG_FILE_EXTENSION);
        try {
            Logger.initLogSys(logFile);
        } catch (IOException e) {
            Logger.logError(LocalizationManager.ERROR_CONFIGURING_LOG_SYSTEM_TEXT);
            Logger.logThrowable(e);
        }
    }

    /**
     * Applicationlies the desired system properties (JVM arguments) to the host runtime
     * environment.
     * Desired system properties are specified by the implementation of
     * {@link #getSystemProperties()}.
     *
     * @since 2021.1
     */
    private void applySystemProperties() {
        getSystemProperties().forEach(System::setProperty);
    }

    /**
     * Configures the logging system (Log4j2) using the properties file specified by
     * implementation of {@link #getLog4j2PropertiesFilePath()}.
     *
     * @since 2021.1
     */
    private void configureLog4j2() {
        PropertyConfigurator.configure(getLog4j2PropertiesFilePath());
    }



    abstract Map<String, String> getSystemProperties();

    abstract String getLog4j2PropertiesFilePath();

    /**
     * Performs launcher closing/clean up tasks necessary for application shut down or restart. This method must be able
     * to be called and complete without waiting at all times.
     *
     * @since 2.0
     */
    public static void cleanupApp() {
        Logger.logStd(LocalizationManager.PERFORMING_APP_CLEANUP_TEXT);
        try {
            DiscordRpcUtility.exit();
            OWTrackerGuiController.exit();
            Logger.shutdownLogSys();
        } catch (Exception ignored) {
        }
        Logger.logStd(LocalizationManager.FINISHED_APP_CLEANUP_TEXT);
    }

    /**
     * Performs launcher closing tasks and the restarts the launcher application. This method must be able to be called
     * and complete without waiting at all times.
     *
     * @since 2.0
     */
    public static void restartApp() {
        restartFlag = true;
        cleanupApp();
        exitLatch.countDown();
    }

    /**
     * Performs launcher closing tasks and then closes the launcher application. This method must be able to be called
     * and complete without waiting at all times.
     *
     * @since 1.1
     */
    public static void closeApp() {
        cleanupApp();
        exitLatch.countDown();
        Logger.logStd(LocalizationManager.SEE_YOU_SOON_TEXT);
        System.exit(ApplicationConstants.EXIT_STATUS_CODE_GOOD);
    }
}
