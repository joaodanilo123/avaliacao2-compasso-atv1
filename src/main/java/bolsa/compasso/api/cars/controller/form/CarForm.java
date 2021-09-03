package bolsa.compasso.api.cars.controller.form;

import bolsa.compasso.api.cars.model.Car;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CarForm {

    @NotNull
    @NotEmpty
    private String model;
    @NotNull
    @NotEmpty
    private String brand;
    @NotNull
    private int year;

    private String color;
    private BigDecimal price;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Car convert(){
        return new Car(model, brand, color, price, year);
    }
}
