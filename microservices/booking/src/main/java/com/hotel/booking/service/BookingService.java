package com.hotel.booking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel.booking.client.RoomClient;
import com.hotel.booking.client.UserClient;
import com.hotel.booking.dtos.BookingDTO;
import com.hotel.booking.dtos.UserDTO;
import com.hotel.booking.dtos.createBookingDTO;
import com.hotel.booking.enums.BookingStatus;
import com.hotel.booking.enums.RoomStatus;
import com.hotel.booking.persistence.Booking;
import com.hotel.booking.repository.BookingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final RoomClient roomClient;

    private final UserClient userClient;

    public String createBooking(createBookingDTO bookingDTO) {

        var room = roomClient.getRoomById(bookingDTO.roomId());

        UserDTO user = userClient.getUserByUsername(bookingDTO.username());

        if (user == null) {
            return "User not found";
        }

        if (room.status() != RoomStatus.AVAILABLE) {
            return "Room is not available for booking";
        }

        // calculate total amount
        long days = bookingDTO.checkOutDate().toEpochDay() - bookingDTO.checkInDate().toEpochDay();
        double totalAmount = days * room.pricePerNight();

        Booking booking = Booking.builder()
                .roomId(room.id())
                .userId(user.id())
                .checkIn(bookingDTO.checkInDate())
                .checkOut(bookingDTO.checkOutDate())
                .totalAmount(totalAmount)
                .build();

        // Update room status to OCCUPIED
        roomClient.occupyRoom(bookingDTO.roomId());

        bookingRepository.save(booking);
        return "Booking created for room: " + room.name();
    }

    public List<BookingDTO> getAllBookings() {
        var bookings = bookingRepository.findAll();
        return bookings.stream().map(booking -> new BookingDTO(booking.getId(), booking.getUserId(),
                booking.getRoomId(), booking.getTotalAmount(), booking.getStatus())).toList();
    }

    public BookingDTO getBookingById(Long bookingId) {
        var booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return new BookingDTO(booking.getId(), booking.getUserId(), booking.getRoomId(), booking.getTotalAmount(),
                booking.getStatus());
    }

    // cancel booking
    public String cancelBooking(Long bookingId) {
        var booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return "Booking is already cancelled";
        }
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        // Update room status to AVAILABLE
        roomClient.releaseRoom(booking.getRoomId(), RoomStatus.AVAILABLE);
        return "Booking cancelled successfully";
    }

    // FILTER BOOKINGS BY USER ID
    public List<BookingDTO> getBookingsByUserId(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId).orElseThrow(()-> new RuntimeException("Not found"));
        return bookings.stream().map(booking -> new BookingDTO(booking.getId(), booking.getUserId(),
                booking.getRoomId(), booking.getTotalAmount(), booking.getStatus())).toList();
    }

}
