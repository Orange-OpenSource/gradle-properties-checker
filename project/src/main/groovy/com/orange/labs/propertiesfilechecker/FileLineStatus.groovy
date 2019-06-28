/**
 Properties File Checker
 Copyright (C) 2019 Orange SA

 MIT License
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package com.orange.labs.propertiesfilechecker

/**
 * Models the status of a line of a processed file.
 *
 * @author Pierre-Yves Lapersonne
 * @since 03/06/2019
 * @version 1.0.0
 */
enum FileLineStatus {

    /**
     * If the line is null.
     */
    IS_NULL("The line is null"),

    /**
     * If the line is empty.
     */
    IS_EMPTY("The line is empty"),

    /**
     * If the line does not contain split symbol.
     */
    NO_SPLIT("The split symbol is missing"),

    /**
     * If the line does not contain only 1 split symbol.
     */
    TOO_MANY_SPLIT("There are too many splits symbols"),

    /**
     * If the entry does not match the expected rule.
     */
    UNEXPECTED_ENTRY("The entry does not match the expected pattern"),

    /**
     * If the value does not match the expected rule.
     */
    UNEXPECTED_VALUE("The value does not match the expected pattern"),

    /**
     * If the entry's value of the line does not comply to the dedicated rule
     */
    DOES_NOT_COMPLY_TO_RULE("The entry does not comply to the checking rule"),

    /**
     * If a key does not have a rule
     */
    DOES_NOT_HAVE_RULE("The  entry does not have a rule for check")

    /**
     * Some details
     */
    public final String details

    /**
     * Default constructor
     */
    FileLineStatus(String details){
        this.details = details
    }

}