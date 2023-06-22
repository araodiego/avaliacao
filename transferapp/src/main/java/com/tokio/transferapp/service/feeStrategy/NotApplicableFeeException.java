package com.tokio.transferapp.service.feeStrategy;

public class NotApplicableFeeException extends  Exception{
    public NotApplicableFeeException(String message){
        super("Not applicable message for the transfer");
    }

}
