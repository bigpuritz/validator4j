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

import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Collection of predefined validation checks.
 *
 * @author Maxim Kalina
 * @version $Id$
 */
public class PredefinedChecks {

    private PredefinedChecks() {
        // invisible constructor
    }

    /**
     * Returns validation check that tests whether date is in the future.
     *
     * @return validation check that tests whether date is in the future.
     */
    public static IValidationCheck<Date> inTheFuture() {
        return after(new Date());
    }

    /**
     * Returns validation check that tests whether date is in the future or null.
     *
     * @return validation check that tests whether date is in the future or null
     */
    public static IValidationCheck<Date> inTheFutureOrNull() {
        return afterOrNull(new Date());
    }

    /**
     * Returns validation check that tests whether date is in the past.
     *
     * @return validation check that tests whether date is in the past.
     */
    public static IValidationCheck<Date> inThePast() {
        return before(new Date());
    }

    /**
     * Returns validation check that tests whether date is in the past or null.
     *
     * @return validation check that tests whether date is in the past or null.
     */
    public static IValidationCheck<Date> inThePastOrNull() {
        return beforeOrNull(new Date());
    }

    /**
     * Returns validation check that tests whether date is before the given <tt>threshold</tt> (compared by
     * timestamps).
     *
     * @return validation check that tests whether date is before the given <tt>threshold</tt> (compared by
     *         timestamps).
     */
    public static IValidationCheck<Date> before(Date threshold) {
        return internalBefore(threshold, false, false);
    }

    /**
     * Returns validation check that tests whether date is before the given <tt>threshold</tt> (compared by timestamps)
     * or null.
     *
     * @return validation check that tests whether date is before the given <tt>threshold</tt> (compared by timestamps)
     *         or null.
     */
    public static IValidationCheck<Date> beforeOrNull(Date threshold) {
        return internalBefore(threshold, false, true);
    }

    /**
     * Returns validation check that tests whether date is before or equals the given <tt>threshold</tt> (compared by
     * timestamps).
     *
     * @return validation check that tests whether date is before or equals the given <tt>threshold</tt> (compared by
     *         timestamps).
     */
    public static IValidationCheck<Date> beforeOrSame(Date threshold) {
        return internalBefore(threshold, true, false);
    }

    /**
     * Returns validation check that tests whether date is before or equals the given <tt>threshold</tt> (compared by
     * timestamps) or is null.
     *
     * @return validation check that tests whether date is before or equals the given <tt>threshold</tt> (compared by
     *         timestamps) or is null.
     */
    public static IValidationCheck<Date> beforeOrSameOrNull(Date threshold) {
        return internalBefore(threshold, true, true);
    }

    private static IValidationCheck<Date> internalBefore(final Date threshold,
                                                         final boolean sameAllowed, final boolean nullAllowed) {
        return new IValidationCheck<Date>() {
            public boolean isSatisfied(Date obj) {

                if (obj == null)
                    return nullAllowed;

                return obj.getTime() < threshold.getTime()
                        || (sameAllowed && obj.getTime() == threshold.getTime());
            }
        };
    }

    /**
     * Returns validation check that tests whether date is after the given <tt>threshold</tt> (compared by timestamps).
     *
     * @return validation check that tests whether date is after the given <tt>threshold</tt> (compared by timestamps).
     */
    public static IValidationCheck<Date> after(Date threshold) {
        return internalAfter(threshold, false, false);
    }

    /**
     * Returns validation check that tests whether date is after the given <tt>threshold</tt> (compared by timestamps)
     * or null.
     *
     * @return validation check that tests whether date is after the given <tt>threshold</tt> (compared by timestamps)
     *         or null.
     */
    public static IValidationCheck<Date> afterOrNull(Date threshold) {
        return internalAfter(threshold, false, true);
    }

