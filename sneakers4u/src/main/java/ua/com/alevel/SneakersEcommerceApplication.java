package ua.com.alevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.com.alevel.persistence.entity.item.Sneaker;
import ua.com.alevel.persistence.entity.order.Order;
import ua.com.alevel.persistence.entity.user.Admin;
import ua.com.alevel.persistence.repository.AdminRepository;
import ua.com.alevel.persistence.repository.ClientRepository;
import ua.com.alevel.persistence.repository.OrderRepository;
import ua.com.alevel.persistence.repository.SneakerRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class SneakersEcommerceApplication {

    private final BCryptPasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;

    public SneakersEcommerceApplication(BCryptPasswordEncoder passwordEncoder, AdminRepository adminRepository) {
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
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
}
