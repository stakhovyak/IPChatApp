package commons;

public interface DataProcessor <T> {
    T process(byte[] data);
}
