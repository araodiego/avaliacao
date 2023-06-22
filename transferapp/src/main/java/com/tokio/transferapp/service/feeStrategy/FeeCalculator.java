package com.tokio.transferapp.service.feeStrategy;

import com.tokio.transferapp.model.Transfer;

import java.math.BigDecimal;

public interface FeeCalculator {
    Double calculateFee(Transfer transfer);
}
