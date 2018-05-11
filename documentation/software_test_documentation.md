# Software test documentation

The program is being tested with automated unit and integration tests, using JUnit testing framework. System and GUI testing has been performed only manually.

## Unit and integration tests

### Domain logic

[UsersManagementTest](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/src/test/java/sudokuinsika/domain/UsersManagementTest.java) contains mostly integration tests that cover functionalities like user login and score saving; it contains also some unit tests, like checkUsernameLengthReturnsTrueWithCorrectLengths().

[FakeUserDao](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/src/main/java/sudokuinsika/dao/FakeUserDao.java) and [FakeScoreDao](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/src/main/java/sudokuinsika/dao/FakeScoreDao.java) are two mock DAO classes which have been created for integration testing; they save data to main memory instead of disk, so the tests are faster.

[GameTest](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/src/test/java/sudokuinsika/domain/GameTest.java) contains mostly unit tests that cover basic game functionalities, like resetting the game timer.

[SmallDigitsCellTest](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/src/test/java/sudokuinsika/domain/SmallDigitsCellTest.java) contains one unit test, that checks if the overridden toString() method returns a correctly formatted string.

### DAO classes

The classes in the data storage tier (package _sudokuinsika.dao_) have been tested on an SQL database file (_test.db_) which has the same table schema as _userdata.db_. If the database file _test.db_ doesn't exist in the expected directory, it is created while executing the tests; its tables are emptied before each test.

### Test coverage

Excluding the presentation tier (package _sudokuinsika.ui_), the tests' line coverage is 99%, and branch coverage is 96%.

![Couldn't load image](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/images/std1.png "Test coverage")

The lines and branches that there were not tested include private methods whose branches were difficult to test (like fillRiddle(int level) from [Game](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/src/main/java/sudokuinsika/domain/Game.java)) and some getters and setters (like getSolution() from [Game](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/src/main/java/sudokuinsika/domain/Game.java)).

## System and GUI testing

The program's system and GUI testing has been performed manually.

### Functionalities

The program has been tested thoroughly in the ways described in the [user guide](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/user_guide.md). Testing has been done mainly on Windows 10; some tests were also performed on Ubuntu 16.04 and Mac OS X.

All the functionalities listed in the [software requirements specification](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/software_requirements_specification.md) have been tested, and no major issue has been found yet. All the text fields have been tested with inputs of the wrong length; the correct error messages are displayed in each case.
