package org.jianfengderek.nettychatservertest;

import org.junit.jupiter.api.Test;

class ChatServerTest {

    @Test
    void chatServerTest() throws Exception {
        new ChatServer(8080).start();
    }

}
