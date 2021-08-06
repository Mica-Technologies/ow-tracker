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

import com.micatechnologies.java.io.FileContents;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

/**
 * Class for a file whose contents are downloaded from a URL and optionally verified against a
 * checksum to ensure download integrity.
 *
 * @author Mica Technologies
 * @version 2021.1
 * @since 2021.1
 */
public class ManagedDownloadableFile {

    /**
     * The remote URL of the downloadable file.
     *
     * @since 2021.1
     */
    private final URL remoteFileUrl;

    /**
     * The local file path for the downloaded file.
     *
     * @since 2021.1
     */
    private final Path localFilePath;

    /**
     * The checksum for the downloaded file.
     *
     * @since 2021.1
     */
    private final String checksum;

    /**
     * The type of checksum for the downloaded file.
     *
     * @since 2021.1
     */
    private final CHECKSUM_TYPE checksumType;

    /**
     * The parent of the local file path for the downloaded file.
     *
     * @since 2021.1
     */
    private Path localFilePathParent;

    /**
     * Constructor for a {@link ManagedDownloadableFile} with the specified remote file URL,
     * local file path, and with checksum verification disabled.
     *
     * @param remoteFileUrl remote file URL
     * @param localFilePath local file path
     * @since 2021.1
     */
    public ManagedDownloadableFile(URL remoteFileUrl, Path localFilePath) {
        this.remoteFileUrl = remoteFileUrl;
        this.localFilePath = localFilePath;
        this.checksum = null;
        this.checksumType = CHECKSUM_TYPE.NONE;
        this.localFilePathParent = null;
    }

    /**
     * Constructor for a {@link ManagedDownloadableFile} with the specified remote file URL,
     * local file path, and checksum verification.
     *
     * @param remoteFileUrl remote file URL
     * @param localFilePath local file path
     * @param checksum      checksum to verify local file
     * @param checksumType  type of checksum to verify local file
     * @since 2021.1
     */
    public ManagedDownloadableFile(URL remoteFileUrl, Path localFilePath, String checksum,
            CHECKSUM_TYPE checksumType) {
        this.remoteFileUrl = remoteFileUrl;
        this.localFilePath = localFilePath;
        this.checksum = checksum;
        this.checksumType = checksumType;
        this.localFilePathParent = null;
    }

    /**
     * Constructor for a {@link ManagedDownloadableFile} with the specified remote file URL,
     * local file path (string), and with checksum verification disabled.
     *
     * @param remoteFileUrl remote file URL
     * @param localFilePath local file path (string)
     * @since 2021.1
     */
    public ManagedDownloadableFile(URL remoteFileUrl, String localFilePath) {
        this.remoteFileUrl = remoteFileUrl;
        this.localFilePath = Path.of(localFilePath);
        this.checksum = null;
        this.checksumType = CHECKSUM_TYPE.NONE;
        this.localFilePathParent = null;
    }

    /**
     * Constructor for a {@link ManagedDownloadableFile} with the specified remote file URL,
     * local file path (string), and checksum verification.
     *
     * @param remoteFileUrl remote file URL
     * @param localFilePath local file path (string)
     * @param checksum      checksum to verify local file
     * @param checksumType  type of checksum to verify local file
     * @since 2021.1
     */
    public ManagedDownloadableFile(URL remoteFileUrl, String localFilePath, String checksum,
            CHECKSUM_TYPE checksumType) {
        this.remoteFileUrl = remoteFileUrl;
        this.localFilePath = Path.of(localFilePath);
        this.checksum = checksum;
        this.checksumType = checksumType;
        this.localFilePathParent = null;
    }

    /**
     * Constructor for a {@link ManagedDownloadableFile} with the specified remote file URL,
     * local file path, local file path parent, and with checksum verification disabled.
     *
     * @param localFilePathParent parent of local file path
     * @param remoteFileUrl       remote file URL
     * @param localFilePath       local file path
     * @since 2021.1
     */
    public ManagedDownloadableFile(Path localFilePathParent, URL remoteFileUrl,
            Path localFilePath) {
        this.remoteFileUrl = remoteFileUrl;
        this.localFilePath = localFilePath;
        this.checksum = null;
        this.checksumType = CHECKSUM_TYPE.NONE;
        this.localFilePathParent = localFilePathParent;
    }

