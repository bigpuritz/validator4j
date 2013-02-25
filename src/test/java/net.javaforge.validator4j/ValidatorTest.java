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

import static net.javaforge.validator4j.PredefinedChecks.*;
import static net.javaforge.validator4j.PredefinedPatterns.alnum;

/**
 * @author Maxim Kalina
 * @version $Id$
 */
public class ValidatorTest extends TestCase {

    private static IValidationMessage key = new IValidationMessage() {
        private static final long serialVersionUID = 1L;

        public CharSequence getKey() {
            return "key";
        }

        public Severity getSeverity() {
            return Severity.ERROR;
        }

        public CharSequence[] getArgs() {
            return null;
        }
    };

    public void testValidateNull() throws Exception {

        try {
            Validator.of(key, notNull()).validate(null).throwIfNotEmpty();
            fail("validation result should be not empty!");
        } catch (ValidationException e) {
            // everything ok
        }

    }

    public void testValidateNotNull() throws Exception {

        try {
            Validator.of(key, notNull()).validate("test").throwIfNotEmpty();
        } catch (ValidationException e) {
            fail("validation result should be empty!");
        }

    }

    public void testValidateAlnum() throws Exception {

        try {
            Validator.of(key, matches(alnum())).validate("test").throwIfNotEmpty();
        } catch (ValidationException e) {
            fail("validation result should be empty!");
        }

    }

    public void testValidateNotAlnum() throws Exception {

        try {
            Validator.of(key, matches(alnum())).validate("!test!").throwIfNotEmpty();
            fail("validation result should be not empty!");
        } catch (ValidationException e) {
            // everything ok
        }

    }

    public void testInRangeInclusiveOK() throws Exception {

        try {

            Validator.of(key, inRangeInclusive(1, 5)).validate(2).throwIfNotEmpty();
            Validator.of(key, inRangeInclusive(1, 5)).validate(1).throwIfNotEmpty();
            Validator.of(key, inRange(1, 5)).validate(5).throwIfNotEmpty();

        } catch (ValidationException e) {
            fail("validation result should be empty!");
        }

    }

    public void testInRangeInclusiveFailed() throws Exception {

        try {

            Validator.of(key, inRangeInclusive(1, 5)).validate(0).throwIfNotEmpty();
            Validator.of(key, inRange(1, 5)).validate(6).throwIfNotEmpty();

            fail("validation result should not be empty!");

        } catch (ValidationException e) {
            // everything ok
        }

    }

    public void testInRangeExclusiveOK() throws Exception {

        try {

            Validator.of(key, inRangeExclusive(1, 5)).validate(2).throwIfNotEmpty();

        } catch (ValidationException e) {
            fail("validation result should be empty!");
        }

    }

    public void testInRangeExclusiveFailed() throws Exception {

        try {

            Validator.of(key, inRangeExclusive(1, 5)).validate(0).throwIfNotEmpty();
            Validator.of(key, inRangeExclusive(1, 5)).validate(1).throwIfNotEmpty();
            Validator.of(key, inRangeExclusive(1, 5)).validate(5).throwIfNotEmpty();
            Validator.of(key, inRange(1, 5, false)).validate(10).throwIfNotEmpty();

            fail("validation result should not be empty!");

        } catch (ValidationException e) {
            // everything ok
        }

    }

}
