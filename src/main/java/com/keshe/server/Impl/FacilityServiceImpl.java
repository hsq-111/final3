package com.keshe.server.Impl;

import com.keshe.mapper.FacilityMapper;
import com.keshe.pojo.Facility;
import com.keshe.server.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {
     @Autowired
    private FacilityMapper facilityMapper;

    @Override
    public List<Facility> getAllFacilities() {
        return facilityMapper.findAll();
    }

    @Override
    public Facility getFacilityById(Integer id) {
        return facilityMapper.findById(id);
    }

    @Override
    public void addFacility(Facility facility) {
        facilityMapper.insert(facility);
    }

    @Override
    public int updateFacility(Facility facility) {
        return facilityMapper.update(facility);
    }

    @Override
    public void deleteFacility(Integer id) {
        facilityMapper.deleteById(id);
    }
}
