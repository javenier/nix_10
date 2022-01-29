package ua.com.alevel.cron;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.com.alevel.elastic.document.SneakerIndex;
import ua.com.alevel.elastic.repository.SneakerIndexRepository;
import ua.com.alevel.persistence.repository.custom.SneakerCustomRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SyncElasticCronService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final SneakerIndexRepository sneakerIndexRepository;
    private final SneakerCustomRepository sneakerCustomRepository;

    public SyncElasticCronService(
            ElasticsearchOperations elasticsearchOperations,
            SneakerIndexRepository sneakerIndexRepository,
            SneakerCustomRepository sneakerCustomRepository) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.sneakerIndexRepository = sneakerIndexRepository;
        this.sneakerCustomRepository = sneakerCustomRepository;
    }

    @Scheduled(fixedDelay = 60000)
    public void syncToElastic() {
        elasticsearchOperations.indexOps(SneakerIndex.class).refresh();
        sneakerIndexRepository.deleteAll();
        sneakerIndexRepository.saveAll(prepareDataset());
    }

    private Collection<SneakerIndex> prepareDataset() {
        List<Object[]> sneakerNames = sneakerCustomRepository.findAllSneakerNames();
        List<SneakerIndex> sneakerIndices = new ArrayList<>();
        sneakerNames.forEach(sneaker -> {
            SneakerIndex sneakerIndex = new SneakerIndex();
            sneakerIndex.setSneakerName(sneaker[0] + " " + sneaker[1] + " " + sneaker[2]);
            sneakerIndices.add(sneakerIndex);
        });
        return sneakerIndices;
    }
}
