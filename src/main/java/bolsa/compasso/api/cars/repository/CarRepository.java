package bolsa.compasso.api.cars.repository;

import bolsa.compasso.api.cars.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByModel(String model);

    List<Car> findAllByBrand(String brand);

    List<Car> findAllByColor(String color);

}
