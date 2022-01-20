package pl.adi.timeanalysistool.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Function entity contains information about Function (id, functionId, name, etc.)
 *
 * functionId - used for parallelism.
 *
 * @since 1.0
 * @author Adrian Prętkowski
 */
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Function implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int functionId;
    private String functionName;
    private String startTime;
    private Double duration;

    /**
     * Mapping with Ecu.
     *
     * JsonBackReference - protection against infinity recursion during creating JSON response in RESTAPI.
     *
     * @see Ecu
     */
    @ManyToOne
    @JoinColumn(name = "ecu_id")
    @JsonBackReference
    private Ecu ecu;
}
