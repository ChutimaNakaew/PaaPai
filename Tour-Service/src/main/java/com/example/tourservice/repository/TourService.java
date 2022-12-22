package com.example.tourservice.repository;

import com.example.tourservice.model.Tour;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.*;

@Service
public class TourService {
    private TourRepository tourRepository;

    @Autowired
    public TourService(TourRepository tourRepository){
        this.tourRepository = tourRepository;
    }
    public Tour saveTour(Tour tour){
        return tourRepository.save(tour);
    }

    public List<Tour> getAllTours(String filters){
        List<Tour> tourList = new ArrayList<Tour>();
        Iterable<Tour> tours = tourRepository.findAll();
        tours.forEach(tourList::add);
        if (filters != null && filters.equals("ราคาถูกที่สุด")) {
            Collections.sort(tourList, new Comparator<Tour>() {
                @Override
                public int compare(Tour a1, Tour a2) {
                    return (int) (a1.getPrice() - a2.getPrice());
                }
            });
        }
        else if (filters != null && filters.equals("ราคาแพงที่สุด")){
            Collections.sort(tourList, new Comparator<Tour>() {
                @Override
                public int compare(Tour a1, Tour a2) {
                    return (int)(a2.getPrice() - a1.getPrice());
                }
            });
        }
        return tourList;
    }

    public List<Tour> getAllTours(){
        List<Tour> tourList = new ArrayList<Tour>();
        Iterable<Tour> tours = tourRepository.findAll();
        tours.forEach(tourList::add);
        return tourList;
    }

    public void deleteAllTours(){
        tourRepository.deleteAll();
    }

    public Tour TourByName(String name){
        return tourRepository.findByName(name);
    }

    public void deleteTourById(String id){
        tourRepository.deleteById(id);
    }

    public Optional<Tour> findTourById(String id){
        return tourRepository.findById(id);
    }

    public List<Tour> getToursByProvince(String province, String filters){
        List<Tour> tourList = new ArrayList<Tour>();
        Iterable<Tour> tours = tourRepository.findByProvince(province);
        tours.forEach(tourList::add);
        if (filters != null && filters.equals("ราคาถูกที่สุด")) {
            Collections.sort(tourList, new Comparator<Tour>() {
                @Override
                public int compare(Tour a1, Tour a2) {
                    return (int) (a1.getPrice() - a2.getPrice());
                }
            });
        }
        else if (filters != null && filters.equals("ราคาแพงที่สุด")){
            Collections.sort(tourList, new Comparator<Tour>() {
                @Override
                public int compare(Tour a1, Tour a2) {
                    return (int)(a2.getPrice() - a1.getPrice());
                }
            });
        }
        return tourList;
//        return tourRepository.findByProvince(province);
    }


    public Tour getTourById(String id){
        Optional<Tour> optional = tourRepository.findById(id);
        Tour tour = null;
        if(optional.isPresent()){
            tour = optional.get();
        }else{
            throw new RuntimeException("Tour not found for id ::"+ id);
        }
        return tour;
    }
    public void changeName(String id , String name, String province, Double price, String schedule, String img, String detail_img, String detail, LocalDate date_first, LocalDate date_second, Integer max_tourist)
    {
        Tour t = new Tour();
        t = tourRepository.findById(id).get();
        t.setName(name);
        t.setProvince(province);
        t.setPrice(price);
        t.setSchedule(schedule);
        t.setImg(img);
        t.setDetail_img(detail_img);
        t.setDetail(detail);
        t.setDate_first(date_first);
        t.setDate_second(date_second);
        t.setMax_tourist(max_tourist);
        tourRepository.save(t);
    }

    public List<String> getProvince() {
        String test = WebClient
                .create()
                .get()
                .uri("https://opend.data.go.th/govspending/bbgfmisprovince?api-key=CjsvDTvt1yp1GQV8mwZ0SBPQgIN6w8JK")
                .retrieve()
                .bodyToMono(String.class).block();
        JSONObject test2 = new JSONObject(test);
        JSONArray result = test2.getJSONArray("result");
        List<String> provinces = new ArrayList<String>();
        for(int i=0; i< result.length(); i++) {
            JSONObject obj = result.getJSONObject(i);
            String province = obj.getString("prov_name");
            provinces.add(province);

        }
        System.out.println(provinces);
        return provinces;
//        JSONParser parser = new JSONParser();
//        System.out.println(test);

    }

}
