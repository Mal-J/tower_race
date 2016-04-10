A simple game rendered in ASCII where two players race to build a tower while using
bombs to attempt to hinder each other's progress. Multithreading is used to allow 
operations on individual towers (performed by TowerController) to occur asynchronously
to others one the same tower yet parallel to operations on the other tower and the
rendering of the game.

Each tower level is composed of several Condos. To complete each new condo players must 
press the key displayed inside it. Incorrect key presses will set players back by one
condo. Once a level is completed, it will need to finish building before the player is 
able to begin the next. When a player press their "bomb" key, a bomb is planted in the 
opponents tower which lasts one second. If a player attempts to build a condo while
a bomb is active in their tower, they will lose all progress on the current level being
built.

Player one keys
 . Build: A, S, D
 . Bomb: LSHIFT
Player two keys
 . Build: J, K, L
 . Bomb: RSHIFT

Code in AsciiPanel package sourced from:
https://github.com/trystan/AsciiPanel
