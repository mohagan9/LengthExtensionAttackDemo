package network.routing;

import network.Packet;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class PacketRouter implements PropertyChangeListener {

    private final Set<NetworkNode> nodes;

    public PacketRouter(Set<NetworkNode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("packets".equals(evt.getPropertyName())) {
            Queue<Packet> packets = (Queue<Packet>) evt.getNewValue();
            Packet packetToSend = packets.remove();
            System.out.println("Routing packet to destination: " + packetToSend.destination.toString());
            nodes.stream()
                    .filter(node -> node.getDestination() == packetToSend.destination)
                    .findFirst().get()
                    .receive(packetToSend);
        }
    }
}
