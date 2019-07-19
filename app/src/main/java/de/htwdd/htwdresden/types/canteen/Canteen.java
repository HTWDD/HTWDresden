package de.htwdd.htwdresden.types.canteen;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Beschreibung einer Kantine
 *
 * @author Kay Förster
 */
public class Canteen extends RealmObject {
    private int id;
    private String city;
    private String name;
    private String address;
    private RealmList<Float> coordinates;
    private Boolean isFav;

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public RealmList<Float> getCoordinates() {
        return coordinates;
    }

    public Boolean getFav() {
        return isFav;
    }
    public void setIsFav(Boolean isFav) {
        this.isFav = isFav;
    }
}
