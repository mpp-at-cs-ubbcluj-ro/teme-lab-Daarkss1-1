import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarsDBRepository implements CarRepository {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {}", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {
        logger.traceEntry("Finding cars by manufacturer: {}", manufacturerN);
        List<Car> cars = new ArrayList<>();
        try (Connection con = dbUtils.getConnection()) {
            String sql = "SELECT * FROM cars WHERE manufacturer = ?";
            try (PreparedStatement preStmt = con.prepareStatement(sql)) {
                preStmt.setString(1, manufacturerN);
                try (ResultSet result = preStmt.executeQuery()) {
                    while (result.next()) {
                        int id = result.getInt("id");
                        String manufacturer = result.getString("manufacturer");
                        String model = result.getString("model");
                        int year = result.getInt("year");

                        Car car = new Car(manufacturer, model, year);
                        car.setId(id);
                        cars.add(car);
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error("Error finding cars by manufacturer", ex);
        }
        logger.traceExit(cars);
        return cars;
    }

    @Override
    public List<Car> findBetweenYears(int min, int max) {
        logger.traceEntry("Finding cars between years {} and {}", min, max);
        List<Car> cars = new ArrayList<>();
        try (Connection con = dbUtils.getConnection()) {
            String sql = "SELECT * FROM cars WHERE year BETWEEN ? AND ?";
            try (PreparedStatement preStmt = con.prepareStatement(sql)) {
                preStmt.setInt(1, min);
                preStmt.setInt(2, max);
                try (ResultSet result = preStmt.executeQuery()) {
                    while (result.next()) {
                        int id = result.getInt("id");
                        String manufacturer = result.getString("manufacturer");
                        String model = result.getString("model");
                        int year = result.getInt("year");

                        Car car = new Car(manufacturer, model, year);
                        car.setId(id);
                        cars.add(car);
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error("Error finding cars between years", ex);
        }
        logger.traceExit(cars);
        return cars;
    }

    @Override
    public void add(Car elem) {
        logger.traceEntry("Adding car: {}", elem);
        try (Connection con = dbUtils.getConnection()) {
            String sql = "INSERT INTO cars (manufacturer, model, year) VALUES (?, ?, ?)";
            try (PreparedStatement preStmt = con.prepareStatement(sql)) {
                preStmt.setString(1, elem.getManufacturer());
                preStmt.setString(2, elem.getModel());
                preStmt.setInt(3, elem.getYear());
                int result = preStmt.executeUpdate();
                logger.trace("Added {} instance(s)", result);
            }
        } catch (SQLException ex) {
            logger.error("Error adding car", ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer id, Car elem) {
        logger.traceEntry("Updating car with ID {}: {}", id, elem);
        try (Connection con = dbUtils.getConnection()) {
            String sql = "UPDATE cars SET manufacturer = ?, model = ?, year = ? WHERE id = ?";
            try (PreparedStatement preStmt = con.prepareStatement(sql)) {
                preStmt.setString(1, elem.getManufacturer());
                preStmt.setString(2, elem.getModel());
                preStmt.setInt(3, elem.getYear());
                preStmt.setInt(4, id);
                int result = preStmt.executeUpdate();
                logger.trace("Updated {} instance(s)", result);
            }
        } catch (SQLException ex) {
            logger.error("Error updating car", ex);
        }
        logger.traceExit();
    }

    @Override
    public Iterable<Car> findAll() {
        logger.traceEntry("Finding all cars");
        List<Car> cars = new ArrayList<>();
        try (Connection con = dbUtils.getConnection()) {
            String sql = "SELECT * FROM cars";
            try (PreparedStatement preStmt = con.prepareStatement(sql)) {
                try (ResultSet result = preStmt.executeQuery()) {
                    while (result.next()) {
                        int id = result.getInt("id");
                        String manufacturer = result.getString("manufacturer");
                        String model = result.getString("model");
                        int year = result.getInt("year");

                        Car car = new Car(manufacturer, model, year);
                        car.setId(id);
                        cars.add(car);
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error("Error finding all cars", ex);
        }
        logger.traceExit(cars);
        return cars;
    }
}
