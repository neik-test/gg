package com.kien.khachsan.controller;

import com.kien.khachsan.dto.booking.BookingRequest;
import com.kien.khachsan.dto.booking.BookingResponse;
import com.kien.khachsan.entity.Booking;
import com.kien.khachsan.entity.Customer;
import com.kien.khachsan.entity.Room;
import com.kien.khachsan.service.BookingService;
import com.kien.khachsan.service.CustomerService;
import com.kien.khachsan.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final CustomerService customerService;
    private final RoomService roomService;

    public BookingController(BookingService bookingService,
                             CustomerService customerService,
                             RoomService roomService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookingResponse> responses = bookingService.getAllBookings().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(convertToResponse(bookingService.getBookingById(id)));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByCustomer(@PathVariable Long customerId) {
        List<BookingResponse> responses = bookingService.getBookingsByCustomer(customerId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('booking:write')")
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest request) {
        Customer customer = customerService.getCustomerById(request.getMaKH());
        Room room = roomService.getRoomById(request.getMaPhong());

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setRoom(room);
        booking.setNgayNhanPhong(request.getNgayNhanPhong());
        booking.setNgayTraPhong(request.getNgayTraPhong());

        Booking saved = bookingService.createBooking(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponse(saved));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('booking:write')")
    public ResponseEntity<BookingResponse> updateBooking(@PathVariable Long id,
                                                          @RequestBody BookingRequest request) {
        Booking booking = new Booking();
        booking.setNgayNhanPhong(request.getNgayNhanPhong());
        booking.setNgayTraPhong(request.getNgayTraPhong());
        Booking updated = bookingService.updateBooking(id, booking);
        return ResponseEntity.ok(convertToResponse(updated));
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('booking:write')")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('booking:delete')")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    // ===== CONVERTER =====
    private BookingResponse convertToResponse(Booking booking) {
        return new BookingResponse(
                booking.getMaDatPhong(),
                booking.getCustomer().getMaKH(),
                booking.getRoom().getMaPhong(),
                booking.getNgayDat(),
                booking.getNgayNhanPhong(),
                booking.getNgayTraPhong(),
                booking.getTongTien(),
                booking.getTrangThai()
        );
    }
}