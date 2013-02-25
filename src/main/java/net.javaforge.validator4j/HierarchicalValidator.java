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

import java.lang.reflect.Field;
import java.util.*;

/**
 * Validator used to test object hierarchies.
 *
 * @author Maxim Kalina
 * @version $Id$
 */
public class HierarchicalValidator<T> implements IValidator<T> {

    private IValidator<T> preValidator;

    private Map<CharSequence, Collection<IValidator<?>>> fieldValidators;

    private IValidator<T> postValidator;

    private boolean processFieldsIfPreValidatorFails = false;

    private boolean postValidateIfFieldValidatorFails = false;

    private boolean appendFieldPrefix = true;

    private boolean stopOnFirstInvalidField = false;

    private CharSequence prefix;

    /**
     * Configures prefix added in front of every validation message.
     *
     * @param prefix is a prefix to add.
     * @return this validator.
     */
    public HierarchicalValidator<T> withPrefix(CharSequence prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * Indicates that object field validator should run even though the pre-validator fails.
     *
     * @return this validator.
     */
    public HierarchicalValidator<T> processFieldsIfPreValidationFails() {
        this.processFieldsIfPreValidatorFails = true;
        return this;
    }

    /**
     * Indicates that the validator run should stop after first invalid field was found.
     *
     * @return this validator.
     */
    public HierarchicalValidator<T> stopOnFirstInvalidField() {
        this.stopOnFirstInvalidField = true;
        return this;
    }

    /**
     * Indicates that post validator (if exists) should run even if previous validation steps
     * (pre-validator, field validator) fail.
     *
     * @return this validator.
     */
    public HierarchicalValidator<T> postValidateIfFieldValidatorFails() {
        this.postValidateIfFieldValidatorFails = true;
        return this;
    }

    /**
     * Indicates that object field validation messages should be created without field name in front
     * of the corresponding validation message.
     *
     * @return this validator.
     */
    public HierarchicalValidator<T> withoutFieldPrefix() {
        this.appendFieldPrefix = false;
        return this;
    }

    /**
     * Configures pre-valdiator that will be executed before field-validators run.
     *
     * @param msg   is a validation message associated with the pre-validator#s check.
     * @param check is a validation check to execute in the pre-validation phase.
     * @return this validator.
     */
    @SuppressWarnings("unchecked")
    public HierarchicalValidator<T> withPreValidator(IValidationMessage msg,
                                                     IValidationCheck<T> check) {
        return this.withPreValidator(new Validator<T>(msg, check));
    }

    /**
     * Configures pre-validator that will be executed before field-validators run.
     *
     * @param msg    is a validation message associated with the pre-validator's checks.
     * @param check1 is a first validation check to execute in the pre-validation phase.
     * @param check2 is a second validation check to execute in the pre-validation phase.
     * @return this validator.
     */
    @SuppressWarnings("unchecked")
    public HierarchicalValidator<T> withPreValidator(IValidationMessage msg,
                                                     IValidationCheck<T> check1, IValidationCheck<T> check2) {
        return this.withPreValidator(new Validator<T>(msg, check1, check2));
    }

    /**
     * Configures pre-valdiator that will be executed before field-validators run.
     *
     * @param msg    is a validation message associated with the pre-validator's checks.
     * @param check1 is a first validation check to execute in the pre-validation phase.
     * @param check2 is a second validation check to execute in the pre-validation phase.
     * @param check3 is a third validation check to execute in the pre-validation phase.
     * @return this validator.
     */
    @SuppressWarnings("unchecked")
    public HierarchicalValidator<T> withPreValidator(IValidationMessage msg,
                                                     IValidationCheck<T> check1, IValidationCheck<T> check2, IValidationCheck<T> check3) {
        return this.withPreValidator(new Validator<T>(msg, check1, check2, check3));
    }

    /**
     * Configures pre-valdiator that will be executed before field-validators run.
     *
     * @param msg    is a validation message associated with the pre-validator's checks.
     * @param checks is an array of validation checks to execute in the pre-validation phase.
     * @return this validator.
     */
    public HierarchicalValidator<T> withPreValidator(IValidationMessage msg,
                                                     IValidationCheck<T>... checks) {
        return this.withPreValidator(new Validator<T>(msg, checks));
    }

    /**
     * Configures pre-valdiator that will be executed before field-validators run.
     *
     * @param preValidator is a validator to run.
     * @return this validator.
     */
    public HierarchicalValidator<T> withPreValidator(IValidator<T> preValidator) {
        this.preValidator = preValidator;
        return this;
    }

    /**
     * Configures post-valdiator that will be executed after field-validators run.
     *
     * @param postValidator is a validator to run.
     * @return this validator.
     */
    public HierarchicalValidator<T> withPostValidator(IValidator<T> postValidator) {
        this.postValidator = postValidator;
        return this;
    }

    /**
     * Configures post-valdiator that will be executed after field-validators run.
     *
     * @param msg   is a validation message associated with the post-validator's checks.
     * @param check is a validation check to execute in the post-validation phase.
     * @return this validator.
     */
    @SuppressWarnings("unchecked")
    public HierarchicalValidator<T> withPostValidator(IValidationMessage msg,
                                                      IValidationCheck<T> check) {
        return this.withPostValidator(new Validator<T>(msg, check));
    }

    /**
     * Configures post-valdiator that will be executed after field-validators run.
     *
     * @param msg    is a validation message associated with the post-validator's checks.
     * @param check1 is a first validation check to execute in the post-validation phase.
     * @param check2 is a second validation check to execute in the post-validation phase.
     * @return this validator.
     */
    @SuppressWarnings("unchecked")
    public HierarchicalValidator<T> withPostValidator(IValidationMessage msg,
                                                      IValidationCheck<T> check1, IValidationCheck<T> check2) {
        return this.withPostValidator(new Validator<T>(msg, check1, check2));
    }

    /**
     * Configures post-valdiator that will be executed after field-validators run.
     *
     * @param msg    is a validation message associated with the post-validator's checks.
     * @param check1 is a first validation check to execute in the post-validation phase.
     * @param check2 is a second validation check to execute in the post-validation phase.
     * @param check3 is a third validation check to execute in the post-validation phase.
     * @return this validator.
     */
    @SuppressWarnings("unchecked")
    public HierarchicalValidator<T> withPostValidator(IValidationMessage msg,
                                                      IValidationCheck<T> check1, IValidationCheck<T> check2, IValidationCheck<T> check3) {
        return this.withPostValidator(new Validator<T>(msg, check1, check2, check3));
    }

    /**
     * Configures post-valdiator that will be executed after field-validators run.
     *
     * @param msg    is a validation message associated with the post-validator's checks.
     * @param checks is an array of validation checks to execute in the post-validation phase.
     * @return this validator.
     */
    public HierarchicalValidator<T> withPostValidator(IValidationMessage msg,
                                                      IValidationCheck<T>... checks) {
        return this.withPostValidator(new Validator<T>(msg, checks));
    }

    /**
     * Adds arrays of validators associated with the object field by given <tt>fieldName</tt>
     *
     * @param fieldName  is a name of the object's field
     * @param validators is an array of validators to run
     * @return this validator.
     */
    public HierarchicalValidator<T> addFieldValidator(CharSequence fieldName,
                                                      IValidator<?>... validators) {
        if (this.fieldValidators == null)
            this.fieldValidators = new LinkedHashMap<CharSequence, Collection<IValidator<?>>>();

        Collection<IValidator<?>> fieldValidators;
        if (this.fieldValidators.containsKey(fieldName)) {
            fieldValidators = this.fieldValidators.get(fieldName);
        } else {
            fieldValidators = new ArrayList<IValidator<?>>();
        }
        fieldValidators.addAll(Arrays.asList(validators));
        this.fieldValidators.put(fieldName, fieldValidators);
        return this;
    }

    /**
     * Associates array of validation checks and the validation messages with the object field by
     * given <tt>fieldName</tt>
     *
     * @param fieldName is a name of the object's field
     * @param msg       is a validation message to use on failed check
     * @param checks    array of validation checks
     * @return this validator
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public HierarchicalValidator<T> addFieldValidator(CharSequence fieldName,
                                                      IValidationMessage msg, IValidationCheck<?>... checks) {
        return this.addFieldValidator(fieldName, new Validator(msg, checks));
    }

    /**
     * {@inheritDoc}
     *
     * @see IValidator#validate(java.lang.Object)
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public final ValidationResult validate(T obj) {

        ValidationResult result = new ValidationResult();
        if (this.prefix != null)
            result.addWithPrefix(this.prefix, preValidate(obj));
        else
            result.add(preValidate(obj));

        if (result.isNotEmpty() && !this.processFieldsIfPreValidatorFails)
            return result;

        for (Map.Entry<CharSequence, Collection<IValidator<?>>> entry : fieldValidators.entrySet()) {
            Object fieldValue = this.resolveFieldValue(obj, entry.getKey());
            Collection<IValidator<?>> fieldValidators = entry.getValue();
            for (IValidator<?> fieldValidator : fieldValidators) {
                ValidationResult fieldValidationResult =
                        ((IValidator) fieldValidator).validate(fieldValue);

                StringBuffer msgPrefix = new StringBuffer();
                if (this.prefix != null)
                    msgPrefix.append(this.prefix);

                if (this.appendFieldPrefix)
                    msgPrefix.append(this.prefix != null ? "." : "").append(entry.getKey());

                if (msgPrefix.length() > 0)
                    result.addWithPrefix(msgPrefix, fieldValidationResult);
                else
                    result.add(fieldValidationResult);

                if (fieldValidationResult.isNotEmpty())
                    break;
            }

            if (stopOnFirstInvalidField && result.isNotEmpty())
                break;
        }

        if (result.isEmpty() || (result.isNotEmpty() && this.postValidateIfFieldValidatorFails)) {
            if (this.prefix != null)
                result.addWithPrefix(this.prefix, postValidate(obj));
            else
                result.add(postValidate(obj));
        }

        return result;
    }

    /**
     * Runs pre-validation. This method can be overridden by subclasses.
     *
     * @param obj is an object to validate.
     * @return result of the pre-validation.
     */
    protected ValidationResult preValidate(T obj) {
        if (preValidator != null) {
            return preValidator.validate(obj);
        }

        return null;
    }

    /**
     * Runs post-validation. This method can be overridden by subclasses.
     *
     * @param obj is an object to validate.
     * @return result of the post-validation.
     */
    protected ValidationResult postValidate(T obj) {
        if (postValidator != null) {
            return postValidator.validate(obj);
        }

        return null;
    }

    private Object resolveFieldValue(T obj, CharSequence fieldName) {
        try {

            if (obj == null)
                return null;

            Field f = this.findFieldRecursively(obj.getClass(), String.valueOf(fieldName));
            f.setAccessible(true);
            return f.get(obj);

        } catch (Exception e) {
            throw new RuntimeException("Error resolving field '" + fieldName
                    + "' within validation object of type '" + obj.getClass() + "'.", e);
        }

    }

    private Field findFieldRecursively(Class<?> clazz, String fieldName)
            throws NoSuchFieldException {

        if (clazz == null)
            throw new NoSuchFieldException(fieldName);

        try {
            Field f = clazz.getDeclaredField(String.valueOf(fieldName));
            return f;
        } catch (NoSuchFieldException e) {
            return findFieldRecursively(clazz.getSuperclass(), fieldName);
        }

    }
}
