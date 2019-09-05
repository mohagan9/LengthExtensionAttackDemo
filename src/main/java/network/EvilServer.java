package network;

import hashing.SHA1;
import network.routing.Destination;
import network.routing.NetworkNode;
import org.apache.commons.lang3.ArrayUtils;
import org.joou.UByte;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.Arrays;

public class EvilServer implements NetworkNode {

    private Network network;
    private static final int SECRET_LENGTH = 7;

    @Override
    public void receive(Packet packet) {
        System.out.println("***");

        System.out.println("EVIL SERVER SAYS:");
        System.out.println("Received Message: " + packet.message);

        int originalMessageLengthInBits = (packet.message.length() + SECRET_LENGTH) * 8;

        String nastyMessage = "At least that's what I would say, if you weren't such an idiot! GO TO HELL BOB!";
        System.out.println("Appending malicious content to the existing message...");
        packet.message += nastyMessage;

        System.out.println("Spoofing the MAC...");
        packet.mac = generateNewMac(packet.mac, nastyMessage, originalMessageLengthInBits);

        System.out.println("Sending tampered message to Bob...");
        System.out.println("***");
        Packet evilPacket = new Packet(packet.message, Destination.BOB, packet.mac);
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

    private byte[] generateNewMac(byte[] seed, String messageExtension, int originalMessageLengthInBits) {
        return new SHA1(seed)
                .digest(pad(messageExtension.getBytes(), originalMessageLengthInBits));
    }

    private byte[] pad(byte[] messageExtension, int originalMessageLengthInBits) {

        //Pad with '0' up to length 448 bits
        int[] padding = new int[14 - ((originalMessageLengthInBits / 32) % 14)];
        Arrays.fill(padding, 0);

        //Append 64 bit length of original message
        int[] combinedPadding = ArrayUtils.addAll(padding, originalMessageLengthInBits);

        //convert padding to byte array
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        for (int i : combinedPadding) {
            byteBuffer.putInt(i);
        }
        System.out.println(Arrays.toString(byteBuffer.array()));

        return ArrayUtils.addAll(byteBuffer.array(), messageExtension);
    }
}