    /**
     * Returns validation check that tests whether date is after or equals the given <tt>threshold</tt> (compared by
     * timestamps).
     *
     * @return validation check that tests whether date is after or equals the given <tt>threshold</tt> (compared by
     *         timestamps).
     */
    public static IValidationCheck<Date> afterOrSame(Date threshold) {
        return internalAfter(threshold, true, false);
    }

    /**
     * Returns validation check that tests whether date is after or equals the given <tt>threshold</tt> (compared by
     * timestamps) or is null.
     *
     * @return validation check that tests whether date is after or equals the given <tt>threshold</tt> (compared by
     *         timestamps) or is null.
     */
    public static IValidationCheck<Date> afterOrSameOrNull(Date threshold) {
        return internalAfter(threshold, true, true);
    }

    private static IValidationCheck<Date> internalAfter(final Date threshold,
                                                        final boolean sameAllowed, final boolean nullAllowed) {
        return new IValidationCheck<Date>() {
            public boolean isSatisfied(Date obj) {
                if (obj == null)
                    return nullAllowed;

                return obj.getTime() > threshold.getTime()
                        || (sameAllowed && obj.getTime() == threshold.getTime());

            }
        };
    }

    /**
     * Returns validation check that tests whether date represents the same day as the given <tt>value</tt>.
     *
     * @return validation check that tests whether date represents the same day as the given <tt>value</tt>.
     */
    public static IValidationCheck<Date> sameDay(final Date value) {
        return new IValidationCheck<Date>() {
            public boolean isSatisfied(Date obj) {
                return obj != null && DateUtils.isSameDay(obj, value);

            }
        };
    }

    /**
     * Returns validation check that tests whether date is null or represents the same day as the given <tt>value</tt>.
     *
     * @return validation check that tests whether date is null or represents the same day as the given <tt>value</tt>.
     */
    public static IValidationCheck<Date> sameDayOrNull(final Date value) {
        return new IValidationCheck<Date>() {
            public boolean isSatisfied(Date obj) {
                return obj == null || DateUtils.isSameDay(obj, value);

            }
        };
    }

    /**
     * Returns validation check that tests whether collection is not null and not empty.
     *
     * @return validation check that tests whether collection is not null and not empty.
     */
    public static IValidationCheck<Collection<?>> notNullOrEmpty() {
        return new IValidationCheck<Collection<?>>() {
            public boolean isSatisfied(Collection<?> obj) {
                return obj != null && !obj.isEmpty();

            }
        };
    }

    /**
     * Returns validation check that tests whether some object is not null.
     *
     * @return validation check that tests whether some object is not null.
     */
    public static <T> IValidationCheck<T> notNull() {
        return new IValidationCheck<T>() {
            public boolean isSatisfied(T obj) {
                return obj != null;

            }
        };
    }

    /**
     * Returns validation check that tests whether some object is null.
     *
     * @return validation check that tests whether some object is null.
     */
    public static <T> IValidationCheck<T> isNull() {
        return new IValidationCheck<T>() {
            public boolean isSatisfied(T obj) {
                return obj == null;

            }
        };
    }

    /**
     * Returns validation check that tests whether string is not blank (not null, not empty and is not whitespaces
     * only).
     *
     * @return validation check that tests whether string is not blank (not null, not empty and is not whitespaces
     *         only).
     */
    public static IValidationCheck<String> notBlank() {
        return new IValidationCheck<String>() {
            public boolean isSatisfied(String obj) {
                return StringUtils.isNotBlank(obj);

            }
        };
    }

    /**
     * Returns validation check that tests whether string is blank (whitespace, empty or null).
     *
     * @return validation check that tests whether string is blank (whitespace, empty or null). doesn't contain any
     *         whitespaces).
     */
    public static IValidationCheck<String> blank() {
        return new IValidationCheck<String>() {
            public boolean isSatisfied(String obj) {
                return StringUtils.isBlank(obj);

            }
        };
    }

