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

import java.util.regex.Pattern;

/**
 * Collection of predefined validation regular expressions.
 *
 * @author Maxim Kalina
 * @version $Id$
 */
public class PredefinedPatterns {

    public static class SpecialChars {

        /**
         * Flag, that indicates that german special characters should be allowed.
         */
        public static final int GERMAN = 1 << 1;

        /**
         * Flag, that indicates that french special characters should be allowed.
         */
        public static final int FRENCH = 1 << 2;

        /**
         * Flag, that indicates that spanish special characters should be allowed.
         */
        public static final int SPANISH = 1 << 3;

        /**
         * Flag, that indicates that italian special characters should be allowed.
         */
        public static final int ITALIAN = 1 << 4;

        /**
         * Flag, that indicates that all known special characters should be allowed.
         */
        public static final int ALL = 1 << 5;

        /**
         * Set of german special characters taken from unicode table. See
         * http://unicode.e-workers.de/deutsch.php for more details.
         */
        private static final String GERMAN_SPECIAL_CHARACTERS =
                "\\u00C4\\u00E4\\u00D6\\u00F6\\u00DC\\u00FC\\u00DF";

        /**
         * Set of french special characters taken from unicode table. See
         * http://unicode.e-workers.de/franzoesisch.php for more details.
         */
        private static final String FRENCH_SPECIAL_CHARACTERS =
                "\\u00C0\\u00E0\\u00C2\\u00E2\\u00C6\\u00E6\\u00C7\\u00E7\\u00C8\\u00E8\\u00C9\\u00E9\\u00CA\\u00EA\\u00CB\\u00EB\\u00CE\\u00EE\\u00CF\\u00EF\\u00D4\\u00F4\\u0152\\u0153\\u00D9\\u00F9\\u00DB\\u00FB\\u0178\\u00FF\\u00BB\\u00AB";

        /**
         * Set of spanish special characters taken from unicode table. See
         * http://unicode.e-workers.de/spanisch.php for more details.
         */
        private static final String SPANISH_SPECIAL_CHARACTERS =
                "\\u00C1\\u00E1\\u00E7\\u00C9\\u00E9\\u00CD\\u00ED\\u00D1\\u00F1\\u00D3\\u00F3\\u00DA\\u00FA\\u00FC\\u00AA\\u00BA\\u00A1\\u00BF";

        /**
         * Set of italian special characters taken from unicode table. See
         * http://unicode.e-workers.de/italienisch.php for more details.
         */
        private static final String ITALIAN_SPECIAL_CHARACTERS =
                "\\u00C0\\u00E0\\u00C8\\u00E8\\u00C9\\u00E9\\u00CC\\u00EC\\u00CD\\u00ED\\u00CF\\u00EF\\u00D2\\u00F2\\u00D3\\u00F3\\u00D9\\u00F9\\u00DA\\u00FA";

    }

    private static final Pattern emailRegexp =
            Pattern.compile(
                    "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z]{2,}){1}$)",
                    Pattern.CASE_INSENSITIVE);

    private static final String anyRegexp = ".";

    private static final String alnumRegexp = "[\\p{Alnum}]";

    private static final String alnumWithBlankRegexp = "[\\p{Alnum}\\p{Blank}]";

    private static final String numericRegexp = "[\\p{Digit}]";

    private static final String blanksRegexp = "[ ]*";

    private static final String digitsWithBlanksRegexp = "(" + blanksRegexp + "\\d+" + blanksRegexp
            + ")+";

    private static final String digitsWithHyphenOrSlashRegexp = "(" + blanksRegexp + "\\d+"
            + blanksRegexp + "[/\\-]?" + blanksRegexp + ")+" + //
            "(" + blanksRegexp + "\\d+" + blanksRegexp + "){1}";

    private static final String phoneRegexp = "[" + //
            blanksRegexp + //
            "([\\+]{0,1}" + digitsWithBlanksRegexp + "){0,1}" + // +#####
            "([\\(]" + digitsWithBlanksRegexp + "[\\)]){0,1}" + // (####)
            digitsWithHyphenOrSlashRegexp + // ###-###-###-#####
            "]";

    private PredefinedPatterns() {
        // empty constructor
    }

