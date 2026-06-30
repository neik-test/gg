package com.kien.khachsan.controller;

import com.kien.khachsan.dto.hotel.HotelRequest;
import com.kien.khachsan.dto.hotel.HotelResponse;
import com.kien.khachsan.entity.Hotel;
import com.kien.khachsan.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    // ===== PUBLIC APIs =====
    @GetMapping
    public ResponseEntity<List<HotelResponse>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        List<HotelResponse> responses = hotels.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getHotelById(@PathVariable Long id) {
        Hotel hotel = hotelService.getHotelById(id);
        return ResponseEntity.ok(convertToResponse(hotel));
    }

    @GetMapping("/search")
    public ResponseEntity<List<HotelResponse>> searchHotelsByName(@RequestParam String name) {
        List<Hotel> hotels = hotelService.getHotelsByName(name);
        List<HotelResponse> responses = hotels.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/star/{star}")
    public ResponseEntity<List<HotelResponse>> getHotelsByStar(@PathVariable Integer star) {
        List<Hotel> hotels = hotelService.getHotelsByStar(star);
        List<HotelResponse> responses = hotels.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // ===== ADMIN APIs =====
    @PostMapping
    @PreAuthorize("hasAuthority('hotel:write')")
    public ResponseEntity<HotelResponse> createHotel(@RequestBody HotelRequest request) {
        Hotel hotel = convertToEntity(request);
        Hotel savedHotel = hotelService.createHotel(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponse(savedHotel));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('hotel:write')")
    public ResponseEntity<HotelResponse> updateHotel(@PathVariable Long id, @RequestBody HotelRequest request) {
        Hotel hotel = convertToEntity(request);
        Hotel updatedHotel = hotelService.updateHotel(id, hotel);
        return ResponseEntity.ok(convertToResponse(updatedHotel));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('hotel:delete')")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }

    // ===== CONVERTERS =====
    private HotelResponse convertToResponse(Hotel hotel) {
        return new HotelResponse(
                hotel.getMaKS(),
                hotel.getTenKS(),
                hotel.getDiaChi(),
                hotel.getSoSao(),
                hotel.getMoTa()
        );
    }

    private Hotel convertToEntity(HotelRequest request) {
        return new Hotel(
                request.getTenKS(),
                request.getDiaChi(),
                request.getSoSao(),
                request.getMoTa()
        );
    }
}