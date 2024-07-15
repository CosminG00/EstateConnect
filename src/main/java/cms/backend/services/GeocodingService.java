package cms.backend.services;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GeocodingService {
    @Value("${google.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate;

    private String parseAddressFromResponse(String responseBody) {
        JSONObject jsonObject = new JSONObject(responseBody);
        if ("OK".equals(jsonObject.getString("status"))) {
            return jsonObject.getJSONArray("results")
                .getJSONObject(0)
                .getString("formatted_address");
        } else {
            throw new RuntimeException("No address found for the given coordinates.");
        }
    }
    private double[] parseCoordsFromResponse(String responseBody) {
        JSONObject jsonObject = new JSONObject(responseBody);
        if ("OK".equals(jsonObject.getString("status"))) {
            JSONObject location = jsonObject.getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONObject("geometry")
                    .getJSONObject("location");
            double lat = location.getDouble("lat");
            double lng = location.getDouble("lng");
            return new double[]{lat, lng};
        } else {
            throw new RuntimeException("No coordinates found for the given address.");
        }
    }
    public String getAddressByCoords(double latitude, double longitude) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&key=" + apiKey;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return parseAddressFromResponse(response.getBody());
            } else {
                throw new RuntimeException("Failed to fetch address: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred while fetching address", e);
        }
    }

    public double[] getCoordsByAddress(String address) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + apiKey;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return parseCoordsFromResponse(response.getBody());
            } else {
                throw new RuntimeException("Failed to fetch coordinates: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred while fetching coordinates", e);
        }
    }
}
