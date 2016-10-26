Server Code Review for Elia Eiroa

by Carlos Leyva
Rate: 5 out of 5


Commentary:
Overall program with all the attached files works and is fully functional. Simulates an entire Nim game for you. 

Things I Noticed:

There was no <arraylist> as you specified in the description for each of the clients to be put into during the similuation of the game.

The server resembled somewhat with the UDP chat assignment we had to complete not too long ago. It works but I had just assumed you would be using the array list. I'm guessing from Nim's history it's only a two player game which makes sense to just make two clients rather than openning it up to more players. 

Another thing that I would advise to implement in the program, when the game is over, create a CloseAllSockets() (or something of the nature) instance to where if one user exits the game, the other user's socket is automatically closed. Since there would be no one to play with that person, it would just make it easier to close both rather than just one. :)

Other than that, you completed all that was asked and all that was specified in your README. Both sides were executed well.


