import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
public class CarRentalApp {
    private CarService carService;
    private JComboBox<String> makeComboBox;
    private JComboBox<String> modelComboBox;
    private JComboBox<String> yearComboBox;
    private JComboBox<String> priceComboBox;
    private DefaultListModel<Car> bookedListModel;
    private DefaultListModel<Car> unbookedListModel;
    private JList<Car> bookedCarList;
    private JList<Car> unbookedCarList;

    public CarRentalApp() {
        carService = new CarService();

        JFrame frame = new JFrame("Car Rental Service");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);

        // Set background color
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 248, 255)); // Alice Blue
        mainPanel.setLayout(new BorderLayout());
        frame.add(mainPanel);

        // Filter panel with light background
        JPanel filterPanel = new JPanel(new GridLayout(2, 2));
        filterPanel.setBackground(new Color(173, 216, 230)); // Light Blue
        makeComboBox = new JComboBox<>(new String[]{"Any", "Toyota", "Honda", "Ford", "Chevrolet", "Nissan"});
        modelComboBox = new JComboBox<>(new String[]{"Any", "Camry", "Civic", "Focus", "Malibu", "Altima"});
        yearComboBox = new JComboBox<>(new String[]{"Any", "2017", "2018", "2019", "2020", "2021", "2022"});
        priceComboBox = new JComboBox<>(new String[]{"Any", "40", "45", "50", "55", "60"});

        filterPanel.add(new JLabel("Make:"));
        filterPanel.add(makeComboBox);
        filterPanel.add(new JLabel("Model:"));
        filterPanel.add(modelComboBox);
        filterPanel.add(new JLabel("Year:"));
        filterPanel.add(yearComboBox);
        filterPanel.add(new JLabel("Price (<=):"));
        filterPanel.add(priceComboBox);

        mainPanel.add(filterPanel, BorderLayout.NORTH);

        bookedListModel = new DefaultListModel<>();
        unbookedListModel = new DefaultListModel<>();
        bookedCarList = new JList<>(bookedListModel);
        unbookedCarList = new JList<>(unbookedListModel);
        bookedCarList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        unbookedCarList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Create list panels with titles and light background
        JPanel listPanel = new JPanel(new GridLayout(1, 2));
        listPanel.setBackground(new Color(240, 248, 255)); // Alice Blue

        // Booked Cars Panel
        JPanel bookedPanel = new JPanel(new BorderLayout());
        bookedPanel.setBackground(new Color(224, 255, 255)); // Light Cyan
        JLabel bookedLabel = new JLabel("Booked Cars", SwingConstants.CENTER);
        bookedLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Increase font size
        bookedLabel.setForeground(new Color(0, 102, 204)); // Darker Blue
        bookedPanel.add(bookedLabel, BorderLayout.NORTH);
        bookedCarList.setBackground(new Color(255, 228, 225)); // Misty Rose for booked cars
        bookedPanel.add(new JScrollPane(bookedCarList), BorderLayout.CENTER);
        listPanel.add(bookedPanel);

        // Available Cars Panel
        JPanel unbookedPanel = new JPanel(new BorderLayout());
        unbookedPanel.setBackground(new Color(224, 255, 255)); // Light Cyan
        JLabel unbookedLabel = new JLabel("Available Cars", SwingConstants.CENTER);
        unbookedLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Increase font size
        unbookedLabel.setForeground(new Color(0, 102, 204)); // Darker Blue
        unbookedPanel.add(unbookedLabel, BorderLayout.NORTH);
        unbookedCarList.setBackground(new Color(240, 255, 240)); // Honeydew for available cars
        unbookedPanel.add(new JScrollPane(unbookedCarList), BorderLayout.CENTER);
        listPanel.add(unbookedPanel);

        mainPanel.add(listPanel, BorderLayout.CENTER);

        // Button panel with custom styles
        JPanel buttonPanel = new JPanel();
        JButton searchButton = new JButton("Search Cars");
        JButton bookButton = new JButton("Book Car");
        JButton unbookButton = new JButton("Unbook Car");

        // Button styles
        styleButton(searchButton);
        styleButton(bookButton);
        styleButton(unbookButton);

        buttonPanel.add(searchButton);
        buttonPanel.add(bookButton);
        buttonPanel.add(unbookButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Search button action
        searchButton.addActionListener(e -> updateCarLists());

        // Book button action
        bookButton.addActionListener(e -> {
            Car selectedCar = unbookedCarList.getSelectedValue();
            if (selectedCar != null) {
                String bookingMessage = carService.bookCar(selectedCar);
                JOptionPane.showMessageDialog(frame, bookingMessage);
                updateCarLists(); // Refresh lists to reflect booking
            } else {
                JOptionPane.showMessageDialog(frame, "Please select an unbooked car to book.");
            }
        });

        // Unbook button action
        unbookButton.addActionListener(e -> {
            Car selectedCar = bookedCarList.getSelectedValue();
            if (selectedCar != null) {
                String unbookingMessage = carService.unbookCar(selectedCar);
                JOptionPane.showMessageDialog(frame, unbookingMessage);
                updateCarLists(); // Refresh lists to reflect unbooking
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a booked car to unbook.");
            }
        });

        // Add selection listeners to handle exclusive selection
        bookedCarList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                unbookedCarList.clearSelection();
            }
        });

        unbookedCarList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                bookedCarList.clearSelection();
            }
        });

        frame.setVisible(true);
        updateCarLists(); // Initial load of car lists
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(135, 206, 250)); // Light Sky Blue
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 40));
    }

    private void updateCarLists() {
        bookedListModel.clear();
        unbookedListModel.clear();

        // Fetch all cars
        List<Car> allCars = carService.getAllCars();

        // Filter cars based on selected criteria
        String selectedMake = makeComboBox.getSelectedItem().toString();
        String selectedModel = modelComboBox.getSelectedItem().toString();
        String selectedYearStr = yearComboBox.getSelectedItem().toString();
        String selectedPriceStr = priceComboBox.getSelectedItem().toString();

        Integer selectedYear = selectedYearStr.equals("Any") ? null : Integer.parseInt(selectedYearStr);
        Double selectedPrice = selectedPriceStr.equals("Any") ? null : Double.parseDouble(selectedPriceStr);

        for (Car car : allCars) {
            boolean matchesFilter = true;

            // Check against filters
            if (!selectedMake.equals("Any") && !car.getMake().equals(selectedMake)) {
                matchesFilter = false;
            }
            if (!selectedModel.equals("Any") && !car.getModel().equals(selectedModel)) {
                matchesFilter = false;
            }
            if (selectedYear != null && car.getYear() != selectedYear) {
                matchesFilter = false;
            }
            if (selectedPrice != null && car.getPricePerDay() > selectedPrice) {
                matchesFilter = false;
            }

            // Categorize based on booking status
            if (matchesFilter) {
                if (car.isBooked()) {
                    bookedListModel.addElement(car);
                } else {
                    unbookedListModel.addElement(car);
                }
            }
        }
    }

    public static void main(String[] args) {
        new CarRentalApp();
    }
}
