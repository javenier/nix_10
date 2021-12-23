package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.dto.PageAndSizeData;
import ua.com.alevel.dto.PageData;
import ua.com.alevel.dto.SortData;
import ua.com.alevel.dto.client.ClientRequestDto;
import ua.com.alevel.dto.client.ClientResponseDto;
import ua.com.alevel.entity.Client;
import ua.com.alevel.facade.ClientFacade;
import ua.com.alevel.service.BankService;
import ua.com.alevel.service.ClientService;
import ua.com.alevel.util.WebRequestUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientFacadeImpl implements ClientFacade {

    private final BankService bankService;
    private final ClientService clientService;

    public ClientFacadeImpl(BankService bankService, ClientService clientService) {
        this.bankService = bankService;
        this.clientService = clientService;
    }

    @Override
    public void create(ClientRequestDto clientRequestDto) {
        Client client = new Client();
        client.setFirstName(clientRequestDto.getFirstName());
        client.setLastName(clientRequestDto.getLastName());
        client.setAge(clientRequestDto.getAge());
        clientService.create(client, clientRequestDto.getBankId());
    }

    @Override
    public void update(ClientRequestDto clientRequestDto) {
        Client client = clientService.findById(clientRequestDto.getId());
        if (client != null) {
            client.setAge(clientRequestDto.getAge());
            client.setLastName(clientRequestDto.getLastName());
            client.setFirstName(clientRequestDto.getFirstName());
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
                peek(clientResponseDto -> clientResponseDto.setBankCount((Integer) dataTableResponse.
                        getOtherParam().get(clientResponseDto.getId()))).
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
    public PageData<ClientResponseDto> findAllByBankId(WebRequest request, Long bankId) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setOrder(sortData.getOrder());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());

        DataTableResponse<Client> dataTableResponse = clientService.findAllByBankId(dataTableRequest, bankId);
        List<ClientResponseDto> clients = dataTableResponse.getItems().
                stream().
                map(ClientResponseDto::new).
                peek(clientResponseDto -> clientResponseDto.setBankCount((Integer) dataTableResponse.
                        getOtherParam().get(clientResponseDto.getId()))).
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

    private List<ClientResponseDto> generateDtoListByEntities(List<Client> list) {
        return list.stream()
                .map(ClientResponseDto::new)
                .collect(Collectors.toList());
    }
}