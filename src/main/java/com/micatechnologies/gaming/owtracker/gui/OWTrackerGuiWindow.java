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

package com.micatechnologies.gaming.owtracker.gui;

import com.jthemedetecor.OsThemeDetector;
import com.micatechnologies.gaming.owtracker.config.ConfigManager;
import com.micatechnologies.gaming.owtracker.consts.ConfigConstants;
import com.micatechnologies.gaming.owtracker.consts.GUIConstants;
import com.micatechnologies.gaming.owtracker.consts.ApplicationConstants;
import com.micatechnologies.gaming.owtracker.utilities.GUIUtilities;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Objects;

public class OWTrackerGuiWindow extends Application
{

    private static final double                MIN_WIDTH  = 750.0;
    private static final double                MIN_HEIGHT = 550.0;
    private              Stage                 stage;
    private OWTrackerAbstractGui gui;

    private OsThemeDetector detector = null;

    @Override
    public void start( Stage stage ) throws Exception {
        // Initialize default scene/GUI
        OWTrackerProgressGui progressGui = new OWTrackerProgressGui( stage, MIN_WIDTH, MIN_HEIGHT );

        // Save stage
        this.stage = stage;

        // Configure stage
        stage.setMinHeight( MIN_HEIGHT );
        stage.setMinWidth( MIN_WIDTH );

        // Set resizable property
        stage.setResizable( ConfigManager.getResizableWindows() );

        // Set application icon
        try {
            InputStream iconStream = getClass().getClassLoader().getResourceAsStream( "micaminecraftlauncher.png" );
            if ( iconStream != null ) {
                Image icon = new Image( iconStream );
                stage.getIcons().add( icon );
            }
        }
        catch ( Exception e ) {
            Logger.logError( "An error occurred while setting the application icon!" );
            Logger.logThrowable( e );
        }

        // Setup theme detector
        try {
            detector = OsThemeDetector.getDetector();
        }
        catch ( Exception e ) {
            Logger.logWarningSilent( "Unable to configure theme detector for dark/light mode!" );
            Logger.logThrowable( e );
        }

        // Set scene
        show();
        setScene( progressGui );
        stage.centerOnScreen();
    }

    void setScene( OWTrackerAbstractGui gui ) {
        // Cleanup previous GUI, if present
        if ( this.gui != null ) {
            this.gui.cleanup();
        }

        // Store new GUI and set it up
        this.gui = gui;
        GUIUtilities.JFXPlatformRun( () -> {
            // Prepare scene environment
            gui.setup();

            // Change stage name
            stage.setTitle(
                    ApplicationConstants.LAUNCHER_APPLICATION_NAME + GUIConstants.TITLE_SPLIT_CHAR + gui.getSceneName() );

            // Set correct first theme
            forceThemeChange();

            // Set scene
            stage.setScene( gui.scene );

            gui.afterShow();
        } );

        // Setup theme detector change listener
        if ( detector != null ) {
            try {
                detector.registerListener( isDark -> forceThemeChange() );
            }
            catch ( Exception e ) {
                Logger.logWarningSilent( "Unable to configure theme change listener for dark/light mode!" );
                Logger.logThrowable( e );
            }
        }
    }

    public void show() {
        GUIUtilities.JFXPlatformRun( () -> stage.show() );
    }

    public Stage getStage() {
        return stage;
    }

    public void forceThemeChange() {
        switch ( ConfigManager.getTheme() ) {
            case ConfigConstants.THEME_AUTOMATIC:
                if ( detector != null ) {
                    if ( detector.isDark() ) {
                        // The OS switched to a dark theme
                        switchToDarkTheme();
                    }
                    else {
                        // The OS switched to a light theme
                        switchToLightTheme();
                    }
                }
                break;
            case ConfigConstants.THEME_LIGHT:
                switchToLightTheme();
                break;
            case ConfigConstants.THEME_DARK:
                switchToDarkTheme();
                break;
        }
    }

    private void switchToLightTheme() {
        GUIUtilities.JFXPlatformRun( () -> {
            gui.rootPane.getStylesheets()
                        .remove(
                                Objects.requireNonNull( getClass().getClassLoader().getResource( "guiStyle-dark.css" ) )
                                       .toExternalForm() );
            gui.rootPane.getStylesheets()
                        .add( Objects.requireNonNull( getClass().getClassLoader().getResource( "guiStyle-light.css" ) )
                                     .toExternalForm() );
        } );
    }

    private void switchToDarkTheme() {
        GUIUtilities.JFXPlatformRun( () -> {
            gui.rootPane.getStylesheets()
                        .remove( Objects.requireNonNull(
                                getClass().getClassLoader().getResource( "guiStyle-light.css" ) ).toExternalForm() );
            gui.rootPane.getStylesheets()
                        .add( Objects.requireNonNull( getClass().getClassLoader().getResource( "guiStyle-dark.css" ) )
                                     .toExternalForm() );
        } );
    }
}

