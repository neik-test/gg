package com.kien.khachsan.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kien.khachsan.entity.Booking;
import com.kien.khachsan.entity.Room;
import com.kien.khachsan.repository.BookingRepository;

@Service
@Transactional
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final CustomerService customerService;

    public BookingService(BookingRepository bookingRepository,
                          RoomService roomService,
                          CustomerService customerService) {
        this.bookingRepository = bookingRepository;
        this.roomService = roomService;
        this.customerService = customerService;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomer_MaKH(customerId);
    }

    public Booking createBooking(Booking booking) {
        // 1. Validate dates
        if (booking.getNgayTraPhong().isBefore(booking.getNgayNhanPhong()) ||
            booking.getNgayTraPhong().isEqual(booking.getNgayNhanPhong())) {
            throw new RuntimeException("Ngày trả phòng phải sau ngày nhận phòng");
        }

        // 2. Check customer exists
        customerService.getCustomerById(booking.getCustomer().getMaKH());
        logger.info("✅ Customer found");

        // 3. Check room exists and is available
        Room room = roomService.getRoomById(booking.getRoom().getMaPhong());
        if (!"Trống".equals(room.getTinhTrang())) {
            throw new RuntimeException("Room is not available");
        }
        logger.info("✅ Room available");

        // 4. Check for booking conflicts
        List<Booking> conflicts = bookingRepository.findConflictingBookings(
                booking.getRoom().getMaPhong(),
                booking.getNgayNhanPhong(),
                booking.getNgayTraPhong()
        );
        if (!conflicts.isEmpty()) {
            Booking conflict = conflicts.get(0);
            throw new RuntimeException("Room is already booked from " +
                    conflict.getNgayNhanPhong() + " to " + conflict.getNgayTraPhong());
        }
        logger.info("✅ No booking conflicts");

        // 5. Set default values
        if (booking.getNgayDat() == null) {
            booking.setNgayDat(LocalDate.now());
        }
        if (booking.getTrangThai() == null) {
            booking.setTrangThai("Đã đặt");
        }

        // 6. Update room status
        room.setTinhTrang("Đã đặt");
        roomService.updateRoom(room.getMaPhong(), room);

        // 7. Save booking
        logger.info("✅ Booking created successfully");
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Long id, Booking bookingDetails) {
        Booking existing = getBookingById(id);
        if (bookingDetails.getNgayNhanPhong() != null) {
            existing.setNgayNhanPhong(bookingDetails.getNgayNhanPhong());
        }
        if (bookingDetails.getNgayTraPhong() != null) {
            existing.setNgayTraPhong(bookingDetails.getNgayTraPhong());
        }
        if (bookingDetails.getTongTien() != null) {
            existing.setTongTien(bookingDetails.getTongTien());
        }
        if (bookingDetails.getTrangThai() != null) {
            existing.setTrangThai(bookingDetails.getTrangThai());
        }
        return bookingRepository.save(existing);
    }

    public void cancelBooking(Long id) {
        Booking booking = getBookingById(id);
        Room room = booking.getRoom();
        room.setTinhTrang("Trống");
        roomService.updateRoom(room.getMaPhong(), room);
        booking.setTrangThai("Hủy");
        bookingRepository.save(booking);
        logger.info("✅ Booking cancelled");
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}