# Object Oriented Programming (DIEF, UNIMORE)
## Personal project - 2048
2048 is a sliding tile puzzle. \
The objective of the game is to slide numbered tiles on a grid to combine them to create a tile with the number 2048.\
2048 is played on a plain 4×4 grid, with numbered tiles that slide when a player moves them using the four arrow keys.\
The game begins with two tiles already in the grid, having a value of either 2 or 4, and another tile appear after every movement.\
If two tiles of the same number collide while moving, they will merge into a tile with the total value of the two tiles that collided.\
If all four spaces in a row or column are filled with tiles of the same value,\
a move parallel to that row/column will combine all the tile

## Strategy
The best strategy is keeping the largest in a corner with the other large tile filling the row / column,\
you should try to not move the largest number from his corner.

## Loss
You lose if you haven't achieved the 2048 tile and can't make another move to free a cell for the next number 
