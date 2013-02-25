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

import java.util.Date;

import static net.javaforge.validator4j.PredefinedChecks.*;
import static net.javaforge.validator4j.PredefinedMessages.IS_NOT_IN_RANGE;
import static net.javaforge.validator4j.PredefinedMessages.IS_NOT_VALID;
import static net.javaforge.validator4j.PredefinedPatterns.alnumMinMax;

/**
 * @author Maxim Kalina
 * @version $Id$
 */
public class ValidationBeanTest extends TestCase {

    public class Bean {

        public Bean(String name, int age, Date tstmp) {
            this.name = name;
            this.age = age;
            this.tstmp = tstmp;
        }

        private String name;

        private int age;

        private Date tstmp;

    }

    private static IValidator<Bean> validator = new IValidator<Bean>() {
        public ValidationResult validate(Bean obj) {
            return new ValidationResult()
                    .addMessageIfCheckFailed(obj.name, matches(alnumMinMax(1, 10)), IS_NOT_VALID)
                    .addMessageIfCheckFailed(obj.age, inRange(18, 50),
                            IS_NOT_IN_RANGE.withArgs("18", "50"))
                    .addMessageIfCheckFailed(obj.tstmp, notNull(), IS_NOT_VALID);
        }
    };

    public void testBeanOK() throws Exception {

        Bean b = new Bean("test", 20, new Date());

        try {
            validator.validate(b).throwIfNotEmpty();
        } catch (ValidationException e) {
            fail("This test should not fail!");
        }

    }

    public void testBeanFailedOnName() throws Exception {

        try {

            validator.validate(new Bean("!!!", 20, new Date())).throwIfNotEmpty();
            fail("This test should fail on invalid name!");

        } catch (ValidationException e) {

            assertNotNull(e.getValidationResult());
            assertTrue(e.getValidationResult().isNotEmpty());
            assertEquals(1, e.getValidationResult().size());

            IValidationMessage msg = e.getValidationResult().iterator().next();

            assertNotNull(msg);
            assertEquals(IS_NOT_VALID.name(), msg.getKey());
            assertEquals(IValidationMessage.Severity.ERROR, msg.getSeverity());
            assertNull(msg.getArgs());
        }

    }

}
