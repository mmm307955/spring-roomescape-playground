package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.exception.TimeDeleteException;

@Service
public class TimeService {
    private final TimeDao timeDao;
    private final ReservationDao reservationDao;

    public TimeService(TimeDao timeDao, ReservationDao reservationDao) {
        this.timeDao = timeDao;
        this.reservationDao = reservationDao;
    }

    @Transactional(readOnly = true)
    public List<TimeResponse> getAllTimes() {
        return timeDao.findAll().stream()
            .map(TimeResponse::from)
            .toList();
    }

    @Transactional
    public TimeResponse createTime(TimeRequest request) {
        Time time = new Time(null, request.time());
        Time saved = timeDao.save(time);
        return TimeResponse.from(saved);
    }

    @Transactional
    public void deleteTime(Long id) {
        if (reservationDao.existsByTimeId(id)) {
            throw new TimeDeleteException(id);
        }
        timeDao.deleteById(id);
    }
}
