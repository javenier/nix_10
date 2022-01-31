package ua.com.alevel;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.alevel.persistence.entity.item.attributes.Model;
import ua.com.alevel.persistence.repository.ModelRepository;
import ua.com.alevel.service.ModelService;

@SpringBootTest(classes = SneakersEcommerceApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModelServiceTest {

    @Autowired
    private ModelService modelService;

    @Autowired
    private ModelRepository modelRepository;

    @Test
    @Order(1)
    public void shouldBeCreateModel() {
        Long currentCount = modelRepository.count();
        Model model = new Model();
        model.setName("test");
        modelService.create(model);
        Assertions.assertEquals(currentCount + 1, modelRepository.count());
    }

    @Test
    @Order(2)
    public void shouldBeUpdateModel() {
        Model model = modelRepository.findByName("test");
        model.setName("test_updated");
        modelService.update(model);
        Assertions.assertEquals("test_updated", modelRepository.getById(model.getId()).getName());
    }

    @Test
    @Order(3)
    public void shouldBeDeleteModelById() {
        Long currentCount = modelRepository.count();
        Model model = modelRepository.findByName("test_updated");
        modelService.delete(model.getId());
        Assertions.assertEquals(currentCount - 1, modelRepository.count());
    }
}
