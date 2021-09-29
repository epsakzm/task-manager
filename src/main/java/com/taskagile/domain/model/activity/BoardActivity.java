package com.taskagile.domain.model.activity;

import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.user.UserId;
import com.taskagile.utils.JsonUtils;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class BoardActivity {

    private UserId userId;
    private BoardId boardId;
    private ActivityType type;
    private Map<String, Object> detail = new HashMap<>();

    public String getDetailJson() {
        return JsonUtils.toJson(detail);
    }
}
