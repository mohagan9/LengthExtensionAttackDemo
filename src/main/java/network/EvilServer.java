package network;

import hashing.SHA1;
import network.routing.Destination;
import network.routing.NetworkNode;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class EvilServer implements NetworkNode {

    private Network network;
    private static final int SECRET_LENGTH = 7;

    @Override
    public void receive(Packet packet) {
        System.out.println("***");

        System.out.println("EVIL SERVER:");
        System.out.println("Received Message: " + packet.message);

        int originalMessageLengthInBits = (packet.message.length() + SECRET_LENGTH) * 8;

        String nastyMessage = "\nAt least that's what I would say, if you weren't such an idiot! GO TO HELL BOB!";
        System.out.println("Appending malicious content to the existing message...");

        byte[] prePaddedNastyMessage = pad(nastyMessage.getBytes(), originalMessageLengthInBits);
        byte[] message = ArrayUtils.addAll(packet.message.getBytes(), prePaddedNastyMessage);

        System.out.println("Spoofing the MAC...");
        packet.mac = generateNewMac(packet.mac, nastyMessage.getBytes(), originalMessageLengthInBits);

        System.out.println("Sending tampered message to Bob...");
        System.out.println("***");
        Packet evilPacket = new Packet(new String(message), Destination.BOB, packet.mac);
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