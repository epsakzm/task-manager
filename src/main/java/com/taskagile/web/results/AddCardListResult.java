package com.taskagile.web.results;

import com.taskagile.domain.model.cardlist.CardList;
import org.springframework.http.ResponseEntity;

public class AddCardListResult {

    public static ResponseEntity<ApiResult> build(CardList cardList) {
        return Result.ok(ApiResult.blank()
            .add("id", cardList.getId().value())
            .add("name", cardList.getName()));
    }
}
