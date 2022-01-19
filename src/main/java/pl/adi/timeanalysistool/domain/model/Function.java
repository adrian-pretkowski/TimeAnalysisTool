package pl.adi.timeanalysistool.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Function {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int functionId;
    private String functionName;
    private String startTime;
    private Double duration;

    @ManyToOne
    @JoinColumn(name = "ecu_id")
    @JsonBackReference
    private Ecu ecu;
}
