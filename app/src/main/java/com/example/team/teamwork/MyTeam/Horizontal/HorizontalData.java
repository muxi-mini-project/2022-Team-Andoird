package com.example.team.teamwork.MyTeam.Horizontal;

import android.widget.ImageView;

import java.util.UUID;

public class HorizontalData {
    private UUID mId;

    public HorizontalData(){
        mId=UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }
}
