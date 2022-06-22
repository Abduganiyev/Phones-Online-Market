package dto;

public class Response<T> {
    private boolean status;
    private String message;
    private T object;
}
