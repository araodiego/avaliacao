package com.tokio.transferapp;

import com.tokio.transferapp.model.Transfer;
import com.tokio.transferapp.repository.TransferRepository;
import com.tokio.transferapp.service.TransferService;
import com.tokio.transferapp.service.feeStrategy.NotApplicableFeeException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@DataJpaTest
public class TransferServiceTest {
    private static String  EXPECTEDMESSAGE = "Not applicable message for the transfer";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TransferRepository transferRepository;

    @Test
    public void testScheduleTransferFeeA() throws Exception{

        TransferService transferService = new TransferService(transferRepository);
        Transfer transfer = new Transfer();
        transfer.setSourceAccount("XXXXXX");
        transfer.setDestinationAccount("YYYYYY");
        transfer.setAmount(1000D);
        transfer.setTransferDate(new Date());
        transfer.setSchedulingDate(new Date());

        Transfer savedTransfer = transferService.scheduleTransfer(transfer);
        System.out.println(savedTransfer);

        assertNotNull(savedTransfer);
        assertNotNull(savedTransfer.getId());
        double fee =  savedTransfer.getFee().doubleValue();

        assertEquals(33 , fee);
    }

    @Test
    public void testScheduleTransferFeeB() throws Exception{
        TransferService transferService = new TransferService(transferRepository);
        Date to = Date.from(LocalDate.now().plusDays(9).atStartOfDay(ZoneId.systemDefault()).toInstant());

        Transfer transfer = new Transfer();
        transfer.setSourceAccount("XXXXXX");
        transfer.setDestinationAccount("YYYYYY");
        transfer.setAmount(1001D);
        transfer.setTransferDate(new Date());
        transfer.setSchedulingDate(to);

        Transfer scheduleTransfer = transferService.scheduleTransfer(transfer);
        System.out.println(scheduleTransfer.getFee());

        assertEquals(12, scheduleTransfer.getFee());
    }

    @Test
    public void testScheduleTransferFeeC_1() throws Exception{
        //Test for this condition: acima de 10 dias da data de agendamento 8.2%
        TransferService transferService = new TransferService(transferRepository);
        Date to = Date.from(LocalDate.now().plusDays(11).atStartOfDay(ZoneId.systemDefault()).toInstant());

        Transfer transfer = new Transfer();
        transfer.setSourceAccount("XXXXXX");
        transfer.setDestinationAccount("YYYYYY");
        transfer.setAmount(2001D);
        transfer.setTransferDate(new Date());
        transfer.setSchedulingDate(to);

        Transfer scheduleTransfer = transferService.scheduleTransfer(transfer);
        System.out.println(scheduleTransfer.getFee());

        assertEquals(164.082, scheduleTransfer.getFee());
    }

    @Test
    public void testScheduleTransferFeeC_2() throws Exception{
        //Test for this condition: acima de 20 dias da data de agendamento 6.9%
        TransferService transferService = new TransferService(transferRepository);
        Date to = Date.from(LocalDate.now().plusDays(21).atStartOfDay(ZoneId.systemDefault()).toInstant());

        Transfer transfer = new Transfer();
        transfer.setSourceAccount("XXXXXX");
        transfer.setDestinationAccount("YYYYYY");
        transfer.setAmount(2001D);
        transfer.setTransferDate(new Date());
        transfer.setSchedulingDate(to);

        Transfer scheduleTransfer = transferService.scheduleTransfer(transfer);
        System.out.println(scheduleTransfer.getFee());

        assertEquals(138.06900000000002, scheduleTransfer.getFee());
    }

    @Test
    public void testScheduleTransferFeeC_3() throws Exception{
        //Test for this condition: acima de 30 dias da data de agendamento 4.7%
        TransferService transferService = new TransferService(transferRepository);
        Date to = Date.from(LocalDate.now().plusDays(31).atStartOfDay(ZoneId.systemDefault()).toInstant());

        Transfer transfer = new Transfer();
        transfer.setSourceAccount("XXXXXX");
        transfer.setDestinationAccount("YYYYYY");
        transfer.setAmount(2001D);
        transfer.setTransferDate(new Date());
        transfer.setSchedulingDate(to);

        Transfer scheduleTransfer = transferService.scheduleTransfer(transfer);
        System.out.println(scheduleTransfer.getFee());

        assertEquals(94.047, scheduleTransfer.getFee());
    }

