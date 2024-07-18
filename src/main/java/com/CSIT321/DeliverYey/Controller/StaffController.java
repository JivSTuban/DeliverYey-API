package com.CSIT321.DeliverYey.Controller;

import com.CSIT321.DeliverYey.Entity.StaffEntity;
import com.CSIT321.DeliverYey.Service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
@Validated
@CrossOrigin(origins = "http://localhost:5173")
public class StaffController {
    @Autowired
    private StaffService staffService;
    @PostMapping
    private StaffEntity insertStaff(@RequestBody StaffEntity staff) {
        return staffService.insertStaff(staff);
    }
    @GetMapping
    public List<StaffEntity> getAllStaff() {
        return staffService.getAllStaff();
    }
    @PutMapping
    public StaffEntity updateStaff(@RequestParam int staffId, @RequestBody StaffEntity newStaffDetails) {
        return staffService.updateStaff(staffId, newStaffDetails);
    }

    @DeleteMapping("/deleteStaff/{staffId}")
    public String deleteStaff(@PathVariable int staffId) {
        return staffService.deleteStaff(staffId);
    }
}
