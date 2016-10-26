Name of the project being reviewed:
Gitub URL of the project being reviewed:
Name of Reviewer:
Name of Programmer:
Date of Review:

On a scale of 1 to 5, I give this code a rating of __4__ based on the following criteria:

1  The program has syntax errors and does not compile
2  The program compiles successfully but generates runtime errors
3  The program compiles and runs but does not perform correctly and does not accomplish everything stated in the README.md file
4  The program compiles and accomplishes everything stated in the README.md file but is insufficiently documented
5  The program accomplishes everything stated in the README.md and is well written and well documented

Suggestions for improving the code:
The Nim game functions perfectly well, adhering to its rules and catching errors effectively. If you wish to add an option
for the player to quit midgame, you may consider placing if statements after lines 87 and 91 to check if the user inputs
a string such as "quit" or "exit". If either string is entered, you can break from the while loop, then create another if 
statement directly outside of that while loop to check if userInput == "quit" or "exit", causing it to break from the initial
while loop if those are true. Should the user enter neither of these strings, the game continues as normal. As for making
the game a best out of 5 matches, it seems you already have a plan on how to implement that with a GameMatch class at some
point in revision. Keep up the good work!