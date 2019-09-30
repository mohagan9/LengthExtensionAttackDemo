package network;

import ansi.Colour;
import hashing.SHA1;
import network.routing.Destination;
import network.routing.NetworkNode;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class EvilServer implements NetworkNode {

    private static final int SECRET_LENGTH = 7;
    private final Destination personToFool;

    private Network network;

    public EvilServer(Destination personToFool) {
        this.personToFool = personToFool;
    }

    @Override
    public void receive(Packet packet) {
        System.out.println("***");
        System.out.println(Colour.ANSI_CYAN + "EVIL SERVER:");
        System.out.println(Colour.ANSI_BLACK + "Received Message: "
                + Colour.ANSI_GREEN + packet.message);

        int originalMessageLengthInBits = (packet.message.length() + SECRET_LENGTH) * 8;

        String nastyMessage = "\nAt least that's what I would say, if you weren't such an idiot!";
        System.out.println(Colour.ANSI_BLACK + "Appending malicious content to the existing message...");

        byte[] prePaddedNastyMessage = pad(nastyMessage.getBytes(), originalMessageLengthInBits);
        byte[] message = ArrayUtils.addAll(packet.message.getBytes(), prePaddedNastyMessage);

        System.out.println("Spoofing the MAC...");
        packet.mac = generateNewMac(packet.mac, nastyMessage.getBytes(), originalMessageLengthInBits);

        System.out.println("Sending tampered message to " + personToFool.toString() + "...");
        System.out.println("***");
        Network.simulateTimePassing(4000);
        Packet evilPacket = new Packet(new String(message), personToFool, packet.mac);
        network.takePacket(evilPacket);
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

    private byte[] generateNewMac(byte[] seed, byte[] messageExtension, int originalMessageLengthInBits) {
        return new SHA1(seed)
                .digest(messageExtension,
                        (messageExtension.length * 8)
                                + originalMessageLengthInBits
                                + (getPaddingLengthInBytes(originalMessageLengthInBits) * 8)
                                + 64);
    }

    private int getPaddingLengthInBytes(int originalMessageLengthInBits) {
        return 56 - ((originalMessageLengthInBits / 8) % 56);
    }
    private byte[] pad(byte[] messageExtension, int originalMessageLengthInBits) {

        //Pad with '0' up to length 448 bits
        byte[] padding = new byte[getPaddingLengthInBytes(originalMessageLengthInBits)];
        Arrays.fill(padding, (byte)0);

        //Append 64 bit length of original message
        ByteBuffer length = ByteBuffer.allocate(8);
        length.putInt(0);
        length.putInt(originalMessageLengthInBits);
        byte[] combinedPadding = ArrayUtils.addAll(padding, length.array());

        return ArrayUtils.addAll(combinedPadding, messageExtension);
    }
}