package com.keshe.server;

import com.keshe.pojo.Facility;

import java.util.List;

public interface FacilityService {


     List<Facility> getAllFacilities();
    Facility getFacilityById(Integer id);
    void addFacility(Facility facility);
    int updateFacility(Facility facility);
    void deleteFacility(Integer id);
}
