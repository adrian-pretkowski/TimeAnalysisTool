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

/**
 * Ecu entity contains information about Ecu (name, connections, and most important - Functions List).
 *
 * @since 1.0
 * @author Adrian Prętkowski
 */
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

    /**
     * Mapping with Function.
     *
     * CascadeType.ALL - when Ecu is removed, delete all mapped functions.
     * FetchType.EAGER - Collections are lazy-loaded by default.
     *                   Single Function has few fields (name, id, etc.)
     *                   In this case FetchType.EAGER is needed - need to load single Function with all fields.
     *                   FetchType.LAZY - loading fields on-demand - not recommended in this case.
     * JsonManagedReference - protection against infinity recursion during creating JSON response in RESTAPI
     *                        (Bi-directional relationship)
     * @see Function
     */
    @OneToMany(mappedBy = "ecu", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Collection<Function> functionList = new ArrayList<>();

    /**
     * Mapping with Vehicle.
     *
     * JsonBackReference - protection against infinity recursion during creating JSON response in RESTAPI.
     * @see Vehicle
     */
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
