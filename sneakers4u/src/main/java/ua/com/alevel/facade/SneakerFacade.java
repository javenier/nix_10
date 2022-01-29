package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.view.dto.cart.CartItemRequestDto;
import ua.com.alevel.view.dto.sneaker.SneakerRequestDto;
import ua.com.alevel.view.dto.sneaker.SneakerResponseDto;
import ua.com.alevel.view.dto.webrequest.PageData;

import java.util.List;

public interface SneakerFacade extends BaseFacade<SneakerRequestDto, SneakerResponseDto> {

    PageData<SneakerResponseDto> findAllByBrandId(WebRequest request, Long brandId);
    PageData<SneakerResponseDto> findAllByModelId(WebRequest request, Long modelId);
    PageData<SneakerResponseDto> findAllByOrderId(WebRequest request, Long orderId);
    PageData<SneakerResponseDto> findAllByGender(WebRequest request, String gender);
    PageData<SneakerResponseDto> findAllBySearchQuery(WebRequest request, String query);
    void addToCart(CartItemRequestDto cartItemRequestDto);
    List<String> searchBySneakerName(String query);
}
