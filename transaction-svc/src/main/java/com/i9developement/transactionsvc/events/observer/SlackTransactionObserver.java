package com.i9developement.transactionsvc.events.observer;

import com.i9developement.transactionsvc.domain.TransactionDTO;
import com.i9developement.transactionsvc.http.SlackAlertService;
import org.springframework.stereotype.Service;

@Service
public class SlackTransactionObserver implements TransactionObserver<TransactionDTO> {

    private SlackAlertService slackAlertService;

    public SlackTransactionObserver(final SlackAlertService slackAlertService) {
        this.slackAlertService = slackAlertService;
    }

    @Override
    public void doObserver(final TransactionDTO item) {

        slackAlertService.send(item);

    }
}
