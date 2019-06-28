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
 * Defines the extension of the plugin with the attributes to define in the Gradle file using this plugin.
 * With this definition the task for the plugin will seem like:
     <pre>
            propertieschecker {
                rulesFile "src/main/assets/app_config.rules.properties"
                targetFile  "src/main/assets/app_config.properties"
                verbose true
            }
     </pre>
 *
 * @author Pierre-Yves Lapersonne
 * @since 28/05/2019
 * @version 2.0.0
 */
class PropertiesFileCheckerPluginExtension {

    /**
     * The file containing the rules to apply on the configuration file.
     * Must be in plain text format with a key value structure, e.g.:
     *
     * the_key_in_config_file = regex_to_follow
     */
    String rulesFile

    /**
     * The file to test following the rules file.
     */
    String targetFile

    /**
     * Flaying indicating if the plugin should be verbose or not (i.e. display more traces).
     * If not defined, wil be defined to false.
     */
    Boolean verbose

}