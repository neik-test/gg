package com.kien.khachsan.service;

import com.kien.khachsan.entity.Hotel;
import com.kien.khachsan.repository.HotelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    // Lấy tất cả khách sạn
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    // Lấy khách sạn theo ID
    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khách sạn không tồn tại với ID: " + id));
    }

    // Tìm khách sạn theo tên
    public List<Hotel> getHotelsByName(String name) {
        return hotelRepository.findByTenKSContainingIgnoreCase(name);
    }

    // Tìm khách sạn theo số sao
    public List<Hotel> getHotelsByStar(Integer star) {
        return hotelRepository.findBySoSao(star);
    }

    // Tạo khách sạn mới
    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    // Cập nhật khách sạn
    public Hotel updateHotel(Long id, Hotel hotelDetails) {
        Hotel existingHotel = getHotelById(id);

        if (hotelDetails.getTenKS() != null) {
            existingHotel.setTenKS(hotelDetails.getTenKS());
        }
        if (hotelDetails.getDiaChi() != null) {
            existingHotel.setDiaChi(hotelDetails.getDiaChi());
        }
        if (hotelDetails.getSoSao() != null) {
            existingHotel.setSoSao(hotelDetails.getSoSao());
        }
        if (hotelDetails.getMoTa() != null) {
            existingHotel.setMoTa(hotelDetails.getMoTa());
        }

        return hotelRepository.save(existingHotel);
    }

    // Xóa khách sạn
    public void deleteHotel(Long id) {
        Hotel hotel = getHotelById(id);
        hotelRepository.delete(hotel);
    }
}