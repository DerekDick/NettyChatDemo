package org.jianfengderek.nettychatservertest;

/**
 * Interface definition for a callback to be invoked when a message is received.
 */
public interface OnMessageReceivedListener {

    /**
     * Called when a message is received.
     *
     * @param message The received message.
     */
    void onMessageReceived(String message);

}
