# Multi-Client Messaging System Using Sockets

## Overview
This project is a **Java-based messaging application** designed for a client-server environment. It allows multiple clients to communicate concurrently with a central server. The system demonstrates practical usage of **sockets, I/O streams, threads, and object-oriented programming** in Java.

The server maintains user accounts and handles messaging requests from multiple clients simultaneously. Each client can create accounts, send messages, and view messages in their inbox.

---

## Key Classes and Their Functionality

### Server
Represents the central server of the application. It can handle multiple client requests concurrently. It maintains a `HashMap<Integer, Account>` called `accountMap`, storing all information about user accounts. The `Server` class processes all user data and establishes the `ServerSocket` for client connections. Each connection spawns a **MessagingServer thread** to handle communication and data processing.

### Client
The client class collects user input and sends it to the server as a single message string. The server processes the request and returns an appropriate response to the client.

### Account
Represents a user account with the following private fields: `username`, `authToken` (unique identifier), and `messagesBox` (list of messages for the user).

### Message
Represents an individual message with private fields: `isRead` (indicates if the message has been read), `sender`, `receiver`, and `body` (content of the message).

### MessagingServer
Extends `Thread` to allow concurrent handling of multiple clients. The `run()` method selects the appropriate function based on the client’s request. Main operations include: 
- `createAccount`
- `showAccounts`
- `sendMessage`
- `showInbox`
- `readMessage`
- `deleteMessage`

Helper functions support these operations, such as `getMessageBody()` for extracting message content, `isValidUsername()` for validating usernames, and `getAuthToken()` for generating unique authentication tokens.

---

## Features
- **Multi-client support:** Handles multiple clients concurrently using threads.
- **Account management:** Create and maintain user accounts.
- **Messaging:** Send, receive, and manage messages.
- **Inbox management:** Track read/unread messages.
- **Validation:** Ensures usernames are valid and generates unique authentication tokens.

---

## Technologies Used
- **Programming Language:** Java
- **Networking:** Java Sockets (TCP)
- **Concurrency:** Threads
- **I/O Handling:** Input/Output Streams

---

## Usage
1. Compile and run the `Server.java` file to start the server.
2. Run `Client.java` for each client that wants to connect.
3. Use the client interface to:
   - Create an account
   - View existing accounts
   - Send messages to other users
   - View and read messages in the inbox
   - Delete messages if needed

---

## Purpose
This project demonstrates key concepts in network programming and concurrency in Java, including:
- Client-server architecture
- Handling multiple clients simultaneously
- Thread management
- Socket-based communication
- Object-oriented design and data encapsulation

It is intended as a **university assignment** for the course on Communication Networks.
