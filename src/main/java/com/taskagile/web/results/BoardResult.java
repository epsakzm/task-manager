package com.taskagile.web.results;

import com.taskagile.domain.common.file.FileUrlCreator;
import com.taskagile.domain.model.board.Board;
import com.taskagile.domain.model.card.Card;
import com.taskagile.domain.model.cardlist.CardList;
import com.taskagile.domain.model.cardlist.CardListId;
import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.user.User;
import com.taskagile.utils.ImageUtils;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardResult {

    public static ResponseEntity<ApiResult> build(Team team, Board board, List<User> members, List<CardList> cardLists, List<Card> cards, FileUrlCreator fileUrlCreator) {
        Map<String, Object> boardData = Map.of(
                "id", board.getId().value(),
                "name", board.getName(),
                "personal", board.isPersonal());

        List<MemberData> membersData = members.stream().map(MemberData::new).collect(Collectors.toList());

        List<CardListData> cardListsData = new ArrayList<>();
        Map<CardListId, List<Card>> cardsByList = new HashMap<>();

        cards.forEach(card -> cardsByList.computeIfAbsent(card.getCardListId(), k -> new ArrayList<>()).add(card));
        cardLists.forEach(cardList -> cardListsData.add(new CardListData(cardList, cardsByList.get(cardList.getId()), fileUrlCreator)));

        ApiResult result = ApiResult.blank()
                .add("board", boardData)
                .add("members", membersData)
                .add("cardLists", cardListsData);

        if (!board.isPersonal()) {
            Map<String, String> teamData = new HashMap<>();
            teamData.put("name", team.getName());
            result.add("team", teamData);
        }

        return Result.ok(result);
    }

    @Getter
    private static class MemberData {

        private long userId;
        private String shortName;
        private String name;

        public MemberData(User user) {
            this.userId = user.getId().value();
            this.shortName = user.getInitials();
            this.name = user.getFirstName() + " " + user.getLastName();
        }
    }

    @Getter
    private static class CardListData {
        private long id;
        private String name;
        private int position;
        private List<CardData> cards = new ArrayList<>();

        CardListData(CardList cardList, List<Card> cards, FileUrlCreator fileUrlCreator) {
            Assert.notNull(cards, "cards must not be null");
            this.id = cardList.getId().value();
            this.name = cardList.getName();
            this.position = cardList.getPosition();

            cards.stream().map(card -> new CardData(card, fileUrlCreator)).forEach(this.cards::add);
        }
    }

    @Getter
    private static class CardData {

        private long id;
        private String title;
        private int position;
        private String coverImage;

        CardData(Card card, FileUrlCreator fileUrlCreator) {
            this.id = card.getId().value();
            this.title = card.getTitle();
            this.position = card.getPosition();
            this.coverImage = card.hasCoverImage() ? ImageUtils.getThumbnailVersion(fileUrlCreator.url(card.getCoverImage())) : "";
        }
    }
}
