package hashing;

public class MacStrategy {

    public byte[] hash(String secret, String message) {
        String secretWithMessage = secret + message;
        return new SHA1().digest(secretWithMessage.getBytes(), secretWithMessage.length() * 8);
    }
}
