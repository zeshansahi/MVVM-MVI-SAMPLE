package life.league.challenge.java.api;

public interface OnRequestCallback<T> {
    void onSuccess(T response);

    void onFailure(String errorMessage);
}
