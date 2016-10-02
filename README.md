# mtchat
This repo contains programs to implement a multi-threaded TCP chat server and client 

* MTClient.java handles keyborad input from the user.
* ClientListener.java recieves responses from the server and displays them
* MTServer.java listens for client connections and creates a ClientHandler for each new client
* ClientHandler.java recieves messages from a client and relays it to the other clients.
