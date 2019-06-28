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
 * Models the status of a file which is processed by the plugin.
 *
 * @author Pierre-Yves Lapersonne
 * @since 29/05/2019
 * @version 1.0.0
 */
enum FileStatus {

    /**
     * If the file is suitable or not
     */
    IS_SUITABLE("The file is suitable, OK!"),

    /**
     * The file has not been defined
     */
    IS_UNDEFINED("The file is undefined... woops!"),

    /**
     * The file is hidden
     */
    IS_HIDDEN("The file is hidden and won't be processed"),

    /**
     * The file is a directory, not a plain file
     */
    IS_DIRECTORY("The file is a directory and cannot be processed, need a plain/text file!"),

    /**
     * The file does not exist
     */
    DOEST_NOT_EXIST("The file does not exist"),

    /**
     * If in the target file an entry has no rule related to it
     */
    MISSING_RULE_FOR_KEY("An entry in the target file has no rule to check it")

    /**
     * Some details
     */
    public final String details

    /**
     * Default constructor
     */
    FileStatus(String details) {
        this.details = details
    }

}