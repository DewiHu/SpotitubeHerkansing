package nl.han.ica.oose.dea.dewihu.models;

import java.util.ArrayList;

public class PlaylistModel {
    private int id;
    private String name;
    private boolean owner;
    private ArrayList<TrackModel> tracks;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public ArrayList<TrackModel> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<TrackModel> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "PlaylistModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", tracks=" + tracks +
                '}';
    }
}