package ua.com.alevel.service;

import java.util.List;

public interface ElasticSneakerSearchService {

    List<String> searchBySneakerName(String query);
}
