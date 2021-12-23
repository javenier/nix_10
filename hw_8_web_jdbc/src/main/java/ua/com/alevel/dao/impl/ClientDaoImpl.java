package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.JpaConfig;
import ua.com.alevel.dao.ClientDao;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Client;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Service
public class ClientDaoImpl implements ClientDao {

    private final JpaConfig jpaConfig;

    private static String CREATE_CLIENT_QUERY = "insert into clients values (default,?,?,?,?,?)";
    private static String UPDATE_CLIENT_QUERY = "update clients set first_name = ?, last_name = ?, age = ?, updated = ? where id = ";
    private static String DELETE_CLIENT_BY_ID_QUERY = "delete from clients where id = ";
    private static String EXIST_CLIENT_BY_ID_QUERY = "select count(*) from clients where id = ";
    private static String FIND_CLIENT_BY_ID_QUERY = "select * from clients where id = ";
    private static String CLIENTS_COUNT_QUERY = "select count(*) as count from clients";
    private static String CREATE_RELATION = "insert into bank_client values (?,LAST_INSERT_ID())";

    public ClientDaoImpl(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }

    @Override
    public void create(Client entity, Long bankId) {
        try(PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_CLIENT_QUERY)) {
            preparedStatement.setTimestamp(1, new Timestamp(entity.getCreated().getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(entity.getUpdated().getTime()));
            preparedStatement.setString(3, entity.getFirstName());
            preparedStatement.setString(4, entity.getLastName());
            preparedStatement.setInt(5, entity.getAge());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_RELATION)) {
            preparedStatement.setLong(1, bankId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(Client entity) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(UPDATE_CLIENT_QUERY + entity.getId())) {
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setInt(3, entity.getAge());
            preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(DELETE_CLIENT_BY_ID_QUERY + id)) {
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean existById(Long id) {
        int count = 0;
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(EXIST_CLIENT_BY_ID_QUERY + id)) {
            if(resultSet.next())
                count = resultSet.getInt("count(*)");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count == 1;
    }

    @Override
    public Client findById(Long id) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_CLIENT_BY_ID_QUERY + id)) {
            if(resultSet.next())
                return convertResultSetToSimpleClient(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public DataTableResponse<Client> findAll(DataTableRequest request) {
        List<Client> clients = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String sql = "select id, created, updated, first_name, last_name, age, count(*) as bankCount" +
                " from clients join bank_client bc on clients.id = bc.client_id group by client_id order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(sql)) {
            while(resultSet.next()) {
                ClientResultSet clientResultSet = convertResultSetToClient(resultSet);
                clients.add(clientResultSet.getClient());
                otherParamMap.put(clientResultSet.getClient().getId(), clientResultSet.getBankCount());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DataTableResponse<Client> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(clients);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    @Override
    public long count() {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(CLIENTS_COUNT_QUERY)) {
            if(resultSet.next())
                return resultSet.getLong("count");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    @Override
    public DataTableResponse<Client> findAllByBankId(DataTableRequest request, Long bankId) {
        List<Client> clients = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String sql = "select id, created, updated, first_name, last_name, age, count(*) as bankCount" +
                " from clients join bank_client bc on clients.id = bc.client_id where bank_id = " +
                bankId + " group by client_id order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(sql)) {
            while(resultSet.next()) {
                ClientResultSet clientResultSet = convertResultSetToClient(resultSet);
                clients.add(clientResultSet.getClient());
                otherParamMap.put(clientResultSet.getClient().getId(), clientResultSet.getBankCount());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DataTableResponse<Client> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(clients);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    private ClientResultSet convertResultSetToClient(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Timestamp created = resultSet.getTimestamp("created");
        Timestamp updated = resultSet.getTimestamp("updated");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        Integer age = resultSet.getInt("age");
        int bankCount = resultSet.getInt("bankCount");
        Client client = new Client();
        client.setId(id);
        client.setCreated(new Date(created.getTime()));
        client.setUpdated(new Date(updated.getTime()));
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setAge(age);
        return new ClientResultSet(client, bankCount);
    }

    private Client convertResultSetToSimpleClient(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Timestamp created = resultSet.getTimestamp("created");
        Timestamp updated = resultSet.getTimestamp("updated");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        Integer age = resultSet.getInt("age");
        Client client = new Client();
        client.setId(id);
        client.setCreated(new Date(created.getTime()));
        client.setUpdated(new Date(updated.getTime()));
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setAge(age);
        return client;
    }

    private static class ClientResultSet {

        private final Client client;
        private final int bankCount;

        private ClientResultSet(Client client, int bankCount) {
            this.client = client;
            this.bankCount = bankCount;
        }

        public Client getClient() {
            return client;
        }

        public int getBankCount() {
            return bankCount;
        }
    }
}