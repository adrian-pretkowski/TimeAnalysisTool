package pl.adi.timeanalysistool;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.adi.timeanalysistool.domain.AppUser;
import pl.adi.timeanalysistool.domain.Role;
import pl.adi.timeanalysistool.extractfromlog.ExtractFromLog;
import pl.adi.timeanalysistool.service.FunctionService;
import pl.adi.timeanalysistool.service.TestPlanService;
import pl.adi.timeanalysistool.service.UserService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TimeAnalysisToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeAnalysisToolApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedMethods("GET", "POST", "DELETE")
                        .exposedHeaders("access_token", "refresh_token", "error_message");
            }
        };
    }

 //Uncomment when DB is restarted, and you want to fill the DB.
    /*
    @Bean
    CommandLineRunner run(UserService userService, TestPlanService testPlanService, FunctionService functionService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new AppUser(
                    null,
                    "John",
                    "Travolta",
                    "john",
                    "1234",
                    "john@travolta.com",
                    new ArrayList<>()));
            userService.saveUser(new AppUser(
                    null,
                    "Will",
                    "Smith",
                    "will",
                    "1234",
                    "will@smith.com",
                    new ArrayList<>()));
            userService.saveUser(new AppUser(
                    null,
                    "Jim",
                    "Carry",
                    "jim",
                    "1234", "jim@carry.com",
                    new ArrayList<>()));
            userService.saveUser(new AppUser(
                    null,
                    "Arnold",
                    "Schwarzenegger",
                    "arnold",
                    "1234",
                    "arnold@schwarzenegger.com",
                    new ArrayList<>()));

            userService.addRoleToUser("john", "ROLE_USER");
            userService.addRoleToUser("john", "ROLE_MANAGER");
            userService.addRoleToUser("will", "ROLE_MANAGER");
            userService.addRoleToUser("jim", "ROLE_ADMIN");
            userService.addRoleToUser("arnold", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("arnold", "ROLE_ADMIN");
            userService.addRoleToUser("arnold", "ROLE_USER");

            //Testing TestPlanFunctionality
            ExtractFromLog extract = new ExtractFromLog();
//            File file = new File("D:\\Projekty\\TimeAnalysisTool\\src\\main\\resources\\testlogs\\timeMeasurement_WV1ZZZSKZNX035307_TP_ML5.txt");

            List<URL> resourceURLs = new ArrayList<>();
            resourceURLs.add(getClass().getClassLoader().getResource("./testlogs/timeMeasurement_WV1ZZZSKZNX035307_TP_ML5.txt"));
            resourceURLs.add(getClass().getClassLoader().getResource("./testlogs/timeMeasurement_WV1ZZZSKZNX035329_TP_ML5.txt"));
            resourceURLs.add(getClass().getClassLoader().getResource("./testlogs/timeMeasurement_WV1ZZZSKZNX035336_TP_ML5.txt"));
            resourceURLs.add(getClass().getClassLoader().getResource("./testlogs/timeMeasurement_WV1ZZZSKZNX035341_TP_ML5.txt"));
            resourceURLs.add(getClass().getClassLoader().getResource("./testlogs/timeMeasurement_WV2ZZZSKZNX035316_TP_ML5.txt"));

            for (URL resourceURL : resourceURLs) {
//                testPlanService.saveTestPlan(extract.extractLog(resourceURL.getPath(), "IBN3", "Caddy5"));
//                testPlanService.saveTestPlan(extract.extractLog(resourceURL.getPath(), "IBN1", "ID.BUZZ"));
//                testPlanService.saveTestPlan(extract.extractLog(resourceURL.getPath(), "IBN3", "ID.BUZZ"));
                testPlanService.saveTestPlan(extract.extractLog(resourceURL.getPath(), "IBN3", "Caddy 5"));
                testPlanService.saveTestPlan(extract.extractLog(resourceURL.getPath(), "IBN1", "Caddy 5"));
                testPlanService.saveTestPlan(extract.extractLog(resourceURL.getPath(), "IBN3", "Test Vehicle"));
            }
        };
    }
 */
}

