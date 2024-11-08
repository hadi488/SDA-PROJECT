import java.util.ArrayList;
import java.util.List;

public class CarService {
    private List<Car> cars;

    public CarService() {
        cars = new ArrayList<>();
        // Sample cars (20 total)
        cars.add(new Car("Toyota", "Camry", 2020, 50));
        cars.add(new Car("Honda", "Civic", 2019, 45));
        cars.add(new Car("Ford", "Focus", 2021, 55));
        cars.add(new Car("Chevrolet", "Malibu", 2018, 40));
        cars.add(new Car("Nissan", "Altima", 2017, 48));
        cars.add(new Car("Hyundai", "Sonata", 2020, 53));
        cars.add(new Car("Kia", "Optima", 2019, 47));
        cars.add(new Car("Subaru", "Legacy", 2021, 60));
        cars.add(new Car("Volkswagen", "Jetta", 2018, 42));
        cars.add(new Car("Mazda", "Mazda3", 2020, 54));
        cars.add(new Car("Chrysler", "300", 2019, 62));
        cars.add(new Car("Buick", "Regal", 2020, 55));
        cars.add(new Car("Dodge", "Charger", 2021, 66));
        cars.add(new Car("Toyota", "Corolla", 2022, 45));
        cars.add(new Car("Honda", "Accord", 2020, 58));
        cars.add(new Car("Ford", "Fusion", 2019, 50));
        cars.add(new Car("Chevrolet", "Impala", 2018, 49));
        cars.add(new Car("Nissan", "Sentra", 2021, 44));
        cars.add(new Car("Hyundai", "Elantra", 2022, 52));
        cars.add(new Car("Kia", "Forte", 2020, 48));
    }

    public List<Car> getAllCars() {
        return cars; // Added this method to return all cars
    }

    public List<Car> searchCars(String make, String model, Integer year, Double maxPrice) {
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if ((make == null || car.getMake().equalsIgnoreCase(make))
                    && (model == null || car.getModel().equalsIgnoreCase(model))
                    && (year == null || car.getYear() == year)
                    && (maxPrice == null || car.getPricePerDay() <= maxPrice)
                    && !car.isBooked()) {
                result.add(car);
            }
        }
        return result;
    }

    public String bookCar(Car car) {
        if (car.isBooked()) {
            return "The car is already booked!";
        } else {
            car.setBooked(true);
            return "You have successfully booked: " + car;
        }
    }

    public String unbookCar(Car car) {
        if (!car.isBooked()) {
            return "The car is not booked!";
        } else {
            car.setBooked(false);
            return "You have successfully unbooked: " + car;
        }
    }
}

