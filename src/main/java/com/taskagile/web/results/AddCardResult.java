package com.taskagile.web.results;

import com.taskagile.domain.model.card.Card;
import org.springframework.http.ResponseEntity;

public class AddCardResult {

    public static ResponseEntity<ApiResult> build(Card card) {
        return Result.ok(ApiResult.blank()
            .add("id", card.getId().value())
            .add("title", card.getTitle())
            .add("position", card.getPosition()));
    }
}
