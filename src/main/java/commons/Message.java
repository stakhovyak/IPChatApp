package commons;

import java.io.Serializable;

public record Message(String sender, String contents)
        implements Serializable {
}
