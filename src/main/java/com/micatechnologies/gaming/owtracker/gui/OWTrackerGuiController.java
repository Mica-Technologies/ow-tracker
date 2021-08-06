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

import com.micatechnologies.gaming.owtracker.utilities.GUIUtilities;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class OWTrackerGuiController
{
    private static final AtomicBoolean       startSuccess = new AtomicBoolean( false );
    private static OWTrackerGuiWindow guiWindow    = null;

    public static Stage getTopStageOrNull() {
        return startSuccess.get() ? guiWindow.getStage() : null;
    }

    public static void requestFocus() {
        Stage topStage = getTopStageOrNull();
        if ( topStage != null ) {
            GUIUtilities.JFXPlatformRun( topStage::requestFocus );
        }
    }

    private static boolean startGui() {
        if ( guiWindow == null ) {
            GUIUtilities.JFXPlatformRun( () -> {
                try {
                    guiWindow = new OWTrackerGuiWindow();
                    guiWindow.start( new Stage() );
                    guiWindow.show();
                    startSuccess.set( true );
                }
                catch ( Exception e ) {
                    guiWindow = null;
                    Logger.logError( "An exception was encountered while starting the application GUI window." );
                    Logger.logThrowable( e );
                    startSuccess.set( false );
                }
            } );
        }
        return startSuccess.get();
    }

    @SuppressWarnings( "UnusedReturnValue" )
    public static OWTrackerMainGui goToMainGui() throws IOException {
        OWTrackerMainGui newMainGui = null;
        boolean guiStarted = startGui();
        if ( guiStarted ) {
            newMainGui = new OWTrackerMainGui( guiWindow.getStage() );
            guiWindow.setScene( newMainGui );
            guiWindow.show();
        }
        else {
            Logger.logError( "The application main GUI could not be displayed due to the application GUI not being " +
                                     "started." );
        }
        return newMainGui;
    }

    @SuppressWarnings( "UnusedReturnValue" )
    public static OWTrackerSettingsGui goToSettingsGui() throws IOException {
        OWTrackerSettingsGui newSettingsGui = null;
        boolean guiStarted = startGui();
        if ( guiStarted ) {
            newSettingsGui = new OWTrackerSettingsGui( guiWindow.getStage() );
            guiWindow.setScene( newSettingsGui );
            guiWindow.show();
        }
        else {
            Logger.logError( "The application settings GUI could not be displayed due to the application GUI not " +
                                     "being started." );
        }
        return newSettingsGui;
    }

    @SuppressWarnings( "UnusedReturnValue" )
    public static OWTrackerEditModPacksGui goToEditModpacksGui() throws IOException {
        OWTrackerEditModPacksGui newEditModpacksGui = null;
        boolean guiStarted = startGui();
        if ( guiStarted ) {
            newEditModpacksGui = new OWTrackerEditModPacksGui( guiWindow.getStage() );
            guiWindow.setScene( newEditModpacksGui );
            guiWindow.show();
        }
        else {
            Logger.logError( "The edit mod-packs GUI could not be displayed due to the application GUI not " +
                                     "being started." );
        }
        return newEditModpacksGui;
    }

    @SuppressWarnings( "UnusedReturnValue" )
    public static OWTrackerLoginGui goToLoginGui() throws IOException {
        OWTrackerLoginGui newLoginGui = null;
        boolean guiStarted = startGui();
        if ( guiStarted ) {
            newLoginGui = new OWTrackerLoginGui( guiWindow.getStage() );
            guiWindow.setScene( newLoginGui );
            guiWindow.show();
        }
        else {
            Logger.logError(
                    "The login GUI could not be displayed due to the application GUI not " + "being started." );
        }
        return newLoginGui;
    }

    @SuppressWarnings( "UnusedReturnValue" )
    public static OWTrackerProgressGui goToProgressGui() throws IOException {
        OWTrackerProgressGui newProgressGui = null;
        boolean guiStarted = startGui();
        if ( guiStarted ) {
            newProgressGui = new OWTrackerProgressGui( guiWindow.getStage() );
            guiWindow.setScene( newProgressGui );
            guiWindow.show();
        }
        else {
            Logger.logError(
                    "The progress GUI could not be displayed due to the application GUI not " + "being started." );
        }
        return newProgressGui;
    }

    public static void forceThemeRefresh() {
        if ( guiWindow != null ) {
            guiWindow.forceThemeChange();
        }
    }

    public static void exit() {
        if ( guiWindow != null && guiWindow.getStage() != null ) {
            Platform.setImplicitExit( false );
            GUIUtilities.JFXPlatformRun( () -> guiWindow.getStage().close() );
            guiWindow = null;
        }
    }

    /**
     * Returns a boolean indicating if the application in its current state should use graphical user interfaces.
     *
     * @return true if GUIs should be used
     *
     * @since 1.0
     */
    public static boolean shouldCreateGui() {
        return !GraphicsEnvironment.isHeadless();
    }
}