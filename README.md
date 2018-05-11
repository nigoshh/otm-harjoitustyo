# SUDOkuinSIKA

An app to play sudoku. Developed on Windows, might have some minor GUI bugs on Linux and Mac OS X.

## Documentation

[User guide](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/user_guide.md)

[Software requirements specification](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/software_requirements_specification.md)

[Software architecture description](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/software_architecture_description.md)

[Software test documentation](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/software_test_documentation.md)

[Timesheet](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/timesheet.md)

## Latest release

[Course evaluation](https://github.com/nigoshh/otm-harjoitustyo/releases/tag/week7)

## Command-line commands

### Testing

Run the project's tests:

```
mvn test
```

Create a test coverage report:

```
mvn jacoco:report
```

You can examine the test coverage report by opening the file _target/site/jacoco/index.html_ with a web browser.

### Creating an executable jar file

The command

```
mvn package
```

creates an executable jar file named _SUDOkuinSIKA-1.0-SNAPSHOT.jar_ into the folder _target_.

### JavaDoc

Create JavaDoc pages:

```
mvn javadoc:javadoc
```

To read the JavaDoc pages open the file _target/site/apidocs/index.html_ with a web browser.

### Checkstyle

To execute the checks defined in the configuration file [checkstyle.xml](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/checkstyle.xml) run:

```
 mvn jxr:jxr checkstyle:checkstyle
```

To examine the Checkstyle results open the file _target/site/checkstyle.html_ with a web browser.
