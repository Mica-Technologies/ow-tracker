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

package com.micatechnologies.java.time;

import java.time.LocalTime;

/**
 * Utility class for getting a friendly time-based greeting for the current or a specified time.
 *
 * @author Mica Technologies
 * @version 2021.1
 * @since 2021.1
 */
public class FriendlyTimeGreeting {

    /**
     * The start time of the 'early morning' time range.
     *
     * @since 2021.1
     */
    private static final LocalTime EARLY_MORNING_TIME_RANGE_START = LocalTime.of(0, 0, 0);

    /**
     * The start time of the 'morning' time range.
     *
     * @since 2021.1
     */
    private static LocalTime morningTimeRangeStart = LocalTime.of(4, 0, 0);

    /**
     * The start time of the 'afternoon' time range.
     *
     * @since 2021.1
     */
    private static LocalTime afternoonTimeRangeStart = LocalTime.of(12, 0, 0);

    /**
     * The start time of the 'evening' time range.
     *
     * @since 2021.1
     */
    private static LocalTime eveningTimeRangeStart = LocalTime.of(17, 0, 0);

    /**
     * The start time of the 'night' time range.
     *
     * @since 2021.1
     */
    private static LocalTime nightTimeRangeStart = LocalTime.of(21, 0, 0);

    /**
     * The greeting used during the 'early morning' time range.
     *
     * @since 2021.1
     */
    private static String earlyMorningTimeRangeGreeting = "Good Morning";

    /**
     * The greeting used during the 'morning' time range.
     *
     * @since 2021.1
     */
    private static String morningTimeRangeGreeting = "Good Morning";

    /**
     * The greeting used during the 'afternoon' time range.
     *
     * @since 2021.1
     */
    private static String afternoonTimeRangeGreeting = "Good Afternoon";

    /**
     * The greeting used during the 'evening' time range.
     *
     * @since 2021.1
     */
    private static String eveningTimeRangeGreeting = "Good Evening";

    /**
     * The greeting used during the 'night' time range.
     *
     * @since 2021.1
     */
    private static String nightTimeRangeGreeting = "Good Night";

    /**
     * Helper method which returns a boolean indicated if the specified time is within the range
     * specified by
     *
     * @param time           time to check
     * @param rangeStartTime start time of range
     * @param rangeEndTime   end time of range
     * @return true if {@code time} is in the range specified by {@code rangeStartTime} and
     * {@code rangeEndTime}
     * @since 2021.1
     */
    private static boolean isTimeInRange(LocalTime time, LocalTime rangeStartTime,
            LocalTime rangeEndTime) {
        return (!time.isBefore(rangeStartTime)) && time.isBefore(rangeEndTime);
    }

    /**
     * Sets the start time of the 'morning' time range.
     *
     * @param morningTimeRangeStart start time of the 'morning' time range
     * @since 2021.1
     */
    public static void setMorningTimeRangeStart(LocalTime morningTimeRangeStart) {
        FriendlyTimeGreeting.morningTimeRangeStart = morningTimeRangeStart;
    }

    /**
     * Sets the start time of the 'afternoon' time range.
     *
     * @param afternoonTimeRangeStart start time of the 'afternoon' time range
     * @since 2021.1
     */
    public static void setAfternoonTimeRangeStart(LocalTime afternoonTimeRangeStart) {
        FriendlyTimeGreeting.afternoonTimeRangeStart = afternoonTimeRangeStart;
    }

    /**
     * Sets the start time of the 'evening' time range.
     *
     * @param eveningTimeRangeStart start time of the 'evening' time range
     * @since 2021.1
     */
    public static void setEveningTimeRangeStart(LocalTime eveningTimeRangeStart) {
        FriendlyTimeGreeting.eveningTimeRangeStart = eveningTimeRangeStart;
    }

    /**
     * Sets the start time of the 'night' time range.
     *
     * @param nightTimeRangeStart start time of the 'night' time range
     * @since 2021.1
     */
    public static void setNightTimeRangeStart(LocalTime nightTimeRangeStart) {
        FriendlyTimeGreeting.nightTimeRangeStart = nightTimeRangeStart;
    }

    /**
     * Sets the greeting used during the 'early morning' time range.
     *
     * @param earlyMorningTimeRangeGreeting greeting used during the 'early morning' time range
     * @since 2021.1
     */
    public static void setEarlyMorningTimeRangeGreeting(String earlyMorningTimeRangeGreeting) {
        FriendlyTimeGreeting.earlyMorningTimeRangeGreeting = earlyMorningTimeRangeGreeting;
    }

    /**
     * Sets the greeting used during the 'morning' time range.
     *
     * @param morningTimeRangeGreeting greeting used during the 'morning' time range
     * @since 2021.1
     */
    public static void setMorningTimeRangeGreeting(String morningTimeRangeGreeting) {
        FriendlyTimeGreeting.morningTimeRangeGreeting = morningTimeRangeGreeting;
    }

    /**
     * Sets the greeting used during the 'afternoon' time range.
     *
     * @param afternoonTimeRangeGreeting greeting used during the 'afternoon' time range
     * @since 2021.1
     */
    public static void setAfternoonTimeRangeGreeting(String afternoonTimeRangeGreeting) {
        FriendlyTimeGreeting.afternoonTimeRangeGreeting = afternoonTimeRangeGreeting;
    }

    /**
     * Sets the greeting used during the 'evening' time range.
     *
     * @param eveningTimeRangeGreeting greeting used during the 'evening' time range
     * @since 2021.1
     */
    public static void setEveningTimeRangeGreeting(String eveningTimeRangeGreeting) {
        FriendlyTimeGreeting.eveningTimeRangeGreeting = eveningTimeRangeGreeting;
    }

    /**
     * Sets the greeting used during the 'night' time range.
     *
     * @param nightTimeRangeGreeting greeting used during the 'night' time range
     * @since 2021.1
     */
    public static void setNightTimeRangeGreeting(String nightTimeRangeGreeting) {
        FriendlyTimeGreeting.nightTimeRangeGreeting = nightTimeRangeGreeting;
    }

    /**
     * Gets a friendly time-based greeting for the specified time.
     *
     * @param time time for time-based greeting
     * @return friendly time-based greeting for the specified time
     * @since 2021.1
     */
    public static String getFor(LocalTime time) {
        String returnString;
        if (isTimeInRange(time, EARLY_MORNING_TIME_RANGE_START, morningTimeRangeStart)) {
            returnString = earlyMorningTimeRangeGreeting;
        } else if (isTimeInRange(time, morningTimeRangeStart, afternoonTimeRangeStart)) {
            returnString = morningTimeRangeGreeting;
        } else if (isTimeInRange(time, afternoonTimeRangeStart, eveningTimeRangeStart)) {
            returnString = afternoonTimeRangeGreeting;
        } else if (isTimeInRange(time, eveningTimeRangeStart, nightTimeRangeStart)) {
            returnString = eveningTimeRangeGreeting;
        } else {
            returnString = nightTimeRangeGreeting;
        }
        return returnString;
    }

    /**
     * Gets a friendly time-based greeting for the current time.
     *
     * @return friendly time-based greeting for the current time
     * @since 2021.1
     */
    public static String getForNow() {
        LocalTime time = LocalTime.now();
        return getFor(time);
    }
}
