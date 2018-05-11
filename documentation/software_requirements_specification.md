# Software requirements specification

## Purpose

This app creates sudoku puzzles, which users can solve. There can be many registered users on the same machine.

## Users

There is only one category of users; there are no superusers with special privileges.

## User interface draft

The user interface consists of five views with roughly this layout:

![Couldn't load image](https://github.com/nigoshh/otm-harjoitustyo/blob/master/documentation/images/srs1.png "User interface draft")

The app starts in the login view. The next view can be either the game view, or the new user view. From the game view we can reach the settings view ("Options" in the draft) and the top scores view ("High scores" in the draft). A link to the top scores view could be placed also into the settings view. It's possible that the settings will need more than one view, depending on how many are implemented, and how clearly they can be grouped into subcategories.

## Basic features

### Before logging in

- the user can create a new user account with a username, a password and possibly an email address
  - the username has to be unique
  - the lengths of username, password, and email address have to be within certain limits, otherwise error messages are displayed
- the user can log in by typing her username and password
  - if the username doesn't exist, or the password and username don't match, an error message is displayed

### While logged in

- the user is presented with a sudoku puzzle, she can try to solve it as quickly as she can
- to solve the puzzle the user must fill all the empty cells with the correct digits
  - digits entered in empty cells can be changed at any time
  - there's no limit to the number of changes that can be made
  - the game's score is simply the time elapsed, from the moment the puzzle was first shown, until all correct digits are entered
- the user can give up and see the puzzle's solution
- if the user solves the puzzle, she is shown a success message
- the user can see hers and all other users' top scores
- the user can check if the puzzle contains any wrong digit
  - this counts as using help, so the score will be in a separated category
- it's possible to insert smaller digits into a cell (candidates for that cell)
- it's possible to choose a difficulty level
  - the level number means how many digits are given in the puzzle at the beginning
- high scores are divided by difficulty level and help used
- the user can change some settings
  - user account related settings, which include the possibility to update or delete the user's data
- the user can log out

## Ideas for further development

These features aren't essential but they could be implemented later, given enough time:
- game settings
  - get more help with solving the game
    - display hints
    - display an error message immediately when the user enters a digit which is already present in the same row/column/block
  - select different input methods (digit first or cell first)
- high scores filtering by timespan (last week, last month etc.)
- graphical settings
  - choose different color schemes (one could be similar to a text editor's color scheme, like Monokai)
  - choose a shape of the puzzle (some shapes could be symmetrical)
  - choose an avatar
  - unlock these settings only when a player achieves a certain threshold score
- brief explanation of sudoku's rules (with a link to more thorough information)
- competition puzzle: identical for all users, changes every week
  - possibility to display only high scores results from competition puzzles
- pause the game
- save a puzzle to solve it later
- save, export and import user settings
- include more information in the user account
  - option to be notified by email when a high score is beaten
- translation to other languages
