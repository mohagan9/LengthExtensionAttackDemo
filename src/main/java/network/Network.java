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

        EvilPacketRouter evilPacketRouter = new EvilPacketRouter();
        propertyChange.addPropertyChangeListener(evilPacketRouter);
    }

    public void connect(NetworkNode node) {
        nodes.add(node);
        propertyChange.removePropertyChangeListener(packetRouter);
        packetRouter = new PacketRouter(nodes);
        propertyChange.addPropertyChangeListener(packetRouter);
    }

    public void takePacket(Packet packet) {
        packets.add(packet);
        propertyChange.firePropertyChange("packets", null, packets);
    }
}
