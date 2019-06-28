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

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Defines the Gradle task for the properties checker.
 * Will trigger the service which deals with rules and properties files.
 *
 * @author Pierre-Yves Lapersonne
 * @since 28/05/2019
 * @version 1.0.0
 */
class PropertiesFileCheckerTask extends DefaultTask {

    /**
     * Prepares the service which will check the properties file and look for errors.
     * This method is the task to call of the plugin.
     */
    @TaskAction
    def propertieschecker() {

        println ">>> Running 'propertieschecker' plugin task"
        println(">>> Preparing options for the service")

        def opts = [:]

        // Get the file containing rules
        opts.rulesFile = project.propertieschecker.rulesFile
        if (opts.rulesFile == "" || opts.rulesFile == null) {
            throw new FileNotSuitableException("The rules file is not defined")
        }

        // Get the target file to process
        opts.targetFile = project.propertieschecker.targetFile
        if (opts.targetFile == "" || opts.targetFile == null){
            throw new FileNotSuitableException("The target file is not defined")
        }

        // Get the verbose flag
        def verboseFlag = project.propertieschecker.verbose
        opts.isVerbose = !(verboseFlag == null || verboseFlag == "")

        // Run the plugin logic
        println ">>> Will apply rules of file '${opts.rulesFile}' for  target file '${opts.targetFile}'"
        def fileCheckerService = new PropertiesFileCheckerService((Boolean) opts.isVerbose)
        fileCheckerService.checkTargetFileWithRule(opts.targetFile.toString(), opts.rulesFile.toString())

    }

}