    /**
     * Constructor for a {@link ManagedDownloadableFile} with the specified remote file URL,
     * local file path, local file path parent, and checksum verification.
     *
     * @param localFilePathParent parent of local file path
     * @param remoteFileUrl       remote file URL
     * @param localFilePath       local file path
     * @param checksum            checksum to verify local file
     * @param checksumType        type of checksum to verify local file
     * @since 2021.1
     */
    public ManagedDownloadableFile(Path localFilePathParent, URL remoteFileUrl, Path localFilePath,
            String checksum,
            CHECKSUM_TYPE checksumType) {
        this.remoteFileUrl = remoteFileUrl;
        this.localFilePath = localFilePath;
        this.checksum = checksum;
        this.checksumType = checksumType;
        this.localFilePathParent = localFilePathParent;
    }

    /**
     * Constructor for a {@link ManagedDownloadableFile} with the specified remote file URL,
     * local file path (string), local file path parent (string), and with checksum verification
     * disabled.
     *
     * @param localFilePathParent parent of local file path (string)
     * @param remoteFileUrl       remote file URL
     * @param localFilePath       local file path (string)
     * @since 2021.1
     */
    public ManagedDownloadableFile(String localFilePathParent, URL remoteFileUrl,
            String localFilePath) {
        this.remoteFileUrl = remoteFileUrl;
        this.localFilePath = Path.of(localFilePath);
        this.checksum = null;
        this.checksumType = CHECKSUM_TYPE.NONE;
        this.localFilePathParent = Path.of(localFilePathParent);
    }

    /**
     * Constructor for a {@link ManagedDownloadableFile} with the specified remote file URL,
     * local file path (string), local file path parent (string), and checksum verification.
     *
     * @param localFilePathParent parent of local file path (string)
     * @param remoteFileUrl       remote file URL
     * @param localFilePath       local file path (string)
     * @param checksum            checksum to verify local file
     * @param checksumType        type of checksum to verify local file
     * @since 2021.1
     */
    public ManagedDownloadableFile(String localFilePathParent, URL remoteFileUrl,
            String localFilePath,
            String checksum,
            CHECKSUM_TYPE checksumType) {
        this.remoteFileUrl = remoteFileUrl;
        this.localFilePath = Path.of(localFilePath);
        this.checksum = checksum;
        this.checksumType = checksumType;
        this.localFilePathParent = Path.of(localFilePathParent);
    }

    /**
     * Removes the parent of the local file path, if configured.
     *
     * @since 2021.1
     */
    public synchronized void removeLocalFilePathParent() {
        setLocalFilePathParent((Path) null);
    }

    /**
     * Sets the parent of the local file path. The local file path parent is appended to the
     * local file path, as its parent folder.
     *
     * @param localFilePathParent parent of local file path
     * @since 2021.1
     */
    public synchronized void setLocalFilePathParent(Path localFilePathParent) {
        this.localFilePathParent = localFilePathParent;
    }

    /**
     * Sets the parent of the local file path (string). The local file path parent is appended to
     * the local file path, as its parent folder.
     *
     * @param localPathParent parent of local file path (string)
     * @since 2021.1
     */
    public synchronized void setLocalFilePathParent(String localPathParent) {
        this.localFilePathParent = Path.of(localPathParent);
    }

    /**
     * Gets the local file path of the {@link ManagedDownloadableFile}, including the parent of
     * the local file path, if configured.
     *
     * @return local file path
     * @since 2021.1
     */
    public synchronized Path getLocalFilePath() {
        Path fullLocalPath;
        if (localFilePathParent != null) {
            fullLocalPath = localFilePathParent.resolve(localFilePath);
        } else {
            fullLocalPath = localFilePath;
        }
        return fullLocalPath;
    }

    /**
     * Gets the local file name of the {@link ManagedDownloadableFile}.
     *
     * @return local file name
     * @since 2021.1
     */
    public synchronized String getLocalFileName() {
        return getLocalFilePath().getFileName().toString();
    }

