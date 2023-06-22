package com.tokio.transferapp.service.feeStrategy;

import com.tokio.transferapp.model.Transfer;

import java.math.BigDecimal;
import java.util.Date;

public class TypeAFeeCalculator implements FeeCalculator{
    @Override
    public Double calculateFee(Transfer transfer) {
        Double amount = transfer.getAmount();

        Date schedulingDate = transfer.getSchedulingDate();
        Date transferDate = transfer.getTransferDate();

        Integer fixedFee = 3;
        double percentageFee = amount * BigDecimal.valueOf(0.03).doubleValue();
        Double fee = fixedFee + percentageFee;

        //Rule, 3$ + 3%
        return fee;
    }
}
