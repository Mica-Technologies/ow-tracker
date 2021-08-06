package com.micatechnologies.java.exception;

import com.micatechnologies.java.annotations.Unsafe;
import com.micatechnologies.java.annotations.UnsafeReason;

import java.lang.invoke.MethodHandles;

/**
 * An exception implementation which allows for the inclusion of an easily interpretable message
 * to improve the debugging process for developers and users alike.
 *
 * @author Mica Technologies
 * @version 2021.1
 * @since 2021.1
 */
public class InterpretableException extends Exception {

    /**
     * The string value used as a filler when no interpretable message is specified to the
     * constructor. The absence of an interpretable message does not conform to the verbosity
     * expected by this class and is the fault of the implementing application developer, thus a
     * filler message is specified for the benefit of the user.
     */
    private static final String NO_INTERPRETABLE_MESSAGE_SPECIFIED_TEXT =
            "No detailed information was specified for the thrown "
                    + MethodHandles.lookup().lookupClass().getName()
                    + ". [WARNING: If you are the developer of this application, you need to "
                    + "update your "
                    + "usage of the "
                    + MethodHandles.lookup().lookupClass().getName()
                    + " class such that an interpretable message with detailed information is "
                    + "included. Failure to do so violates the intention of this class and will "
                    + "result in this message being displayed.]";

    /**
     * Create an {@link InterpretableException} with the specified detail message.
     *
     * @param interpretableMessage the detail message
     */
    public InterpretableException(String interpretableMessage) {
        super(interpretableMessage);
    }

    /**
     * Create an {@link InterpretableException} with the specified cause. This constructor should
     * <em>NEVER</em> be used, as it violates the intention of verbosity expected by this class.
     *
     * @param cause the cause. (A <code>null</code> value is permitted, and
     *              indicates that the cause is nonexistent or unknown.)
     */
    @Unsafe(Unsafe.Reason.USAGE_VIOLATION)
    public InterpretableException(Throwable cause) {
        super(NO_INTERPRETABLE_MESSAGE_SPECIFIED_TEXT, cause);
    }

    /**
     * Create an {@link InterpretableException} with the specified detail message and cause.
     *
     * @param interpretableMessage the detail message
     * @param cause                the cause. (A <code>null</code> value is permitted, and
     *                             indicates that the cause is nonexistent or unknown.)
     */
    public InterpretableException(String interpretableMessage, Throwable cause) {
        super(interpretableMessage, cause);
    }

    /**
     * Create an {@link InterpretableException} with the specified detail message, cause, enable
     * suppression flag, and writable stack trace flag.
     *
     * @param interpretableMessage the detail message
     * @param cause                the cause. (A <code>null</code> value is permitted, and
     *                             indicates that the cause is nonexistent or unknown.)
     * @param enableSuppression    whether or not suppression is enabled or disabled
     * @param writableStackTrace   whether or not the stack trace should be writable
     */
    public InterpretableException(String interpretableMessage, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(interpretableMessage, cause, enableSuppression, writableStackTrace);
    }
}
