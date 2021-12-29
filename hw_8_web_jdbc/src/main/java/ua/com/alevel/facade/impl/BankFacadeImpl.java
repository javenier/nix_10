package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.dto.LinkRequestDto;
import ua.com.alevel.dto.PageAndSizeData;
import ua.com.alevel.dto.PageData;
import ua.com.alevel.dto.SortData;
import ua.com.alevel.dto.bank.BankRequestDto;
import ua.com.alevel.dto.bank.BankResponseDto;
import ua.com.alevel.dto.client.ClientResponseDto;
import ua.com.alevel.entity.Bank;
import ua.com.alevel.entity.Client;
import ua.com.alevel.facade.BankFacade;
import ua.com.alevel.service.BankService;
import ua.com.alevel.util.WebRequestUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankFacadeImpl implements BankFacade {

    private final BankService bankService;

    public BankFacadeImpl(BankService bankService) {
        this.bankService = bankService;
    }

    @Override
    public PageData<BankResponseDto> findAllByClientId(WebRequest request, Long clientId) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setOrder(sortData.getOrder());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());

        DataTableResponse<Bank> dataTableResponse = bankService.findAllByClientId(dataTableRequest, clientId);
        List<BankResponseDto> banks = dataTableResponse.getItems().
                stream().
                map(BankResponseDto::new).
                peek(bankResponseDto -> bankResponseDto.setClientCount((Integer) dataTableResponse.
                        getOtherParam().get(bankResponseDto.getId()))).
                collect(Collectors.toList());

        PageData<BankResponseDto> pageData = new PageData<>();
        pageData.setItems(banks);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());
        return pageData;
    }

    @Override
    public void link(LinkRequestDto dto) {
        Long bankId = dto.getBankId();
        Long clientId = dto.getClientId();
        bankService.link(bankId, clientId);
    }

    @Override
    public void unlink(LinkRequestDto dto) {
        Long bankId = dto.getBankId();
        Long clientId = dto.getClientId();
        bankService.unlink(bankId, clientId);
    }

    @Override
    public void create(BankRequestDto bankRequestDto) {
        Bank bank = new Bank();
        bank.setBankType(bankRequestDto.getBankType());
        bank.setName(bankRequestDto.getName());
        bank.setYearOfFoundation(bankRequestDto.getYearOfFoundation());
        bankService.create(bank);
    }

    @Override
    public void update(BankRequestDto bankRequestDto) {
        Bank bank = bankService.findById(bankRequestDto.getId());
        if(bank != null) {
            bank.setYearOfFoundation(bankRequestDto.getYearOfFoundation());
            bank.setBankType(bankRequestDto.getBankType());
            bank.setName(bankRequestDto.getName());
            bankService.update(bank);
        }
    }

    @Override
    public void delete(Long id) {
        bankService.delete(id);
    }

    @Override
    public BankResponseDto findById(Long id) {
        return new BankResponseDto(bankService.findById(id));
    }

    @Override
    public PageData<BankResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setOrder(sortData.getOrder());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());

        DataTableResponse<Bank> dataTableResponse = bankService.findAll(dataTableRequest);
        List<BankResponseDto> banks = dataTableResponse.getItems().
                stream().
                map(BankResponseDto::new).
                peek(bankResponseDto -> bankResponseDto.setClientCount((Integer) dataTableResponse.
                        getOtherParam().get(bankResponseDto.getId()))).
                collect(Collectors.toList());

        PageData<BankResponseDto> pageData = new PageData<>();
        pageData.setItems(banks);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());
        return pageData;
    }

    private List<BankResponseDto> generateDtoListByEntities(List<Bank> list) {
        return list.stream()
                .map(BankResponseDto::new)
                .collect(Collectors.toList());
    }
}