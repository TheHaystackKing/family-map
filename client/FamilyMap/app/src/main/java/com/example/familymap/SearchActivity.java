package com.example.familymap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import model.Event;
import model.Person;

public class SearchActivity extends AppCompatActivity {

    private static final int LIFE_EVENT_ITEM_VIEW_TYPE = 0;
    private static final int FAMILY_MEMBER_ITEM_VIEW_TYPE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchView search = findViewById(R.id.searchBar);
        RecyclerView searchResults = findViewById(R.id.searchResults);
        searchResults.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                DataCache dataCache = DataCache.getInstance();
                AdvancedViewsGenerator generator = new AdvancedViewsGenerator();
                ArrayList<LifeEvents> lifeEvents = generator.getLifeEvents(dataCache.searchEvents(newText));
                ArrayList<FamilyMember> familyMembers = generator.getPeopleModel(dataCache.searchPeople(newText));
                searchResultsAdapter adapter = new searchResultsAdapter(lifeEvents, familyMembers);
                searchResults.setAdapter(adapter);
                return true;
            }
        });
    }

    private class searchResultsAdapter extends RecyclerView.Adapter<searchResultsViewHolder> {

        private final ArrayList<LifeEvents> lifeEvents;
        private final ArrayList<FamilyMember> familyMembers;

        searchResultsAdapter(ArrayList<LifeEvents> lifeEvents, ArrayList<FamilyMember> familyMember) {
            this.lifeEvents = lifeEvents;
            this.familyMembers = familyMember;
        }

        @Override
        public int getItemViewType(int position) {
            //return position < lifeEvents.size() ? LIFE_EVENT_ITEM_VIEW_TYPE : FAMILY_MEMBER_ITEM_VIEW_TYPE;
            if(position < lifeEvents.size()) {
                return LIFE_EVENT_ITEM_VIEW_TYPE;
            }
            else {
                return FAMILY_MEMBER_ITEM_VIEW_TYPE;
            }
        }

        @NonNull
        @Override
        public searchResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;

            if(viewType == LIFE_EVENT_ITEM_VIEW_TYPE) {
                view = getLayoutInflater().inflate(R.layout.life_events_item, parent, false);
            }
            else {
                view = getLayoutInflater().inflate(R.layout.family_member_item, parent, false);
            }

            return new searchResultsViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull searchResultsViewHolder holder, int position) {
            if(position < lifeEvents.size()) {
                holder.bind(lifeEvents.get(position));
            }
            else {
                holder.bind(familyMembers.get(position - lifeEvents.size()));
            }
        }

        @Override
        public int getItemCount() {
            return lifeEvents.size() + familyMembers.size();
        }
    }


    private class searchResultsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView firstName;
        private final TextView lastName;
        private final TextView eventType;
        private final TextView city;
        private final TextView country;
        private final TextView year;
        private final ImageView image;

        private final int viewType;
        private LifeEvents lifeEvent;
        private FamilyMember familyMember;

        searchResultsViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;

            itemView.setOnClickListener(this);

            if(viewType == LIFE_EVENT_ITEM_VIEW_TYPE) {
                eventType = itemView.findViewById(R.id.eventType);
                city = itemView.findViewById(R.id.city);
                country = itemView.findViewById(R.id.country);
                year = itemView.findViewById(R.id.yearItem);
                firstName = itemView.findViewById(R.id.firstNameItem);
                lastName = itemView.findViewById(R.id.lastNameItem);
                image = itemView.findViewById(R.id.eventIcon);

            }
            else {
                firstName = itemView.findViewById(R.id.firstNameFamily);
                lastName = itemView.findViewById(R.id.lastNameFamily);
                image = itemView.findViewById(R.id.genderIcon);
                city = null;
                eventType = null;
                country = null;
                year = null;
            }
        }

        private void bind(LifeEvents lifeEvent) {
            this.lifeEvent = lifeEvent;
            eventType.setText(lifeEvent.getEventType() + " ");
            city.setText(lifeEvent.getCity() + " ,");
            country.setText(lifeEvent.getCountry() + " ");
            year.setText("(" + String.valueOf(lifeEvent.getYear()) + ")");
            firstName.setText(lifeEvent.getFirstName() + " ");
            lastName.setText(lifeEvent.getLastName());
            image.setImageResource(R.drawable.calendar);
        }

        private void bind(FamilyMember familyMember) {
            this.familyMember = familyMember;
            firstName.setText(familyMember.getFirstName() + " ");
            lastName.setText(familyMember.getLastName());

            if(familyMember.getGender().equals("m")) {
                image.setImageResource(R.drawable.male);
            }
             else {
                 image.setImageResource(R.drawable.female);
            }
        }

        @Override
        public void onClick(View view) {
            if(viewType == LIFE_EVENT_ITEM_VIEW_TYPE) {
                DataCache dataCache = DataCache.getInstance();
                Event event = dataCache.findEvent(lifeEvent.getEventID());
                System.out.println(event);
                dataCache.setCurrentEvent(event);
                dataCache.setInEventActivity(true);
                Intent intent = EventActivity.newIntent(SearchActivity.this);
                startActivity(intent);
            }
            else {
                DataCache dataCache = DataCache.getInstance();
                Person person = dataCache.getPerson(familyMember.getPersonID());
                dataCache.setCurrentPerson(person);
                Intent intent = PersonActivity.newIntent(SearchActivity.this);
                startActivity(intent);
            }
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

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext,SearchActivity.class);
        return intent;
    }
}