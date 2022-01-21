package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.view.dto.model.ModelRequestDto;
import ua.com.alevel.view.dto.model.ModelResponseDto;
import ua.com.alevel.view.dto.webrequest.PageData;

public interface ModelFacade extends BaseFacade<ModelRequestDto, ModelResponseDto> {

    PageData<ModelResponseDto> findAllByBrandId(WebRequest request, Long brandId);
}
