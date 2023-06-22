package com.tokio.transferapp.service.feeStrategy;

import com.tokio.transferapp.model.Transfer;
import com.tokio.transferapp.utils.DateUtils;

import java.math.BigDecimal;

public class TypeCFeeCalculator implements FeeCalculator{
    @Override
    public Double calculateFee(Transfer transfer) {
        long daysBetween = DateUtils.daysBetween(transfer.getTransferDate(), transfer.getSchedulingDate());

        double percentageFee = 0D;
        double amount = transfer.getAmount();

        if(daysBetween >= 10 && daysBetween <= 20){
            //Acima de 10 dias da data de agendamento 8.2%
            percentageFee = amount * BigDecimal.valueOf(0.082).doubleValue();
            
        } else if (daysBetween >= 20 && daysBetween <= 30) {
           //Acima de 20 dias da data de agendamento 6.9%
            percentageFee = amount * BigDecimal.valueOf(0.069).doubleValue();
            
        } else if (daysBetween >= 30 && daysBetween <= 40) {
            //Acima de 30 dias da data de agendamento 4.7%
            percentageFee = amount * BigDecimal.valueOf(0.047).doubleValue();
        }else{
            //Acima de 40 dias da data de agendamento 1.7%
            percentageFee = amount * BigDecimal.valueOf(0.017).doubleValue();
        }

        return percentageFee;
    }
}
