package ua.com.alevel.facade;

import ua.com.alevel.view.dto.RequestDto;
import ua.com.alevel.view.dto.ResponseDto;

import java.util.List;

public interface BaseFacade<REQ extends RequestDto, RES extends ResponseDto> {

    void create(REQ req);

    void update(REQ req);

    void delete(Long id);

    RES findById(Long id);

    List<RES> findAll();
}
