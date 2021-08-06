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

package com.micatechnologies.gaming.owtracker.config;

import com.google.gson.JsonObject;
import com.micatechnologies.gaming.owtracker.consts.ConfigConstants;
import com.micatechnologies.gaming.owtracker.consts.localization.LocalizationManager;
import com.micatechnologies.gaming.owtracker.files.LocalPathManager;

import java.io.File;

/**
 * Class that manages the configuration and persistence of the configuration for the application.
 *
 * @author Mica Technologies
 * @version 1.0.0
 * @since 2021.1
 */
public class ConfigManager {

    /**
     * Configuration object. Must be loaded from disk and saved to disk on change.
     *
     * @see #readConfigurationFromDisk()
     * @see #writeConfigurationToDisk()
     * @since 1.0
     */
    private static JsonObject configObject = null;

    /**
     * Gets the configured state of debug logging for the application.
     *
     * @return true if debug logging enabled, otherwise false
     * @since 1.0
     */
    public synchronized static boolean getDebugLogging() {
        // Read configuration from disk if not loaded
        if (configObject == null) {
            readConfigurationFromDisk();
        }

        // Check for presence of field, and create default if does not exist
        if (!configObject.has(ConfigConstants.LOG_DEBUG_ENABLE_KEY)) {
            // Add property with default value
            configObject.addProperty(ConfigConstants.LOG_DEBUG_ENABLE_KEY, ConfigConstants.LOG_DEBUG_ENABLE_DEFAULT);

            // Save configuration to disk
            writeConfigurationToDisk();
        }

        // Get and return value of min RAM
        return configObject.get(ConfigConstants.LOG_DEBUG_ENABLE_KEY).getAsBoolean();
    }

    /**
     * Sets the configured state of debug logging for the application.
     *
     * @param debugLogging true to enable application debug logging, otherwise false
     * @since 1.0
     */
    public synchronized static void setDebugLogging(boolean debugLogging) {
        // Read configuration from disk if not loaded
        if (configObject == null) {
            readConfigurationFromDisk();
        }

        // Set value of debug logging
        configObject.addProperty(ConfigConstants.LOG_DEBUG_ENABLE_KEY, debugLogging);

        // Save configuration to disk
        writeConfigurationToDisk();
    }

    /**
     * Gets the configured state of resizable windows for the application.
     *
     * @return true if resizable windows enabled, otherwise false
     * @since 1.0
     */
    public synchronized static boolean getResizableWindows() {
        // Read configuration from disk if not loaded
        if (configObject == null) {
            readConfigurationFromDisk();
        }

        // Check for presence of field, and create default if does not exist
        if (!configObject.has(ConfigConstants.RESIZE_WINDOWS_ENABLE_KEY)) {
            // Add property with default value
            configObject.addProperty(ConfigConstants.RESIZE_WINDOWS_ENABLE_KEY, ConfigConstants.RESIZE_WINDOWS_ENABLE_DEFAULT);

            // Save configuration to disk
            writeConfigurationToDisk();
        }

        // Get and return value of min RAM
        return configObject.get(ConfigConstants.RESIZE_WINDOWS_ENABLE_KEY).getAsBoolean();
    }

    /**
     * Sets the configured state of resizable windows for the application.
     *
     * @param resizableWindows true to enable resizable windows, otherwise false
     * @since 1.0
     */
    public synchronized static void setResizableWindows(boolean resizableWindows) {
        // Read configuration from disk if not loaded
        if (configObject == null) {
            readConfigurationFromDisk();
        }

        // Set value of resizable windows
        configObject.addProperty(ConfigConstants.RESIZE_WINDOWS_ENABLE_KEY, resizableWindows);

        // Save configuration to disk
        writeConfigurationToDisk();
    }

