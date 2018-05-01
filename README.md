# Netty Chat Demo

This is a demo repository for implementing a _naïve_ instant messaging platform using [Netty](https://netty.io/).

## Usage

### Server

```java
new ChatServer(8080).start();
```

### Client

```java
ChatClient chatClient = new ChatClient("localhost", 8080);
chatClient.setOnMessageReceivedListener(new OnMessageReceivedListener() {

    @Override
    public void onMessageReceived(String message) {
        System.out.println("Message received: \"" + message + "\".");
    }

});
chatClient.start();
```

Alternatively, you can just use _telnet_ as a client to connect the server.

```shell
telnet localhost 8080
```

## Reference

- [Netty API Documentation](https://netty.io/4.1/api/index.html)
