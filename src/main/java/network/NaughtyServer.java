package network;

import network.routing.Destination;
import network.routing.NetworkNode;

public class NaughtyServer implements NetworkNode {

    private Network network;

    @Override
    public void receive(Packet packet) {
        System.out.println("***");

        System.out.println("NAUGHTY SERVER SAYS:");
        System.out.println("Received message: " + packet.message);

        String nastyMessage = "You suck Bob!";
        System.out.println("Changing message to: " + nastyMessage);

        System.out.println("Sending new nasty message to Bob...");
        System.out.println("***");
        Packet nastyPacket = new Packet(nastyMessage, Destination.BOB, packet.mac);
        network.takePacket(nastyPacket);
    }

    @Override
    public void connectToNetwork(Network network) {
        this.network = network;
        network.connect(this);
    }

    @Override
    public Destination getDestination() {
        return Destination.BAD_GUY;
    }
}