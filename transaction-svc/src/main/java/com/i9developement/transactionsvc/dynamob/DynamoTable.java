package com.i9developement.transactionsvc.dynamob;

import com.amazonaws.services.dynamodbv2.document.Table;

public interface DynamoTable<T extends Table> {


    T getTable();
}
