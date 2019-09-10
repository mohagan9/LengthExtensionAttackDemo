package network.routing;

import network.Network;
import network.Packet;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Queue;

public class EvilPacketRouter implements PropertyChangeListener {

    private boolean reRouted = false;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("packets".equals(evt.getPropertyName()) && !reRouted) {
            Queue<Packet> packets = (Queue<Packet>) evt.getNewValue();
            Packet packetToTamperWith = packets.remove();
            packets.add(new Packet(packetToTamperWith.message, Destination.BAD_GUY, packetToTamperWith.mac));
            Network.simulateTimePassing(1000);
            System.out.println("Packet intercepted!");
            reRouted = true;
        }
    }
}