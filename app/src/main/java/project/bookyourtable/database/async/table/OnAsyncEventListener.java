package project.bookyourtable.database.async.table;

public interface OnAsyncEventListener {
    void onSuccess();
    void onFailure(Exception e);
}

