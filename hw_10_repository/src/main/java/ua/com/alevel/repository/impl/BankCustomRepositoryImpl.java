package ua.com.alevel.repository.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Bank;
import ua.com.alevel.repository.BankCustomRepository;
import ua.com.alevel.type.BankType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class BankCustomRepositoryImpl implements BankCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DataTableResponse<Bank> findAll(DataTableRequest request) {
        List<Bank> banks = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Object[]> banksRl = entityManager.createNativeQuery("select id, created, updated, name, year_of_foundation, bank_type, count(bank_id) as clientCount " +
                "from banks as bank left join bank_client as bc on bank.id = bc.bank_id" +
                " group by bank.id order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize()).getResultList();

        for(Object[] object : banksRl) {
            BankResultSet bankRs = convertResultSetToBank(object);
            banks.add(bankRs.getBank());
            otherParamMap.put(bankRs.getBank().getId(), bankRs.getClientCount());
        }

        DataTableResponse<Bank> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(banks);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    @Override
    public void deleteById(Long id) {
        entityManager.createQuery("delete from Bank b where b.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public DataTableResponse<Bank> findAllByClientId(DataTableRequest request, Long clientId) {
        List<Bank> banks = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Object[]> banksRl = entityManager.createNativeQuery("select id, created, updated, name, year_of_foundation, bank_type, count(bank_id) as clientCount " +
                "from banks as bank left join bank_client as bc on bank.id = bc.bank_id where client_id = " + clientId +
                " group by bank.id order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize()).getResultList();

        for(Object[] object : banksRl) {
            BankResultSet bankRs = convertResultSetToBank(object);
            banks.add(bankRs.getBank());
            otherParamMap.put(bankRs.getBank().getId(), bankRs.getClientCount());
        }

        DataTableResponse<Bank> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(banks);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    private BankResultSet convertResultSetToBank(Object[] resultSet) {
        BigInteger id = (BigInteger) resultSet[0];
        Timestamp created = (Timestamp) resultSet[1];
        Timestamp updated = (Timestamp) resultSet[2];
        String name = (String) resultSet[3];
        Integer year = (Integer) resultSet[4];
        String type = (String) resultSet[5];
        BigInteger clientCount = (BigInteger) resultSet[6];
        Bank bank = new Bank();
        bank.setId(id.longValue());
        bank.setCreated(new Date(created.getTime()));
        bank.setUpdated(new Date(updated.getTime()));
        bank.setName(name);
        bank.setYearOfFoundation(year);
        bank.setBankType(BankType.valueOf(type));
        return new BankResultSet(bank, clientCount.intValue());
    }

    private static class BankResultSet {

        private final Bank bank;
        private final int clientCount;
        private Long id;

        public BankResultSet(Bank bank, int clientCount) {
            this.bank = bank;
            this.clientCount = clientCount;
        }

        public Bank getBank() {
            return bank;
        }

        public int getClientCount() {
            return clientCount;
        }
    }
}