    @Test
    public void testScheduleTransferFeeC_4() throws Exception{
        //Test for this condition: acima de 40 dias da data de agendamento 1.7%
        TransferService transferService = new TransferService(transferRepository);
        Date to = Date.from(LocalDate.now().plusDays(41).atStartOfDay(ZoneId.systemDefault()).toInstant());

        Transfer transfer = new Transfer();
        transfer.setSourceAccount("XXXXXX");
        transfer.setDestinationAccount("YYYYYY");
        transfer.setAmount(2001D);
        transfer.setTransferDate(new Date());
        transfer.setSchedulingDate(to);

        Transfer scheduleTransfer = transferService.scheduleTransfer(transfer);
        System.out.println(scheduleTransfer.getFee());

        assertEquals(34.017, scheduleTransfer.getFee());
    }

    @Test
    public void testScheduleTransferFeeBError() throws Exception{
        TransferService transferService = new TransferService(transferRepository);
        Date to = Date.from(LocalDate.now().plusDays(9).atStartOfDay(ZoneId.systemDefault()).toInstant());

        Transfer transfer = new Transfer();
        transfer.setSourceAccount("XXXXXX");
        transfer.setDestinationAccount("YYYYYY");
        transfer.setAmount(900D);
        transfer.setTransferDate(new Date());
        transfer.setSchedulingDate(to);

        NotApplicableFeeException exception =  assertThrows(NotApplicableFeeException.class, () -> {
            Transfer savedTransfer = transferService.scheduleTransfer(transfer);
        });

        assertEquals(EXPECTEDMESSAGE, exception.getMessage());
    }

    @Test
    public void testScheduleTransferFeeAError() throws Exception{

        TransferService transferService = new TransferService(transferRepository);

        Transfer transfer = new Transfer();
        transfer.setSourceAccount("XXXXXX");
        transfer.setDestinationAccount("YYYYYY");
        transfer.setAmount(2002D);
        transfer.setTransferDate(new Date());
        transfer.setSchedulingDate(new Date());

        NotApplicableFeeException exception =  assertThrows(NotApplicableFeeException.class, () -> {
            Transfer savedTransfer = transferService.scheduleTransfer(transfer);
        });

        assertEquals(EXPECTEDMESSAGE, exception.getMessage());
    }

    @Test
    public void testScheduleTransferFeeCError() throws Exception{
        //Test for this condition: acima de 40 dias da data de agendamento 1.7% e valor abaixo de 2000
        TransferService transferService = new TransferService(transferRepository);
        Date to = Date.from(LocalDate.now().plusDays(41).atStartOfDay(ZoneId.systemDefault()).toInstant());

        Transfer transfer = new Transfer();
        transfer.setSourceAccount("XXXXXX");
        transfer.setDestinationAccount("YYYYYY");
        transfer.setAmount(900D);
        transfer.setTransferDate(new Date());
        transfer.setSchedulingDate(to);

        NotApplicableFeeException exception =  assertThrows(NotApplicableFeeException.class, () -> {
            Transfer savedTransfer = transferService.scheduleTransfer(transfer);
        });

        assertEquals(EXPECTEDMESSAGE, exception.getMessage());
    }

    @Test
    public void testGetAllTransfers() throws Exception {
        TransferService transferService = new TransferService(transferRepository);

        Transfer transfer1 = new Transfer();
        transfer1.setSourceAccount("XXXXXX");
        transfer1.setDestinationAccount("YYYYYY");
        transfer1.setAmount(1000D);
        transfer1.setTransferDate(new Date());
        transfer1.setSchedulingDate(new Date());

        Transfer transfer2= new Transfer();
        transfer2.setSourceAccount("XXXXXX");
        transfer2.setDestinationAccount("YYYYYY");
        transfer2.setAmount(1000D);
        transfer2.setTransferDate(new Date());
        transfer2.setSchedulingDate(new Date());

        transferService.scheduleTransfer(transfer1);
        transferService.scheduleTransfer(transfer2);

        List<Transfer> transfers = transferService.getAllTransfers();

        assertNotNull(transfers);
        assertEquals(transfers.size(), 2);
    }
}
