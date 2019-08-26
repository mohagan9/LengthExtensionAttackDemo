package network;

import network.routing.Destination;
import network.routing.NetworkNode;

public class EvilServer implements NetworkNode {

    private Network network;

    @Override
    public void receive(Packet packet) {
        System.out.println("***");
        System.out.println("EVIL SERVER SAYS:");
        System.out.println("Received Message: " + packet.message);
        System.out.println("***");
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

    @Override
    public String hash(String message) {
        return null;
    }
}