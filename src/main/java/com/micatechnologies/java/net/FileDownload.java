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

package com.micatechnologies.java.net;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Utility class for downloading to a specified file from a URL with optionally specified accept
 * content type.
 *
 * @author Mica Technologies
 * @version 2021.1
 * @since 2021.1
 */
public class FileDownload {

    /**
     * Downloads the file from the specified source URL (as string) to the specified destination
     * file.
     *
     * @param source      source URL (as string)
     * @param destination destination file
     * @throws IOException if unable to download or save file
     * @since 2021.1
     */
    public static void from(String source, File destination) throws IOException {
        from(new URL(source), destination);
    }

    /**
     * Downloads the file from the specified source URL (as string) to the specified destination
     * file with specified accept content type.
     *
     * @param source              source URL (as string)
     * @param destination         destination file
     * @param responseContentType content type of response
     * @throws IOException if unable to download or save file
     * @since 2021.1
     */
    public static void from(String source, File destination, String responseContentType)
            throws IOException {
        from(new URL(source), destination, responseContentType);
    }

    /**
     * Downloads the file from the specified source URL to the specified destination file.
     *
     * @param source      source URL
     * @param destination destination file
     * @throws IOException if unable to download or save file
     * @since 2021.1
     */
    public static void from(URL source, File destination) throws IOException {
        URLConnection connection = source.openConnection();
        connection.setUseCaches(false);
        FileUtils.copyInputStreamToFile(connection.getInputStream(), destination);
    }

    /**
     * Downloads the file from the specified source URL to the specified destination file with
     * specified accept content type.
     *
     * @param source              source URL
     * @param destination         destination file
     * @param responseContentType content type of response
     * @throws IOException if unable to download or save file
     * @since 2021.1
     */
    public static void from(URL source, File destination, String responseContentType)
            throws IOException {
        URLConnection connection = source.openConnection();
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept", responseContentType);
        FileUtils.copyInputStreamToFile(connection.getInputStream(), destination);
    }
}
