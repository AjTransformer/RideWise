package com.airtribe.service;

import com.airtribe.model.Location;
import com.airtribe.model.Rider;
import com.airtribe.util.IdGenerator;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RiderService {
    private final Map<String, Rider> riders = new ConcurrentHashMap<>();

    public Rider registerRider(String name, double x, double y) {
        String id = IdGenerator.generate("R");
        Rider r = new Rider(id, name, new Location(x, y));
        riders.put(id, r);
        return r;
    }

    public Rider getRiderById(String id) {
        return riders.get(id);
    }

    public Collection<Rider> listAll() {
        return riders.values();
    }
}
