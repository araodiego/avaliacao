package com.tokio.transferapp.service;

import com.tokio.transferapp.model.Transfer;
import com.tokio.transferapp.repository.TransferRepository;
import com.tokio.transferapp.service.feeStrategy.FeeCalculator;
import com.tokio.transferapp.service.feeStrategy.FeeCalculatorFactory;
import com.tokio.transferapp.service.feeStrategy.NotApplicableFeeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferService {
    private final TransferRepository transferRepository;

    @Autowired
    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public Transfer scheduleTransfer(Transfer transfer) throws NotApplicableFeeException {
        FeeCalculator feeCalculator = FeeCalculatorFactory.getFeeCalculator(transfer);
        Double fee = feeCalculator.calculateFee(transfer);
        transfer.setFee(fee);

        return transferRepository.save(transfer);
    }

    /***
     * Retorna todas as transações agendadas.
     * @return
     */
    public List<Transfer> getAllTransfers() {
        return transferRepository.findAll();
    }
}
