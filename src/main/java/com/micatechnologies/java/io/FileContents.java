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

package com.micatechnologies.java.io;

import com.google.gson.*;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.annotation.Nonnull;

/**
 * Utility class for reading and writing to file in a synchronized, thread-safe manner.
 *
 * @author Mica Technologies
 * @version 2021.1
 * @since 2021.1
 */
public class FileContents {

    /**
     * Writes the specified {@link String} contents to the specified {@link File} with the
     * specified {@link Charset}, overwriting previous content if present.
     *
     * @param file     file to write to
     * @param contents string contents to write
     * @param charset  charset to write with
     * @throws IOException if unable to write contents to file
     * @since 2021.1
     */
    public static void write(@Nonnull File file, String contents, Charset charset)
            throws IOException {
        synchronized (file.getCanonicalPath().intern()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
            FileUtils.writeStringToFile(file, contents, charset, false);
        }
    }

    /**
     * Writes the specified {@link JsonObject} contents to the specified {@link File} with the
     * specified {@link Charset}, overwriting previous content if present.
     *
     * @param file     file to write to
     * @param contents {@link JsonObject} contents to write
     * @param charset  charset to write with
     * @throws IOException if unable to write contents to file
     * @since 2021.1
     */
    public static void write(@Nonnull File file, JsonObject contents, Charset charset)
            throws IOException {
        String jsonContents = new Gson().toJson(contents);
        write(file, jsonContents, charset);
    }

    /**
     * Appends the specified {@link String} contents to the specified {@link File} with the
     * specified {@link Charset}.
     *
     * @param file     file to append to
     * @param contents string contents to append
     * @param charset  charset to append with
     * @throws IOException if unable to append contents to file
     * @since 2021.1
     */
    public static void append(@Nonnull File file, String contents, Charset charset)
            throws IOException {
        synchronized (file.getCanonicalPath().intern()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
            FileUtils.writeStringToFile(file, contents, charset, true);
        }
    }

    /**
     * Appends the specified {@link JsonObject} contents to the specified {@link File} with the
     * specified {@link Charset}.
     *
     * @param file     file to append to
     * @param contents {@link JsonObject} contents to append
     * @param charset  charset to append with
     * @throws IOException if unable to append contents to file
     * @since 2021.1
     */
    public static void append(@Nonnull File file, JsonObject contents, Charset charset)
            throws IOException {
        String jsonStringContents = new Gson().toJson(contents);
        append(file, jsonStringContents, charset);
    }

    /**
     * Reads the string contents of the specified {@link File} with the specified {@link Charset}.
     *
     * @param file    file to read from
     * @param charset charset to read with
     * @return file contents
     * @throws IOException if file does not exist or unable to read contents from file
     * @since 2021.1
     */
    public static String readString(@Nonnull File file, Charset charset) throws IOException {
        synchronized (file.getCanonicalPath().intern()) {
            return FileUtils.readFileToString(file, charset);
        }
    }

    /**
     * Reads the {@link JsonObject} contents of the specified {@link File} with the specified
     * {@link Charset}.
     *
     * @param file    file to read from
     * @param charset charset to read with
     * @return file contents
     * @throws IOException if file does not exist or unable to read contents from file
     * @since 2021.1
     */
    public static JsonObject readJsonObject(@Nonnull File file, Charset charset)
            throws IOException {
        String jsonStringContents = readString(file, charset);
        return new Gson().fromJson(jsonStringContents, JsonObject.class);
    }

    /**
     * Reads the contents of the specified {@link File} with the specified {@link Charset} as the
     * specified type.
     *
     * @param file    file to read from
     * @param charset charset to read with
     * @return file contents
     * @throws IOException if file does not exist or unable to read contents from file
     * @since 2021.1
     */
    public static String of(@Nonnull File file, Charset charset)
            throws IOException {
        return readString(file, charset);
    }

    /**
     * Reads the contents of the specified {@link File} with the specified {@link Charset} as the
     * specified type.
     *
     * @param file    file to read from
     * @param charset charset to read with
     * @param <T>     return object type
     * @param tClass  return object class
     * @return file contents
     * @throws IOException if file does not exist or unable to read contents from file
     * @since 2021.1
     */
    public static <T> T of(@Nonnull File file, Charset charset, Class<T> tClass)
            throws IOException {
        String stringFileContents = readString(file, charset);
        return new Gson().fromJson(stringFileContents, tClass);
    }

    /**
     * Creates a stream of the specified {@link File}'s contents.
     *
     * @param file file to read from
     * @return stream of file contents
     * @throws IOException if file does not exist or unable to read contents from file
     * @since 2021.1
     */
    public static FileInputStream streamOf(@Nonnull File file) throws IOException {
        return new FileInputStream(file);
    }
}
