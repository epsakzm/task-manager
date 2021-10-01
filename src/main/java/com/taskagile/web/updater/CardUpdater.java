package com.taskagile.web.updater;

import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.card.Card;
import com.taskagile.utils.JsonUtils;
import com.taskagile.web.socket.SubscriptionHub;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CardUpdater {

    public void onCardAdded(long boardId, Card card) {
        HashMap<String, Object> cardData = new HashMap<>();
        cardData.put("id", card.getId().value());
        cardData.put("title", card.getTitle());
        cardData.put("cardListId", card.getCardListId().value());
        cardData.put("position", card.getPosition());

        HashMap<String, Object> update = new HashMap<>();
        update.put("type", "cardAdded");
        update.put("card", cardData);

        SubscriptionHub.send("/board/" + boardId, JsonUtils.toJson(update));
    }
}
