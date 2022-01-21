package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.facade.ModelFacade;
import ua.com.alevel.persistence.entity.item.attributes.Model;
import ua.com.alevel.service.BrandService;
import ua.com.alevel.service.ModelService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.model.ModelRequestDto;
import ua.com.alevel.view.dto.model.ModelResponseDto;
import ua.com.alevel.view.dto.webrequest.PageAndSizeData;
import ua.com.alevel.view.dto.webrequest.PageData;
import ua.com.alevel.view.dto.webrequest.SortData;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelFacadeImpl implements ModelFacade {

    private final ModelService modelService;
    private final BrandService brandService;

    public ModelFacadeImpl(ModelService modelService, BrandService brandService) {
        this.modelService = modelService;
        this.brandService = brandService;
    }

    @Override
    public void create(ModelRequestDto modelRequestDto) {
        Model model = new Model();
        model.setName(modelRequestDto.getName());
        model.setBrand(brandService.findById(modelRequestDto.getBrandId()));
        modelService.create(model);
    }

    @Override
    public void update(ModelRequestDto modelRequestDto) {
        Model model = modelService.findById(modelRequestDto.getId());
        if(model != null) {
            model.setName(modelRequestDto.getName());
            modelService.update(model);
        }
    }

    @Override
    public void delete(Long id) {
        modelService.delete(id);
    }

    @Override
    public ModelResponseDto findById(Long id) {
        return new ModelResponseDto(modelService.findById(id));
    }

    @Override
    public PageData<ModelResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setOrder(sortData.getOrder());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());

        DataTableResponse<Model> dataTableResponse = modelService.findAll(dataTableRequest);
        List<ModelResponseDto> models = dataTableResponse.getItems().
                stream().
                map(ModelResponseDto::new).
                collect(Collectors.toList());

        PageData<ModelResponseDto> pageData = new PageData<>();
        pageData.setItems(models);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());
        return pageData;
    }

    @Override
    public PageData<ModelResponseDto> findAllByBrandId(WebRequest request, Long brandId) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setOrder(sortData.getOrder());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());

        DataTableResponse<Model> dataTableResponse = modelService.findAllByBrandId(dataTableRequest, brandId);
        List<ModelResponseDto> models = dataTableResponse.getItems().
                stream().
                map(ModelResponseDto::new).
                collect(Collectors.toList());

        PageData<ModelResponseDto> pageData = new PageData<>();
        pageData.setItems(models);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());
        return pageData;
    }
}
