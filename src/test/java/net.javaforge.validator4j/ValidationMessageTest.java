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

import junit.framework.TestCase;

/**
 * @author Maxim Kalina
 * @version $Id$
 */
public class ValidationMessageTest extends TestCase {

    enum Keys implements IValidationMessage {

        KEY1;

        public CharSequence getKey() {
            return this.name();
        }

        public Severity getSeverity() {
            return Severity.ERROR;
        }

        public CharSequence[] getArgs() {
            return new CharSequence[]{"a", "b"};
        }
    }

    public void testValidationMessage() {
        assertEquals("KEY1", Keys.KEY1.getKey());
        assertEquals(IValidationMessage.Severity.ERROR, Keys.KEY1.getSeverity());
        assertNotNull(Keys.KEY1.getArgs());
        assertTrue(Keys.KEY1.getArgs().length == 2);
    }

}
