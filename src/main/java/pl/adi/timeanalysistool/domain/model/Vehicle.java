package pl.adi.timeanalysistool.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Vehicle entity contains information about Vehicle in TestPlan (id, typ, kennNumber, vinNumber, etc.)
 * Vehicle Typ - example: Caddy 5, T6, T7, ID.BUZZ
 * Kenn Number - example: 3520221235181
 * Vin Number - example: ABCDEF000123415
 *
 * @since 1.0
 * @author Adrian Prętkowski
 */

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Vehicle implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String vehicleTyp;
    private String kennNumber;
    private String vinNumber;

    //ToDo: PR-Numbers implementation
    //private List<String> prNumbers = new ArrayList<>();

    /**
     * Mapping with ECUs.
     *
     * CascadeType.ALL - when Vehicle is removed, delete all mapped ecus.
     * FetchType.EAGER - Collections are lazy-loaded by default.
     *                   Single Ecu has few fields (id, name, counters, functions...)
     *                   In this case FetchType.EAGER is needed - need to load single Ecu with all fields.
     *                   FetchType.LAZY - loading fields on-demand - not recommended in this case,
     * JsonManagedReference - protection against infinity recursion during creating JSON response in RESTAPI
     *                        (Bi-directional relationship)
     *
     * @see Ecu
     */
    @OneToMany(mappedBy = "vehicle", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Map<String, Ecu> ecuMap = new LinkedHashMap<>();

    /**
     * Mapping with TestPlan - OneToOne.
     *
     * JsonBackReference - protection against infinity recursion during creating JSON response in RESTAPI.
     *
     * @see TestPlan
     */
    @OneToOne(mappedBy = "vehicle")
    @JsonBackReference
    private TestPlan testPlan;
}
