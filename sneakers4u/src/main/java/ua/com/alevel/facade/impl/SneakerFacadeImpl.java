package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.facade.SneakerFacade;
import ua.com.alevel.persistence.entity.item.Sneaker;
import ua.com.alevel.persistence.entity.item.attributes.Size;
import ua.com.alevel.service.ModelService;
import ua.com.alevel.service.SizeService;
import ua.com.alevel.service.SneakerService;
import ua.com.alevel.util.MoneyConverterUtil;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.cart.CartItemRequestDto;
import ua.com.alevel.view.dto.sneaker.SneakerRequestDto;
import ua.com.alevel.view.dto.sneaker.SneakerResponseDto;
import ua.com.alevel.view.dto.webrequest.PageAndSizeData;
import ua.com.alevel.view.dto.webrequest.PageData;
import ua.com.alevel.view.dto.webrequest.SortData;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SneakerFacadeImpl implements SneakerFacade {

    private final SneakerService sneakerService;
    private final ModelService modelService;
    private final SizeService sizeService;

    public SneakerFacadeImpl(SneakerService sneakerService, ModelService modelService, SizeService sizeService) {
        this.sneakerService = sneakerService;
        this.modelService = modelService;
        this.sizeService = sizeService;
    }

    @Override
    public void create(SneakerRequestDto sneakerRequestDto) {
        Sneaker sneaker = new Sneaker();
        sneaker.setDescription(sneakerRequestDto.getDescription());
        sneaker.setQuantity(sneakerRequestDto.getQuantity());
        sneaker.setPrice(MoneyConverterUtil.stringToPenny(sneakerRequestDto.getPrice()));
        sneaker.setImageUrl(sneakerRequestDto.getImageUrl());
        sneaker.setModel(modelService.findById(sneakerRequestDto.getModelId()));
        sneaker.setSneakerGender(sneakerRequestDto.getSneakerGender());
        sneaker.setVersionOfModel(sneakerRequestDto.getVersionOfModel());
        sneakerService.create(sneaker);
    }

    @Override
    public void update(SneakerRequestDto sneakerRequestDto) {
        Sneaker sneaker = sneakerService.findById(sneakerRequestDto.getId());
        if(sneaker != null) {
            sneaker.setDescription(sneakerRequestDto.getDescription());
            sneaker.setQuantity(sneakerRequestDto.getQuantity());
            sneaker.setPrice(MoneyConverterUtil.stringToPenny(sneakerRequestDto.getPrice()));
            sneaker.setImageUrl(sneakerRequestDto.getImageUrl());
            sneaker.setSneakerGender(sneakerRequestDto.getSneakerGender());
            sneaker.setVersionOfModel(sneakerRequestDto.getVersionOfModel());
            sneakerService.update(sneaker);
        }
    }

    @Override
    public void delete(Long id) {
        sneakerService.delete(id);
    }

    @Override
    public SneakerResponseDto findById(Long id) {
        return new SneakerResponseDto(sneakerService.findById(id));
    }

    @Override
    public PageData<SneakerResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request, null);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setOrder(sortData.getOrder());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());

        DataTableResponse<Sneaker> dataTableResponse = sneakerService.findAll(dataTableRequest);
        List<SneakerResponseDto> sneakers = dataTableResponse.getItems().
                stream().
                map(SneakerResponseDto::new).
                collect(Collectors.toList());

        PageData<SneakerResponseDto> pageData = new PageData<>();
        pageData.setItems(sneakers);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());
        return pageData;
    }

    @Override
    public PageData<SneakerResponseDto> findAllByBrandId(WebRequest request, Long brandId) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request, null);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setOrder(sortData.getOrder());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());

        DataTableResponse<Sneaker> dataTableResponse = sneakerService.findAllByBrandId(dataTableRequest, brandId);
        List<SneakerResponseDto> sneakers = dataTableResponse.getItems().
                stream().
                map(SneakerResponseDto::new).
                collect(Collectors.toList());

        PageData<SneakerResponseDto> pageData = new PageData<>();
        pageData.setItems(sneakers);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());
        return pageData;
    }

    @Override
    public PageData<SneakerResponseDto> findAllByModelId(WebRequest request, Long modelId) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request, null);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setOrder(sortData.getOrder());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());

        DataTableResponse<Sneaker> dataTableResponse = sneakerService.findAllByModelId(dataTableRequest, modelId);
        List<SneakerResponseDto> sneakers = dataTableResponse.getItems().
                stream().
                map(SneakerResponseDto::new).
                collect(Collectors.toList());

        PageData<SneakerResponseDto> pageData = new PageData<>();
        pageData.setItems(sneakers);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());
        return pageData;
    }

    @Override
    public PageData<SneakerResponseDto> findAllByOrderId(WebRequest request, Long orderId) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request, null);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setOrder(sortData.getOrder());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());

        DataTableResponse<Sneaker> dataTableResponse = sneakerService.findAllByOrderId(dataTableRequest, orderId);
        List<SneakerResponseDto> sneakers = dataTableResponse.getItems().
                stream().
                map(SneakerResponseDto::new).
                collect(Collectors.toList());

        PageData<SneakerResponseDto> pageData = new PageData<>();
        pageData.setItems(sneakers);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());
        return pageData;
    }

    @Override
    public PageData<SneakerResponseDto> findAllByGender(WebRequest request, String gender) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request, null);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setOrder(sortData.getOrder());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());

        DataTableResponse<Sneaker> dataTableResponse = sneakerService.findAllByGender(dataTableRequest, gender);
        List<SneakerResponseDto> sneakers = dataTableResponse.getItems().
                stream().
                map(SneakerResponseDto::new).
                collect(Collectors.toList());

        PageData<SneakerResponseDto> pageData = new PageData<>();
        pageData.setItems(sneakers);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());
        return pageData;
    }

    @Override
    public void addToCart(CartItemRequestDto cartItemRequestDto) {
        Sneaker sneaker = sneakerService.findById(cartItemRequestDto.getSneakerId());
        Size size = sizeService.findById(cartItemRequestDto.getSizeId());
        sneakerService.addToCart(sneaker, size);
    }
}
