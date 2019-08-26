package network;

import network.routing.Destination;
import network.routing.NetworkNode;

import java.security.MessageDigest;
import java.util.Arrays;

public class Alice implements NetworkNode {

    private Network network;
    private final String SECRET_KEY = "secret.";
    private final MessageDigest messageDigest;

    public Alice(MessageDigest messageDigest) {
        this.messageDigest = messageDigest;
    }

    public void sendMessage(String message, Destination destination) {
        System.out.println("***");
        System.out.println("ALICE SAYS:");
        System.out.println("Sending message onto the network...");
        System.out.println("***");
        network.takePacket(new Packet(message, destination, hash(SECRET_KEY + message)));
    }

    @Override
    public void connectToNetwork(Network network) {
        this.network = network;
        network.connect(this);
    }

    @Override
    public String hash(String message) {
        return Arrays.toString(messageDigest.digest(message.getBytes()));
    }

    @Override
    public Destination getDestination() {
        return Destination.ALICE;
    }

    @Override
    public void receive(Packet packet) {
        //Not implemented.
    }
}