    /**
     * Returns validation check that tests whether string is not empty (not null and not empty).
     *
     * @return validation check that tests whether string is not empty (not null and not empty).
     */
    public static IValidationCheck<String> notEmpty() {
        return new IValidationCheck<String>() {
            public boolean isSatisfied(String obj) {
                return StringUtils.isNotEmpty(obj);

            }
        };
    }

    /**
     * Returns validation check that tests whether string is empty (null or empty).
     *
     * @return validation check that tests whether string is empty (null or empty).
     */
    public static IValidationCheck<String> empty() {
        return new IValidationCheck<String>() {
            public boolean isSatisfied(String obj) {
                return StringUtils.isEmpty(obj);

            }
        };
    }

    /**
     * Returns validation check that tests whether string contains only unicode letters.
     *
     * @return validation check that tests whether string contains only unicode letters.
     */
    public static IValidationCheck<String> alpha() {
        return new IValidationCheck<String>() {
            public boolean isSatisfied(String obj) {
                return StringUtils.isAlpha(obj);

            }
        };
    }

    /**
     * Returns validation check that tests whether string contains only unicode letters or digits.
     *
     * @return validation check that tests whether string contains only unicode letters or digits.
     */
    public static IValidationCheck<String> alphanumeric() {
        return new IValidationCheck<String>() {
            public boolean isSatisfied(String obj) {
                return StringUtils.isAlphanumeric(obj);

            }
        };
    }

    /**
     * Returns validation check that tests whether string contains only unicode letters, digits or space.
     *
     * @return validation check that tests whether string contains only unicode letters, digits or space
     */
    public static IValidationCheck<String> alphanumericSpace() {
        return new IValidationCheck<String>() {
            public boolean isSatisfied(String obj) {
                return StringUtils.isAlphanumericSpace(obj);

            }
        };
    }

    /**
     * Returns validation check that tests whether string contains only unicode letters or space.
     *
     * @return validation check that tests whether string contains only unicode letters or space
     */
    public static IValidationCheck<String> alphaSpace() {
        return new IValidationCheck<String>() {
            public boolean isSatisfied(String obj) {
                return StringUtils.isAlphaSpace(obj);

            }
        };
    }

    /**
     * Returns validation check that tests whether string contains only ASCII printable characters.
     *
     * @return validation check that tests whether string contains only ASCII printable characters
     */
    public static IValidationCheck<String> asciiPrintable() {
        return new IValidationCheck<String>() {
            public boolean isSatisfied(String obj) {
                return StringUtils.isAsciiPrintable(obj);

            }
        };
    }

    /**
     * Returns validation check that tests whether string contains only digits.
     *
     * @return validation check that tests whether string contains only digits
     */
    public static IValidationCheck<String> numeric() {
        return new IValidationCheck<String>() {
            public boolean isSatisfied(String obj) {
                return StringUtils.isNumeric(obj);

            }
        };
    }

    /**
     * Returns validation check that tests whether string contains only digits or space.
     *
     * @return validation check that tests whether string contains only digits or space
     */
    public static IValidationCheck<String> numericSpace() {
        return new IValidationCheck<String>() {
            public boolean isSatisfied(String obj) {
                return StringUtils.isNumericSpace(obj);

            }
        };
    }

    /**
     * Returns validation check that tests whether string contains only whitespace.
     *
     * @return validation check that tests whether string contains only whitespace
     */
    public static IValidationCheck<String> whitespace() {
        return new IValidationCheck<String>() {
            public boolean isSatisfied(String obj) {
                return StringUtils.isWhitespace(obj);

            }
        };
    }

    /**
     * Returns validation check that tests whether charsequence is not null and matches given regexp.
     *
     * @return validation check that tests whether charsequence is not null and matches given regexp.
     */
    public static IValidationCheck<CharSequence> matches(final Pattern pattern) {
        return new IValidationCheck<CharSequence>() {
            public boolean isSatisfied(CharSequence obj) {
                return obj != null && pattern.matcher(obj).matches();

            }
        };
    }

