package capston.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum Code {
    /**
     *  400 BAD_REQUEST
     */
    ACCESS_DENIED(BAD_REQUEST, "권한이 없습니다."),

    /**
     * 401 UNAUTHORIZED
     */
    UNSUPPORTED_TOKEN(UNAUTHORIZED, "변조된 토큰입니다."),
    WRONG_TYPE_TOKEN(UNAUTHORIZED, "변조된 토큰입니다."),
    EXPIRED_TOKEN(UNAUTHORIZED, "만료된 토큰입니다."),
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "로그인이 필요합니다."),

    /**
     * 403 FORBIDDEN
     */
    FORBIDDEN_AUTHORIZATION(FORBIDDEN,"접근 권한이 없습니다"),

    /**
     * 404 NOT_FOUND
     */
    UNKNOWN_ERROR(NOT_FOUND, "토큰이 존재하지 않습니다."),
    TOKEN_NOT_FOUND(NOT_FOUND, "토큰이 존재하지 않습니다."),
    FILE_NOT_FOUND(NOT_FOUND, "등록된 파일이 없습니다."),
    MEMBER_NOT_FOUND(NOT_FOUND, "등록된 멤버가 없습니다."),
    TRIP_NOT_FOUND(NOT_FOUND, "등록된 여행이 없습니다."),
    TRIP_CODE_NOT_FOUND(NOT_FOUND,"코드에 일치하는 여행을 찾을 수 없습니다."),

    /**
     * 409 CONFLICT
     */
    DUPLICATE_NICKNAME(CONFLICT,"이미 존재하는 닉네임 입니다."),

    /**
     * 415 UNSUPPORTED_MEDIA_TYPE
     */
    FILE_TYPE_UNSUPPORTED(UNSUPPORTED_MEDIA_TYPE, "파일 형식은 '.jpg', '.jpeg', '.png' 만 가능합니다."),

    /**
     * 500 INTERNAL_SERVER_ERROR
     */
    SERVER_ERROR(INTERNAL_SERVER_ERROR,"서버 에러 발생");

    private final HttpStatus httpStatus;
    private final String message;


}
