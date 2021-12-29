package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.JpaConfig;
import ua.com.alevel.dao.BankDao;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Bank;
import ua.com.alevel.entity.Client;
import ua.com.alevel.type.BankType;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Service
public class BankDaoImpl implements BankDao {

    private final JpaConfig jpaConfig;

    private static String CREATE_BANK_QUERY = "insert into banks values (default,?,?,?,?,?)";
    private static String UPDATE_BANK_QUERY = "update banks set name = ?, year_of_foundation = ?, updated = ?, bank_type = ? where id = ";
    private static String DELETE_BANK_BY_ID_QUERY = "delete from banks where id = ";
    private static String EXIST_BANK_BY_ID_QUERY = "select count(*) from banks where id = ";
    private static String FIND_BANK_BY_ID_QUERY = "select * from banks where id = ";
    private static String BANKS_COUNT_QUERY = "select count(*) as count from banks";
    private static String LINK_BANK_TO_CLIENT = "insert into bank_client values (?,?)";
    private static String UNLINK_BANK_FROM_CLIENT = "delete from bank_client where bank_id = ? and client_id = ?";

    public BankDaoImpl(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }

    @Override
    public DataTableResponse<Bank> findAllByClientId(DataTableRequest request, Long clientId) {
        List<Bank> banks = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String sql = "select id, created, updated, name, year_of_foundation, bank_type, count(bank_id) as clientCount " +
                "from banks as bank left join bank_client as bc on bank.id = bc.bank_id where client_id = " + clientId +
                " group by bank.id order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(sql)) {
            while(resultSet.next()) {
                BankDaoImpl.BankResultSet bankResultSet = convertResultSetToBank(resultSet);
                banks.add(bankResultSet.getBank());
                otherParamMap.put(bankResultSet.getBank().getId(), bankResultSet.getClientCount());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DataTableResponse<Bank> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(banks);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    @Override
    public void link(Long bankId, Long clientId) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(LINK_BANK_TO_CLIENT)) {
            preparedStatement.setLong(1, bankId);
            preparedStatement.setLong(2, clientId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void unlink(Long bankId, Long clientId) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(UNLINK_BANK_FROM_CLIENT)) {
            preparedStatement.setLong(1, bankId);
            preparedStatement.setLong(2, clientId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void create(Bank entity) {
        try(PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_BANK_QUERY)) {
            preparedStatement.setTimestamp(1, new Timestamp(entity.getCreated().getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(entity.getUpdated().getTime()));
            preparedStatement.setString(3, entity.getName());
            preparedStatement.setInt(4, entity.getYearOfFoundation());
            preparedStatement.setString(5, entity.getBankType().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Bank entity) {
        try(PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(UPDATE_BANK_QUERY + entity.getId())) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getYearOfFoundation());
            preparedStatement.setTimestamp(3, new Timestamp(new Date().getTime()));
            preparedStatement.setString(4, entity.getBankType().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try(PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(DELETE_BANK_BY_ID_QUERY + id)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existById(Long id) {
        int count = 0;
        try(ResultSet resultSet = jpaConfig.getStatement().executeQuery(EXIST_BANK_BY_ID_QUERY + id)) {
            if(resultSet.next())
                count = resultSet.getInt("count(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count == 1;
    }

    @Override
    public Bank findById(Long id) {
        try(ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_BANK_BY_ID_QUERY + id)) {
            if(resultSet.next())
                return convertResultSetToSimpleBank(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DataTableResponse<Bank> findAll(DataTableRequest request) {
        List<Bank> banks = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String sql = "select id, created, updated, name, year_of_foundation, bank_type, count(bank_id) as clientCount " +
                "from banks as bank left join bank_client as bc on bank.id = bc.bank_id " +
                "group by bank.id order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(sql)) {
            while(resultSet.next()) {
                BankDaoImpl.BankResultSet bankResultSet = convertResultSetToBank(resultSet);
                banks.add(bankResultSet.getBank());
                otherParamMap.put(bankResultSet.getBank().getId(), bankResultSet.getClientCount());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DataTableResponse<Bank> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(banks);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    @Override
    public long count() {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(BANKS_COUNT_QUERY)) {
            if(resultSet.next())
                return resultSet.getLong("count");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;

    }

    private BankResultSet convertResultSetToBank(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Timestamp created = resultSet.getTimestamp("created");
        Timestamp updated = resultSet.getTimestamp("updated");
        String name = resultSet.getString("name");
        Integer year = resultSet.getInt("year_of_foundation");
        String type = resultSet.getString("bank_type");
        int clientCount = resultSet.getInt("clientCount");
        Bank bank = new Bank();
        bank.setId(id);
        bank.setCreated(new Date(created.getTime()));
        bank.setUpdated(new Date(updated.getTime()));
        bank.setName(name);
        bank.setYearOfFoundation(year);
        bank.setBankType(BankType.valueOf(type));
        return new BankResultSet(bank, clientCount);
    }

    private Bank convertResultSetToSimpleBank(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Timestamp created = resultSet.getTimestamp("created");
        Timestamp updated = resultSet.getTimestamp("updated");
        String name = resultSet.getString("name");
        Integer year = resultSet.getInt("year_of_foundation");
        String type = resultSet.getString("bank_type");
        Bank bank = new Bank();
        bank.setId(id);
        bank.setCreated(new Date(created.getTime()));
        bank.setUpdated(new Date(updated.getTime()));
        bank.setName(name);
        bank.setYearOfFoundation(year);
        bank.setBankType(BankType.valueOf(type));
        return bank;
    }

    private static class BankResultSet {

        private final Bank bank;
        private final int clientCount;

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
