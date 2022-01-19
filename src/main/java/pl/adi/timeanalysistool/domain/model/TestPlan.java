package pl.adi.timeanalysistool.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class TestPlan {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String testLocation;
    private String testDate;
    private Double testDuration;

    //ToDo: releaseVersion implementation
//    private String releaseVersion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
