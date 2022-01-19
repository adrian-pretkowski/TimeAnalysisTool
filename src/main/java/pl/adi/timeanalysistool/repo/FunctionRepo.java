package pl.adi.timeanalysistool.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.adi.timeanalysistool.domain.model.Function;

public interface FunctionRepo extends JpaRepository<Function, Long> {
    Function findByFunctionName(String functionName);

    void deleteById(Long id);
}