    /**
     * Returns validation check that tests whether charsequence is null or matches given regexp.
     *
     * @return validation check that tests whether charsequence is null or matches given regexp.
     */
    public static IValidationCheck<CharSequence> matchesOrNull(final Pattern pattern) {
        return new IValidationCheck<CharSequence>() {
            public boolean isSatisfied(CharSequence obj) {
                return obj == null || pattern.matcher(obj).matches();

            }
        };
    }

    /**
     * Returns validation check that tests whether charsequence is not null and is equals to the given test object.
     *
     * @param other is a value to check against.
     * @return validation check that tests whether charsequence is not null and is equals to the given test object.
     */
    public static IValidationCheck<CharSequence> eq(final CharSequence other) {
        return new IValidationCheck<CharSequence>() {
            public boolean isSatisfied(CharSequence obj) {
                return obj != null && other.equals(obj);

            }
        };
    }

    /**
     * Returns validation check that tests whether charsequence is not null and is not equals to the given test object.
     *
     * @return validation check that tests whether charsequence is not null and is not equals to the given test object.
     */
    public static IValidationCheck<CharSequence> neq(final CharSequence other) {
        return new IValidationCheck<CharSequence>() {
            public boolean isSatisfied(CharSequence obj) {
                return obj != null && !other.equals(obj);

            }
        };
    }

    /**
     * Returns validation check that tests whether {@link Comparable} is one of the given values.
     *
     * @param <T>    is a generic type of {@link Comparable}
     * @param values is an array of comparable values to check against.
     * @return validation check that tests whether {@link Comparable} is one of the given values
     */
    public static <T extends Comparable<T>> IValidationCheck<T> in(final T... values) {
        return new IValidationCheck<T>() {
            public boolean isSatisfied(T obj) {
                if (obj == null)
                    return values == null;

                if (values != null) {
                    for (T v : values) {
                        if (v.compareTo(obj) == 0)
                            return true;
                    }
                }

                return false;
            }
        };
    }

    /**
     * Returns validation check that tests whether length of the {@link CharSequence} is in the given (inclusive)
     * range.
     *
     * @param min is a range minimum
     * @param max is a range maximum
     * @return validation check that tests whether length of the {@link CharSequence} is in the given (inclusive)
     *         range.
     */
    public static IValidationCheck<CharSequence> lengthMinMax(final int min, final int max) {
        return lengthMinMaxInclusive(min, max);
    }

    /**
     * Returns validation check that tests whether length of the {@link CharSequence} is in the given range (inclusive
     * or exclusive).
     *
     * @param min       is a range minimum
     * @param max       is a range maximum
     * @param inclusive is a flag to indicate whether the range is inclusive or exclusive
     * @return validation check that tests whether length of the {@link CharSequence} is in the given (inclusive or
     *         exclusive) range.
     */
    public static IValidationCheck<CharSequence> lengthMinMax(final int min, final int max,
                                                              boolean inclusive) {
        return inclusive ? lengthMinMaxInclusive(min, max) : lengthMinMaxExclusive(min, max);
    }

    /**
     * Returns validation check that tests whether length of the {@link CharSequence} is in the given (inclusive)
     * range.
     *
     * @param min is a range minimum
     * @param max is a range maximum
     * @return validation check that tests whether length of the {@link CharSequence} is in the given (inclusive)
     *         range.
     */
    public static IValidationCheck<CharSequence> lengthMinMaxInclusive(final int min, final int max) {
        return new IValidationCheck<CharSequence>() {
            public boolean isSatisfied(CharSequence obj) {
                return obj.length() >= min && obj.length() <= max;
            }
        };
    }

