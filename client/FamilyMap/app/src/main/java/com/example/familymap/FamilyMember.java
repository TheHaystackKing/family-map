package com.example.familymap;

public class FamilyMember {

    private String firstName;
    private String lastName;
    private String relationship;
    private String gender;
    private String personID;

    public FamilyMember(String firstName, String lastName, String relationship, String gender, String personID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.relationship = relationship;
        this.gender = gender;
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRelationship() {
        return relationship;
    }

    public String getGender() {
        return gender;
    }

    public String getPersonID() {
        return personID;
    }
}
