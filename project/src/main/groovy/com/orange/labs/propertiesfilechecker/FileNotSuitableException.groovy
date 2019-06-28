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
 * Exception to throw if a file to process is not suitable.
 *
 * @author Pierre-Yves Lapersonne
 * @see Exception
 * @since 29/05/2019
 * @version 1.0.0
 */
class FileNotSuitableException extends Exception {

    /**
     * Default constructor
     * @param message The message to scream loudly
     */
    FileNotSuitableException(String message) {
        super(message)
    }

    /**
     * Constructor using enumeration for status of file
     * @param status The status of the file
     */
    FileNotSuitableException(FileStatus status){
        this(status.details)
    }

    /**
     * Constructor using enumeration for status of line
     * @param status The status of the file related to a bad line
     */
    FileNotSuitableException(FileLineStatus status){
        this(status.details)
    }
}
