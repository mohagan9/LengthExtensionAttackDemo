package network;

import network.routing.Destination;
import network.routing.NetworkNode;
import hashing.SHA1;

import java.util.Arrays;

public class Bob implements NetworkNode {

    private Network network;
    private final String SECRET_KEY = "secret.";

    private byte[] hash(String secretWithMessage) {
        return new SHA1().digest(secretWithMessage.getBytes());
    }

    @Override
    public void receive(Packet packet) {
        System.out.println("***");
        System.out.println("BOB SAYS:");

        System.out.println("Verifying origin of message...");
        if (isMessageFromOriginalSender(packet)) {
            System.out.println("Received Message: " + packet.message);
        }
        System.out.println("***");
    }

    @Override
    public void connectToNetwork(Network network) {
        this.network = network;
        network.connect(this);
    }

    @Override
    public Destination getDestination() {
        return Destination.BOB;
    }

    private boolean isMessageFromOriginalSender(Packet packet) {
        if (Arrays.equals(hash(SECRET_KEY + packet.message), packet.mac)) {
            System.out.println("Message is from Alice...");
            return true;
        } else {
            System.out.println("Message is NOT from Alice!");
            System.out.println("Message rejected!");
            return false;
        }
    }
}
