package roomescape.exception;

import org.springframework.http.HttpStatus;

public class TimeDeleteException extends BusinessException {
    public TimeDeleteException(Long timeId) {
        super("time.conflict", "예약이 이미 존재하는 시간은 삭제할 수 없습니다. 예약을 먼저 삭제해주세요.", HttpStatus.CONFLICT);
        addArgument("timeId", timeId);
    }
}
