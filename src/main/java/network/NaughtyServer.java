package network;

import ansi.Colour;
import network.routing.Destination;
import network.routing.NetworkNode;

public class NaughtyServer implements NetworkNode {

    private Network network;

    @Override
    public void receive(Packet packet) {
        System.out.println("***");

        System.out.println(Colour.ANSI_CYAN + "NAUGHTY SERVER:");
        System.out.println(Colour.ANSI_BLACK + "Received message: "
                + Colour.ANSI_GREEN + packet.message);

        String nastyMessage = "You suck Bob!";
        System.out.println(Colour.ANSI_BLACK + "Changing message to: " + nastyMessage);

        System.out.println("Sending new nasty message to Bob...");
        System.out.println("***");
        Network.simulateTimePassing(4000);
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