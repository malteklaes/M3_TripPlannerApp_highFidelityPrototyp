package com.example.m3_tripplannerapp_highfidelityprototyp;

import android.net.Uri;

/**
 * structures the transport-type of a chosen journey
 * @author TripPlannerApp-Team
 */
public enum DataEnumTransport {
    Car_Sharing, Bus, Ship, Train, Plane, Mix, Nothing;

    public Uri getVendorURL() {
        switch (this) {
            case Car_Sharing:
                return Uri.parse("https://www.share-now.com/at/de/");
            case Bus:
                return Uri.parse("https://www.flixbus.at/");
            case Ship:
                return Uri.parse("https://www.directferries.com/europe.htm");
            case Train:
                return Uri.parse("https://www.oebb.at/");
            case Plane:
                return Uri.parse("https://www.skyscanner.at/fluge-nach/e/billigfluge-nach-europa.html");
            case Mix:
                return Uri.parse("https://www.oebb.at/");
            case Nothing:
                return Uri.parse("https://www.skyscanner.at/fluge-nach/e/billigfluge-nach-europa.html");
            default:
                throw new IllegalArgumentException("Invalid transport");
        }
    }
}
