package network;

import ansi.Colour;
import hashing.MacStrategy;
import network.routing.Destination;
import network.routing.NetworkNode;

import java.util.Arrays;

public class Alice implements NetworkNode {

    private Network network;
    private final String SECRET_KEY = "secret.";
    private final MacStrategy macStrategy;

    public Alice(MacStrategy macStrategy) {
        this.macStrategy = macStrategy;
    }

    public void sendMessage(String message, Destination destination) {
        System.out.println("***");
        System.out.println(Colour.ANSI_CYAN + "ALICE:");
        System.out.println(Colour.ANSI_BLACK + "Sending message onto the network...");
        System.out.println("***");
        Network.simulateTimePassing(2000);
        network.takePacket(new Packet(message, destination, macStrategy.hash(SECRET_KEY, message)));
    }

    @Override
    public void connectToNetwork(Network network) {
        this.network = network;
        network.connect(this);
    }

    @Override
    public Destination getDestination() {
        return Destination.ALICE;
    }

    @Override
    public void receive(Packet packet) {
        System.out.println("***");
        System.out.println(Colour.ANSI_CYAN + "ALICE:");
        System.out.println(Colour.ANSI_BLACK + "Verifying origin of message...");

        if (isMessageFromOriginalSender(packet)) {
            System.out.println("Received Message: " + Colour.ANSI_GREEN + packet.message);
        }

        System.out.println(Colour.ANSI_BLACK + "***");
        Network.simulateTimePassing(3000);
    }

    private boolean isMessageFromOriginalSender(Packet packet) {
        if (Arrays.equals(macStrategy.hash(SECRET_KEY, packet.message), packet.mac)) {
            System.out.println("Message is from Bob!");
            return true;
        } else {
            System.out.println("Message is NOT from Bob!");
            System.out.println("Message rejected!");
            return false;
        }
    }
}
