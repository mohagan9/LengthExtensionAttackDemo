package network;

import network.routing.Destination;

public class Packet {

    public String message;
    public String mac;
    public Destination destination;

    public Packet(String message, Destination destination, String mac) {
        this.message = message;
        this.destination = destination;
        this.mac = mac;
    }
}
