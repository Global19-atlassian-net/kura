/*******************************************************************************
 * Copyright (c) 2011, 2017 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.kura;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link KuraException} class is the superclass of all errors and exceptions in the Kura project. It extends the
 * JDK {@link Exception} class by requesting its invokers to provide an error code when building its instances. The code
 * is one value of {@link KuraErrorCode}; the code is used to document the possible error conditions generated by the
 * platform as well as to identify the localized exception messages to be reported. Exceptions messages are stored in
 * the {@code KuraExceptionMessagesBundle} Properties Bundle and they are keyed on the exception code.
 *
 * @see KuraErrorCode
 */

public class KuraException extends Exception {

    /** The Constant denoting resource bundle. */
    private static final String KURA_EXCEPTION_MESSAGES_BUNDLE = "org.eclipse.kura.core.messages.KuraExceptionMessagesBundle";

    /** The Constant denoting message pattern. */
    private static final String KURA_GENERIC_MESSAGES_PATTERN = "Generic Error - {0}: {1}";

    /** The Logger. */
    private static final Logger logger = LoggerFactory.getLogger(KuraException.class);

    /** The Constant denoting serial version identifier. */
    private static final long serialVersionUID = 7468633737373095296L;

    /** The associated arguments. */
    private Object[] arguments;

    /** The associated error code. */
    private final KuraErrorCode code;

    /**
     * Builds a new {@link KuraException} instance based on the supplied {@link KuraErrorCode}.
     *
     * @param code
     *            the error code
     */
    public KuraException(final KuraErrorCode code) {
        this.code = code;
    }

    /**
     * Builds a new {@link KuraException} instance based on the supplied {@link KuraErrorCode}.
     *
     * @param code
     *            the error code
     * @param arguments
     *            the arguments
     */
    public KuraException(final KuraErrorCode code, final Object... arguments) {
        this.code = code;
        this.arguments = arguments;
    }

    /**
     * Builds a new {@link KuraException} instance based on the supplied {@link KuraErrorCode}, an optional Throwable
     * cause, and optional arguments for the associated exception message.
     *
     * @param code
     *            the error code
     * @param cause
     *            the cause
     * @param arguments
     *            the arguments
     */
    public KuraException(final KuraErrorCode code, final Throwable cause, final Object... arguments) {
        super(cause);
        this.code = code;
        this.arguments = arguments;
    }

    /**
     * Factory method to build an {@link KuraException} with the {@link KuraErrorCode#INTERNAL_ERROR} code providing
     * only a message. This method internally sets the error code to {@link KuraErrorCode#INTERNAL_ERROR} which is not
     * meaningful at all for exception translations. That is why, the use of this method is <b>highly discouraged</b>.
     * Hence, the advised way to construct a {@link KuraException} is to use its constructor with a proper
     * {@link KuraErrorCode}.
     *
     * @param message
     *            the message
     * @return the kura exception
     */
    public static KuraException internalError(final String message) {
        return new KuraException(KuraErrorCode.INTERNAL_ERROR, null, message);
    }

    /**
     * Factory method to build an {@link KuraException} with the {@link KuraErrorCode#INTERNAL_ERROR} code providing a
     * cause and a message. This method internally sets the error code to {@link KuraErrorCode#INTERNAL_ERROR} which is
     * not meaningful at all for exception translations. That is why, the use of this method is <b>highly
     * discouraged</b>. Hence, the advised way to construct a {@link KuraException} is to use its constructor with a
     * proper {@link KuraErrorCode}.
     *
     * @param cause
     *            the cause
     * @return the kura exception
     */
    public static KuraException internalError(final Throwable cause) {
        return new KuraException(KuraErrorCode.INTERNAL_ERROR, cause, "");
    }

    /**
     * Factory method to build an {@link KuraException} with the {@link KuraErrorCode#INTERNAL_ERROR} code providing a
     * cause and a message. This method internally sets the error code to {@link KuraErrorCode#INTERNAL_ERROR} which is
     * not meaningful at all for exception translations. That is why, the use of this method is <b>highly
     * discouraged</b>. Hence, the advised way to construct a {@link KuraException} is to use its constructor with a
     * proper {@link KuraErrorCode}.
     *
     * @param cause
     *            the cause
     * @param message
     *            the message
     * @return the kura exception
     */
    public static KuraException internalError(final Throwable cause, final String message) {
        return new KuraException(KuraErrorCode.INTERNAL_ERROR, cause, message);
    }

    /**
     * Gets the error code.
     *
     * @return the error code
     */
    public KuraErrorCode getCode() {
        return this.code;
    }

    /** {@inheritDoc} */
    @Override
    public String getLocalizedMessage() {
        return getLocalizedMessage(Locale.getDefault());
    }

    /**
     * Gets the localized message.
     *
     * @param locale
     *            the locale
     * @return the localized message
     */
    private String getLocalizedMessage(final Locale locale) {
        final String pattern = getMessagePattern(locale, this.code);
        if ((this.code == null) || KuraErrorCode.INTERNAL_ERROR.equals(this.code)) {
            if ((this.arguments != null) && (this.arguments.length > 1)) {
                // append all arguments into a single one
                final StringBuilder sbAllArgs = new StringBuilder();
                for (final Object arg : this.arguments) {
                    sbAllArgs.append(" - ");
                    sbAllArgs.append(arg);
                }
                this.arguments = new Object[] { sbAllArgs.toString() };
            }
        }
        return MessageFormat.format(pattern, this.arguments);
    }

    /** {@inheritDoc} */
    @Override
    public String getMessage() {
        return getLocalizedMessage(Locale.US);
    }

    /**
     * Gets the message pattern.
     *
     * @param locale
     *            the locale
     * @param code
     *            the code
     * @return the message pattern
     */
    private String getMessagePattern(final Locale locale, final KuraErrorCode code) {
        // Load the message pattern from the bundle
        String messagePattern = null;
        ResourceBundle resourceBundle = null;
        try {
            resourceBundle = ResourceBundle.getBundle(KURA_EXCEPTION_MESSAGES_BUNDLE, locale);
            if ((resourceBundle != null) && (code != null)) {
                messagePattern = resourceBundle.getString(code.name());
                if (messagePattern == null) {
                    logger.warn("Could not find Exception Messages for Locale {} and code {}", locale, code);
                }
            }
        } catch (final MissingResourceException mre) {
            // log the failure to load a message bundle
            logger.warn("Could not load Exception Messages Bundle for Locale {}", locale);
        }
        // If no bundle or code in the bundle is found, use a generic message
        if (messagePattern == null) {
            if (code != null) {
                // build a generic message format
                messagePattern = MessageFormat.format(KURA_GENERIC_MESSAGES_PATTERN, code.name());
            } else {
                // build a generic message format
                messagePattern = MessageFormat.format(KURA_GENERIC_MESSAGES_PATTERN, "Unknown");
            }
        }
        return messagePattern;
    }
}