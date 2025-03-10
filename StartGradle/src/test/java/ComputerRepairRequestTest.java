import model.ComputerRepairRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComputerRepairRequestTest {

    @Test
    @DisplayName("Test Owner Name")
    public void testGetOwnerName() {
        ComputerRepairRequest request = new ComputerRepairRequest(1, "John Doe", "123 Street", "072222", "Asus", "13/10/2020", "Broken display");
        assertEquals("John Doe", request.getOwnerName(), "Owner name should match");
    }

    @Test
    @DisplayName("Simple Equality Test")
    public void testEquality() {
        assertEquals(2, 2, "Numbers should be equal");
    }
}
