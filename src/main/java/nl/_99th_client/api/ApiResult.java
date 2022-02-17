package nl._99th_client.api;

public class ApiResult<T> {
    private boolean success;
    private T data;

    ApiResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public boolean succeed() {
        return this.success;
    }

    public T getData() { return this.data; }
}
