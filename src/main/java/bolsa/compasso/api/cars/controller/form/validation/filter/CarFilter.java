package bolsa.compasso.api.cars.controller.form.validation.filter;

import bolsa.compasso.api.cars.controller.form.validation.filter.options.CarFilterOptionsEnum;
import bolsa.compasso.api.cars.controller.form.validation.filter.options.CarOrderOptionsEnum;
import bolsa.compasso.api.cars.model.Car;
import bolsa.compasso.api.cars.repository.CarRepository;

import java.util.Comparator;
import java.util.List;

public class CarFilter {

    private final CarRepository carRepository;

    private CarOrderOptionsEnum orderBy;
    private CarFilterOptionsEnum filterBy;
    private String filterValue;

    public CarFilter(String orderBy, String filterBy, String filterValue, CarRepository carRepository){

        this.carRepository = carRepository;

        try {
            if(orderBy != null){
                this.orderBy = CarOrderOptionsEnum.valueOf(orderBy.toUpperCase());
            } else this.orderBy= CarOrderOptionsEnum.ANY;

            if(filterBy != null && filterValue != null){
                this.filterBy = CarFilterOptionsEnum.valueOf(filterBy.toUpperCase());
                this.filterValue = filterValue;
            } else this.filterBy= CarFilterOptionsEnum.ANY;

        } catch (IllegalArgumentException exception){
            this.orderBy= CarOrderOptionsEnum.ANY;
            this.filterBy= CarFilterOptionsEnum.ANY;
        }
    }

    public List<Car> getResults(){
        return this.order(this.filter());
    }

    private List<Car> filter(){

        return switch (filterBy) {
            case ANY -> carRepository.findAll();
            case BRAND -> carRepository.findAllByBrand(filterValue);
            case COLOR -> carRepository.findAllByColor(filterValue);
            case MODEL -> carRepository.findAllByModel(filterValue);
        };
    }

    private List<Car> order(List<Car> filteredCarList){
        switch (orderBy) {
            case ANY:
                break;
            case YEAR:
                filteredCarList.sort((car, otherCar) -> Integer.compare(otherCar.getYear(), car.getYear()));
                break;
            case MODEL:
                filteredCarList.sort(Comparator.comparing(Car::getModel));
                break;
            case PRICE:
                filteredCarList.sort(Comparator.comparing(Car::getPrice));
                break;
        }

        return filteredCarList;
    }

}