    /**
     * Returns validation check that tests whether length of the {@link CharSequence} is in the given (exclusive)
     * range.
     *
     * @param min is a range minimum
     * @param max is a range maximum
     * @return validation check that tests whether length of the {@link CharSequence} is in the given (exclusive)
     *         range.
     */
    public static IValidationCheck<CharSequence> lengthMinMaxExclusive(final int min, final int max) {
        return new IValidationCheck<CharSequence>() {
            public boolean isSatisfied(CharSequence obj) {
                return obj.length() > min && obj.length() < max;
            }
        };
    }

    /**
     * Returns validation check that tests whether {@link Number} is in the given (inclusive) range<br>
     *
     * @param <T> is a generic type of {@link Number}
     * @param min is a range minimum
     * @param max is a range maximum
     * @return validation check that tests whether {@link Number} is in the given (inclusive) range
     */
    public static <T extends Number & Comparable<T>> IValidationCheck<T> inRangeInclusive(
            final T min, final T max) {
        return new IValidationCheck<T>() {
            public boolean isSatisfied(T obj) {
                return obj.compareTo(min) >= 0 && obj.compareTo(max) <= 0;
            }
        };
    }

    /**
     * Returns validation check that tests whether {@link Number} is in the given (exclusive) range<br>
     *
     * @param <T> is a generic type of {@link Number}
     * @param min is a range minimum
     * @param max is a range maximum
     * @return validation check that tests whether {@link Number} is in the given (exclusive) range
     */
    public static <T extends Number & Comparable<T>> IValidationCheck<T> inRangeExclusive(
            final T min, final T max) {
        return new IValidationCheck<T>() {
            public boolean isSatisfied(T obj) {
                return obj.compareTo(min) > 0 && obj.compareTo(max) < 0;
            }
        };
    }

    /**
     * Returns validation check that tests whether {@link Number} is in the given (inclusive) range<br>
     *
     * @param <T> is a generic type of {@link Number}
     * @param min is a range minimum
     * @param max is a range maximum
     * @return validation check that tests whether {@link Number} is in the given (exclusive) range
     */
    public static <T extends Number & Comparable<T>> IValidationCheck<T> inRange(final T min,
                                                                                 final T max) {
        return inRange(min, max, true);
    }

    /**
     * Returns validation check that tests whether {@link Number} is in the given range.<br>
     *
     * @param <T>       is a generic type of {@link Number}
     * @param min       is a range minimum
     * @param max       is a range maximum
     * @param inclusive is a flag whether the range is inclusive or not.
     * @return validation check that tests whether {@link Number} is in the given (exclusive) range
     */
    public static <T extends Number & Comparable<T>> IValidationCheck<T> inRange(final T min,
                                                                                 final T max, boolean inclusive) {
        return inclusive ? inRangeInclusive(min, max) : inRangeExclusive(min, max);
    }

    /**
     * Returns validation check that tests whether {@link Number} is less than given threshold<br>
     *
     * @param <T>       is a generic type of {@link Number}
     * @param threshold is a threshold to check against
     * @return validation check that tests whether {@link Number} is less than given threshold
     */
    public static <T extends Number & Comparable<T>> IValidationCheck<T> lt(final T threshold) {
        return new IValidationCheck<T>() {
            public boolean isSatisfied(T obj) {
                return obj.compareTo(threshold) < 0;
            }
        };
    }

    /**
     * Returns validation check that tests whether {@link Number} is less or equals than given threshold<br>
     *
     * @param <T>       is a generic type of {@link Number}
     * @param threshold is a threshold to check against
     * @return validation check that tests whether {@link Number} is less or equals than given threshold
     */
    public static <T extends Number & Comparable<T>> IValidationCheck<T> lte(final T threshold) {
        return new IValidationCheck<T>() {
            public boolean isSatisfied(T obj) {
                return obj.compareTo(threshold) <= 0;
            }
        };
    }

