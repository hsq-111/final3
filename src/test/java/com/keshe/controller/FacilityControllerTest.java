package com.keshe.controller;

import com.keshe.pojo.Facility;
import com.keshe.pojo.Result;
import com.keshe.server.FacilityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacilityController.class)
public class FacilityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacilityService facilityService;

    @Autowired
    private ObjectMapper objectMapper;

    private Facility testFacility;

    @BeforeEach
    void setUp() {
        testFacility = new Facility();
        testFacility.setFacilityId(1);
        testFacility.setName("测试跑步机");
        testFacility.setType("有氧器材");
        testFacility.setLocation("一楼东区");
        testFacility.setCapacity(10);
        testFacility.setStatus("AVAILABLE");
        testFacility.setCreatedAt(LocalDateTime.now());
        testFacility.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testGetAllFacilities() throws Exception {
        List<Facility> facilities = Arrays.asList(testFacility);
        Mockito.when(facilityService.getAllFacilities()).thenReturn(facilities);

        mockMvc.perform(get("/admin/facilities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("操作成功"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("测试跑步机"));
    }

    @Test
    void testGetFacilityById() throws Exception {
        Mockito.when(facilityService.getFacilityById(1)).thenReturn(testFacility);

        mockMvc.perform(get("/admin/facilities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").value("测试跑步机"));
    }

    @Test
    void testGetFacilityByIdNotFound() throws Exception {
        Mockito.when(facilityService.getFacilityById(999)).thenReturn(null);

        mockMvc.perform(get("/admin/facilities/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("设施不存在"));
    }

    @Test
    void testAddFacility() throws Exception {
        Facility newFacility = new Facility();
        newFacility.setName("新跑步机");
        newFacility.setType("有氧器材");
        newFacility.setLocation("一楼西区");
        newFacility.setCapacity(5);

        Mockito.doNothing().when(facilityService).addFacility(any(Facility.class));

        mockMvc.perform(post("/admin/facilities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newFacility)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("操作成功"));
    }

    @Test
    void testAddFacilityWithInvalidData() throws Exception {
        Facility invalidFacility = new Facility();
        // 缺少必需字段

        mockMvc.perform(post("/admin/facilities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFacility)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500));
    }

    @Test
    void testUpdateFacility() throws Exception {
        Mockito.when(facilityService.getFacilityById(1)).thenReturn(testFacility);
        Mockito.when(facilityService.updateFacility(any(Facility.class))).thenReturn(1);

        Facility updateFacility = new Facility();
        updateFacility.setName("更新的跑步机");
        updateFacility.setStatus("MAINTENANCE");

        mockMvc.perform(put("/admin/facilities/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateFacility)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("操作成功"));
    }

    @Test
    void testDeleteFacility() throws Exception {
        Mockito.when(facilityService.getFacilityById(1)).thenReturn(testFacility);
        Mockito.doNothing().when(facilityService).deleteFacility(1);

        mockMvc.perform(delete("/admin/facilities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("操作成功"));
    }
}