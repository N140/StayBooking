package com.example.staybooking.service;

import com.example.staybooking.model.*;
import com.example.staybooking.repository.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/*
* stay(一间民宿) from host side
* reservation from user side
* */
@Service
public class StayService {

    private int setMonth=30;
    private ImageStorageService imageStorageService;
    private StayRepository stayRepository;

    @Autowired
    public StayService(ImageStorageService imageStorageService, StayRepository stayRepository) {
        this.imageStorageService = imageStorageService;
        this.stayRepository = stayRepository;
    }



    //stay save, delete by id, list by user and get by id.
    public List<Stay> listByUser(String username) {
        return stayRepository.findByHost(
                new User.Builder().setUsername(username).build());
    }
    public Stay findByIdAndHost(Long stayId) {
        return stayRepository.findById(stayId).orElse(null);
    }

    public void add(Stay stay, MultipartFile[] images) {
        /*
        *
        * 1. set stayAvailability of stay
        * 2. upload image to GCS and insert (stay_id, url) to StayImage
        * 2. store stay record in stay table
        * */
        LocalDate date= LocalDate.now().plusDays(1);
        List<StayAvailability> availabilities= new ArrayList<>();
        for(int i=0; i<setMonth; i++) {
            StayAvailability stayAvailability= new StayAvailability.Builder()
                    .setId(new StayAvailabilityKey(stay.getId(),date))
                    .setStay(stay)
                    .setState(StayAvailabilityState.AVAILABLE)
                    .build();
            availabilities.add(stayAvailability);
            date=date.plusDays(1);
        }
        stay.setAvailabilities(availabilities);
        List<String> mediaLinks=
                Arrays
                    .stream(images)
                    .parallel()
                    .map( image -> imageStorageService.save(image))
                    .collect(Collectors.toList());
        List<StayImage> stayImages =new ArrayList<>();
        for(String url: mediaLinks) {
            stayImages.add(new StayImage(url,stay));
        }
        stay.setstayImages(stayImages);
        stayRepository.save(stay);
    }

    public void delete(Long stayID) {
        stayRepository.deleteById(stayID);
    }
}
