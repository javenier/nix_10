package ua.com.alevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.com.alevel.elastic.document.SneakerIndex;
import ua.com.alevel.persistence.entity.user.Admin;
import ua.com.alevel.persistence.repository.AdminRepository;

import javax.annotation.PreDestroy;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class SneakersEcommerceApplication {

    private final BCryptPasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public SneakersEcommerceApplication(BCryptPasswordEncoder passwordEncoder, AdminRepository adminRepository, ElasticsearchOperations elasticsearchOperations) {
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public static void main(String[] args) {
        SpringApplication.run(SneakersEcommerceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void listen() {
        Admin admin = adminRepository.findByEmail("admin@mail.com");
        if (admin == null) {
            admin = new Admin();
            admin.setEmail("admin@mail.com");
            admin.setPassword(passwordEncoder.encode("rootroot"));
            adminRepository.save(admin);
        }
    }

    @PreDestroy
    public void resetElastic() {
        elasticsearchOperations.indexOps(SneakerIndex.class).delete();
    }
}
