package com.example.familymap;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.Collection;

import model.Event;
import model.Person;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {

    private GoogleMap map;
    private LinearLayout eventBox;
    private ImageView eventIcon;
    private TextView eventText;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataCache dataCache = DataCache.getInstance();
        if(!dataCache.isInEventActivity()) {
            setHasOptionsMenu(true);
        }
        else {
            setHasOptionsMenu(false);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        View view = layoutInflater.inflate(R.layout.fragment_map, container, false);

        eventIcon = view.findViewById(R.id.eventImage);
        eventIcon.setImageResource(R.drawable.profile);

        eventText = view.findViewById(R.id.eventText);

        eventBox = view.findViewById(R.id.mapTextView);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(map != null) {
            map.clear();
            loadEventsWithFilters();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapLoadedCallback(this);
        loadEventsWithFilters();
        DataCache dataCache = DataCache.getInstance();
        if(dataCache.isInEventActivity()) {
            Event selectedEvent = DataCache.getInstance().getCurrentEvent();
            eventText.setText(getEventDetails(selectedEvent));
            if(dataCache.isDisplayStoryLines()) {
                generateStoryLines(selectedEvent.getPersonID());
            }
            if(dataCache.isDisplaySpouseLines()) {
                generateSpouseLine(selectedEvent.getPersonID(), selectedEvent);
            }
            if(dataCache.isDisplayFamilyLines()) {
                generateFamilyLines(selectedEvent.getPersonID(), selectedEvent);
            }
            LatLng eventLatLng = new LatLng(selectedEvent.getLatitude(), selectedEvent.getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLng(eventLatLng));
        }

    }

    @Override
    public void onMapLoaded() {

    }

    private void loadEventsWithFilters() {
        DataCache dataCache = DataCache.getInstance();
        if(dataCache.isDisplayFatherSide() && dataCache.isDisplayMaleEvents()) {
            ArrayList<Event> tempEvents = dataCache.getFatherMaleEvent();
            Event[] events = tempEvents.toArray(new Event[tempEvents.size()]);
            loadEvents(events);
        }
        if(dataCache.isDisplayMotherSide() && dataCache.isDisplayMaleEvents()) {
            ArrayList<Event> tempEvents = dataCache.getMotherMaleEvent();
            Event[] events = tempEvents.toArray(new Event[tempEvents.size()]);
            loadEvents(events);
        }
        if(dataCache.isDisplayFemaleEvents() && dataCache.isDisplayFatherSide()) {
            ArrayList<Event> tempEvent = dataCache.getFatherFemaleEvent();
            Event[] events = tempEvent.toArray(new Event[tempEvent.size()]);
            loadEvents(events);
        }
        if(dataCache.isDisplayFemaleEvents() && dataCache.isDisplayMotherSide()) {
            ArrayList<Event> tempEvents = dataCache.getMotherFemaleEvent();
            Event[] events = tempEvents.toArray(new Event[tempEvents.size()]);
            loadEvents(events);
        }
    }

    private void loadEvents(Event[] events) {
        for(int i =0; i < events.length; ++i) {
            Event event = events[i];
            LatLng coord = new LatLng(event.getLatitude(), event.getLongitude());
            DataCache dataCache = DataCache.getInstance();
            int index = dataCache.checkEventType(event.getEventType().toLowerCase());
            float googleColor = dataCache.getEventColor(index);
            /*float googleColor = 0;
            if(event.getEventType().equals("Birth")) {
                googleColor = BitmapDescriptorFactory.HUE_BLUE;
            }
            else if(event.getEventType().equals("Marriage")) {
                googleColor = BitmapDescriptorFactory.HUE_GREEN;
            }
            else if(event.getEventType().equals("Death")) {
                googleColor = BitmapDescriptorFactory.HUE_RED;
            }
            else {
                googleColor = BitmapDescriptorFactory.HUE_MAGENTA;
            }*/
            Marker marker = map.addMarker(new MarkerOptions().position(coord).icon(BitmapDescriptorFactory.defaultMarker(googleColor))
                    .title(event.getCity() + "," + event.getCountry()));
            marker.setTag(event);
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Event event = (Event) marker.getTag();
                    DataCache dataCache = DataCache.getInstance();
                    eventText.setText(getEventDetails(event));
                    eventBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dataCache.setCurrentPerson(dataCache.getPerson(event.getPersonID()));
                            Intent intent = PersonActivity.newIntent(getActivity());
                            startActivity(intent);
                        }
                    });
                    map.clear();
                    loadEventsWithFilters();
                    if(dataCache.isDisplayStoryLines()) {
                        generateStoryLines(event.getPersonID());
                    }
                    if(dataCache.isDisplaySpouseLines()) {
                        generateSpouseLine(event.getPersonID(), event);
                    }
                    if(dataCache.isDisplayFamilyLines()) {
                        generateFamilyLines(event.getPersonID(), event);
                    }
                    return true;
                }
            });
        }
    }

    private String getEventDetails(Event event) {
        DataCache dataCache = DataCache.getInstance();
        StringBuilder builder = new StringBuilder();
        Person person = dataCache.getPerson(event.getPersonID());
        if(person.getGender().equals("m")) {
            eventIcon.setImageResource(R.drawable.male);
        }
        else {
            eventIcon.setImageResource(R.drawable.female);
        }
        builder.append(person.getFirstName() + " " + person.getLastName() + "\n");
        builder.append(event.getEventType() + ": " + event.getCity() + ", "
                + event.getCountry() + " (" + event.getYear() + ")");
        return builder.toString();
    }

    private void generateStoryLines(String personID) {
        DataCache dataCache = DataCache.getInstance();
        ArrayList<Event> personEvents = dataCache.getEvents(personID);
        for(int i = 0; i < personEvents.size() - 1; ++i) {
            drawLine(personEvents.get(i), personEvents.get(i+1),0xff000000,12 );
        }
    }

    private void generateSpouseLine(String personID, Event event) {
        DataCache dataCache = DataCache.getInstance();
        Person person = dataCache.getPerson(personID);
        if(person.getSpouseID() != null) {
            ArrayList<Event> spouseEvents = dataCache.getEvents(person.getSpouseID());
            Event endEvent = getEarliestEvent(spouseEvents);
            if(endEvent != null) {
                int googleColor = 0xffF9A825;
                drawLine(event,endEvent,googleColor, 12);
            }
        }
    }

    private void generateFamilyLines(String PersonID, Event event) {
        DataCache dataCache = DataCache.getInstance();
        Person person = dataCache.getPerson(PersonID);
        int googleColor = 0xff81C784;
        if(person.getFatherID() != null) {
            Person father = dataCache.getPerson(person.getFatherID());
            if(dataCache.getEvents(father.getPersonID()).size() > 0) {
                Event endEvent = getEarliestEvent(dataCache.getEvents(father.getPersonID()));
                drawLine(event, endEvent, googleColor, 14);
                recursiveTreeGen(father, endEvent, dataCache, googleColor, 14 - 4);
            }
        }
        if(person.getMotherID() != null) {
            Person mother = dataCache.getPerson(person.getMotherID());
            if(dataCache.getEvents(mother.getPersonID()).size() > 0) {
                Event endEvent = getEarliestEvent(dataCache.getEvents(mother.getPersonID()));
                drawLine(event, endEvent, googleColor, 14);
                recursiveTreeGen(mother, endEvent, dataCache, googleColor, 14 - 4);
            }
        }
    }

    private void recursiveTreeGen(Person person, Event prevEvent,
                                  DataCache dataCache, int googleColor, float width) {
        if(person.getFatherID() != null) {
            Person father = dataCache.getPerson(person.getFatherID());
            if(dataCache.getEvents(father.getPersonID()).size() > 0) {
                Event endEvent = getEarliestEvent(dataCache.getEvents(father.getPersonID()));
                drawLine(prevEvent, endEvent, googleColor, width);
                recursiveTreeGen(father, endEvent, dataCache, googleColor, width - 4);
            }
        }
        if(person.getMotherID() != null) {
            Person mother = dataCache.getPerson(person.getMotherID());
            if(dataCache.getEvents(mother.getPersonID()).size() > 0) {
                Event endEvent = getEarliestEvent(dataCache.getEvents(mother.getPersonID()));
                drawLine(prevEvent, endEvent, googleColor, width);
                recursiveTreeGen(mother, endEvent, dataCache, googleColor, width - 4);
            }
        }
    }

    private Event getEarliestEvent(ArrayList<Event> events) {
        for(int i = 0; i < events.size(); ++i) {
            if(events.get(i).getEventType().equals("Birth")) {
                return events.get(i);
            }
        }
        if(events.size() == 0) {
            return null;
        }
        Event earliestEvent = events.get(0);
        for(int i = 0; i < events.size(); ++i) {
            if(earliestEvent.getYear() > events.get(i).getYear()) {
                earliestEvent = events.get(i);
            }
        }
        return earliestEvent;
    }

    private void drawLine(Event startEvent, Event endEvent, int googleColor, float width) {
        LatLng startPoint = new LatLng(startEvent.getLatitude(), startEvent.getLongitude());
        LatLng endPoint = new LatLng(endEvent.getLatitude(), endEvent.getLongitude());

        PolylineOptions options = new PolylineOptions().add(startPoint).add(endPoint).color(googleColor).width(width);
        Polyline line = map.addPolyline(options);
    }
}