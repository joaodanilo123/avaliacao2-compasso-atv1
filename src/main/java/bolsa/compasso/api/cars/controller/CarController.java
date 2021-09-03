package bolsa.compasso.api.cars.controller;

import bolsa.compasso.api.cars.controller.form.CarForm;
import bolsa.compasso.api.cars.controller.form.validation.filter.CarFilter;
import bolsa.compasso.api.cars.model.Car;
import bolsa.compasso.api.cars.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/build")
    @Transactional
    public ResponseEntity<?> registerNewCars(){
        Car c1 = new Car("New Fiesta", "Ford", "white", new BigDecimal("30000"), 2018);
        Car c2 = new Car("Camaro", "Chevrolet", "yellow", new BigDecimal("150000"), 2014);
        Car c3 = new Car("Civic", "Honda", "white", new BigDecimal("90000"), 2018);
        Car c4 = new Car("F-150", "Ford", "grey", new BigDecimal("150000"), 2020);
        Car c5 = new Car("NSX", "Honda", "white", new BigDecimal("90000"), 1993);
        Car c6 = new Car("Cruze", "Chevrolet", "grey", new BigDecimal("90000"), 2020);

        carRepository.save(c1);
        carRepository.save(c2);
        carRepository.save(c3);
        carRepository.save(c4);
        carRepository.save(c5);
        carRepository.save(c6);

        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CarForm form, UriComponentsBuilder uriBuilder){
        Car car = form.convert();
        carRepository.save(car);

        URI uri = uriBuilder.path("/cars").buildAndExpand().toUri();
        return ResponseEntity.created(uri).body(car);
    }


    /*@GetMapping
    public ResponseEntity<?> showAll(){
        List<Car> cars = carRepository.findAll();
        if (cars.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cars);
    }*/

    @GetMapping
    public ResponseEntity<?> completeFilter(
            @RequestParam(name="filter", required = false) String filter,
            @RequestParam(name="value", required = false) String value,
            @RequestParam(name="order", required = false) String order
    ){
        List<Car> cars = new CarFilter(order, filter, value, carRepository).getResults();

        if(cars.size() == 0) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(cars);

    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<?> filterByBrand(@PathVariable String brand){
        List<Car> cars = carRepository.findAllByBrand(brand);
        if (cars.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cars);
    }

    @GetMapping("/model/{model}")
    public ResponseEntity<?> filterByModel(@PathVariable String model){
        List<Car> cars = carRepository.findAllByModel(model);
        if (cars.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cars);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<?> filterByColor(@PathVariable String color){
        List<Car> cars = carRepository.findAllByColor(color);
        if (cars.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cars);
    }

    @GetMapping("/order/{field}")
    public ResponseEntity<?> orderBy(@PathVariable String field) {
        List<Car> cars = carRepository.findAll();
        if (cars.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        if (field.equals("year")) {
            Collections.sort(cars, (car, otherCar) -> {
                if (car.getYear() > otherCar.getYear()) return -1;
                if (car.getYear() < otherCar.getYear()) return 1;
                return 0;
            });
        }

        if (field.equals("price")) {
            Collections.sort(cars, new Comparator<Car>() {
                public int compare(Car car, Car otherCar) {
                    return car.getPrice().compareTo(otherCar.getPrice());
                }
            });
        }

        if (field.equals("model")) {
            Collections.sort(cars, new Comparator<Car>() {
                public int compare(Car car, Car otherCar) {
                    return car.getModel().compareTo(otherCar.getModel());
                }
            });
        }

        return ResponseEntity.ok(cars);

    }
}
