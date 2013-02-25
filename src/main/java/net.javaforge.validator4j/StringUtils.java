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
import java.util.Iterator;

/**
 * This class contains methods copied from org.apache.commons.lang.StringUtils class.
 * Copy was made just to avoid unnecessary dependency.
 *
 * @author Maxim Kalina
 * @version $Id$
 */
public final class StringUtils {


    /**
     * The empty String <code>""</code>.
     *
     * @since 2.0
     */
    public static final String EMPTY = "";

    private StringUtils() {
    }


    // Empty checks
    //-----------------------------------------------------------------------

    /**
     * <p>Checks if a String is empty ("") or null.</p>
     * <p/>
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     * <p/>
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the String.
     * That functionality is available in isBlank().</p>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * <p>Checks if a String is not empty ("") and not null.</p>
     * <p/>
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is not empty and not null
     */
    public static boolean isNotEmpty(String str) {
        return !StringUtils.isEmpty(str);
    }

    /**
     * <p>Checks if a String is whitespace, empty ("") or null.</p>
     * <p/>
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 2.0
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if a String is not empty (""), not null and not whitespace only.</p>
     * <p/>
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is
     *         not empty and not null and not whitespace
     * @since 2.0
     */
    public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }


    // Character Tests
    //-----------------------------------------------------------------------

    /**
     * <p>Checks if the String contains only unicode letters.</p>
     * <p/>
     * <p><code>null</code> will return <code>false</code>.
     * An empty String (length()=0) will return <code>true</code>.</p>
     * <p/>
     * <pre>
     * StringUtils.isAlpha(null)   = false
     * StringUtils.isAlpha("")     = true
     * StringUtils.isAlpha("  ")   = false
     * StringUtils.isAlpha("abc")  = true
     * StringUtils.isAlpha("ab2c") = false
     * StringUtils.isAlpha("ab-c") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains letters, and is non-null
     */
    public static boolean isAlpha(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the String contains only unicode letters and
     * space (' ').</p>
     * <p/>
     * <p><code>null</code> will return <code>false</code>
     * An empty String (length()=0) will return <code>true</code>.</p>
     * <p/>
     * <pre>
     * StringUtils.isAlphaSpace(null)   = false
     * StringUtils.isAlphaSpace("")     = true
     * StringUtils.isAlphaSpace("  ")   = true
     * StringUtils.isAlphaSpace("abc")  = true
     * StringUtils.isAlphaSpace("ab c") = true
     * StringUtils.isAlphaSpace("ab2c") = false
     * StringUtils.isAlphaSpace("ab-c") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains letters and space,
     *         and is non-null
     */
    public static boolean isAlphaSpace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ((!Character.isLetter(str.charAt(i))) && (str.charAt(i) != ' ')) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the String contains only unicode letters or digits.</p>
     * <p/>
     * <p><code>null</code> will return <code>false</code>.
     * An empty String (length()=0) will return <code>true</code>.</p>
     * <p/>
     * <pre>
     * StringUtils.isAlphanumeric(null)   = false
     * StringUtils.isAlphanumeric("")     = true
     * StringUtils.isAlphanumeric("  ")   = false
     * StringUtils.isAlphanumeric("abc")  = true
     * StringUtils.isAlphanumeric("ab c") = false
     * StringUtils.isAlphanumeric("ab2c") = true
     * StringUtils.isAlphanumeric("ab-c") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains letters or digits,
     *         and is non-null
     */
    public static boolean isAlphanumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLetterOrDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the String contains only unicode letters, digits
     * or space (<code>' '</code>).</p>
     * <p/>
     * <p><code>null</code> will return <code>false</code>.
     * An empty String (length()=0) will return <code>true</code>.</p>
     * <p/>
     * <pre>
     * StringUtils.isAlphanumeric(null)   = false
     * StringUtils.isAlphanumeric("")     = true
     * StringUtils.isAlphanumeric("  ")   = true
     * StringUtils.isAlphanumeric("abc")  = true
     * StringUtils.isAlphanumeric("ab c") = true
     * StringUtils.isAlphanumeric("ab2c") = true
     * StringUtils.isAlphanumeric("ab-c") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains letters, digits or space,
     *         and is non-null
     */
    public static boolean isAlphanumericSpace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ((!Character.isLetterOrDigit(str.charAt(i))) && (str.charAt(i) != ' ')) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the string contains only ASCII printable characters.</p>
     * <p/>
     * <p><code>null</code> will return <code>false</code>.
     * An empty String (length()=0) will return <code>true</code>.</p>
     * <p/>
     * <pre>
     * StringUtils.isAsciiPrintable(null)     = false
     * StringUtils.isAsciiPrintable("")       = true
     * StringUtils.isAsciiPrintable(" ")      = true
     * StringUtils.isAsciiPrintable("Ceki")   = true
     * StringUtils.isAsciiPrintable("ab2c")   = true
     * StringUtils.isAsciiPrintable("!ab-c~") = true
     * StringUtils.isAsciiPrintable("\u0020") = true
     * StringUtils.isAsciiPrintable("\u0021") = true
     * StringUtils.isAsciiPrintable("\u007e") = true
     * StringUtils.isAsciiPrintable("\u007f") = false
     * StringUtils.isAsciiPrintable("Ceki G\u00fclc\u00fc") = false
     * </pre>
     *
     * @param str the string to check, may be null
     * @return <code>true</code> if every character is in the range
     *         32 thru 126
     * @since 2.1
     */
    public static boolean isAsciiPrintable(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!isAsciiPrintable(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * <p>Checks whether the character is ASCII 7 bit printable.</p>
     * <p/>
     * <pre>
     *   CharUtils.isAsciiPrintable('a')  = true
     *   CharUtils.isAsciiPrintable('A')  = true
     *   CharUtils.isAsciiPrintable('3')  = true
     *   CharUtils.isAsciiPrintable('-')  = true
     *   CharUtils.isAsciiPrintable('\n') = false
     *   CharUtils.isAsciiPrintable('&copy;') = false
     * </pre>
     *
     * @param ch the character to check
     * @return true if between 32 and 126 inclusive
     */
    public static boolean isAsciiPrintable(char ch) {
        return ch >= 32 && ch < 127;
    }

    /**
     * <p>Checks if the String contains only unicode digits.
     * A decimal point is not a unicode digit and returns false.</p>
     * <p/>
     * <p><code>null</code> will return <code>false</code>.
     * An empty String (length()=0) will return <code>true</code>.</p>
     * <p/>
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = true
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains digits, and is non-null
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the String contains only unicode digits or space
     * (<code>' '</code>).
     * A decimal point is not a unicode digit and returns false.</p>
     * <p/>
     * <p><code>null</code> will return <code>false</code>.
     * An empty String (length()=0) will return <code>true</code>.</p>
     * <p/>
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = true
     * StringUtils.isNumeric("  ")   = true
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("12 3") = true
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains digits or space,
     *         and is non-null
     */
    public static boolean isNumericSpace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ((!Character.isDigit(str.charAt(i))) && (str.charAt(i) != ' ')) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the String contains only whitespace.</p>
     * <p/>
     * <p><code>null</code> will return <code>false</code>.
     * An empty String (length()=0) will return <code>true</code>.</p>
     * <p/>
     * <pre>
     * StringUtils.isWhitespace(null)   = false
     * StringUtils.isWhitespace("")     = true
     * StringUtils.isWhitespace("  ")   = true
     * StringUtils.isWhitespace("abc")  = false
     * StringUtils.isWhitespace("ab2c") = false
     * StringUtils.isWhitespace("ab-c") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains whitespace, and is non-null
     * @since 2.0
     */
    public static boolean isWhitespace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }


    /**
     * <p>Joins the elements of the provided array into a single String
     * containing the provided list of elements.</p>
     * <p/>
     * <p>No delimiter is added before or after the list.
     * A <code>null</code> separator is the same as an empty String ("").
     * Null objects or empty strings within the array are represented by
     * empty strings.</p>
     * <p/>
     * <pre>
     * StringUtils.join(null, *)                = null
     * StringUtils.join([], *)                  = ""
     * StringUtils.join([null], *)              = ""
     * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
     * StringUtils.join(["a", "b", "c"], null)  = "abc"
     * StringUtils.join(["a", "b", "c"], "")    = "abc"
     * StringUtils.join([null, "", "a"], ',')   = ",,a"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param separator the separator character to use, null treated as ""
     * @return the joined String, <code>null</code> if null array input
     */
    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    /**
     * <p>Joins the elements of the provided array into a single String
     * containing the provided list of elements.</p>
     * <p/>
     * <p>No delimiter is added before or after the list.
     * A <code>null</code> separator is the same as an empty String ("").
     * Null objects or empty strings within the array are represented by
     * empty strings.</p>
     * <p/>
     * <pre>
     * StringUtils.join(null, *)                = null
     * StringUtils.join([], *)                  = ""
     * StringUtils.join([null], *)              = ""
     * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
     * StringUtils.join(["a", "b", "c"], null)  = "abc"
     * StringUtils.join(["a", "b", "c"], "")    = "abc"
     * StringUtils.join([null, "", "a"], ',')   = ",,a"
     * </pre>
     *
     * @param array      the array of values to join together, may be null
     * @param separator  the separator character to use, null treated as ""
     * @param startIndex the first index to start joining from.  It is
     *                   an error to pass in an end index past the end of the array
     * @param endIndex   the index to stop joining from (exclusive). It is
     *                   an error to pass in an end index past the end of the array
     * @return the joined String, <code>null</code> if null array input
     */
    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = EMPTY;
        }

        // endIndex - startIndex > 0:   Len = NofStrings *(len(firstString) + len(separator))
        //           (Assuming that all Strings are roughly equally long)
        int bufSize = (endIndex - startIndex);
        if (bufSize <= 0) {
            return EMPTY;
        }

        bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length())
                + separator.length());

        StringBuilder buf = new StringBuilder(bufSize);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    /**
     * <p>Joins the elements of the provided <code>Iterator</code> into
     * a single String containing the provided elements.</p>
     * <p/>
     * <p>No delimiter is added before or after the list. Null objects or empty
     * strings within the iteration are represented by empty strings.</p>
     * <p/>
     *
     * @param iterator  the <code>Iterator</code> of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, <code>null</code> if null iterator input
     * @since 2.0
     */
    public static String join(Iterator iterator, char separator) {

        // handle null, zero and one elements before building a buffer
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        Object first = iterator.next();
        if (!iterator.hasNext()) {
            return toString(first);
        }

        // two or more elements
        StringBuilder buf = new StringBuilder(256); // Java default is 16, probably too small
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            buf.append(separator);
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }

        return buf.toString();
    }

    /**
     * <p>Joins the elements of the provided <code>Iterator</code> into
     * a single String containing the provided elements.</p>
     * <p/>
     * <p>No delimiter is added before or after the list.
     * A <code>null</code> separator is the same as an empty String ("").</p>
     * <p/>
     * <p>See the examples here: {@link #join(Object[], String)}. </p>
     *
     * @param iterator  the <code>Iterator</code> of values to join together, may be null
     * @param separator the separator character to use, null treated as ""
     * @return the joined String, <code>null</code> if null iterator input
     */
    public static String join(Iterator iterator, String separator) {

        // handle null, zero and one elements before building a buffer
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        Object first = iterator.next();
        if (!iterator.hasNext()) {
            return toString(first);
        }

        // two or more elements
        StringBuilder buf = new StringBuilder(256); // Java default is 16, probably too small
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    /**
     * <p>Joins the elements of the provided <code>Collection</code> into
     * a single String containing the provided elements.</p>
     * <p/>
     * <p>No delimiter is added before or after the list. Null objects or empty
     * strings within the iteration are represented by empty strings.</p>
     * <p/>
     *
     * @param collection the <code>Collection</code> of values to join together, may be null
     * @param separator  the separator character to use
     * @return the joined String, <code>null</code> if null iterator input
     * @since 2.3
     */
    public static String join(Collection collection, char separator) {
        if (collection == null) {
            return null;
        }
        return join(collection.iterator(), separator);
    }

    /**
     * <p>Joins the elements of the provided <code>Collection</code> into
     * a single String containing the provided elements.</p>
     * <p/>
     * <p>No delimiter is added before or after the list.
     * A <code>null</code> separator is the same as an empty String ("").</p>
     * <p/>
     * <p>See the examples here: {@link #join(Object[], String)}. </p>
     *
     * @param collection the <code>Collection</code> of values to join together, may be null
     * @param separator  the separator character to use, null treated as ""
     * @return the joined String, <code>null</code> if null iterator input
     * @since 2.3
     */
    public static String join(Collection collection, String separator) {
        if (collection == null) {
            return null;
        }
        return join(collection.iterator(), separator);
    }


    // ToString
    //-----------------------------------------------------------------------

    /**
     * <p>Gets the <code>toString</code> of an <code>Object</code> returning
     * an empty string ("") if <code>null</code> input.</p>
     * <p/>
     * <pre>
     * ObjectUtils.toString(null)         = ""
     * ObjectUtils.toString("")           = ""
     * ObjectUtils.toString("bat")        = "bat"
     * ObjectUtils.toString(Boolean.TRUE) = "true"
     * </pre>
     *
     * @param obj the Object to <code>toString</code>, may be null
     * @return the passed in Object's toString, or nullStr if <code>null</code> input
     */
    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

}