    /**
     * Verifies that the local file exists and matches the configured checksum.
     *
     * @return verification result enum
     * @throws IOException if unable to read contents from file
     */
    public VERIFICATION_RESULT verify() throws IOException {
        File localFile = getLocalFilePath().toFile();
        VERIFICATION_RESULT verificationResult = VERIFICATION_RESULT.BAD;
        if (localFile.isFile() && checksumType == CHECKSUM_TYPE.NONE) {
            verificationResult = VERIFICATION_RESULT.GOOD;
        } else if (localFile.isFile() && checksumType == CHECKSUM_TYPE.MD5) {
            synchronized (localFile.getCanonicalPath().intern()) {
                verificationResult = DigestUtils.md5Hex(
                        FileContents.streamOf(localFile)).equalsIgnoreCase(checksum)
                        ? VERIFICATION_RESULT.GOOD : VERIFICATION_RESULT.BAD;
            }
        } else if (localFile.isFile() && checksumType == CHECKSUM_TYPE.SHA1) {
            synchronized (localFile.getCanonicalPath().intern()) {
                verificationResult = DigestUtils.sha1Hex(
                        FileContents.streamOf(localFile)).equalsIgnoreCase(checksum)
                        ? VERIFICATION_RESULT.GOOD : VERIFICATION_RESULT.BAD;
            }
        } else if (localFile.isFile() && checksumType == CHECKSUM_TYPE.SHA256) {
            synchronized (localFile.getCanonicalPath().intern()) {
                verificationResult = DigestUtils.sha256Hex(
                        FileContents.streamOf(localFile)).equalsIgnoreCase(checksum)
                        ? VERIFICATION_RESULT.GOOD : VERIFICATION_RESULT.BAD;
            }
        } else if (localFile.isFile() && checksumType == CHECKSUM_TYPE.SHA512) {
            synchronized (localFile.getCanonicalPath().intern()) {
                verificationResult = DigestUtils.sha512Hex(
                        FileContents.streamOf(localFile)).equalsIgnoreCase(checksum)
                        ? VERIFICATION_RESULT.GOOD : VERIFICATION_RESULT.BAD;
            }
        }
        return verificationResult;
    }

    /**
     * Verifies that the local file exists and matches the configured checksum. When the {@code
     * replace} parameter is {@code true}, if the local file does not exist or does not match the
     * configured checksum, a replacement will be downloaded.
     *
     * @return verification result enum
     * @throws IOException if unable to read contents from file
     */
    public VERIFICATION_RESULT verify(boolean replace) throws IOException {
        // Verify file
        VERIFICATION_RESULT verificationResult = verify();

        // Download replacement if <code>replace</code> is true
        if (replace && verificationResult == VERIFICATION_RESULT.BAD) {
            download();
            VERIFICATION_RESULT reverificationResult = verify();
            if (reverificationResult == VERIFICATION_RESULT.GOOD) {
                verificationResult = VERIFICATION_RESULT.REPLACED_GOOD;
            } else {
                verificationResult = VERIFICATION_RESULT.REPLACED_BAD;
            }
        }

        return verificationResult;
    }

    /**
     * Downloads the remote file to the local file path, overwriting existing contents (if present).
     *
     * @throws IOException if unable to download or save file
     * @since 2021.1
     */
    public void download() throws IOException {
        FileDownload.from(remoteFileUrl, getLocalFilePath().toFile());
    }

    /**
     * Downloads the remote file to the local file path with the requested content type,
     * overwriting existing contents (if present).
     *
     * @param requestedContentType requested download content type (HTTP accept header)
     * @throws IOException if unable to download or save file
     * @since 2021.1
     */
    public void download(String requestedContentType) throws IOException {
        FileDownload.from(remoteFileUrl, getLocalFilePath().toFile(), requestedContentType);
    }

    /**
     * Enumeration of supported checksum types for {@link ManagedDownloadableFile}s.
     *
     * @since 2021.1
     */
    public enum CHECKSUM_TYPE {
        SHA1, SHA256, SHA512, MD5, NONE
    }

    /**
     * Enumeration of possible file verification results for {@link ManagedDownloadableFile}s.
     *
     * @since 2021.1
     */
    public enum VERIFICATION_RESULT {
        GOOD, BAD, REPLACED_GOOD, REPLACED_BAD
    }
}
