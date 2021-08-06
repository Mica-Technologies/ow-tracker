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

import java.util.function.Supplier;

/**
 * Abstract {@link Supplier} used for submitting tasks to be completed by a {@link ThreadedTaskManager} with tracked
 * progress information.
 *
 * @param <V> return type of task
 *
 * @author Mica Technologies
 * @version 1.0
 * @since 2021.3
 */
public abstract class ThreadedTask< V > implements Supplier< V >
{
    /**
     * The parent {@link ThreadedTaskManager} for this {@link ThreadedTask}, used to report progress information.
     *
     * @since 1.0
     */
    private ThreadedTaskManager< V > parentThreadedTaskManager = null;

    /**
     * The last value reported to the {@link #parentThreadedTaskManager}, used to ensure that duplicate progress is not
     * reported.
     *
     * @since 1.0
     */
    private double lastProgress = 0.0;

    /**
     * Sets the parent {@link ThreadedTaskManager}.
     *
     * @since 1.0
     */
    void setParentMultiThreadedMultiTaskManager( ThreadedTaskManager< V > parentThreadedTaskManager )
    {
        this.parentThreadedTaskManager = parentThreadedTaskManager;
    }

    /**
     * Method which should be called by code in the {@link #get()} method to update the progress of the task.
     *
     * @param progressText progress text information (i.e. "Downloading file Y")
     * @param progress     progress value (0.0 - 1.0)
     *
     * @since 1.0
     */
    protected void submitProgress( String progressText, double progress ) {
        if ( parentThreadedTaskManager != null ) {
            final double diffSinceLastProgress = ( progress - lastProgress );
            double reportedTaskProgress = Math.max( 0.0, diffSinceLastProgress );
            parentThreadedTaskManager.recieveThreadProgress( progressText, reportedTaskProgress );
            lastProgress = progress;
        }
        else {
            Logger.logWarningSilent(
                    "A task could not submit progress because its parent task manager is null. This should not " +
                            "happen, and is likely the result of a critical error, or improper use of this class " +
                            "and/or its methods." );
        }
    }
}
