import ansi.Colour;
import hashing.HmacStrategy;
import hashing.MacStrategy;
import network.*;
import network.routing.Destination;

public class Main {

    private static MacStrategy macStrategy = new MacStrategy();
    private static HmacStrategy hmacStrategy = new HmacStrategy();

    public static void main(String[] args) {
        System.out.println(Colour.ANSI_BLACK + "Starting...");

//        macExample();
//        lengthExtensionAttackExample();
        hmacExample();

        System.out.println("\n\n\n\n\n");
    }

    private static void macExample() {
        Network network = new Network();

        Alice alice = new Alice(macStrategy);
        Bob bob = new Bob(macStrategy);
        NaughtyServer naughtyServer = new NaughtyServer();

        alice.connectToNetwork(network);
        bob.connectToNetwork(network);

        naughtyServer.connectToNetwork(network);
        network.activateEvilPacketRouter();

        alice.sendMessage("Good work today Bob, thanks for helping out.", Destination.BOB);
    }

    private static void lengthExtensionAttackExample() {
        Network network = new Network();

        Alice alice = new Alice(macStrategy);
        Bob bob = new Bob(macStrategy);
        EvilServer evilServer = new EvilServer();

        alice.connectToNetwork(network);
        bob.connectToNetwork(network);

        evilServer.connectToNetwork(network);
        network.activateEvilPacketRouter();

        alice.sendMessage("Good work today Bob, thanks for helping out.", Destination.BOB);
    }

    private static void hmacExample() {
        Network network = new Network();

        Alice alice = new Alice(hmacStrategy);
        Bob bob = new Bob(hmacStrategy);
        EvilServer evilServer = new EvilServer();

        alice.connectToNetwork(network);
        bob.connectToNetwork(network);

        alice.sendMessage("Good work today Bob, thanks for helping out.", Destination.BOB);

        evilServer.connectToNetwork(network);
        network.activateEvilPacketRouter();

        bob.sendMessage("Thank you!", Destination.ALICE);
    }
}
