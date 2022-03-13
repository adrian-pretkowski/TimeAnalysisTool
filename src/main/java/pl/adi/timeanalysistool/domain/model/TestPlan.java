package pl.adi.timeanalysistool.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * TestPlan entity contains information about a single TestPlan.
 * testLocation - current test location (ex. IBN1, IBN3).
 * testDate - date of UPS ECOS test.
 * testDuration - duration of the UPS ECOS test.
 * releaseVersion - version of realese UPS-TestProgram.
 * Vehicle - current Vehicle in TestPlan
 *
 * @since 1.0
 * @author Adrian Prętkowski
 */

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class TestPlan implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testLocation;
    private String testDate;
    private Double testDuration;

    //ToDo: releaseVersion implementation
//    private String releaseVersion;

    /**
     * Mapping OneToOne with Vehicle.
     * CascadeType.ALL - removing Vehicle with TestPlan.
     * JsonManagedReference - protection against infinity recursion during creating JSON response in RESTAPI.
     *
     * @see Vehicle
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id")
    @JsonManagedReference
    private Vehicle vehicle;
}
