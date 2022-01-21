package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.facade.ClientFacade;
import ua.com.alevel.persistence.entity.item.attributes.Model;
import ua.com.alevel.persistence.entity.order.Order;
import ua.com.alevel.persistence.entity.user.Client;
import ua.com.alevel.persistence.type.Gender;
import ua.com.alevel.service.ClientService;
import ua.com.alevel.util.MoneyConverterUtil;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.model.ModelResponseDto;
import ua.com.alevel.view.dto.user.ClientRequestDto;
import ua.com.alevel.view.dto.user.ClientResponseDto;
import ua.com.alevel.view.dto.webrequest.PageAndSizeData;
import ua.com.alevel.view.dto.webrequest.PageData;
import ua.com.alevel.view.dto.webrequest.SortData;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientFacadeImpl implements ClientFacade {

    private final ClientService clientService;

    public ClientFacadeImpl(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void create(ClientRequestDto clientRequestDto) {
        Client client = new Client();
        client.setEmail(clientRequestDto.getEmail());
        client.setPassword(clientRequestDto.getPassword());
        client.setFirstName(clientRequestDto.getFirstName());
        client.setLastName(clientRequestDto.getLastName());
        client.setAge(clientRequestDto.getAge());
        client.setGender(clientRequestDto.getGender().equals("MALE") ? Gender.MALE : Gender.FEMALE);
        clientService.create(client);
    }

    @Override
    public void update(ClientRequestDto clientRequestDto) {
        Client client = clientService.findById(clientRequestDto.getId());
        if(client != null) {
            client.setAge(clientRequestDto.getAge());
            client.setFirstName(clientRequestDto.getFirstName());
            client.setLastName(clientRequestDto.getLastName());
            client.setGender(clientRequestDto.getGender().equals("MALE") ? Gender.MALE : Gender.FEMALE);
            clientService.update(client);
        }
    }

    @Override
    public void delete(Long id) {
        clientService.delete(id);
    }

    @Override
    public ClientResponseDto findById(Long id) {
        return new ClientResponseDto(clientService.findById(id));
    }

    @Override
    public PageData<ClientResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setOrder(sortData.getOrder());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());

        DataTableResponse<Client> dataTableResponse = clientService.findAll(dataTableRequest);
        List<ClientResponseDto> clients = dataTableResponse.getItems().
                stream().
                map(ClientResponseDto::new).
                collect(Collectors.toList());

        PageData<ClientResponseDto> pageData = new PageData<>();
        pageData.setItems(clients);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());
        return pageData;
    }

    @Override
    public ClientResponseDto findByEmail(String email) {
        return new ClientResponseDto(clientService.findByEmail(email));
    }

    @Override
    public void disableClient(Long id) {
        clientService.disableClient(id);
    }

    @Override
    public void enableClient(Long id) {
        clientService.enableClient(id);
    }
}
