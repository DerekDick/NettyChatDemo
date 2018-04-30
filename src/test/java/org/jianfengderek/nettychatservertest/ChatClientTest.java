package org.jianfengderek.nettychatservertest;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

class ChatClientTest {

    @Test
    void chatClientTest() throws Exception {
        ChatClient chatClient = new ChatClient("localhost", 8080);
        chatClient.setOnMessageReceivedListener(new OnMessageReceivedListener() {

            @Override
            public void onMessageReceived(String message) {
                System.out.println("Message received: \"" + message + "\".");
            }

        });
        chatClient.start();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

}
