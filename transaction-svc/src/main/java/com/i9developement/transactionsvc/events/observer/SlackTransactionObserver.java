package com.i9developement.transactionsvc.events.observer;

import br.com.coffeeandit.transaction.domain.TransactionDTO;
import br.com.coffeeandit.transaction.http.SlackAlertService;
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
