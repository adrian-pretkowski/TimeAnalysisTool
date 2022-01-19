package pl.adi.timeanalysistool.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Ecu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String ecuName;
    private Double totalEcuDuration;

    private int connectionOpenCounter;
    private int connectionReleaseCounter;
    private int assemblyCheckCounter;

    @OneToMany(mappedBy = "ecu", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Collection<Function> functionList = new ArrayList<>();

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public Ecu(String ecuName) {
        this.ecuName = ecuName;
    }

    public void addFunction(Function function) {
        if (function != null) {
            functionList.add(function);
        }
    }

    public void addConnectionOpenCounter() {
        this.connectionOpenCounter += 1;
    }

    public void addConnectionReleaseCounter() {
        this.connectionReleaseCounter += 1;
    }

    public void addAssemblyCheckCounter() {
        this.assemblyCheckCounter += 1;
    }
}
