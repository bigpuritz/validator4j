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
 * Default {@link IValidationMessage} implementation.
 *
 * @author Maxim Kalina
 * @version $Id$
 */
public class ValidationMessageAdapter implements IValidationMessage {

    private static final long serialVersionUID = 1L;

    private CharSequence key;

    private Severity severity;

    private CharSequence[] args;

    public ValidationMessageAdapter(CharSequence key, CharSequence... args) {
        this(key, Severity.ERROR, args);
    }

    public ValidationMessageAdapter(CharSequence key, Severity severity, CharSequence... args) {
        this.key = key;
        this.severity = severity;
        this.args = args;
    }

    /**
     * {@inheritDoc}
     *
     * @see IValidationMessage#getKey()
     */
    public CharSequence getKey() {
        return this.key;
    }

    /**
     * {@inheritDoc}
     *
     * @see IValidationMessage#getSeverity()
     */
    public Severity getSeverity() {
        return this.severity;
    }

    /**
     * {@inheritDoc}
     *
     * @see IValidationMessage#getArgs()
     */
    public CharSequence[] getArgs() {
        return this.args;
    }

}
