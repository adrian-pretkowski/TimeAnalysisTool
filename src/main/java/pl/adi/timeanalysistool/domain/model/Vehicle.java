package pl.adi.timeanalysistool.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Vehicle {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String vehicleTyp;
    private String kennNumber;
    private String vinNumber;

    //ToDo: PR-Numbers implementation
    //private List<String> prNumbers = new ArrayList<>();

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Map<String, Ecu> ecuMap = new LinkedHashMap<>();

    @OneToOne(mappedBy = "vehicle")
    private TestPlan testPlan;
}
