import network.*;
import network.routing.Destination;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {

    private static MessageDigest messageDigest;

    public static void main(String[] args) {
        System.out.println("Starting...");

//        macExample();
        lengthExtensionAttackExample();
//        hmacExample();
    }

    private static void macExample() {
        Network network = new Network();

        Alice alice = new Alice();
        Bob bob = new Bob();
        NaughtyServer naughtyServer = new NaughtyServer();

        alice.connectToNetwork(network);
        bob.connectToNetwork(network);
        naughtyServer.connectToNetwork(network);

        alice.sendMessage("Good work today Bob, thanks for helping out.", Destination.BOB);
    }

    private static void lengthExtensionAttackExample() {
        Network network = new Network();

        Alice alice = new Alice();
        Bob bob = new Bob();
        EvilServer evilServer = new EvilServer();

        alice.connectToNetwork(network);
        bob.connectToNetwork(network);
        evilServer.connectToNetwork(network);

        alice.sendMessage("Good work today Bob, thanks for helping out.", Destination.BOB);
    }

    private static void hmacExample() {

    }
}
