package dao;

import model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A class used to communicate with the Event table of the database
 */
public class EventDao {

    /**
     * The connection to the database
     */
    private final Connection conn;

    /**
     * Creates a new EventDAO object
     *
     * @param conn the new EventDAO's connection
     */
    public EventDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts new event into database
     *
     * @param event event to be added
     * @throws DataAccessException if an SQL error occurs
     */
    public void insert(Event event) throws DataAccessException {
        String sql = "INSERT INTO Events (EventID, Username, PersonID, Latitude, Longitude, Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * searches the database for an event
     *
     * @param eventID event to be found
     * @return the event, if found
     * @throws DataAccessException if an SQL error occurs
     */
    public Event find(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE EventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("Username"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public ArrayList<Event> findPerson(String personID) throws DataAccessException {
        ArrayList<Event> events = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE personId = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();

            while(rs.next()) {
                Event event = new Event(rs.getString("EventID"), rs.getString("Username"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                events.add(event);
            }
            if(events.size() > 0) {
                return events;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding events");
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * searches the datbase for an event and deletes it
     *
     * @param eventId event to be deleted
     * @throws DataAccessException if an SQL error occurs
     */
    public void delete(String eventId) throws DataAccessException {
        String sql = "DELETE FROM Events WHERE eventId = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,eventId);
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered when attempting to delete event");
        }

    }

    /**
     * Clears the entire event table in the database
     *
     * @throws DataAccessException if an SQL error occurs
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Events";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new DataAccessException("Error encountered while clearing table");
        }

    }
}
