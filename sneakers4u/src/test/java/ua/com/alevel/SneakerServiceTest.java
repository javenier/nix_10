package ua.com.alevel;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.alevel.persistence.entity.item.Sneaker;
import ua.com.alevel.persistence.repository.SneakerRepository;
import ua.com.alevel.service.SneakerService;

@SpringBootTest(classes = SneakersEcommerceApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SneakerServiceTest {

    @Autowired
    private SneakerRepository sneakerRepository;

    @Autowired
    private SneakerService sneakerService;

    @Test
    @Order(1)
    public void shouldBeCreateSneaker() {
        Long currentCount = sneakerRepository.count();
        Sneaker sneaker = new Sneaker();
        sneaker.setVersionOfModel("test");
        sneaker.setQuantity(1);
        sneaker.setPrice(0L);
        sneakerService.create(sneaker);
        Assertions.assertEquals(currentCount + 1, sneakerRepository.count());
    }

    @Test
    @Order(2)
    public void shouldBeUpdateSneaker() {
        Sneaker sneaker = sneakerRepository.findSneakerByVersionOfModel("test");
        sneaker.setVersionOfModel("test_updated");
        sneakerService.update(sneaker);
        Assertions.assertEquals("test_updated", sneakerRepository.getById(sneaker.getId()).getVersionOfModel());
    }

    @Test
    @Order(3)
    public void shouldBeDeleteSneakerById() {
        Long currentCount = sneakerRepository.count();
        Sneaker sneaker = sneakerRepository.findSneakerByVersionOfModel("test_updated");
        sneakerService.delete(sneaker.getId());
        Assertions.assertEquals(currentCount - 1, sneakerRepository.count());
    }
}
