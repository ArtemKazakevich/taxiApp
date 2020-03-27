package by.citytaxi.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeolocationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double latitude;
    private double longitude;

    public GeolocationUser(double v, double v1) {
    }
}
