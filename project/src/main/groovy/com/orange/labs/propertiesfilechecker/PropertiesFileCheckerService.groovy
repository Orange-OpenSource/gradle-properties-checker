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
 * Defines the logic of the plugin ran by the task.
 *
 * @author Pierre-Yves Lapersonne
 * @since 28/05/2019
 * @version 1.0.0
 */
class PropertiesFileCheckerService {

    /**
     * The dictionary of entries defined in the target file to check
     */
    private HashMap<String, String> targetEntries

    /**
     * The dictionary of entries defined in the rule file to apply on the target file
     */
    private HashMap<String, String> rulesEntries

    /**
     * Flag indicating if more traces must be displayed or not
     */
    private Boolean isVerbose

    /**
     * Default constructor
     *
     * @param isVerbose True if more traces must be displayed, false otherwise
     */
    PropertiesFileCheckerService(Boolean isVerbose) {
        super()
        this.isVerbose = isVerbose
    }

    /**
     * Parses the rules file to get regular expressions and name of entries.
     * Look for these entries in the target file and compare their values with the matching regular expressions.
     *
     * @param targetFile The properties file to test
     * @param rulesFile The file containing the regular expressions to match
     */
     def checkTargetFileWithRule(String targetFile, String rulesFile) {

        verbose ">>> Must parse the '$targetFile' following entries and rules of file '$rulesFile'"
        checkIfSuitableFile(targetFile)
        checkIfSuitableFile(rulesFile)

        loadRuleEntries(rulesFile)
        loadTargetEntries(targetFile)

        if (checkIfAllEntriesAreRuled()) {
            println ">>> All entries of target file have rules"
            checkEntriesWithRules()
        } else {
            println "ERROR: Target file '$targetFile' will not be processed"
            throw new FileNotSuitableException(FileStatus.MISSING_RULE_FOR_KEY)
        }

         println ">>> WOOP WOOP! If you see this message, it means all entries have been processed without failures :)"

    }

    /**
     * Checks if all entries have been ruled by rules defined in the rule file
     *
     * @return Boolean True of each entry in the target file is managed, false otherwise
     */
    private Boolean checkIfAllEntriesAreRuled() {

        // Entries loaded?
        if (targetEntries == null || rulesEntries == null) {
            println ">>> ERROR: the entries for the target file or the rule file have not been loaded!"
            return false
        }

        // Same amount of entries on both sides?
        def numberOfTargetEntries = targetEntries.keySet().size()
        def numberOfRulesEntries = rulesEntries.keySet().size()
        if (numberOfTargetEntries != numberOfRulesEntries){
            println ">>> WARNING: Quantities of entries do not match: target = $numberOfTargetEntries, rules = $numberOfRulesEntries"
        }

        // Is an entry missing in the rules file?
        targetEntries.each { key, value ->
            if (!rulesEntries.keySet().contains(key)) {
                println ">>> ERROR: It seems the key '$key' in the target file is missing in the rules file!"
                return false
            }
        }

        return true
    }

    /**
     * Checks if the content has a format which compiles to the rule (regular expression).
     *
     * @param content The string value to test
     * @param rule The rule the content must comply to
     * @return Boolean True if the content complies to the rule, false otherwise
     */
    private static Boolean checkIfContentMatchRule(String content, String rule) {
        return content ==~ /$rule/
    }

    /**
     * Prints the message according to the internal flag for verbose mode
     *
     * @param message The message to print, if verbose mode enabled
     */
    private def verbose(String message) {
        if (isVerbose) println message
    }

    /**
     * Checks if the file is suitable or not
     *
     * @param file The file to test
     * @throws FileNotSuitableException If the file under test is not suitable
     */
    private checkIfSuitableFile(String file) throws FileNotSuitableException {
        def state = FileParser.checkIfSuitable(file)
        if (!state.first) {
            throw new FileNotSuitableException("The file $file is not suitable: $state.second")
        } else {
            verbose ">>> The file $file is suitable and can be processed"
        }
    }

    /**
     * Loads in the internal dictionary the rules to apply stored in the file
     * @param rulesFile The file containing the rules to load
     */
    private loadRuleEntries(String rulesFile){
        rulesEntries = FileParser.parseRuleFile(rulesFile)
        rulesEntries.each { key, value ->
            verbose ">>>>>> In rule file, gotten : '$key' = '$value'"
        }
    }

    /**
     * Load sin the internal dictionary the target file entries to test
     * @param targetFile The file containing the entries to load
     */
    private loadTargetEntries(String targetFile){
        targetEntries = FileParser.parseTargetFile(targetFile)
        targetEntries.each { key, value ->
            verbose ">>>>>> In target file, gotten : '$key' = '$value'"
        }
    }

    /**
     * Using the previously loaded rules, check if each entry loaded from the target file matches
     * the corresponding rule.
     * If not throws an exception.
     *
     * @throws FileNotSuitableException If an entry in the target file has a value which does not comply to the dedicated rule
     */
    private checkEntriesWithRules() throws FileNotSuitableException {
        targetEntries.each { key, value ->
            String rule = rulesEntries.get(key)
            if (rule == null || rule.isEmpty()) {
                println ">>> ERROR: The rule for the key '$key' is missing"
                throw new FileNotSuitableException(FileLineStatus.DOES_NOT_HAVE_RULE)
            }
            verbose ">>>>>> Checking property '$key' value '$value' with rule '$rule'"
            if (! checkIfContentMatchRule(value, rule)) {
                println ">>> ERROR: Entry '$key' in target file does not comply to rule '$rule'"
                throw new FileNotSuitableException(FileLineStatus.DOES_NOT_COMPLY_TO_RULE)
            }
        }
    }

}
