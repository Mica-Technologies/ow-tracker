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

package com.micatechnologies.gaming.owtracker.consts;


import com.micatechnologies.gaming.owtracker.consts.localization.LocalizationManager;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Class containing constants that are used in the launcher configuration package.
 * <p>
 * NOTE: This class should NOT contain display strings that are visible to the end-user. All localizable strings MUST be
 * stored and retrieved using {@link LocalizationManager}.
 *
 * @author Mica Technologies
 * @version 1.0
 * @since 2021.1
 */
public class ConfigConstants {

    /**
     * The name of the configuration file when stored on disk.
     *
     * @since 1.0
     */
    public static final String CONFIG_FILE_NAME = File.separator + "configuration.json";


    /**
     * The default value for debug logging being enabled.
     *
     * @since 1.0
     */
    public static final boolean LOG_DEBUG_ENABLE_DEFAULT = false;

    /**
     * The default value for resizable windows being enabled.
     *
     * @since 1.0
     */
    public static final boolean RESIZE_WINDOWS_ENABLE_DEFAULT = false;

    /**
     * The default value for the theme.
     *
     * @since 1.0
     */
    public static final String THEME_DEFAULT = ConfigConstants.THEME_AUTOMATIC;

    /**
     * The default value for the last tab.
     *
     * @since 1.0
     */
    public static final int LAST_TAB_DEFAULT = 0;

    /**
     * Key for accessing the value of the theme.
     *
     * @since 1.1
     */
    public static final String THEME_KEY = "theme";

    /**
     * Key for accessing the value of the last tab.
     *
     * @since 1.1
     */
    public static final String LAST_TAB_KEY = "lastTab";

    /**
     * Key for accessing the debug logging enable value.
     *
     * @since 1.0
     */
    public static final String LOG_DEBUG_ENABLE_KEY = "debug";

    /**
     * Key for accessing the resizable windows enable value.
     *
     * @since 1.0
     */
    public static final String RESIZE_WINDOWS_ENABLE_KEY = "resizableWindows";

    /**
     * Theme value representing the dark theme.
     *
     * @since 1.0
     */
    public static final String THEME_DARK = "Dark";

    /**
     * Theme value representing the light theme.
     *
     * @since 1.0
     */
    public static final String THEME_LIGHT = "Light";

    /**
     * Theme value representing the automatic (light/dark) theme.
     *
     * @since 1.0
     */
    public static final String THEME_AUTOMATIC = "Automatic";

    /**
     * List of allowed values for the theme.
     *
     * @since 1.0
     */
    public static final List<String> ALLOWED_THEMES = Arrays.asList(THEME_AUTOMATIC, THEME_DARK, THEME_LIGHT);
}
