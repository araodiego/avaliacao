package com.tokio.transferapp.repository;

import com.tokio.transferapp.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

}
