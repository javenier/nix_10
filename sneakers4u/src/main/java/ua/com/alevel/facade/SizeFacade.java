package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.view.dto.link.SneakerSizeLinkRequestDto;
import ua.com.alevel.view.dto.size.SizeRequestDto;
import ua.com.alevel.view.dto.size.SizeResponseDto;
import ua.com.alevel.view.dto.webrequest.PageData;

public interface SizeFacade extends BaseFacade<SizeRequestDto, SizeResponseDto> {

    void link(SneakerSizeLinkRequestDto dto);

    void unlink(SneakerSizeLinkRequestDto dto);

    PageData<SizeResponseDto> findAllBySneakerId(WebRequest request, Long sneakerId);
}
