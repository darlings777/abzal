import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Path to your JSON test case file
        String filePath = "testcase1.json"; // Make sure this file path is correct
        
        // Parse the JSON file
        JSONParser parser = new JSONParser();
        
        try (FileReader reader = new FileReader(filePath)) {
            // Read JSON file
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            
            // Get the keys part (number of roots and minimum required roots)
            JSONObject keys = (JSONObject) jsonObject.get("keys");
            long n = (long) keys.get("n");
            long k = (long) keys.get("k");
            System.out.println("n: " + n + ", k: " + k);
            
            // Extract roots and decode Y values based on base
            Map<Long, Long> points = new HashMap<>();
            
            for (int i = 1; i <= n; i++) {
                JSONObject point = (JSONObject) jsonObject.get(String.valueOf(i));
                if (point != null) {
                    String baseStr = (String) point.get("base");
                    String valueStr = (String) point.get("value");
                    
                    int base = Integer.parseInt(baseStr);
                    BigInteger value = new BigInteger(valueStr, base);  // Decode Y value based on base
                    points.put((long) i, value.longValue());
                } else {
                    System.out.println("Point " + i + " is missing from JSON");
                }
            }
            
            // Example output of points
            for (Map.Entry<Long, Long> entry : points.entrySet()) {
                System.out.println("x: " + entry.getKey() + ", y: " + entry.getValue());
            }
            
            // Now you can proceed to calculate the constant term 'c' using interpolation or other methods
            // (Interpolation, Gaussian Elimination, Lagrange Interpolation, etc.)
            
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error in decoding base or value");
            e.printStackTrace();
        }
    }
}