    /**
     * Returns validation check that tests whether {@link Number} is greater than given threshold<br>
     *
     * @param <T>       is a generic type of {@link Number}
     * @param threshold is a threshold to check against
     * @return validation check that tests whether {@link Number} is greater than given threshold
     */
    public static <T extends Number & Comparable<T>> IValidationCheck<T> gt(final T threshold) {
        return new IValidationCheck<T>() {
            public boolean isSatisfied(T obj) {
                return obj.compareTo(threshold) > 0;
            }
        };
    }

    /**
     * Returns validation check that tests whether {@link Number} is greater or equals than given threshold<br>
     *
     * @param <T>       is a generic type of {@link Number}
     * @param threshold is a threshold to check against
     * @return validation check that tests whether {@link Number} is greater or equals than given threshold
     */
    public static <T extends Number & Comparable<T>> IValidationCheck<T> gte(final T threshold) {
        return new IValidationCheck<T>() {
            public boolean isSatisfied(T obj) {
                return obj.compareTo(threshold) >= 0;
            }
        };
    }

    /**
     * Returns validation check that tests whether {@link Number} is equals to the given value<br> <br>
     *
     * @param <T>   is a generic type of {@link Number}
     * @param value is a value to check against
     * @return validation check that tests whether {@link Number} is equals to the given value
     */
    public static <T extends Number & Comparable<T>> IValidationCheck<T> eq(final T value) {
        return new IValidationCheck<T>() {
            public boolean isSatisfied(T obj) {
                return obj.compareTo(value) == 0;
            }
        };
    }

    /**
     * Returns validation check that tests whether {@link Number} is not equals to the given value<br> <br> <b> NOTE!
     * This method uses {@link Comparable#compareTo(Object)} method to check the condition! In case of using Comparables
     * other than Number this check can lead to unexpected side-effects (e.g. compareTo in case of string compares
     * strings contents). </b>
     *
     * @param <T>   is a generic type of {@link Number}
     * @param value is a value to check against
     * @return validation check that tests whether {@link Number} is not equals to the given value
     */
    public static <T extends Number & Comparable<T>> IValidationCheck<T> neq(final T value) {
        return new IValidationCheck<T>() {
            public boolean isSatisfied(T obj) {
                return obj.compareTo(value) != 0;
            }
        };
    }

    /**
     * see {@link #lt(Number)}
     */
    public static IValidationCheck<Integer> lt(final int threshold) {
        return lt(Integer.valueOf(threshold));
    }

    /**
     * see {@link #lte(Number)}
     */
    public static IValidationCheck<Integer> lte(final int threshold) {
        return lte(Integer.valueOf(threshold));
    }

    /**
     * see {@link #gt(Number)}
     */
    public static IValidationCheck<Integer> gt(final int threshold) {
        return gt(Integer.valueOf(threshold));
    }

    /**
     * see {@link #gte(Number)}
     */
    public static IValidationCheck<Integer> gte(final int threshold) {
        return gte(Integer.valueOf(threshold));
    }

    /**
     * see {@link #eq(Number)}
     */
    public static IValidationCheck<Integer> eq(final int threshold) {
        return eq(Integer.valueOf(threshold));
    }

    /**
     * see {@link #neq(Number)}
     */
    public static IValidationCheck<Integer> neq(final int threshold) {
        return neq(Integer.valueOf(threshold));
    }

    /**
     * see {@link #lt(Number)}
     */
    public static IValidationCheck<Double> lt(final double threshold) {
        return lt(Double.valueOf(threshold));
    }

    /**
     * see {@link #lte(Number)}
     */
    public static IValidationCheck<Double> lte(final double threshold) {
        return lte(Double.valueOf(threshold));
    }

    /**
     * see {@link #gt(Number)}
     */
    public static IValidationCheck<Double> gt(final double threshold) {
        return gt(Double.valueOf(threshold));
    }

    /**
     * see {@link #gte(Number)}
     */
    public static IValidationCheck<Double> gte(final double threshold) {
        return gte(Double.valueOf(threshold));
    }

