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

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Base class for the Gradle plugin.
 * Will trigger a task which will check a configuration file with rules so as to test if the file under test
 * is well written or not.
 *
 * @author Pierre-Yves Lapersonne
 * @since 28/05/2019
 * @see Plugin, Project
 * @version 1.0.0
 */
class PropertiesFileCheckerPlugin implements Plugin<Project> {

    /**
     * The name of the plugin and task
     */
    static final String NAME = 'propertieschecker'

    /**
     *
     * @param project
     */
    @Override
    void apply(Project project) {

        println ">>> Running plugin PropertiesFileChecker"

        project.extensions.create(NAME, PropertiesFileCheckerPluginExtension)
        project.task(NAME, type: PropertiesFileCheckerTask).doLast {
            println ">>> Plugin execution completed!"
        }

    }

}
