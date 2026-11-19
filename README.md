# Marvellous Chat

Two-way client-server chat application in Java using TCP sockets and multithreading.

## Features

- Client and server connect over TCP sockets on port 2100
- A listener thread on each side receives messages while the main thread sends, so both users can type and receive at the same time
- Messages are logged to `ChatHistory.txt` with a timestamp and sender name
- Either side types `end` to close the chat

## Tech stack

Java, Sockets (ServerSocket, Socket), Threads, File I/O (FileWriter, BufferedWriter), java.time

## Files

- `ChatServer.java`: server (class `ChatServer`)
- `ChatClient.java`: client (class `ChatClient`)

## How to run

Compile both files:

```bash
javac ChatServer_up.java ChatClient_up.java
```

Start the server in one terminal:

```bash
java ChatServer
```

Start the client in a second terminal, in the same folder:

```bash
java ChatClient
```

Type messages in either terminal. Type `end` to finish.

## Chat log

Each side writes the messages it receives to `ChatHistory.txt` in the folder where it was started, in the format `[yyyy-MM-dd HH:mm:ss] Sender : message`. When both run from the same folder, the file holds the whole conversation.

## Limitations

- The server accepts one client at a time
- Host and port are hardcoded (`localhost:2100`)

## Possible improvements

- Accept multiple clients with one thread per client and broadcast messages between them
- Make the host and port configurable

## Author

Sneha Borgaon
