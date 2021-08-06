package com.micatechnologies.java.util.jar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ExtractableJarFile extends JarFile {
    /**
     * Creates a new {@code ExtractableJarFile} to read from the specified
     * file {@code name}. The {@code ExtractableJarFile} will be verified if
     * it is signed.
     *
     * @param name the name of the jar file to be opened for reading
     * @throws IOException       if an I/O error has occurred
     * @throws SecurityException if access to the file is denied
     *                           by the SecurityManager
     * @since 2021.1
     */
    public ExtractableJarFile(String name) throws IOException {
        super(name);
    }

    /**
     * Creates a new {@code ExtractableJarFile} to read from the specified
     * file {@code name}.
     *
     * @param name   the name of the jar file to be opened for reading
     * @param verify whether or not to verify the jar file if
     *               it is signed.
     * @throws IOException       if an I/O error has occurred
     * @throws SecurityException if access to the file is denied
     *                           by the SecurityManager
     * @since 2021.1
     */
    public ExtractableJarFile(String name, boolean verify) throws IOException {
        super(name, verify);
    }

    /**
     * Creates a new {@code ExtractableJarFile} to read from the specified
     * {@code File} object. The {@code ExtractableJarFile} will be verified if
     * it is signed.
     *
     * @param file the jar file to be opened for reading
     * @throws IOException       if an I/O error has occurred
     * @throws SecurityException if access to the file is denied
     *                           by the SecurityManager
     * @since 2021.1
     */
    public ExtractableJarFile(File file) throws IOException {
        super(file);
    }

    /**
     * Creates a new {@code ExtractableJarFile} to read from the specified
     * {@code File} object.
     *
     * @param file   the jar file to be opened for reading
     * @param verify whether or not to verify the jar file if
     *               it is signed.
     * @throws IOException       if an I/O error has occurred
     * @throws SecurityException if access to the file is denied
     *                           by the SecurityManager.
     * @since 2021.1
     */
    public ExtractableJarFile(File file, boolean verify) throws IOException {
        super(file, verify);
    }

    /**
     * Creates a new {@code ExtractableJarFile} to read from the specified
     * {@code File} object in the specified mode.  The mode argument
     * must be either {@code OPEN_READ} or {@code OPEN_READ | OPEN_DELETE}.
     *
     * @param file   the jar file to be opened for reading
     * @param verify whether or not to verify the jar file if
     *               it is signed.
     * @param mode   the mode in which the file is to be opened
     * @throws IOException              if an I/O error has occurred
     * @throws IllegalArgumentException if the {@code mode} argument is invalid
     * @throws SecurityException        if access to the file is denied
     *                                  by the SecurityManager
     * @since 2021.1
     */
    public ExtractableJarFile(File file, boolean verify, int mode) throws IOException {
        super(file, verify, mode);
    }

    /**
     * Creates a new {@code ExtractableJarFile} to read from the specified
     * {@code File} object in the specified mode.  The mode argument
     * must be either {@code OPEN_READ} or {@code OPEN_READ | OPEN_DELETE}.
     * The version argument, after being converted to a canonical form, is
     * used to configure the {@code ExtractableJarFile} for processing
     * multi-release jar files.
     * <p>
     * The canonical form derived from the version parameter is
     * {@code Runtime.Version.parse(Integer.toString(n))} where {@code n} is
     * {@code Math.max(version.feature(), ExtractableJarFile.baseVersion().feature())}.
     *
     * @param file    the jar file to be opened for reading
     * @param verify  whether or not to verify the jar file if
     *                it is signed.
     * @param mode    the mode in which the file is to be opened
     * @param version specifies the release version for a multi-release jar file
     * @throws IOException              if an I/O error has occurred
     * @throws IllegalArgumentException if the {@code mode} argument is invalid
     * @throws SecurityException        if access to the file is denied
     *                                  by the SecurityManager
     * @throws NullPointerException     if {@code version} is {@code null}
     * @since 2021.1
     */
    public ExtractableJarFile(File file, boolean verify, int mode, Runtime.Version version)
            throws IOException {
        super(file, verify, mode, version);
    }

    /**
     * Extracts the {@code ExtractableJarFile} to the specified destination path (string).
     *
     * @param destination destination path (string) to extract to
     * @throws IOException if unable to enumerate over jar file entries or unable to extract to
     *                     destination
     * @since 2021.1
     */
    public void extractTo(String destination) throws IOException {
        extractTo(destination, null);
    }

    /**
     * Extracts the {@code ExtractableJarFile} to the specified destination path.
     *
     * @param destination destination path to extract to
     * @throws IOException if unable to enumerate over jar file entries or unable to extract to
     *                     destination
     * @since 2021.1
     */
    public void extractTo(Path destination) throws IOException {
        extractTo(destination, null);
    }

    /**
     * Extracts the {@code ExtractableJarFile} to the specified destination path (string),
     * excluding any files with names matching those in the specified list of names to exclude.
     *
     * @param destination  destination path (string) to extract to
     * @param excludeNames list of file names to exclude from extraction
     * @throws IOException if unable to enumerate over jar file entries or unable to extract to
     *                     destination
     * @since 2021.1
     */
    public void extractTo(String destination, ArrayList<String> excludeNames) throws IOException {
        extractTo(Path.of(destination), excludeNames);
    }

    /**
     * Extracts the {@code ExtractableJarFile} to the specified destination path, excluding any
     * files with names matching those in the specified list of names to exclude.
     *
     * @param destination  destination path to extract to
     * @param excludeNames list of file names to exclude from extraction
     * @throws IOException if unable to enumerate over jar file entries or unable to extract to
     *                     destination
     * @since 2021.1
     */
    public void extractTo(Path destination, ArrayList<String> excludeNames) throws IOException {
        // Enumerate over each jar entry
        Enumeration<JarEntry> jarEntryEnumeration = this.entries();
        while (jarEntryEnumeration.hasMoreElements()) {
            // Store current jar entry
            JarEntry jarEntry = jarEntryEnumeration.nextElement();

            // Skip excluded file(s)
            if (excludeNames.stream().anyMatch(s -> jarEntry.getName().contains(s))) {
                continue;
            }

            // Create extracted file File object
            File extractedJarEntry = destination.resolve(jarEntry.getName()).toFile();

            // Create directory if expected
            if (extractedJarEntry.isDirectory()) {
                //noinspection ResultOfMethodCallIgnored
                extractedJarEntry.mkdir();
                continue;
            }

            // Make sure the parent folders exist
            //noinspection ResultOfMethodCallIgnored
            extractedJarEntry.getParentFile().mkdirs();

            // Create file if doesn't exist
            if (!extractedJarEntry.exists()) {
                //noinspection ResultOfMethodCallIgnored
                extractedJarEntry.createNewFile();
            }

            // Read file from jar to extracted file
            InputStream inputStream;
            FileOutputStream fileOutputStream;
            inputStream = this.getInputStream(jarEntry);
            fileOutputStream = new FileOutputStream(extractedJarEntry);
            while (inputStream.available() > 0) {
                fileOutputStream.write(inputStream.read());
            }

            // Close streams
            fileOutputStream.close();
            inputStream.close();

        }
    }
}
