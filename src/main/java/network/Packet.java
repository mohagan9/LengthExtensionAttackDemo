package network;

import network.routing.Destination;

public class Packet {

    public String message;
    public byte[] mac;
    public Destination destination;

    public Packet(String message, Destination destination, byte[] mac) {
        this.message = message;
        this.destination = destination;
        this.mac = mac;
    }
}
