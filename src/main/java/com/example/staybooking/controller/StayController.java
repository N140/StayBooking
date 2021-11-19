package com.example.staybooking.controller;

import com.example.staybooking.model.Stay;
import com.example.staybooking.model.StayAvailability;
import com.example.staybooking.model.StayImage;
import com.example.staybooking.model.User;
import com.example.staybooking.service.StayService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

@RestController
public class StayController {

    private StayService stayService;

    @Autowired
    public StayController(StayService stayService) {
        this.stayService = stayService;
    }

    @GetMapping("/stays")
    public List<Stay> listStay(@RequestParam(name="host") String hostname) {
        return stayService.listByUser(hostname);
    }
    @GetMapping("/stays/{stayId}")
    public Stay getStay( @PathVariable Long stayId){
        return stayService.findByIdAndHost(stayId);
    }
    //?
    @PostMapping("/stays")
    public void addStay(@RequestParam String name,
                        @RequestParam String description,
                        @RequestParam String address,
                        @RequestParam(name="guest_number") int guestNumber,
                        @RequestParam(name="host") String hostName,
                        @RequestParam MultipartFile[] images) {
        Stay stay= new Stay.Builder()
                .setName(name)
                .setDescription(description)
                .setAddress(address)
                .setGuestNumber(guestNumber)
                .setHost(new User.Builder().setUsername(hostName).build())
                .build();
        stayService.add(stay, images);
    }

    @DeleteMapping(value="/stays/{stayId}")
    public void deleteStay(@PathVariable Long stayId) {
        stayService.delete(stayId);
    }
}
