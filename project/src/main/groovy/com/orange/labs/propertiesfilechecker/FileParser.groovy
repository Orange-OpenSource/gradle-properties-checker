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
 * Utility class used to check iof a file is suitable or not.
 *
 * @author Pierre-Yves Lapersonne
 * @since 29/05/2019
 * @version 1.0.0
 */
class FileParser {

    /**
     * The escape symbol to use in files to process.
     * If a line starts with this symbol, it won't be parsed.
     */
    public static final String ESCAPE_SYMBOL = "#"

    /**
     * The regular expression an entry of the target or rule files must follow.
     * Here some characters without restrictions.
     */
    public static final String ENTRY_PATTERN = /.+/

    /**
     * The regular expression a value of the target or rule files must follow.
     * Here some characters without restrictions.
     */
    public static final String VALUE_PATTERN = /.+/

    /**
     * The symbol the object must have between the entry and the value
     */
    public static final String SPLIT_SYMBOL = "="

    /**
     * Checks if the file pointed by the give file path is suitable or not.
     * A suitable file must not be a directory, must not be hidden and must exist.
     * Returns a tuple with 2 values: a flag saying if the file is suitable (true) or not (false), and a
     * value to indicate the type of problem if not suitable.
     *
     * @param filePath The path to the file to process
     * @return Tuple2 The result of the check
     */
    static Tuple2<Boolean, FileStatus> checkIfSuitable(String filePath) {
        def file = new File(filePath)
        def status = {
            if (!file.exists()) return FileStatus.DOEST_NOT_EXIST
            else if (!file.isFile()) return FileStatus.IS_DIRECTORY
            else if (file.isHidden()) return FileStatus.IS_HIDDEN
            else return FileStatus.IS_SUITABLE
        }
        return new Tuple2<Boolean, FileStatus>(status() == FileStatus.IS_SUITABLE, status())
    }

    /**
     * Parses the rules file pointed by this path.
     * Returns an HashMap with in keys the entries of the file and in values the regular expressions to apply for
     * these keys.
     *
     * @param path The path to the rules file to parse
     * @return HashMap The dictionary of entries/values
     */
    static HashMap<String, String> parseRuleFile(String path) {
        return parsePlainTextFile(new File(path))
    }

    /**
     * Parses the target file pointed by this path.
     * Returns an HashMap with in keys the entries of the file (i.e. configuration elements) and in values the
     * values these entries have.
     *
     * @param path The path to the rules file to parse
     * @return HashMap The dictionary of entries/values
     */
    static HashMap<String, String> parseTargetFile(String path) {
        return parsePlainTextFile(new File(path))
    }

    /**
     * Parses the pointed file which is in plain/text format.
     * Entries must follow the pattern
         <pre>
                key = value
         </pre>
     * Entries starting with the scape symbol will be ignored.
     * If a line does not start by the escape symbol and does not comply with the pattern above, an exception will be thrown.
     *
     * Returns an HashMap with in keys the entries of the file and in values the values these entries have.
     *
     * @param file The file to process
     * @return HashMap The dictionary of entries/values
     * @throws FileNotSuitableException
     */
    protected static HashMap<String, String> parsePlainTextFile(File file) throws FileNotSuitableException {
        HashMap<String, String> dictionary = new HashMap<String, String>()
        file.eachLine { line ->
            if (!line.isEmpty()) {
                if (!line.startsWith(ESCAPE_SYMBOL)) {
                    Tuple2<Boolean, FileLineStatus> status = isSuitableLineForPlugin(line)
                    if (!status.first) {
                        println ">>> ERROR: Problem with line '$line'"
                        throw new FileNotSuitableException(status.second)
                    }
                    String[] splits = line.split(SPLIT_SYMBOL)
                    String entry = splits[0].replaceAll("\\s","")
                    String value = splits[1].replaceAll("\\s","")
                    dictionary.put(entry, value)
                }
            }
        }
        return dictionary
    }

    /**
     * Checks if the line in parameters is suitable for calculations / logic of the plugin, i.e.
     * if the line matches several syntax rules.
     * A line is not seen has being processed if empty or does not comply with patterns.
     * If should contain only 1 SPLIT_SYMBOL between an entry on the left and a value on the right (does not care of spaces).
     * Entry must match the ENTRY_PATTERN, value must match VALUE_PATTERN.
     *
     * @param line The line to test
     * @return Tuple2<Boolean, FileLineStatus> True if the line can be processed, false otherwise and in this case with a status.
     */
    protected static Tuple2<Boolean, FileLineStatus> isSuitableLineForPlugin(String line) {

        if (line == null) return new Tuple2<Boolean, FileLineStatus>(false, FileLineStatus.IS_NULL)
        if (line.length() <= 0) return new Tuple2<Boolean, FileLineStatus>(false, FileLineStatus.IS_EMPTY)

        if (line.count(SPLIT_SYMBOL) != 1) return new Tuple2<Boolean, FileLineStatus>(false, FileLineStatus.NO_SPLIT)
        String [] splits = line.split(SPLIT_SYMBOL)
        if (splits.length != 2) return new Tuple2<Boolean, FileLineStatus>(false, FileLineStatus.TOO_MANY_SPLIT)

        String entry = splits[0].replaceAll("\\s","")
        if (!(entry ==~ ENTRY_PATTERN)) return new Tuple2<Boolean, FileLineStatus>(false, FileLineStatus.UNEXPECTED_ENTRY)

        String value = splits[0].replaceAll("\\s","")
        if (!(value ==~ VALUE_PATTERN)) return new Tuple2<Boolean, FileLineStatus>(false, FileLineStatus.UNEXPECTED_VALUE)

        return new Tuple2<Boolean, FileLineStatus>(true, null)

    }

}
