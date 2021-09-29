package com.taskagile.domain.model.activity;

import com.taskagile.domain.model.card.CardId;

import java.util.List;

public interface ActivityRepository {

    void save(Activity activity);

    List<Activity> findCardActivities(CardId cardId);

}
