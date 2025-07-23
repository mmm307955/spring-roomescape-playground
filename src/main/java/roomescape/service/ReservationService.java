package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationService(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> getAllReservations() {
        return reservationDao.findAll().stream()
            .map(ReservationResponse::from)
            .toList();
    }

    @Transactional
    public ReservationResponse createReservation(ReservationRequest request) {
        Time time = timeDao.findById(request.timeId());
        Reservation reservation = new Reservation(null, request.name(), request.date(),
            time);
        Reservation saved = reservationDao.save(reservation);
        return ReservationResponse.from(saved);
    }

    @Transactional
    public void deleteReservation(Long id) {
        reservationDao.deleteById(id);
    }
}
