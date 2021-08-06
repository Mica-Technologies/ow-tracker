package com.micatechnologies.java.net;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Utility class for downloading a {@link JsonObject} or serialized JSON object class from a
 * specified URL.
 *
 * @author Mica Technologies
 * @version 2021.1
 * @since 2021.1
 */
public class JsonDownload {

    /**
     * Downloads a {@link JsonObject} from the specified source URL (string) using the specified
     * charset.
     *
     * @param source  source URL (string)
     * @param charset charset
     * @return downloaded {@link JsonObject}
     * @throws IOException if unable to download or parse from source URL
     * @since 2021.1
     */
    public static JsonObject from(String source, Charset charset) throws IOException {
        return from(source, charset, JsonObject.class);
    }

    /**
     * Downloads a serialized JSON object class from the specified source URL (string) using the
     * specified charset.
     *
     * @param source  source URL (string)
     * @param charset charset
     * @param <T>     return object type
     * @param tClass  return object class
     * @return downloaded {@link JsonObject}
     * @throws IOException if unable to download or parse from source URL
     * @since 2021.1
     */
    public static <T> T from(String source, Charset charset, Class<T> tClass)
            throws IOException {
        return from(new URL(source), charset, tClass);
    }

    /**
     * Downloads a {@link JsonObject} from the specified source URL using the specified charset.
     *
     * @param source  source URL
     * @param charset charset
     * @return downloaded {@link JsonObject}
     * @throws IOException if unable to download or parse from source URL
     * @since 2021.1
     */
    public static JsonObject from(URL source, Charset charset) throws IOException {
        return from(source, charset, JsonObject.class);
    }

    /**
     * Downloads a serialized JSON object class from the specified source URL using the specified
     * charset.
     *
     * @param source  source URL
     * @param charset charset
     * @param <T>     return object type
     * @param tClass  return object class
     * @return downloaded {@link JsonObject}
     * @throws IOException if unable to download or parse from source URL
     * @since 2021.1
     */
    public static <T> T from(URL source, Charset charset, Class<T> tClass) throws IOException {
        URLConnection connection = source.openConnection();
        connection.setUseCaches(false);
        String jsonString = IOUtils.toString(connection.getInputStream(), charset);
        return new Gson().fromJson(jsonString, tClass);
    }
}
