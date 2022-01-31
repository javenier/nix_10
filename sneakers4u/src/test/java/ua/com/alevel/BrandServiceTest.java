package ua.com.alevel;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.alevel.persistence.entity.item.attributes.Brand;
import ua.com.alevel.persistence.repository.BrandRepository;
import ua.com.alevel.service.BrandService;
import ua.com.alevel.service.impl.BrandServiceImpl;


@SpringBootTest(classes = SneakersEcommerceApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BrandServiceTest {

    @Autowired
    private BrandService brandService;

    @Autowired
    private BrandRepository brandRepository;

    @Test
    @Order(1)
    public void shouldBeCreateBrand() {
        Long currentCount = brandRepository.count();
        Brand brand = new Brand();
        brand.setImageUrl("");
        brand.setName("test");
        brandService.create(brand);
        Assertions.assertEquals(currentCount + 1, brandRepository.count());
    }

    @Test
    @Order(2)
    public void shouldBeUpdateBrand() {
        Brand brand = brandRepository.findByName("test");
        brand.setName("test_updated");
        brandService.update(brand);
        Assertions.assertEquals("test_updated", brandRepository.getById(brand.getId()).getName());
    }

    @Test
    @Order(3)
    public void shouldBeDeleteBrandById() {
        Long currentCount = brandRepository.count();
        Brand brand = brandRepository.findByName("test_updated");
        brandService.delete(brand.getId());
        Assertions.assertEquals(currentCount - 1, brandRepository.count());
    }
}