    /**
     * Gets the last tab selected.
     *
     * @return last tab selected
     * @since 1.0
     */
    public synchronized static int getLastTabSelected() {
        // Read configuration from disk if not loaded
        if (configObject == null) {
            readConfigurationFromDisk();
        }

        // Check for presence of field, and create default if does not exist
        if (!configObject.has(ConfigConstants.LAST_TAB_KEY)) {
            // Add property with default value
            configObject.addProperty(ConfigConstants.LAST_TAB_KEY, ConfigConstants.LAST_TAB_DEFAULT);

            // Save configuration to disk
            writeConfigurationToDisk();
        }

        // Get and return value of custom JVM args
        return configObject.get(ConfigConstants.LAST_TAB_KEY).getAsInt();
    }

    /**
     * Sets the last tab selected.
     *
     * @param lastTabSelected last tab selected
     * @since 1.0
     */
    public synchronized static void setLastTabSelected(int lastTabSelected) {
        // Read configuration from disk if not loaded
        if (configObject == null) {
            readConfigurationFromDisk();
        }

        // Set value of last mod pack selected
        configObject.addProperty(ConfigConstants.LAST_TAB_KEY, lastTabSelected);

        // Save configuration to disk
        writeConfigurationToDisk();
    }

    /**
     * Gets the theme.
     *
     * @return theme
     * @since 3.0
     */
    public synchronized static String getTheme() {
        // Read configuration from disk if not loaded
        if (configObject == null) {
            readConfigurationFromDisk();
        }

        // Check for presence of field, and create default if does not exist
        if (!configObject.has(ConfigConstants.THEME_KEY)) {
            // Add property with default value
            configObject.addProperty(ConfigConstants.THEME_KEY, ConfigConstants.THEME_DEFAULT);

            // Save configuration to disk
            writeConfigurationToDisk();
        }

        // Get and return value of custom JVM args
        return configObject.get(ConfigConstants.THEME_KEY).getAsString();
    }

    /**
     * Sets the theme.
     *
     * @param theme theme
     * @since 2.0
     */
    public synchronized static void setTheme(String theme) {
        // Read configuration from disk if not loaded
        if (configObject == null) {
            readConfigurationFromDisk();
        }

        // Set value of last mod pack selected
        configObject.addProperty(ConfigConstants.THEME_KEY, theme);

        // Save configuration to disk
        writeConfigurationToDisk();
    }

    /**
     * Reads the application configuration from its file on persistent storage. In the event that a file does not exist,
     * or an error occurred with the file, a new default configuration file will be created.
     *
     * @since 1.0
     */
    private synchronized static void readConfigurationFromDisk() {
        // Get file path and file object for config file
        String configFilePath = LocalPathManager.getLauncherConfigFolderPath() + ConfigConstants.CONFIG_FILE_NAME;
        File configFile = SynchronizedFileManager.getSynchronizedFile(configFilePath);

        // Check if file exists (and is file), and attempt to read
        boolean read = configFile.isFile();
        if (read) {
            try {
                configObject = FileUtilities.readAsJsonObject(configFile);
            } catch (Exception e) {
                Logger.logError(LocalizationManager.CONFIG_EXISTS_CORRUPT_RESET_ERROR_TEXT);
                Logger.logThrowable(e);
                read = false;
            }
        }

        // If configuration not read or failed to read, use default configuration
        if (!read) {
            configObject = new JsonObject();
            Logger.logStd(LocalizationManager.CONFIG_RESET_SUCCESS_TEXT);
        }
    }

    /**
     * Writes the application configuration to its file on persistent storage.
     *
     * @since 1.0
     */
    private synchronized static void writeConfigurationToDisk() {
        // Check if configuration is loaded, return if not
        if (configObject == null) {
            Logger.logError(LocalizationManager.CONFIG_NOT_LOADED_CANT_SAVE_ERROR_TEXT);
            return;
        }

        // Get file path and file object for config file
        String configFilePath = LocalPathManager.getLauncherConfigFolderPath() + ConfigConstants.CONFIG_FILE_NAME;
        File configFile = SynchronizedFileManager.getSynchronizedFile(configFilePath);

        try {
            FileUtilities.writeFromJson(configObject, configFile);
        } catch (Exception e) {
            Logger.logError(LocalizationManager.CONFIG_SAVE_ERROR_TEXT);
            Logger.logThrowable(e);
        }
    }
}