    /**
     * Creates a regular expression pattern to validate email addresses.
     *
     * @return regular expression pattern to validate email addresses
     */
    public static Pattern email() {
        return emailRegexp;
    }

    /**
     * Creates a regular expression pattern to validate telephone numbers.
     *
     * @return regular expression pattern to validate telephone numbers
     */
    public static Pattern phone() {
        return Pattern.compile(phoneRegexp + "+");
    }

    /**
     * Creates a regular expression pattern to match alphanumeric strings any length.
     *
     * @return regular expression pattern to match alphanumeric strings any length
     */
    public static Pattern alnum() {
        return Pattern.compile(alnumRegexp + "+");
    }

    /**
     * Creates a regular expression pattern to match alphanumeric characters including chars for
     * some european languages. While creating pattern you can use logical "or" composition of
     * integer flags like this:
     * <p/>
     * <pre>
     * Patttern p = alnumEuropean(SpecialChars.GERMAN | SpecialChars.FRENCH | SpecialChars.SPANISH);
     * </pre>
     *
     * @param flag is a flag indicating type of special chars (see constants in the
     *             {@link SpecialChars} class).
     * @return regular expression pattern to match alphanumeric characters including chars for some
     *         european languages.
     */
    public static Pattern alnumEuropean(int flag) {
        return Pattern.compile("[\\p{Alnum}" + determineSpecialChars(flag) + "]+");
    }

    /**
     * Creates a regular expression pattern to match alphanumeric characters including chars for
     * some european languages. While creating pattern you can use logical "or" composition of
     * integer flags like this:
     * <p/>
     * <pre>
     * Patttern p = alnumEuropeanMinMax(SpecialChars.GERMAN | SpecialChars.FRENCH | SpecialChars.SPANISH,
     *         1, 10);
     * </pre>
     *
     * @param flag is a flag indicating type of special chars (see constants in the
     *             {@link SpecialChars} class).
     * @param min  is a allowed minimum string length
     * @param max  is a allowed maximum string length
     * @return regular expression pattern to match alphanumeric characters including chars for some
     *         european languages.
     */
    public static Pattern alnumEuropeanMinMax(int flag, int min, int max) {
        assertMinMax(min, max);
        return Pattern.compile("[\\p{Alnum}" + determineSpecialChars(flag) + "]{" + min + "," + max
                + "}");
    }

    /**
     * Creates a regular expression pattern to match alphanumeric characters including chars for
     * some european languages or blank. While creating pattern you can use logical "or" composition
     * of integer flags like this:
     * <p/>
     * <pre>
     * Patttern p = alnumEuropeanWithBlank(SpecialChars.GERMAN | SpecialChars.FRENCH
     *         | SpecialChars.SPANISH);
     * </pre>
     *
     * @param flag is a flag indicating type of special chars (see constants in the
     *             {@link SpecialChars} class).
     * @return regular expression pattern to match alphanumeric characters including chars for some
     *         european languages or blank.
     */
    public static Pattern alnumEuropeanWithBlank(int flag) {
        return Pattern.compile("[\\p{Alnum}\\p{Blank}" + determineSpecialChars(flag) + "]+");
    }

    /**
     * Creates a regular expression pattern to match alphanumeric characters including chars for
     * some european languages or blank. While creating pattern you can use logical "or" composition
     * of integer flags like this:
     * <p/>
     * <pre>
     * Patttern p = alnumEuropeanWithBlankMinMax(SpecialChars.GERMAN | SpecialChars.FRENCH
     *         | SpecialChars.SPANISH, 1, 10);
     * </pre>
     *
     * @param flag is a flag indicating type of special chars (see constants in the
     *             {@link SpecialChars} class).
     * @param min  is a allowed minimum string length
     * @param max  is a allowed maximum string length
     * @return regular expression pattern to match alphanumeric characters including chars for some
     *         european languages or blank.
     */
    public static Pattern alnumEuropeanWithBlankMinMax(int flag, int min, int max) {
        return Pattern.compile("[\\p{Alnum}\\p{Blank}" + determineSpecialChars(flag) + "]{" + min
                + "," + max + "}");
    }

