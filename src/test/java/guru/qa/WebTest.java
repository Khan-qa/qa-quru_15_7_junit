package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import guru.qa.data.TeamName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class WebTest {

    static Stream<Arguments> teamCompositionTest() {
        return Stream.of(
                Arguments.of(TeamName.Team_Spirit, List.of("Illya Mulyarchuk", "Denis Sigitov", "Magomed Khalilov"
                        , "Miroslaw Kolpakov", "Yaroslav Naidenov")),
                Arguments.of(TeamName.Tundra_Esports, List.of("Oliver Lepko", "Leon Kirilin", "Neta Shapira"
                        , "Martin Sazdov", "Wu Jingjun"))
        );
    }

    @DisplayName("Проверка состава команд по Dota 2")
    @MethodSource()
    @ParameterizedTest(name = "Проверка состава {0}")
    void teamCompositionTest(TeamName teamName, List<String> rosterCard) {
        open("https://liquipedia.net/");
        $$("#searchWikiSelect option").find(text("Dota 2")).click();
        $("#searchWikiInput").setValue(teamName.name()).pressEnter();
        $$(By.xpath("//tr[./th[text()='Active Squad']]/following-sibling::tr[@class='Player']"))
                .shouldHave(CollectionCondition.texts(rosterCard));
    }

    @DisplayName("Проверка поиска статьи на wikipedia")
    @ValueSource(strings = {"Бокс", "Хоккей"})
    @ParameterizedTest(name = "Проверка результата поиска для запроса {0}")
    void wikipediaSearchTest(String testData) {
        open("https://ru.wikipedia.org/");
        $("#searchInput").setValue(testData);
        $("#searchButton").click();
        $(".mw-page-title-main").shouldHave(text(testData));
    }
}
