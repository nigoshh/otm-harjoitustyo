# Software requirements specification
## Purpose
This app creates sudoku puzzles, which users can solve. There can be many registered users on the same machine.
## Users
There is only one category of users (normal users). An additional category with superuser privileges might be introduced at some point, depending on how the app evolves.
## User interface draft
The user interface consists of at least five views with roughly this layout:
![alt text](https://github.com/nigoshh/otm-harjoitustyo/tree/master/documentation/images/srs1.png "User interface draft")
The app starts in the login view. The next view can be either the main game view, or the one where a new user account can be created. From the main game view we can reach the settings view ("Options" in the draft) and the high scores view. Alternatively (or in addition) the link to the high scores view could be placed into the settings view. It's possible that the settings will need more than one view, depending on how many are implemented, and how clearly they can be grouped into subcategories.
## Basic features
### Before logging in
- the user can create a new user account with a username and possibly a password
  - the username has to be unique
  - if a password system is implemented, the password has to be at least 8 characters long
- the user can log in by typing her username and password
  - if the username doesn't exist, or the password and username don't match, an error message is displayed
### While logged in
- the user is presented with a sudoku puzzle, she can try to solve it as quickly as she can
- to solve the puzzle the user must fill all the empty cells with the correct digits
  - digits entered in empty cells can be changed at any time
  - there's no limit to the number of changes that can be made
  - the game's score is simply the time elapsed, from the moment the puzzle was first shown, until all correct digits are entered
- the user can give up and see the puzzle's solution
- if the user solves the puzzle, she is shown a success message and all users' high scores
  - the user can see the high scores whenever she likes, it isn't required to solve a puzzle first
- the user can change some settings
  - user account related settings, which include at least the possibility to delete the user's account
  - possibly some game related settings (see next section)
- the user can log out
## Ideas for further development
These features aren't essential but they could be implemented, given enough time:
- game settings
  - difficulty levels
    - there could be pre-made categories, or one could simply select how many digits are given in the puzzle at the beginning
    - high scores divided per difficulty level
  - get help with solving the game
    - display hints
    - check if the puzzle contains wrong digits
    - display an error message immediately when the user enters a digit which is already present in the same row/column/block
      - separate high scores achieved using these settings
  - select different input methods (digit first or cell first)
  - possibility to insert smaller digits into cells (candidates for a cell)
- high scores filtering by timespan (last week, last month etc.)
- graphical settings
  - choose different color schemes (one could be similar to a text editor's color scheme, like Monokai)
  - choose a shape of the puzzle (some shapes could be symmetrical)
  - choose an avatar
    - unlock these settings only when a player achieves a certain threshold score
- brief explanation of the game's rules (with a link to more thorough information)
- competition puzzle: identical for all users, changes every week
  - possibility to display only high scores results from competition puzzles
- pause the game
- save, export and import user settings
- include more information in the user account
  - option to be notified by email when a high score is beaten
- translation to other languages