    private static String determineSpecialChars(int flag) {
        String specialChars = "";
        if ((flag & SpecialChars.GERMAN) != 0) {
            specialChars += SpecialChars.GERMAN_SPECIAL_CHARACTERS;
        }

        if ((flag & SpecialChars.FRENCH) != 0) {
            specialChars += SpecialChars.FRENCH_SPECIAL_CHARACTERS;
        }

        if ((flag & SpecialChars.SPANISH) != 0) {
            specialChars += SpecialChars.SPANISH_SPECIAL_CHARACTERS;
        }

        if ((flag & SpecialChars.ITALIAN) != 0) {
            specialChars += SpecialChars.ITALIAN_SPECIAL_CHARACTERS;
        }

        if ((flag & SpecialChars.ALL) != 0) {
            specialChars +=
                    SpecialChars.GERMAN_SPECIAL_CHARACTERS + SpecialChars.FRENCH_SPECIAL_CHARACTERS
                            + SpecialChars.SPANISH_SPECIAL_CHARACTERS
                            + SpecialChars.ITALIAN_SPECIAL_CHARACTERS;
        }

        return specialChars;
    }

    /**
     * Creates a regular expression pattern to match alphanumeric or blank strings any length.
     *
     * @return regular expression pattern to match alphanumeric strings any length
     */
    public static Pattern alnumWithBlank() {
        return Pattern.compile(alnumWithBlankRegexp + "+");
    }

    /**
     * Creates a regular expression pattern to match numeric strings any length.
     *
     * @return regular expression pattern to match numeric strings any length.
     */
    public static Pattern numeric() {
        return Pattern.compile(numericRegexp + "+");
    }

    /**
     * Creates a regular expression pattern to match alphanumeric strings of minimum length
     * <tt>min</tt> and maximum length <tt>max</tt>.
     *
     * @param min is a allowed minimum string length
     * @param max is a allowed maximum string length
     * @return regular expression pattern to match alphanumeric strings in predefined length range
     */
    public static Pattern alnumMinMax(int min, int max) {
        assertMinMax(min, max);
        return Pattern.compile(alnumRegexp + "{" + min + "," + max + "}");
    }

    /**
     * Creates a regular expression pattern to match alphanumeric strings or blanksRegexp of minimum
     * length <tt>min</tt> and maximum length <tt>max</tt>.
     *
     * @param min is a allowed minimum string length
     * @param max is a allowed maximum string length
     * @return regular expression pattern to match alphanumeric or blank strings in predefined
     *         length range
     */
    public static Pattern alnumWithBlankMinMax(int min, int max) {
        assertMinMax(min, max);
        return Pattern.compile(alnumWithBlankRegexp + "{" + min + "," + max + "}");
    }

    /**
     * Creates a regular expression pattern to match anumeric strings of minimum length <tt>min</tt>
     * and maximum length <tt>max</tt>.
     *
     * @param min is a allowed minimum string length
     * @param max is a allowed maximum string length
     * @return regular expression pattern to match numeric in predefined length range
     */
    public static Pattern numericMinMax(int min, int max) {
        assertMinMax(min, max);
        return Pattern.compile(numericRegexp + "{" + min + "," + max + "}");
    }

    /**
     * Creates a regular expression pattern that matches any characters of minimum length
     * <tt>min</tt> and maximum length <tt>max</tt>.
     *
     * @param min is a allowed minimum string length
     * @param max is a allowed maximum string length
     * @return regular expression pattern that matches any characters in predefined length range
     */
    public static Pattern anyMinMax(int min, int max) {
        assertMinMax(min, max);
        return Pattern.compile(anyRegexp + "{" + min + "," + max + "}", Pattern.DOTALL);
    }

    /**
     * Creates a regular expression pattern that matches telephone numbers of minimum length
     * <tt>min</tt> and maximum length <tt>max</tt>.
     *
     * @param min is a allowed minimum string length
     * @param max is a allowed maximum string length
     * @return regular expression pattern that matches telephone numbers in predefined length range
     */
    public static Pattern phoneMinMax(int min, int max) {
        assertMinMax(min, max);
        return Pattern.compile(phoneRegexp + "{" + min + "," + max + "}");
    }

    private static void assertMinMax(int min, int max) {

        if (min < 0)
            throw new IllegalArgumentException("Parameter 'min' cannot be less than 0!");

        if (max < min)
            throw new IllegalArgumentException("Parameter 'max' cannot be less than 'min'!");
    }

}
