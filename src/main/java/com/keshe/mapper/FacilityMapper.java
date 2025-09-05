package com.keshe.mapper;

import com.keshe.pojo.Facility;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FacilityMapper {
    @Select("SELECT * FROM facility")
    List<Facility> findAll();

    @Select("SELECT * FROM facility WHERE facility_id = #{id}")
    Facility findById(Integer id);

    @Insert("INSERT INTO facility(name, type, location, capacity, status, created_at, updated_at) " +
            "VALUES(#{name}, #{type}, #{location}, #{capacity}, #{status}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "facilityId")
    void insert(Facility facility);

    int update(Facility facility);

    @Delete("DELETE FROM facility WHERE facility_id = #{id}")
    void deleteById(Integer id);
}