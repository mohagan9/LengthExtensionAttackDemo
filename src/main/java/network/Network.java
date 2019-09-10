package network;

import network.routing.EvilPacketRouter;
import network.routing.NetworkNode;
import network.routing.PacketRouter;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.*;

public class Network implements Serializable {

    private Queue<Packet> packets;
    private Set<NetworkNode> nodes;

    private PropertyChangeSupport propertyChange = new PropertyChangeSupport(this);

    private PacketRouter packetRouter;

    public Network() {
        nodes = new HashSet<>();
        packets = new PriorityQueue<>();
    }

    void connect(NetworkNode node) {
        nodes.add(node);
        propertyChange.removePropertyChangeListener(packetRouter);
        packetRouter = new PacketRouter(nodes);
        propertyChange.addPropertyChangeListener(packetRouter);
    }

    void takePacket(Packet packet) {
        packets.add(packet);
        propertyChange.firePropertyChange("packets", null, packets);
    }

    public void activateEvilPacketRouter() {
        EvilPacketRouter evilPacketRouter = new EvilPacketRouter();

        propertyChange.removePropertyChangeListener(packetRouter);
        propertyChange.addPropertyChangeListener(evilPacketRouter);
        propertyChange.addPropertyChangeListener(packetRouter);
    }

    public static void simulateTimePassing(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
