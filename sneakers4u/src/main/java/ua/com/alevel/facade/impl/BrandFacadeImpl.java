package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.facade.BrandFacade;
import ua.com.alevel.persistence.entity.item.attributes.Brand;
import ua.com.alevel.service.BrandService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.brand.BrandRequestDto;
import ua.com.alevel.view.dto.brand.BrandResponseDto;
import ua.com.alevel.view.dto.webrequest.PageAndSizeData;
import ua.com.alevel.view.dto.webrequest.PageData;
import ua.com.alevel.view.dto.webrequest.SortData;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandFacadeImpl implements BrandFacade {

    private final BrandService brandService;

    public BrandFacadeImpl(BrandService brandService) {
        this.brandService = brandService;
    }

    @Override
    public void create(BrandRequestDto brandRequestDto) {
        Brand brand = new Brand();
        brand.setName(brandRequestDto.getName());
        brand.setImageUrl(brandRequestDto.getImageUrl());
        brandService.create(brand);
    }

    @Override
    public void update(BrandRequestDto brandRequestDto) {
        Brand brand = brandService.findById(brandRequestDto.getId());
        if (brand != null) {
            brand.setName(brandRequestDto.getName());
            brand.setImageUrl(brandRequestDto.getImageUrl());
            brandService.update(brand);
        }
    }

    @Override
    public void delete(Long id) {
        brandService.delete(id);
    }

    @Override
    public BrandResponseDto findById(Long id) {
        return new BrandResponseDto(brandService.findById(id));
    }

    @Override
    public PageData<BrandResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request, null);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setOrder(sortData.getOrder());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());

        DataTableResponse<Brand> dataTableResponse = brandService.findAll(dataTableRequest);
        List<BrandResponseDto> brands = dataTableResponse.getItems().
                stream().
                map(BrandResponseDto::new).
                collect(Collectors.toList());

        PageData<BrandResponseDto> pageData = new PageData<>();
        pageData.setItems(brands);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());
        return pageData;
    }
}
