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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Base validation implementation.
 *
 * @author Maxim Kalina
 * @version $Id$
 */
public class Validator<T> implements IValidator<T> {

    private Map<IValidationCheck<T>, IValidationMessage> checksContainer;

    private boolean cancelOnFirstFailedCheck = true;

    private CharSequence prefix;

    public Validator() {
    }

    public Validator(IValidationMessage msg, IValidationCheck<T>... checks) {
        this.addChecks(msg, checks);
    }

    /**
     * Configures prefix added in front of every validation message.
     *
     * @param prefix is a prefix to add.
     * @return this validator.
     */
    public Validator<T> withPrefix(CharSequence prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see IValidator#validate(java.lang.Object)
     */
    public final ValidationResult validate(T obj) {
        ValidationResult vr = new ValidationResult();
        for (Map.Entry<IValidationCheck<T>, IValidationMessage> e : checksContainer.entrySet()) {
            if (this.prefix != null)
                vr.addMessageWithPrefixIfCheckFailed(obj, e.getKey(), prefix, e.getValue());
            else
                vr.addMessageIfCheckFailed(obj, e.getKey(), e.getValue());
            if (this.cancelOnFirstFailedCheck && vr.isNotEmpty())
                break;
        }
        return vr;
    }

    /**
     * Set true, if you want to cancel validation procedure after first validation check fails.
     *
     * @param cancelOnFirstFailedCheck is a boolean flag indicating whether validation procedure should be canceled after
     *                                 first failed validation check.
     */
    public void setCancelOnFirstFailedCheck(boolean cancelOnFirstFailedCheck) {
        this.cancelOnFirstFailedCheck = cancelOnFirstFailedCheck;
    }

    /**
     * Adds validation checks associated with the given validation message.
     *
     * @param msg    is a validation message to use.
     * @param checks is an array of validation checks to execute.
     * @return this validator.
     */
    public Validator<T> addChecks(IValidationMessage msg, IValidationCheck<T>... checks) {
        if (this.checksContainer == null)
            this.checksContainer = new LinkedHashMap<IValidationCheck<T>, IValidationMessage>();

        for (IValidationCheck<T> check : checks) {
            this.checksContainer.put(check, msg);
        }

        return this;
    }

    /**
     * Static builder for this validator.
     *
     * @param <T>   is a generic type of the object that will be validated by returned validator.
     * @param msg   is a validation message associated with the validation check.
     * @param check is a validation check to execute
     * @return created validator instance.
     */
    @SuppressWarnings("unchecked")
    public static <T> Validator<T> of(IValidationMessage msg, IValidationCheck<T> check) {
        return new Validator<T>(msg, check);
    }

    @SuppressWarnings("unchecked")
    public static <T> Validator<T> of(IValidationMessage msg, IValidationCheck<T> check1,
                                      IValidationCheck<T> check2) {
        return new Validator<T>(msg, check1, check2);
    }

    @SuppressWarnings("unchecked")
    public static <T> Validator<T> of(IValidationMessage msg, IValidationCheck<T> check1,
                                      IValidationCheck<T> check2, IValidationCheck<T> check3) {
        return new Validator<T>(msg, check1, check2, check3);
    }

    @SuppressWarnings("unchecked")
    public static <T> Validator<T> of(IValidationMessage msg, IValidationCheck<T> check1,
                                      IValidationCheck<T> check2, IValidationCheck<T> check3, IValidationCheck<T> check4) {
        return new Validator<T>(msg, check1, check2, check3, check4);
    }

    @SuppressWarnings("unchecked")
    public static <T> Validator<T> of(IValidationMessage msg, IValidationCheck<T> check1,
                                      IValidationCheck<T> check2, IValidationCheck<T> check3, IValidationCheck<T> check4,
                                      IValidationCheck<T> check5) {
        return new Validator<T>(msg, check1, check2, check3, check4, check5);
    }

    public static <T> Validator<T> of(IValidationMessage msg, IValidationCheck<T>[] checks) {
        return new Validator<T>(msg, checks);
    }
}
