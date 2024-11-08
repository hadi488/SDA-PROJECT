public class Car {
    private String make;
    private String model;
    private int year;
    private double pricePerDay;
    private boolean booked;

    public Car(String make, String model, int year, double pricePerDay) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.pricePerDay = pricePerDay;
        this.booked = false;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    @Override
    public String toString() {
        return year + " " + make + " " + model + " - $" + pricePerDay + " per day" + (booked ? " (Booked)" : "");
    }
}

