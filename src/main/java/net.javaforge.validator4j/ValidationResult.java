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
import java.util.*;

/**
 * This class is a kind of container for validation messages.
 *
 * @author Maxim Kalina
 * @version $Id$
 */
public class ValidationResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Returns new empty validation result.
     *
     * @return new empty validation result.
     */
    public static ValidationResult empty() {
        return new ValidationResult();
    }

    private Collection<IValidationMessage> messages;

    public ValidationResult() {
        this(new LinkedHashSet<IValidationMessage>());
    }

    public ValidationResult(ValidationResult validationResult) {
        this.messages = new LinkedHashSet<IValidationMessage>(validationResult.messages);
    }

    public ValidationResult(IValidationMessage... messages) {
        this.messages = new LinkedHashSet<IValidationMessage>(Arrays.asList(messages));
    }

    public ValidationResult(Collection<IValidationMessage> messages) {
        this.messages = new LinkedHashSet<IValidationMessage>(messages);
    }

    /**
     * Adds validation messages array to this validation result.
     *
     * @param messages is an array of messages to add
     * @return this validation result
     */
    public ValidationResult add(IValidationMessage... messages) {
        if (messages != null) {
            Collections.addAll(this.messages, messages);
        }
        return this;
    }

    /**
     * Adds validation messages collection to this validation result.
     *
     * @param messages is a collection of messages to add
     * @return this validation result
     */
    public ValidationResult add(Collection<IValidationMessage> messages) {
        if (messages != null)
            return add(messages.toArray(new IValidationMessage[messages.size()]));

        return this;
    }

    /**
     * Adds existing validation result to this validation result.
     *
     * @param result is an existing validation result
     * @return this validation result
     */
    public ValidationResult add(ValidationResult result) {
        if (result != null && result.isNotEmpty())
            return add(result.getMessages());

        return this;
    }

    /**
     * Adds validation messages by prepending all message keys with the given prefix.
     *
     * @param prefix   is a prefix to use
     * @param messages is an array of validation messages to add
     * @return this validation result
     */
    public ValidationResult addWithPrefix(final CharSequence prefix,
                                          final IValidationMessage... messages) {
        if (messages != null) {
            for (final IValidationMessage msg : messages) {
                this.messages.add(new IValidationMessage() {
                    private static final long serialVersionUID = 1L;

                    public CharSequence getKey() {
                        return new StringBuffer().append(prefix).append(".").append(msg.getKey());
                    }

                    public Severity getSeverity() {
                        return msg.getSeverity();
                    }

                    public CharSequence[] getArgs() {
                        return msg.getArgs();
                    }
                });
            }
        }
        return this;
    }

    /**
     * Adds validation messages by prepending all message keys with the given prefix.
     *
     * @param prefix   is a prefix to use
     * @param messages is a collection of validation messages to add
     * @return this validation result
     */
    public ValidationResult addWithPrefix(CharSequence prefix,
                                          Collection<IValidationMessage> messages) {
        if (messages != null)
            return addWithPrefix(prefix, messages.toArray(new IValidationMessage[messages.size()]));

        return this;
    }

    /**
     * Adds validation result by prepending all message keys with the given prefix.
     *
     * @param prefix is a prefix to use
     * @param result is a validation result to add
     * @return this validation result
     */
    public ValidationResult addWithPrefix(CharSequence prefix, ValidationResult result) {
        if (result != null && result.isNotEmpty())
            return addWithPrefix(prefix, result.getMessages());

        return this;
    }

    /**
     * Returns true, if this validation result doesn't contain any messages. Otherwise returns
     * false.
     *
     * @return true, if this validation result doesn't contain any messages. Otherwise returns
     *         false.
     */
    public boolean isEmpty() {
        return this.messages.isEmpty();
    }

    /**
     * Returns true, if this validation result contains at least one message. Otherwise returns
     * false.
     *
     * @return true, if this validation result contains at least one message. Otherwise returns
     *         false.
     */
    public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    /**
     * Returns number of validation messages in this validation result.
     *
     * @return number of validation messages in this validation result.
     */
    public int size() {
        return this.messages.size();
    }

    /**
     * Returns unmodifiable (!) collection of validation messages assigned to this validation
     * result.
     *
     * @return the (unmodifiable) collection of validation messages
     */
    public Collection<IValidationMessage> getMessages() {
        return Collections.unmodifiableCollection(this.messages);
    }

    /**
     * Returns subset of validation messages filtered by the given severity.
     *
     * @param severity is a severity to use
     * @return filtered unmodifiable collection of validation messages
     */
    public Collection<IValidationMessage> getMessagesBySeverity(final IValidationMessage.Severity severity) {
        return this.getMessagesByFilter(new IValidationMessageFilter() {
            public boolean accept(IValidationMessage msg) {
                return severity == msg.getSeverity();
            }
        });
    }

    /**
     * Returns subset of validation messages those key starts with the given prefix.
     *
     * @param prefix is a prefix to use.
     * @return filtered unmodifiable collection of validation messages
     */
    public Collection<IValidationMessage> getMessagesByPrefix(final CharSequence prefix) {
        return this.getMessagesByFilter(new IValidationMessageFilter() {
            public boolean accept(IValidationMessage msg) {
                return msg != null
                        && String.valueOf(msg.getKey()).startsWith(String.valueOf(prefix));
            }
        });
    }

    /**
     * Returns subset validation messages filtered by given validation message filter.
     *
     * @param filter is a filter to use.
     * @return filtered unmodifiable collection of validation messages
     */
    public Collection<IValidationMessage> getMessagesByFilter(IValidationMessageFilter filter) {

        if (isEmpty())
            return Collections.emptySet();

        Collection<IValidationMessage> filtered = new ArrayList<IValidationMessage>();
        for (IValidationMessage msg : this.getMessages()) {
            if (filter.accept(msg))
                filtered.add(msg);
        }

        return Collections.unmodifiableCollection(filtered);
    }

    /**
     * Returns an iterator over the internal collection of validation messages.
     *
     * @return iterator over the internal collection of validation messages.
     */
    public Iterator<IValidationMessage> iterator() {
        return this.getMessages().iterator();
    }

    /**
     * Adds validation message <tt>msg</tt> on failed validation check.
     *
     * @param obj   is an object to execute check against
     * @param check is a validation check to execute
     * @param msg   is a validation message to add on failed check
     * @return this validation result
     */
    public <T> ValidationResult addMessageIfCheckFailed(T obj, IValidationCheck<T> check,
                                                        IValidationMessage msg) {
        if (!check.isSatisfied(obj))
            this.add(msg);

        return this;
    }

    /**
     * Adds validation message <tt>msg</tt> on failed validation check adding specified prefix at
     * the front of the message key.
     *
     * @param obj    is an object to execute check against
     * @param check  is a validation check to execute
     * @param prefix is a message prefix to use
     * @param msg    is a validation message to add on failed check
     * @return this validation result
     */
    public <T> ValidationResult addMessageWithPrefixIfCheckFailed(T obj, IValidationCheck<T> check,
                                                                  CharSequence prefix, IValidationMessage msg) {
        if (!check.isSatisfied(obj))
            this.addWithPrefix(prefix, msg);

        return this;
    }

    /**
     * Converts this validation result to ValidationException.
     *
     * @return this validation result as ValidationException.
     */
    public ValidationException toValidationException() {
        return new ValidationException(this);
    }

    /**
     * Throws validation exception containing this validation result, if it contains at least one
     * message. Otherwise returns the validation result itself.
     *
     * @return this validation result if it is empty
     * @throws ValidationException will be thrown if current validation result is not empty
     */
    public ValidationResult throwIfNotEmpty() throws ValidationException {
        if (this.isNotEmpty())
            throw this.toValidationException();

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append("[");
        if (this.messages != null) {
            sb.append("\n");
            for (IValidationMessage msg : messages) {
                sb.append("\tkey=").append(msg.getKey()).append(",severity=")
                        .append(msg.getSeverity()).append(",args={");

                if (msg.getArgs() != null)
                    sb.append(StringUtils.join(msg.getArgs(), ", "));

                sb.append("}\n");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Interface used to accept or filter validation messages.
     *
     * @author Maxim Kalina (maxim.kalina@isban.de)
     * @version $Id: ValidationResult.java,v 1.5 2011/05/05 15:02:17 mkalina Exp $
     */
    public interface IValidationMessageFilter {

        /**
         * Returns true, if the given validation message should be accepted by this filter.
         * Otherwise returns false, if this message should be filtered.
         *
         * @param msg is a validation message to check.
         * @return true, if message should be accepted. Otherwise returns false.
         */
        boolean accept(IValidationMessage msg);

    }

}
