package com.bookme.entity.enums;

public enum LikeResult {
    LIKED,          // 찜 추가됨
    UNLIKED,        // 찜 취소됨
    ALREADY_LIKED,  // 이미 찜한 상태
    NOT_LIKED       // 찜 안 된 상태라서 찜 취소 불가
}
