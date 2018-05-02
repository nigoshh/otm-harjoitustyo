# User guide

Download the file [SUDOkuinSIKA_v0.1.jar](https://github.com/nigoshh/otm-harjoitustyo/releases/tag/week5)

## Configuration

The app saves all users' data into a database file named _usersdata.db_. If it there isn't any such file into the same folder where _SUDOkuinSIKA_v0.1.jar_ is located, the app will create a new _usersdata.db_. If you delete _usersdata.db_ you will lose all users' data forever.

## Starting the app

You can launch the app either by double clicking on it, or by typing this command into the command line:

```
SUDOkuinSIKA_v0.1.jar
```

## Login

The app opens with the login scene. If you are a registered user you can log in by typing your username and password into the corresponding text fields and pressing the _log in_ button (or the enter key).

## Create a new user

If you aren't a registered user you can access the new user scene from the login scene, by clicking on the hyperlink _new here?_. In the new user scene you can create a new user by filling in the form and pressing the _create user_ button. The username must be between 1 and 230 characters long; the password must be between 10 and 1000 characters long; the email must be between 0 and 230 characters long. Therefore The only field you can leave blank is the email field. If the user creation is successful you will be redirected to the login scene; you can also go back to the login scene via the _been here before?_ hyperlink.

## Game

After you login you'll be redirected to the game scene.

A puzzle is generated automatically when you log in, but you can generate a new one by selecting the desired level with the slider and then clicking the _new puzzle_ button. The level number indicates how many cells will be already filled in at the beginning. Therefore a lower number means greater difficulty. You can't overwrite the values of the cells' that are given at the beginning; you can easily distinguish them because their font's weight it bold, while the values you input are displayed with a normally weighted font.

To input values into the grid you have to click on the desired digit (or press the corresponding key on your keyboard) and then click on the cell where you want to write that value. The _delete_ value erases a cell's content.

You can check that the puzzle is valid by pressing the _check puzzle_ button; doing this at least once will penalize your score for that given puzzle, meaning that scores are saved into two different categories, depending on whether help was used or not.

Your objective is completing the puzzle correctly in the shortest amount of time.

You can read the game's instructions also by clicking on the hyperlink _how to play_.

You can log out by pressing the _log out_ hyperlink.

Clicking on the _top scores_ hyperlink will redirect you to the top scores scene.

## Top scores

In the top scores scene you'll see your top scores on the left, and all users' top scores on the right. You can filter the top scores by level and by help used (press the _update_ button after selecting the desired filter).

You can go back to the game via the _back to the game_ hyperlink.
