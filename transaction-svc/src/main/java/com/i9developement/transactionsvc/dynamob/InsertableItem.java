package com.i9developement.transactionsvc.dynamob;

import com.amazonaws.services.dynamodbv2.document.Item;

public interface InsertableItem<E> {


    Item putItem(E item);
}
