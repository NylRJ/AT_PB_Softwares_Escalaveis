package com.i9developement.transactionsvc.events.observer;

public interface TransactionObserver<E> {

    void doObserver(E item);
}
