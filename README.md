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


## Examples

For example you use a _dumb.properties_ file like:
```
enable_foo_feature = true

enable_bar_feature = false

some_granularity = medium

three_decimal_numbers = 1.5;0.9;0.7

four_integer_numbers = 0;20;40;60

one_integer_number = 600

complex_string = ED408701-6264-F393-FFFF-E50E24DDCA9E
````

You can apply a set of rules for your configuration (_dumb.rules.properties_) like:
```
enable_foo_feature = true|false

enable_bar_feature = true|false

some_granularity = low|medium|high

three_decimal_numbers = [0-9]+\.[0-9]+;[0-9]+\.[0-9]+;[0-9]+\.[0-9]+

four_integer_numbers = [0-9]+;[0-9]+;[0-9]+;[0-9]+

one_integer_number = [0-9]+

complex_string = [0-9A-Z]+-[0-9A-Z]+-[0-9A-Z]+-[0-9A-Z]+-[0-9A-Z]+
```

Use the same entry name for your configurations details.

Finally the Gradle task to add to your build script:

```groovy
            propertieschecker {
                rulesFile "src/main/assets/dumb.properties"
                targetFile  "src/main/assets/dumb.rules.properties"
            }
```


## Test the plugin

In the _samples_ folder you can find several configuration files containing entries to test and rules to apply.  

The _build.gradle_ file uses many combinations of these files to test several cases (if an entry is missing, an entry does not apply to the rule, etc.).
Update the build Gradle file to test the behavior of the plugin (warning or error messages, exceptions thrown, ...).
In your IDE like _InlliJ_ you should build the project, then in the Gradle tasks panel run the task _publishToMavenLocal_ after having modified the files, and finally in command line run the task with:
```shell
	gradlew  propertieschecker 
```


## Integration to your projects

You can clone this project and add the built JAR to your local dependencies.  
This plugin may be hosted on online platform soon.  


