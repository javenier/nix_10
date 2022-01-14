package ua.com.alevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import ua.com.alevel.config.jpa.JpaConfig;

@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class})
public class FinanceApplication {

    private final JpaConfig jpaConfig;

    public FinanceApplication(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(FinanceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initDb() {
        jpaConfig.connect();
    }
}
