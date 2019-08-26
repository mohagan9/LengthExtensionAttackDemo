package network.routing;

import network.Network;
import network.Packet;

public interface NetworkNode {
    void receive(Packet packet);
    void connectToNetwork(Network network);
    Destination getDestination();
    String hash(String message);
}
