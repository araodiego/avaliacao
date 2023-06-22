package com.tokio.transferapp.service.feeStrategy;

import com.tokio.transferapp.model.Transfer;
import com.tokio.transferapp.utils.DateUtils;

public class FeeCalculatorFactory {
    public static FeeCalculator getFeeCalculator(Transfer transfer) throws NotApplicableFeeException {

        double amount = transfer.getAmount().doubleValue();
        long daysBetween = DateUtils.daysBetween(transfer.getTransferDate() , transfer.getSchedulingDate());

        if(amount <= 1000){
            if(daysBetween == 0)
                return new TypeAFeeCalculator();
            else
                throw new NotApplicableFeeException(transfer.toString());
        }else if(amount > 1000 && amount <= 2000){
            if (daysBetween <= 10) {
                return new TypeBFeeCalculator();
            }else{
                throw new NotApplicableFeeException(transfer.toString());
            }
        }else{
            if(daysBetween < 10){
                throw  new NotApplicableFeeException(transfer.toString());
            }else{
                return new TypeCFeeCalculator();
            }
        }


    }
}
