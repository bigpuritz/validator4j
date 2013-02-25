/*
 * Copyright 2013 [name of copyright owner]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.javaforge.validator4j;


/**
 * Enumeration of typical predefined validation messages.
 * <br><br>
 * <b>Attention</b> : all predefined messages have {@link Severity#ERROR}-severity
 *
 * @author Maxim Kalina (maxim.kalina@isban.de)
 * @version $Id$
 */
public enum PredefinedMessages implements IValidationMessage {

    IS_NULL, IS_NOT_NULL, //
    IS_VALID, IS_NOT_VALID, //
    IS_REQUIRED, IS_NOT_REQUIRED, //
    IS_EMPTY, IS_NOT_EMPTY, //
    IS_LT, IS_LTE, //
    IS_GT, IS_GTE, //
    IS_EQ, IS_NEQ, //
    IS_IN_RANGE, IS_NOT_IN_RANGE, //
    IS_AFTER, IS_NOT_AFTER, //
    IS_BEFORE, IS_NOT_BEFORE, //
    IS_IN, IS_NOT_IN, //
    IS_TOO_LOW, IS_TOO_HIGH, //
    IS_NOT_POSITIVE, IS_NOT_POSITIVE_OR_ZERO, //
    IS_NOT_NEGATIVE, IS_NOT_NEGATIVE_OR_ZERO, //
    IS_POSITIVE, IS_POSITIVE_OR_ZERO, //
    IS_NEGATIVE, IS_NEGATIVE_OR_ZERO, //
    IS_THE_SAME, IS_NOT_THE_SAME, //
    IS_ALPHA, IS_NOT_ALPHA, //
    IS_ALPHANUMERIC, IS_NOT_ALPHANUMERIC, //
    IS_NUMERIC, IS_NOT_NUMERIC, //
    IS_ASCII, IS_NOT_ASCII, //
    IS_MATCHES, IS_NOT_MATCHES, //
    IS_IN_THE_FUTURE, IS_IN_THE_PAST;

    public CharSequence getKey() {
        return this.name();
    }

    public Severity getSeverity() {
        return Severity.ERROR;
    }

    public CharSequence[] getArgs() {
        return null;
    }

    /**
     * Creates copy (new instance) of this validation message and re-initializes arguments in it.
     *
     * @param args is an array of validation arguments to use in the new validation message.
     * @return copy (new instance) of this validation message
     */
    public IValidationMessage withArgs(final CharSequence... args) {
        return withPrefixAndArgs(null, args);
    }

    /**
     * Creates copy (new instance) of this validation message and re-initializes key (by adding
     * given prefix at the front of the existing key) and arguments in it.
     *
     * @param prefix is a message key prefix.
     * @param args   is an array of validation arguments to use in the new validation message.
     * @return copy (new instance) of this validation message
     */
    public IValidationMessage withPrefixAndArgs(final CharSequence prefix,
                                                final CharSequence... args) {
        return new IValidationMessage() {
            private static final long serialVersionUID = 1L;

            public CharSequence getKey() {
                return prefix != null ? new StringBuffer(prefix).append(".").append(
                        PredefinedMessages.this.getKey()) : PredefinedMessages.this.getKey();
            }

            public Severity getSeverity() {
                return PredefinedMessages.this.getSeverity();
            }

            public CharSequence[] getArgs() {
                return args;
            }

        };
    }

}
