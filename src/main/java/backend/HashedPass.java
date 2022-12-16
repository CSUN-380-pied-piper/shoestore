package backend;

public class HashedPass implements Queryable {

    private final String hash;
    private final String salt;
    private final String rawInput;

    public HashedPass(String input) {
        this.rawInput = String.valueOf(input);
        this.hash = rawInput.split(":",2)[0];
        this.salt = rawInput.split(":",2)[1];
    }

    public boolean equals(String input) {
        return this.rawInput.equals(input);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("hash: ");
        builder.append(hash);
        builder.append(", ");
        builder.append("salt: ");
        builder.append(salt);
        return builder.toString();
    }

    public String getSalt() {
        return this.salt;
    }
}
