# Software architecture description

## Structure

The code is organized in a [three-tier architecture](https://en.wikipedia.org/wiki/Multitier_architecture).

The package _sudokuinsika.ui_, the presentation tier, contains code to build and control the graphical user interface.

The package _sudokuinsika.domain_ is the domain logic tier.

The package _sudokuinsika.dao_ is the data storage tier. It contains code to access an SQLite database with the DAO design pattern.

## Graphical user interface

The GUI consists of five different views:
- login
- new user
- game
- scores
- settings
They are implemented as [Scene](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html) objects. Only one scene is staged at a time.

The scenes are built using [FXML](https://en.wikipedia.org/wiki/FXML), a user interface markup language for [JavaFX](https://en.wikipedia.org/wiki/JavaFX). The FXML files can be found in [/src/main/resources/fxml/](https://github.com/nigoshh/otm-harjoitustyo/tree/master/SUDOkuinSIKA/src/main/resources/fxml); I created them using [Scene Builder](http://www.oracle.com/technetwork/java/javase/downloads/sb2download-2177776.html), and then modified something by editing them directly as text. Some repetitive elements would have been painful to create manually with Scene Builder, so I create them in [GameController](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/src/main/java/sudokuinsika/ui/GameController.java) class. They include the 81 buttons representing the cells of the sudoku board.

Each FXML file is controller by its own controller class. There is a base class, [Controller](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/src/main/java/sudokuinsika/ui/Controller.java), which contains stuff that can be useful to any controller. All the [other controllers](https://github.com/nigoshh/otm-harjoitustyo/tree/master/SUDOkuinSIKA/src/main/java/sudokuinsika/ui) extend the base Controller class.

[MainApp](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/src/main/java/sudokuinsika/ui/MainApp.java) is the main class, which injects the dependencies into the controllers and stages the scenes.

## Domain logic

![Couldn't load image](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/images/arc1.svg "Class diagram")

The classes in _sudokuinsika.ui_ have direct access only to the [UsersManagement](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/src/main/java/sudokuinsika/domain/UsersManagement.java) class, which is the core of the program logic. It interacts with DAO classes in _sudokuinsika.dao_, it manages user creation, login and logout, user scores, and lets users play sudoku.

Actually the core of the sudoku game logic comes from Riddle, GameMatrix and Creator, three classes of [this sudoku library](https://github.com/sfuhrm/sudoku), to which I've also contributed [two lines of code](https://github.com/sfuhrm/sudoku/commit/84cadc3d266e73e62158ba282f00198f300bb9ae). These classes are used into the [Game](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/src/main/java/sudokuinsika/domain/Game.java) class to create a sudoku puzzle, and provide the data structures to implement the sudoku game's logic. In addition, [SmallDigitsCell](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/src/main/java/sudokuinsika/domain/SmallDigitsCell.java) provides a data structure to save the small digits that are annotated in a cell.

[User](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/src/main/java/sudokuinsika/domain/User.java) and [Score](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/src/main/java/sudokuinsika/domain/Score.java) are basic object-oriented classes that represent a user and a score.

## Data storage

The classes in _sudokuinsika.dao_ follow the [DAO](https://en.wikipedia.org/wiki/Data_access_object) design pattern, and store data in an SQL database. These classes are accessed only through two interfaces, [UserDao](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/src/main/java/sudokuinsika/dao/UserDao.java) and [ScoreDao](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/src/main/java/sudokuinsika/dao/ScoreDao.java). This means that, in case a different storage solution is preferred, it can be switched quite easily.

### SQLite database file

User data is saved to _userdata.db_. You'll find its CREATE TABLE statements in the [Database](https://github.com/nigoshh/otm-harjoitustyo/blob/master/SUDOkuinSIKA/src/main/java/sudokuinsika/dao/Database.java) class.

### Main functionalities

I'll describe in detail an example of the app's functionalities using a sequence diagram.

#### Login

When a user fills in the login scene's text fields with her username and password, and then presses the _log in_ button, the app execution follows this path:

![Couldn't load image](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/images/arc2.png "Sequence diagram - User logs in")

The LoginController's method login() is called. It then gets the app's UsersManagement instance from the Controller, and it gets the text from the scene's text fields. Using these as parameters it calls UsersManagement's method login(username, password). This method calls DBUserDao's method findOne(username), which first creates a new User with the given username, and then gets the rest of the user's data from _userdata.db_ (in this case the user is already registered). DBUserDao then returns this User object's pointer to the UsersManagement, which in turn gets from the User object all the user's data it needs (password hash, password salt, password iterations, password key length). With this data UsersManagement calls its own method hashPassword(password, pwSalt. pwIterations, pwKeyLength), and then compares the return value with the user's stored password hash. In this case they match, so UsersManagement creates a new Game, and returns its pointer to LoginController. LoginController then calls Controller's method toGameFromLogin(). This method goes through MainApp and GameController, and it prepares the Game Scene. This includes creating a new Riddle into the Game object, and drawing the corresponding sudoku board to the Game Scene. Finally, MainApp stages the Game Scene.

#### Other functionalities

All the other functionalities work in a similar way: a GUI event handler calls a method from the domain logic, and in some cases the domain logic then calls a method from one of the DAO classes, and returns all needed data to the GUI.

## Known weaknesses and bugs

### GUI

I'm not sure the Controller base class is a good idea; it might have made code more complicated.

#### Bugs

- in the game scene, after selecting a digit it's not possible to immediately click on a cell and write that digit; for a few seconds no digit will appear in the cell when clicking on it
- after switching to a different scene by clicking a hyperlink, the mouse cursor keeps the hand shape, while it should change back to the arrow shape
