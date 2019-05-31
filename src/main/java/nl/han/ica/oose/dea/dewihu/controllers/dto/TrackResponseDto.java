package nl.han.ica.oose.dea.dewihu.controllers.dto;

import nl.han.ica.oose.dea.dewihu.models.TrackModel;

import java.util.ArrayList;

public class TrackResponseDto {
    private ArrayList<TrackModel> tracks;

    public void setTracks(ArrayList<TrackModel> tracks) {
        this.tracks = tracks;
    }

    public ArrayList<TrackModel> getTracks() {
        return tracks;
    }
}
