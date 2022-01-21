package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.view.dto.sneaker.SneakerRequestDto;
import ua.com.alevel.view.dto.sneaker.SneakerResponseDto;
import ua.com.alevel.view.dto.webrequest.PageData;

public interface SneakerFacade extends BaseFacade<SneakerRequestDto, SneakerResponseDto> {

    PageData<SneakerResponseDto> findAllByBrandId(WebRequest request, Long brandId);
    PageData<SneakerResponseDto> findAllByModelId(WebRequest request, Long modelId);
    PageData<SneakerResponseDto> findAllByOrderId(WebRequest request, Long orderId);
    PageData<SneakerResponseDto> findAllByGender(WebRequest request, String gender);
}
