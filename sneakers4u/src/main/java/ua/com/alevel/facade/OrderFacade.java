package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.persistence.entity.order.Order;
import ua.com.alevel.view.dto.order.OrderRequestDto;
import ua.com.alevel.view.dto.order.OrderResponseDto;
import ua.com.alevel.view.dto.webrequest.PageData;

public interface OrderFacade extends BaseFacade<OrderRequestDto, OrderResponseDto> {

    PageData<OrderResponseDto> findAllByClientId(WebRequest request, Long clientId);

    Order findEntityById(Long id);
}
