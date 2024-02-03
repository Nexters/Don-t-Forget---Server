package com.dontforget.dontforget.integration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.dontforget.dontforget.app.anniversary.api.request.AnniversaryCreateRequest;
import com.dontforget.dontforget.app.anniversary.api.request.AnniversaryUpdateRequest;
import com.dontforget.dontforget.common.CalenderType;
import com.dontforget.dontforget.common.CardType;
import com.dontforget.dontforget.domain.notice.NoticeType;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

@DisplayName("AnniversaryController 인수 테스트")
class AnniversaryControllerTest extends AcceptanceTest {

    @Test
    void 기념일이_정상적으로_생성된다() {
        // given
        final AnniversaryCreateRequest request = new AnniversaryCreateRequest(
            "생일",
            LocalDate.now(), "hello", CalenderType.SOLAR,
            CardType.ARM, List.of(NoticeType.D_DAY)
        );

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("deviceId", "deviceId")
            .body(request).log().all()
            .when().post("/api/anniversary")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
        assertThat(response.header("Location")).isEqualTo("/api/anniversary/1");
    }

    @Test
    void 기념일이_정상적으로_단건_조회된다() {
        // given
        final AnniversaryCreateRequest request = new AnniversaryCreateRequest(
            "생일",
            LocalDate.of(2000, 3, 23), "hello",
            CalenderType.SOLAR, CardType.ARM, List.of(NoticeType.D_DAY)
        );
        final String deviceId = "deviceId";
        final Long anniversaryId = RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("deviceId", deviceId)
            .body(request).log().all()
            .when().post("/api/anniversary")
            .then().log().all()
            .extract()
            .body().as(Long.class);
        // when
        final ExtractableResponse<Response> getResponse = RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/api/anniversary/" + anniversaryId)
            .then().log().all()
            .extract();

        // then
        assertThat(getResponse.statusCode()).isEqualTo(OK.value());
    }


    @Test
    void 기념일이_정상적으로_리스트_조회된다() {
        // given
        final AnniversaryCreateRequest request = new AnniversaryCreateRequest(
            "생일",
            LocalDate.of(2000, 2, 1), "hello", CalenderType.SOLAR,
            CardType.ARM, List.of(NoticeType.D_DAY)
        );
        final AnniversaryCreateRequest request2 = new AnniversaryCreateRequest(
            "생일",
            LocalDate.of(2000, 5, 21), "hello", CalenderType.SOLAR,
            CardType.ARM, List.of(NoticeType.D_DAY)
        );

        RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("deviceId", "deviceId")
            .body(request).log().all()
            .when().post("/api/anniversary")
            .then().log().all()
            .extract();

        RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("deviceId", "deviceId")
            .body(request2).log().all()
            .when().post("/api/anniversary")
            .then().log().all()
            .extract();

        // when
        final ExtractableResponse<Response> getResponse = RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("deviceId", "deviceId")
            .when().get("/api/anniversary")
            .then().log().all()
            .extract();

        // then
        assertThat(getResponse.statusCode()).isEqualTo(OK.value());
    }

    @Test
    void 기념일이_정상적으로_수정된다() {
        // given
        final AnniversaryCreateRequest request = new AnniversaryCreateRequest(
            "생일",
            LocalDate.of(2000, 2, 1), "hello",
            CalenderType.SOLAR, CardType.ARM, List.of(NoticeType.D_DAY)
        );
        final Long anniversaryId = RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("deviceId", "deviceId")
            .body(request).log().all()
            .when().post("/api/anniversary")
            .then().log().all()
            .extract()
            .body().as(Long.class);

        final AnniversaryUpdateRequest updateRequest = new AnniversaryUpdateRequest(
            "생일",
            LocalDate.of(2000, 3, 23),
            CalenderType.SOLAR,
            List.of(NoticeType.ONE_MONTH, NoticeType.ONE_DAYS),
            "hello2"
        );

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(updateRequest).log().all()
            .when().put("/api/anniversary/" + anniversaryId)
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode())
            .isEqualTo(OK.value());
    }

    @Test
    void 기념일이_정상적으로_삭제된다() {
        // given
        final AnniversaryCreateRequest request = new AnniversaryCreateRequest(
            "생일",
            LocalDate.of(2000, 2, 1), "hello",
            CalenderType.SOLAR, CardType.ARM,
            List.of(NoticeType.D_DAY)
        );
        final Long anniversaryId = RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("deviceId", "deviceId")
            .body(request).log().all()
            .when().post("/api/anniversary")
            .then().log().all()
            .extract()
            .body().as(Long.class);

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/api/anniversary/" + anniversaryId)
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode())
            .isEqualTo(NO_CONTENT.value());
    }
}
