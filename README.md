# SUDOkuinSIKA

__Please note:__ the method _Game.checkPuzzle()_, which checks the validity of the sudoku puzzle, doesn't work correctly at the moment. All the rows and columns are checked correctly, but only one block is checked (the one at the top left); therefore the method might return _true_ even if the puzzle is invalid (i.e. a block contains two equal digits). The reason for this is that _Game.checkPuzzle()_ uses an external library method, _GameMatrix.isValid()_, which doesn't work properly. I've already fixed it in my fork of the library, and created a pull request; but I still haven't figured out how to use the code from the fork in my project, before it gets pulled into the upstream branch. So please bear with this for now.

An app to play sudoku. Developed on Windows, might have some minor GUI bugs in Linux and Mac OS X.

## Documentation

[Software requirements specification](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/software_requirements_specification.md)

[Software architecture description](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/architecture.md)

[Timesheet](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/timesheet.md)

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
