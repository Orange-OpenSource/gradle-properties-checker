# Gradle plugin to check properties files

## Purpose of this plugin

Project of a Gradle plugin written in Groovy used to check if a configuration file is well formatted or not using a set of rules to follow.

These files can be for example .properties files stored in the _assets_ folder of an Android project.

Basicaly it reads target file to check and also a 2nd file containing rules (regular expression), and stores their data into dictionaries.
Then it makes assertions on each entry's value (of the file to process) to check if it matches to the dedicated rule.  

This plugin is useful if you use principles of "configuration as code".
To make your project modular you may write in configuration file some configuration details, and you would like to be sure this file is well written and does not contain errors or mistakes in the syntax.  

## Format for files to process

Target file with configuration details to process must follow the format below:
```
	# A line which won't be parsed because '#' is an escape symbol

	# A blanck or empty line is not parsed

	# The line below is correct
	the_key_of_configuration = some value
	# Use the '=' symbol for affectation
	# 'some value' here is whatever you need
```

File containing rules for the target file must follow the format below:
```
	# A line which won't be parsed because '#' is an escape symbol

	# A blanck or empty line is not parsed

	# The line below is correct
	the_key_of_configuration = the regex
	# Use the '=' symbol for affectation
	# 'the regex' here is the regular expression to apply to the value of the entry with "the_key_of_configuration" name is the target file
```

## The Gradle task to add to build script

The Gradle task has 2 mandatory and 1 optional fields, and must follow the pattern below:
```groovy
            propertieschecker {
                rulesFile "src/main/assets/app_config.rules.properties"
                targetFile  "src/main/assets/app_config.properties"
                verbose true
            }
```

The _rulesFile_ is the path to the file storing the regular expressions to apply to configuration details of _targetFile_.
The _targetFile_ is your true configuration file with all the details you want to use for your project.
The _verbose_ field must be defined to _true_ or _false_, but this field can be missing. If defined to true, more traces will be displayed.
The common format for the task to use can be:

```groovy
            propertieschecker {
                rulesFile "src/main/assets/app_config.rules.properties"
                targetFile  "src/main/assets/app_config.properties"
            }
```

## Test the plugin

In the _com.orange.labs.propertiesfilechecker.samples_ package you can find several configuration files containing entries to test and rules to apply.
The _build.gradle_ file uses many combinations of these files to test several cases (if an entry is missing, an entry does not apply to the rule, etc.).
Update the build Gradle file to test the behavior of the plugin (warning or error messages, exceptions thrown, ...).
In your IDE like _InlliJ_ you should build the project, then in the Gradle tasks panel run the task _publishToMavenLocal_ after having modified the files, and finally in command line run the task with:
```shell
	gradlew  propertieschecker 
```
You have to be located in the _samples_ packages to run the task. You can use the _gralew_ executable with relative path because this binary is located several directories above.

