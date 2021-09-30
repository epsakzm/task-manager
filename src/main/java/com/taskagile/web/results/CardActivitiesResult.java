package com.taskagile.web.results;

import com.taskagile.domain.model.activity.Activity;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CardActivitiesResult {

    public static ResponseEntity<ApiResult> build(List<Activity> activities) {
        List<ListableActivity> result = activities.stream().map(ListableActivity::new).collect(Collectors.toList());
        ApiResult apiResult = ApiResult.blank()
                .add("activities", result);
        return Result.ok(apiResult);
    }

    @Getter
    private static class ListableActivity {
        private long id;
        private String type;
        private String detail;
        private long userId;
        private long createdDate;

        ListableActivity(Activity activity) {
            this.id = activity.getId().value();
            this.type = activity.getType().getType();
            this.detail = activity.getDetail();
            this.userId = activity.getUserId().value();
            this.createdDate = activity.getCreatedDate().getTime();
        }
    }
}
