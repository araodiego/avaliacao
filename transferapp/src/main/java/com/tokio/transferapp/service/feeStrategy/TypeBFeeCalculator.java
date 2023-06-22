package com.tokio.transferapp.service.feeStrategy;

import com.tokio.transferapp.model.Transfer;

public class TypeBFeeCalculator implements FeeCalculator{

    @Override
    public Double calculateFee(Transfer transfer) {
        //Rule: Tranferências até 10 dias da data de agendamento possuem uma taxa de $12.
        Double fixedFee = 12D;

        return fixedFee;
    }
}
