# Software architecture description

## Structure

The code is organized in a three-tier architecture.

The package _sudokuinsika.ui_, the presentation tier, contains JavaFX code to build the graphical user interface. Some FXML files are also needed to build the GUI; these can be found in _/src/main/resources/fxml/_.

The package _sudokuinsika.domain_ is the domain logic tier. Some of its classes depend on [this sudoku library](https://github.com/sfuhrm/sudoku), to which I've also contributed [two lines of code](https://github.com/sfuhrm/sudoku/commit/84cadc3d266e73e62158ba282f00198f300bb9ae).

The package _sudokuinsika.dao_ is the data storage tier. It contains code to access an SQLite database with the DAO design pattern.

## Graphical user interface

## Domain logic

![couldn't load image](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/images/arc1.svg "Class diagram")

## Data storage

### SQLite database file

User data is saved to _userdata.db_. You'll find its CREATE TABLE statements in the [Database](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/src/main/java/sudokuinsika/dao/Database.java) class.

### Main functionalities

I'll describe some of the app's functionalities using sequence diagrams.

#### Login

When a user fills in the login scene's text fields with her username and password, and then presses the _log in_ button, the app execution follows this path:

![couldn't load image](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/images/arc2.png "Sequence diagram - User logs in")

The LoginController's method login() is called. It then gets the app's UsersManagement instance from the Controller, and it gets the text from the scene's text fields. Using these as parameters it calls UsersManagement's method login(username, password). This method calls DBUserDao's method findOne(username), which first creates a new User with the given username, and then gets the rest of the user's data from _userdata.db_ (in this case the user is already registered). DBUserDao then returns this User object's pointer to the UsersManagement, which in turn gets from the User object all the user's data it needs (password hash, password salt, password iterations, password key length). With this data UsersManagement calls its own method hashPassword(password, pwSalt. pwIterations, pwKeyLength), and then compares the return value with the user's stored password hash. In this case they match, so UsersManagement creates a new Game, and return its pointer to LoginController. (TODO: update diagram to reflect recent changes in the code). LoginController then calls Game's method createRiddle(), which creates a new riddle for the Game, and Controller's method toGame(). This method goes through a MainApp method, and calls GameController's clear() method, which cleans up and stages the Game scene (which had been initialized previously when the app was launched). After that LoginController's login() method finally finishes it's job by clearing the login scene's text fields username and password, for security reasons.

#### Other functionalities

## Observed structural weaknesses

### GUI

### DAO-classes