    /**
     * see {@link #eq(Number)}
     */
    public static IValidationCheck<Double> eq(final double threshold) {
        return eq(Double.valueOf(threshold));
    }

    /**
     * see {@link #neq(Number)}
     */
    public static IValidationCheck<Double> neq(final double threshold) {
        return neq(Double.valueOf(threshold));
    }

    /**
     * see {@link #lt(Number)}
     */
    public static IValidationCheck<Long> lt(final long threshold) {
        return lt(Long.valueOf(threshold));
    }

    /**
     * see {@link #lte(Number)}
     */
    public static IValidationCheck<Long> lte(final long threshold) {
        return lte(Long.valueOf(threshold));
    }

    /**
     * see {@link #gt(Number)}
     */
    public static IValidationCheck<Long> gt(final long threshold) {
        return gt(Long.valueOf(threshold));
    }

    /**
     * see {@link #gte(Number)}
     */
    public static IValidationCheck<Long> gte(final long threshold) {
        return gte(Long.valueOf(threshold));
    }

    /**
     * see {@link #eq(Number)}
     */
    public static IValidationCheck<Long> eq(final long threshold) {
        return eq(Long.valueOf(threshold));
    }

    /**
     * see {@link #neq(Number)}
     */
    public static IValidationCheck<Long> neq(final long threshold) {
        return neq(Long.valueOf(threshold));
    }

    /**
     * see {@link #lt(Number)}
     */
    public static IValidationCheck<Float> lt(final float threshold) {
        return lt(Float.valueOf(threshold));
    }

    /**
     * see {@link #lte(Number)}
     */
    public static IValidationCheck<Float> lte(final float threshold) {
        return lte(Float.valueOf(threshold));
    }

    /**
     * see {@link #gt(Number)}
     */
    public static IValidationCheck<Float> gt(final float threshold) {
        return gt(Float.valueOf(threshold));
    }

    /**
     * see {@link #gte(Number)}
     */
    public static IValidationCheck<Float> gte(final float threshold) {
        return gte(Float.valueOf(threshold));
    }

    /**
     * see {@link #eq(Number)}
     */
    public static IValidationCheck<Float> eq(final float threshold) {
        return eq(Float.valueOf(threshold));
    }

    /**
     * see {@link #neq(Number)}
     */
    public static IValidationCheck<Float> neq(final float threshold) {
        return neq(Float.valueOf(threshold));
    }


    /**
     * Returns check to test whether given value if true.
     *
     * @param value is a value to check.
     * @return validation check
     */
    public static IValidationCheck<Boolean> isTrue(final Boolean value) {
        return new IValidationCheck<Boolean>() {
            public boolean isSatisfied(Boolean obj) {
                return obj != null && obj;
            }
        };
    }

    /**
     * Returns check to test whether given value if true or null.
     *
     * @param value is a value to check.
     * @return validation check
     */
    public static IValidationCheck<Boolean> isTrueOrNull(final Boolean value) {
        return new IValidationCheck<Boolean>() {
            public boolean isSatisfied(Boolean obj) {
                return obj == null || obj;
            }
        };
    }

    /**
     * Returns check to test whether given value if false.
     *
     * @param value is a value to check.
     * @return validation check
     */
    public static IValidationCheck<Boolean> isFalse(final Boolean value) {
        return new IValidationCheck<Boolean>() {
            public boolean isSatisfied(Boolean obj) {
                return obj != null && !obj;
            }
        };
    }

    /**
     * Returns check to test whether given value if false or null.
     *
     * @param value is a value to check.
     * @return validation check
     */
    public static IValidationCheck<Boolean> isFalseOrNull(final Boolean value) {
        return new IValidationCheck<Boolean>() {
            public boolean isSatisfied(Boolean obj) {
                return obj == null || !obj;
            }
        };
    }

}
