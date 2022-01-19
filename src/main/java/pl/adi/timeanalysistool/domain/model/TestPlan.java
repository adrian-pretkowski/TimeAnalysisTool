package pl.adi.timeanalysistool.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class TestPlan implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String testLocation;
    private String testDate;
    private Double testDuration;

    //ToDo: releaseVersion implementation
//    private String releaseVersion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id")
    @JsonManagedReference
    private Vehicle vehicle;
}
