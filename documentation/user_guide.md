# User guide

Download the file [SUDOkuinSIKA_v1.0.jar](https://github.com/nigoshh/otm-harjoitustyo/releases/tag/week7)

## Database file

The app saves all users' data into a database file named _usersdata.db_. If it there isn't any such file into the same folder where _SUDOkuinSIKA_v0.1.jar_ is located, the app will create a new _usersdata.db_. If you delete _usersdata.db_ you will lose all users' data forever.

## Starting the app

To start the app you need to have Java Runtime Environment installed on your computer. In case you don't have it yet, you can download it from [here](https://www.java.com/en/download/). Once you've installed the JRE, you can launch the SUDOkuinSIKA app by simply double-clicking on it, or by typing this command into the command line:

```
java -jar SUDOkuinSIKA_v1.0.jar
```

## Login

The app opens on the login view.

![Couldn't load image](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/images/ug1.png "Login view")

If you are a registered user you can log in by typing your username and password into the corresponding fields, and then clicking the _log in_ button (or pressing the enter key on your keyboard).

If you aren't registered yet you can access the new user view by clicking the _new here?_ hyperlink.

## Create a new user

![Couldn't load image](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/images/ug2.png "New user view")

 In the new user view you can create a new user by filling in the text fields and then clicking the _create user_ button (or pressing the enter key on your keyboard). The username must be between 1 and 230 characters long; the password must be between 10 and 1000 characters long; the email must be between 0 and 230 characters long. Therefore the only field you can leave blank is the email field.

 If the user creation is successful you will be redirected to the login view; you can also go back to the login view by clicking the _been here before?_ hyperlink.

## Play the game

After you login you'll be redirected to the game view.

![Couldn't load image](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/images/ug3.png "Game view")

A puzzle is generated automatically when you log in, but you can generate a new one by selecting the desired level with the slider and then clicking the _new puzzle_ button. The level number indicates how many cells will be already filled in at the beginning; therefore a lower number means greater difficulty. You can't overwrite the values of the cells that are given at the beginning; you can easily distinguish them because their font's weight is bold, while the values you input are displayed with a normally weighted font.

To input a digit into the sudoku grid you have to click on the desired digit and then click on the cell where you want to write that digit. The _delete_ value erases a cell's content. You can also select the input digit by pressing the corresponding number key on you keyboard (0 is for _delete_).

You can annotate small digits into a cell, representing possible candidates for that cell. To do so you have to click the _small digits_ toggle button, so that it becomes selected. When you want to switch back to writing normally sized digits, click the _small digits_ toggle button once more, so that it becomes unselected. You can toggle the small-digits writing mode also by pressing the S key on your keyboard.

You can check that the puzzle is valid by clicking the _check puzzle_ button. This checks if each digit appears at most once in each row, column and block. Only normal sized digits are taken into account when checking; small digits don't influence the check result. If you check a puzzle's validity at least once, your score will be saved into a separate category ("with help").

Your objective is completing the puzzle correctly in the shortest amount of time.

You can read the game's instructions also by clicking the _how to play_ hyperlink.

Clicking the _settings_ hyperlink will redirect you to the settings view. Clicking the _top scores_ hyperlink will redirect you to the top scores view. Clicking the _log out_ hyperlink will log you out.

## See the top scores

In the top scores view you'll see your top scores on the left, and all users' top scores on the right.

![Couldn't load image](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/images/ug4.png "Top scores view")

The score indicates the time elapsed since the puzzle's creation, until it was solved. Next to the score you'll find the date and time when the puzzle was solved, and the user who solved it.

You can filter the top scores by level and by help used (click the _update_ button after selecting the desired filter).

You can go back to the game view by clicking the _back to the game_ hyperlink.

## Update or delete user data

![Couldn't load image](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/images/ug5.png "Settings view")

In the settings view you can change your username, your password and your email address. You can also delete all your user data; this includes your username, your password, your email address and all your scores.

To make any change you always need to type your current password in the password field at the top. Then write in the desired field and click the corresponding button (or press the enter key on your keyboard). The input length restrictions already explained in the [Create a new user](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/userguide.md#create-a-new-user) section still apply.

You can go back to the game view by clicking the _back to the game_ hyperlink.
