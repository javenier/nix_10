package ua.com.alevel.service.impl;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import ua.com.alevel.elastic.document.SneakerIndex;
import ua.com.alevel.service.ElasticSneakerSearchService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ElasticSneakerSearchServiceImpl implements ElasticSneakerSearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    public ElasticSneakerSearchServiceImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public List<String> searchBySneakerName(String query) {
        QueryBuilder queryBuilder = QueryBuilders.
                wildcardQuery("sneakerName", "*" + query.toLowerCase() + "*");
        Query searchQuery = new NativeSearchQueryBuilder().
                withFilter(queryBuilder).
                withPageable(PageRequest.of(0, 5)).
                build();
        SearchHits<SneakerIndex> searchSuggestions = elasticsearchOperations.
                search(searchQuery, SneakerIndex.class, IndexCoordinates.of("sneaker_index"));
        final List<String> suggestions = new ArrayList<>();
        searchSuggestions.
                getSearchHits().
                forEach(searchHit -> suggestions.add(searchHit.getContent().getSneakerName()));
        return suggestions;
    }
}