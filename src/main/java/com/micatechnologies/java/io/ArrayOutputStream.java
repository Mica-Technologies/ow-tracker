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

import javax.annotation.Nonnull;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An output stream implementation which writes to every {@link OutputStream} in a configured
 * array of {@link OutputStream}s.
 *
 * @author Mica Technologies
 * @version 2021.1
 * @implNote Adapted from an online example at https://www.codeproject
 * .com/Tips/315892/A-quick-and-easy-way-to-direct-Java-System-out-to
 * @since 2021.1
 */
public class ArrayOutputStream extends OutputStream {

    /**
     * Array of children {@link OutputStream}s to parent {@link ArrayOutputStream}.
     *
     * @since 2021.1
     */
    private final OutputStream[] outputStreams;

    /**
     * Constructor for creating an {@link ArrayOutputStream} with specified child
     * {@link OutputStream}s.
     *
     * @since 2021.1
     */
    public ArrayOutputStream(OutputStream... outputStreams) {
        this.outputStreams = outputStreams;
    }

    /**
     * Writes the specified byte to this output stream. The general
     * contract for {@code write} is that one byte is written
     * to the output stream. The byte to be written is the eight
     * low-order bits of the argument {@code b}. The 24
     * high-order bits of {@code b} are ignored.
     * <p>
     * Subclasses of {@code OutputStream} must provide an
     * implementation for this method.
     *
     * @param b the {@code byte}.
     * @throws IOException if an I/O error occurs. In particular,
     *                     an {@code IOException} may be thrown if the
     *                     output stream has been closed.
     */
    @Override
    public void write(int b) throws IOException {
        for (OutputStream outputStream : outputStreams) outputStream.write(b);
    }

    /**
     * Writes {@code b.length} bytes from the specified byte array
     * to this output stream. The general contract for {@code write(b)}
     * is that it should have exactly the same effect as the call
     * {@code write(b, 0, b.length)}.
     *
     * @param b the data.
     * @throws IOException if an I/O error occurs.
     * @see OutputStream#write(byte[], int, int)
     * @since 2021.1
     */
    @Override
    public void write(@Nonnull byte[] b) throws IOException {
        for (OutputStream outputStream : outputStreams) outputStream.write(b);
    }

    /**
     * Writes {@code len} bytes from the specified byte array
     * starting at offset {@code off} to this output stream.
     * The general contract for {@code write(b, off, len)} is that
     * some of the bytes in the array {@code b} are written to the
     * output stream in order; element {@code b[off]} is the first
     * byte written and {@code b[off+len-1]} is the last byte written
     * by this operation.
     * <p>
     * The {@code write} method of {@code OutputStream} calls
     * the write method of one argument on each of the bytes to be
     * written out. Subclasses are encouraged to override this method and
     * provide a more efficient implementation.
     * <p>
     * If {@code b} is {@code null}, a
     * {@code NullPointerException} is thrown.
     * <p>
     * If {@code off} is negative, or {@code len} is negative, or
     * {@code off+len} is greater than the length of the array
     * {@code b}, then an {@code IndexOutOfBoundsException} is thrown.
     *
     * @param b   the data.
     * @param off the start offset in the data.
     * @param len the number of bytes to write.
     * @throws IOException if an I/O error occurs. In particular,
     *                     an {@code IOException} is thrown if the output
     *                     stream is closed.
     * @since 2021.1
     */
    @Override
    public void write(@Nonnull byte[] b, int off, int len) throws IOException {
        for (OutputStream outputStream : outputStreams) outputStream.write(b, off, len);
    }

    /**
     * Flushes this output stream and forces any buffered output bytes
     * to be written out. The general contract of {@code flush} is
     * that calling it is an indication that, if any bytes previously
     * written have been buffered by the implementation of the output
     * stream, such bytes should immediately be written to their
     * intended destination.
     * <p>
     * If the intended destination of this stream is an abstraction provided by
     * the underlying operating system, for example a file, then flushing the
     * stream guarantees only that bytes previously written to the stream are
     * passed to the operating system for writing; it does not guarantee that
     * they are actually written to a physical device such as a disk drive.
     * <p>
     * The {@code flush} method of {@code OutputStream} does nothing.
     *
     * @throws IOException if an I/O error occurs.
     * @since 2021.1
     */
    @Override
    public void flush() throws IOException {
        for (OutputStream outputStream : outputStreams) outputStream.flush();
    }

    /**
     * Closes this output stream and releases any system resources
     * associated with this stream. The general contract of {@code close}
     * is that it closes the output stream. A closed stream cannot perform
     * output operations and cannot be reopened.
     * <p>
     * The {@code close} method of {@code OutputStream} does nothing.
     *
     * @throws IOException if an I/O error occurs.
     * @since 2021.1
     */
    @Override
    public void close() throws IOException {
        for (OutputStream outputStream : outputStreams) outputStream.close();
    }
}
