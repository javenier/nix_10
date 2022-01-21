package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.facade.SizeFacade;
import ua.com.alevel.persistence.entity.item.attributes.Size;
import ua.com.alevel.service.SizeService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.link.SneakerSizeLinkRequestDto;
import ua.com.alevel.view.dto.size.SizeRequestDto;
import ua.com.alevel.view.dto.size.SizeResponseDto;
import ua.com.alevel.view.dto.webrequest.PageAndSizeData;
import ua.com.alevel.view.dto.webrequest.PageData;
import ua.com.alevel.view.dto.webrequest.SortData;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SizeFacadeImpl implements SizeFacade {

    private final SizeService sizeService;

    public SizeFacadeImpl(SizeService sizeService) {
        this.sizeService = sizeService;
    }

    @Override
    public void create(SizeRequestDto sizeRequestDto) {
        Size size = new Size();
        size.setSize(sizeRequestDto.getSize());
        sizeService.create(size);
    }

    @Override
    public void update(SizeRequestDto sizeRequestDto) {
        Size size = sizeService.findById(sizeRequestDto.getId());
        if(size != null) {
            size.setSize(sizeRequestDto.getSize());
            sizeService.update(size);
        }
    }

    @Override
    public void delete(Long id) {
        sizeService.delete(id);
    }

    @Override
    public SizeResponseDto findById(Long id) {
        return new SizeResponseDto(sizeService.findById(id));
    }

    @Override
    public PageData<SizeResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setOrder(sortData.getOrder());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());

        DataTableResponse<Size> dataTableResponse = sizeService.findAll(dataTableRequest);
        List<SizeResponseDto> sizes = dataTableResponse.getItems().
                stream().
                map(SizeResponseDto::new).
                collect(Collectors.toList());

        PageData<SizeResponseDto> pageData = new PageData<>();
        pageData.setItems(sizes);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());
        return pageData;
    }

    @Override
    public void link(SneakerSizeLinkRequestDto dto) {
        Long sneakerId = dto.getSneakerId();
        Long sizeId = dto.getSizeId();
        sizeService.link(sneakerId, sizeId);
    }

    @Override
    public void unlink(SneakerSizeLinkRequestDto dto) {
        Long sneakerId = dto.getSneakerId();
        Long sizeId = dto.getSizeId();
        sizeService.unlink(sneakerId, sizeId);
    }

    @Override
    public PageData<SizeResponseDto> findAllBySneakerId(WebRequest request, Long sneakerId) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setOrder(sortData.getOrder());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());

        DataTableResponse<Size> dataTableResponse = sizeService.findAllBySneakerId(dataTableRequest, sneakerId);
        List<SizeResponseDto> sizes = dataTableResponse.getItems().
                stream().
                map(SizeResponseDto::new).
                collect(Collectors.toList());

        PageData<SizeResponseDto> pageData = new PageData<>();
        pageData.setItems(sizes);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());
        return pageData;
    }
}
