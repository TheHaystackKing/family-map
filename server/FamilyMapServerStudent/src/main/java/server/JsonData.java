package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class and all of its subclasses store json data from the json folder in way that allows easy access when generating family trees and locations.
 * The class also includes functions to randomly select values from the class.
 */
public class JsonData {

    public class MaleName {
        public String[] data;

        public MaleName(String[] data) {
            this.data = data;
        }
    }

    public class FemaleName {
        public String[] data;

        public FemaleName(String[] data) {
            this.data = data;
        }
    }

    public class Surname {
        public String[] data;

        public Surname(String[] data) {
            this.data = data;
        }
    }

    public class Location {
        public LocationData[] data;

        public class LocationData {
            public String country;
            public String city;
            public float latitude;
            public float longitude;
        }

        public Location(LocationData[] data) {
            this.data = data;
        }
    }

    private static MaleName maleNames;
    private static FemaleName femaleNames;
    private static Surname surnames;
    private static Location locations;

    public JsonData() {
        try {
            Gson gson = new Gson();
            Reader mNameReader = new FileReader("json/mnames.json");
            maleNames = gson.fromJson(mNameReader, MaleName.class);

            Reader fNameReader = new FileReader("json/fnames.json");
            femaleNames = gson.fromJson(fNameReader, FemaleName.class);

            Reader sNameReader = new FileReader("json/snames.json");
            surnames = gson.fromJson(sNameReader, Surname.class);

            Reader lNameReader = new FileReader("json/locations.json");
            locations = gson.fromJson(lNameReader, Location.class);
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public String randomMaleName() {
        int index = new Random().nextInt(maleNames.data.length);
        return maleNames.data[index];
    }

    public String randomFemaleName() {
        int index = new Random().nextInt(femaleNames.data.length);
        return femaleNames.data[index];
    }

    public String randomSurname() {
        int index = new Random().nextInt(surnames.data.length);
        return surnames.data[index];
    }

    public Location.LocationData randomLocation() {
        int index = new Random().nextInt(locations.data.length);
        return locations.data[index];
    }

}
