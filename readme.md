Bot Engine
===

Bot Engine is a 2-dimensional, queue-based, concurrent programming language inspired by the game [Manufactoria](http://pleasingfungus.com/Manufactoria/).

Execution consists of one or more "bots", each carrying a queue of symbols, traversing the grid of instructions. Execution begins with a single bot carrying an empty queue and going EAST, which begins in the northwesternmost cell.

At each step of the execution, every bot advances 1 cell in whatever direction it is facing, unless doing so would move it into a cell which is already occupied; and every bot which successfully moved then executes the command (if any) corresponding to the cell they entered.
Bots are updated in the order they were created.

Execution halts when no bots remain.

Additionally, any rows in the program grid that are shorter than the longest row will be automatically right-padded with spaces by the interpreter to match the length of the longest row.

---

<b>Instructions</b>

```
X  Destroy the current bot. (This will also happen when a bot exits the program bounds)
Z  Destroy all bots.
T  Destroy the current bot and print TRUE.
F  Destroy the current bot and print FALSE.
P  Destroy the current bot and print the contents of its queue.
r  Rotate the current bot's direction of travel 90 degrees clockwise.
l  Rotate the current bot's direction of travel 90 degrees counterclockwise.
R  Reverse the current bot's queue.
^  The current bot is now going NORTH.
v  The current bot is now going SOUTH.
>  The current bot is now going EAST.
<  The current bot is now going WEST.
|  Reverse the current bot's direction of travel.
C  Duplicate the current bot. Allow the original to continue in its current direction, but reverse the direction of the copy.
E  If the current bot's queue is empty, rotate its direction of travel 90 degrees clockwise.
I  Read a line from the input and add its characters to the current bot's queue.
e  Find the symbol to the left (relative to the current bot's direction of travel). Add it to the current bot's queue.
d  Duplicate the symbol at the front of the current bot's queue. A no-op if the queue is empty.
S  Find the symbol to the left (relative to the current bot's direction of travel). If the symbol at the front of the current bot's queue matches this symbol, dequeue it and rotate the bot's direction of travel 90 degrees clockwise.
~  Move a symbol from the front to the end of the current bot's queue. A no-op if the queue is empty.
@  Move a symbol from the end to the front of the current bot's queue. A no-op if the queue is empty.
```

---

![](https://i.creativecommons.org/l/by-sa/4.0/88x31.png)

This work is licensed under a [Creative Commons Attribution-ShareAlike 4.0 International License](http://creativecommons.org/licenses/by-sa/4.0/).
