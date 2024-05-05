package commons;

public interface Serializer <T> {
    byte[] serialize(T obj);
}
