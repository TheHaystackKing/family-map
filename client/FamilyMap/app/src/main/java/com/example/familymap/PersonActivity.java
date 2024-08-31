package com.example.familymap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import model.Event;
import model.Person;

import com.example.familymap.LifeEvents;
import  com.example.familymap.FamilyMember;

public class PersonActivity extends AppCompatActivity {

    TextView firstNameField;
    TextView lastNameField;
    TextView genderField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        DataCache dataCache = DataCache.getInstance();

        firstNameField = findViewById(R.id.firstName);
        firstNameField.setText(dataCache.getCurrentPerson().getFirstName());
        lastNameField = findViewById(R.id.lastName);
        lastNameField.setText(dataCache.getCurrentPerson().getLastName());
        genderField = findViewById(R.id.gender);
        if(dataCache.getCurrentPerson().getGender().equals("m")) {
            genderField.setText("Male");
        }
        else {
            genderField.setText("Female");
        }

        ExpandableListView lifeEventListView = findViewById(R.id.lifeEventListView);
        ArrayList<Event> events = dataCache.getEvents(dataCache.getCurrentPerson().getPersonID());
        AdvancedViewsGenerator generator = new AdvancedViewsGenerator();
        ArrayList<LifeEvents> lifeEvents = generator.getLifeEvents(events);
        ArrayList<FamilyMember> familyMembers = getFamily();
        lifeEventListView.setAdapter(new ExpandableListAdapter(lifeEvents, familyMembers));
    }

    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private static final int LIFE_EVENT_GROUP_POSITION = 0;
        private static final int FAMILY_MEMBERS_GROUP_POSITION = 1;

        private final ArrayList<LifeEvents> lifeEvents;
        private final ArrayList<FamilyMember> familyMembers;

        ExpandableListAdapter(ArrayList<LifeEvents> lifeEvents, ArrayList<FamilyMember> familyMembers) {
            this.lifeEvents = lifeEvents;
            this.familyMembers = familyMembers;
        }

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            switch (groupPosition) {
                case LIFE_EVENT_GROUP_POSITION:
                    return lifeEvents.size();
                case FAMILY_MEMBERS_GROUP_POSITION:
                    return familyMembers.size();
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            switch (groupPosition) {
                case LIFE_EVENT_GROUP_POSITION:
                    return getString(R.string.lifeEventsDropdown);
                case FAMILY_MEMBERS_GROUP_POSITION:
                    return getString(R.string.familyDropdown);
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            switch (groupPosition) {
                case LIFE_EVENT_GROUP_POSITION:
                    return lifeEvents.get(childPosition);
                case FAMILY_MEMBERS_GROUP_POSITION:
                    return familyMembers.get(childPosition);
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_group, parent, false);
            }
            TextView titleView = convertView.findViewById(R.id.listTitle);

            switch (groupPosition) {
                case LIFE_EVENT_GROUP_POSITION:
                    titleView.setText(R.string.lifeEventsDropdown);
                    break;
                case FAMILY_MEMBERS_GROUP_POSITION:
                    titleView.setText(R.string.familyDropdown);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View itemView;

            switch (groupPosition) {
                case LIFE_EVENT_GROUP_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.life_events_item, parent, false);
                    initalizeLifeEvents(itemView, childPosition);
                    break;
                case FAMILY_MEMBERS_GROUP_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.family_member_item, parent, false);
                    initializeFamilyMembers(itemView, childPosition);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
            return itemView;
        }

        private void initalizeLifeEvents(View lifeEventItemView, final int childPosition) {
            TextView eventType = lifeEventItemView.findViewById(R.id.eventType);
            eventType.setText(lifeEvents.get(childPosition).getEventType() + " ");

            TextView city = lifeEventItemView.findViewById(R.id.city);
            city.setText(lifeEvents.get(childPosition).getCity() + " ");

            TextView country = lifeEventItemView.findViewById(R.id.country);
            country.setText(lifeEvents.get(childPosition).getCountry() + " ");

            TextView year = lifeEventItemView.findViewById(R.id.yearItem);
            year.setText("(" + String.valueOf(lifeEvents.get(childPosition).getYear()) + ")");

            TextView firstName = lifeEventItemView.findViewById(R.id.firstNameItem);
            firstName.setText(lifeEvents.get(childPosition).getFirstName() + " ");

            TextView lastName = lifeEventItemView.findViewById(R.id.lastNameItem);
            lastName.setText(lifeEvents.get(childPosition).getLastName());

            ImageView eventImage = lifeEventItemView.findViewById(R.id.eventIcon);
            eventImage.setImageResource(R.drawable.calendar);

            lifeEventItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataCache dataCache = DataCache.getInstance();
                    dataCache.setInEventActivity(true);
                    dataCache.setCurrentEvent(dataCache.findEvent(lifeEvents.get(childPosition).getEventID()));
                    Intent intent = EventActivity.newIntent(PersonActivity.this);
                    startActivity(intent);
                }
            });
        }

        private void initializeFamilyMembers(View familyMembersItemView, final int childPosition) {
            TextView firstName = familyMembersItemView.findViewById(R.id.firstNameFamily);
            firstName.setText(familyMembers.get(childPosition).getFirstName() + " ");

            TextView lastName = familyMembersItemView.findViewById(R.id.lastNameFamily);
            lastName.setText(familyMembers.get(childPosition).getLastName());

            TextView relationship = familyMembersItemView.findViewById(R.id.relationship);
            relationship.setText(familyMembers.get(childPosition).getRelationship());

            ImageView gender = familyMembersItemView.findViewById(R.id.genderIcon);
            if(familyMembers.get(childPosition).getGender().equals("m")) {
                gender.setImageResource(R.drawable.male);
            }
            else {
                gender.setImageResource(R.drawable.female);
            }

            familyMembersItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataCache dataCache = DataCache.getInstance();
                    dataCache.setCurrentPerson(dataCache.getPerson(familyMembers.get(childPosition).getPersonID()));
                    Intent intent = PersonActivity.newIntent(PersonActivity.this);
                    startActivity(intent);
                }
            });
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }

    private ArrayList<LifeEvents> getLifeEvents(ArrayList<Event> events) {
        DataCache dataCache = DataCache.getInstance();
        ArrayList<LifeEvents> lifeEvents = new ArrayList<>();
        for(int i = 0; i < events.size(); ++i) {
            Event event = events.get(i);
            Person person = dataCache.getPerson(event.getPersonID());
            lifeEvents.add(new LifeEvents(event.getEventType(), event.getCity(), event.getCountry(),
                    event.getYear(),person.getFirstName(), person.getLastName(), event.getEventID()));
        }
        return lifeEvents;
    }

    private ArrayList<FamilyMember> getFamily() {
        DataCache dataCache = DataCache.getInstance();
        ArrayList<FamilyMember> familyMembers = new ArrayList<>();
        Person person = dataCache.getCurrentPerson();
        if(person.getMotherID() != null) {
            Person relative = dataCache.getPerson(person.getMotherID());
            familyMembers.add(new FamilyMember(relative.getFirstName(), relative.getLastName(),
            "Mother", relative.getGender(), relative.getPersonID()));
        }
        if(person.getFatherID() != null) {
            Person relative = dataCache.getPerson(person.getFatherID());
            familyMembers.add(new FamilyMember(relative.getFirstName(), relative.getLastName(),
                    "Father", relative.getGender(), relative.getPersonID()));
        }
        if(person.getSpouseID() != null) {
            Person relative = dataCache.getPerson(person.getSpouseID());
            familyMembers.add(new FamilyMember(relative.getFirstName(), relative.getLastName(),
                    "Spouse", relative.getGender(), relative.getPersonID()));
        }
        Person[] people = dataCache.getPeople();
        for(int i = 0; i < people.length; ++i) {
            Person relative = people[i];
            if(relative.getMotherID() != null && relative.getFatherID() != null) {
                if(relative.getFatherID().equals(person.getPersonID()) ||
                        relative.getMotherID().equals(person.getPersonID())) {
                    familyMembers.add(new FamilyMember(relative.getFirstName(),
                            relative.getLastName(), "Child", relative.getGender(), relative.getPersonID()));
                }
            }
        }
        return familyMembers;
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, PersonActivity.class);
        return intent;
    }
}