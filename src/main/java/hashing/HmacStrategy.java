package hashing;

public class HmacStrategy extends MacStrategy {

    @Override
    public byte[] hash(String secret, String message) {
        String hash = new String(super.hash(secret, message));
        return super.hash(secret, hash);
    }
}
