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

import java.io.Serializable;

/**
 * Interface describing validation message.
 *
 * @author Maxim Kalina
 * @version $Id$
 */
public interface IValidationMessage extends Serializable {

    public enum Severity {
        ERROR, WARN, INFO;
    }

    /**
     * Returns unique key of the validation message.
     *
     * @return unique key of the validation message.
     */
    CharSequence getKey();

    /**
     * Returns severity of the validation message.
     *
     * @return severity of the validation message.
     */
    Severity getSeverity();

    /**
     * Returns array of arguments associated with this validation message.
     *
     * @return array of arguments associated with this validation message.
     */
    CharSequence[] getArgs();

}
