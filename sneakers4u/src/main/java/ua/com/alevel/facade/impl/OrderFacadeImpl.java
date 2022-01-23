package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.facade.OrderFacade;
import ua.com.alevel.persistence.entity.item.Sneaker;
import ua.com.alevel.persistence.entity.order.Order;
import ua.com.alevel.service.OrderService;
import ua.com.alevel.service.SneakerService;
import ua.com.alevel.util.MoneyConverterUtil;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.order.OrderRequestDto;
import ua.com.alevel.view.dto.order.OrderResponseDto;
import ua.com.alevel.view.dto.webrequest.PageAndSizeData;
import ua.com.alevel.view.dto.webrequest.PageData;
import ua.com.alevel.view.dto.webrequest.SortData;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderFacadeImpl implements OrderFacade {

    private final OrderService orderService;
    private final SneakerService sneakerService;

    public OrderFacadeImpl(OrderService orderService, SneakerService sneakerService) {
        this.orderService = orderService;
        this.sneakerService = sneakerService;
    }

    @Override
    public void create(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setAddress(orderRequestDto.getAddress());
        order.setComment(orderRequestDto.getComment());
        order.setPostOffice(orderRequestDto.getPostOffice());
        order.setTotalPrice(MoneyConverterUtil.stringToPenny(orderRequestDto.getTotalPrice()));
        orderService.create(order);
    }

    @Override
    public void update(OrderRequestDto orderRequestDto) {
        Order order = orderService.findById(orderRequestDto.getId());
        if(order != null) {
            order.setAddress(orderRequestDto.getAddress());
            order.setComment(orderRequestDto.getComment());
            order.setPostOffice(orderRequestDto.getPostOffice());
            order.setTotalPrice(MoneyConverterUtil.stringToPenny(orderRequestDto.getTotalPrice()));
            orderService.update(order);
        }
    }

    @Override
    public void delete(Long id) {
        orderService.delete(id);
    }

    @Override
    public OrderResponseDto findById(Long id) {
        return new OrderResponseDto(orderService.findById(id));
    }

    @Override
    public PageData<OrderResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setOrder(sortData.getOrder());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());

        DataTableResponse<Order> dataTableResponse = orderService.findAll(dataTableRequest);
        List<OrderResponseDto> orders = dataTableResponse.getItems().
                stream().
                map(OrderResponseDto::new).
                collect(Collectors.toList());

        PageData<OrderResponseDto> pageData = new PageData<>();
        pageData.setItems(orders);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());
        return pageData;
    }

    @Override
    public PageData<OrderResponseDto> findAllByClientId(WebRequest request, Long clientId) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setOrder(sortData.getOrder());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());

        DataTableResponse<Order> dataTableResponse = orderService.findAllByClientId(dataTableRequest, clientId);
        List<OrderResponseDto> orders = dataTableResponse.getItems().
                stream().
                map(OrderResponseDto::new).
                collect(Collectors.toList());

        PageData<OrderResponseDto> pageData = new PageData<>();
        pageData.setItems(orders);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());
        return pageData;
    }

    @Override
    public Order findEntityById(Long id) {
        return orderService.findById(id);
    }

    @Override
    public void confirmOrder(OrderRequestDto requestDto) {
        Order order = orderService.findById(requestDto.getId());
        if(order != null) {
            order.setAddress(requestDto.getAddress());
            order.setComment(requestDto.getComment());
            order.setPostOffice(requestDto.getPostOffice());
            order.setFinished(true);
            for(Sneaker sneaker : order.getSneakers()) {
                sneaker.setQuantity(sneaker.getQuantity() - 1);
                sneakerService.update(sneaker);
            }
            orderService.update(order);
        }
    }

    @Override
    public void removeItemFromCart(Long sneakerId, Long orderId) {
        Sneaker sneaker = sneakerService.findById(sneakerId);
        Order order = orderService.findById(orderId);
        orderService.removeItemFromCart(sneaker, order);
    }
}
