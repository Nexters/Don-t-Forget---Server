package com.dontforget.dontforget.integration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.dontforget.dontforget.app.anniversary.api.request.AnniversaryCreateRequest;
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
    @DisplayName("기념일이 정상적으로 생성된다.")
    void sut_create_anniversary() {
        // given
        final AnniversaryCreateRequest request = new AnniversaryCreateRequest(
            "생일",
            LocalDate.now(), "hello", "solar", List.of(NoticeType.D_DAY)
        );

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("deviceId", "deviceId")
            .body(request).log().all()
            .when().post("/api/anniversary/")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
        assertThat(response.header("Location")).isEqualTo("/anniversary/1");
    }

    @Test
    @DisplayName("기념일이 정상적으로 단건 조회된다.")
    void sut_get_anniversary_detail() {
        // given
        final AnniversaryCreateRequest request = new AnniversaryCreateRequest(
            "생일",
            LocalDate.of(2000, 3,23), "hello", "solar", List.of(NoticeType.D_DAY)
        );
        final String anniversaryId = RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("deviceId", "deviceId")
            .body(request).log().all()
            .when().post("/api/anniversary/")
            .then().log().all()
            .extract()
            .header("Location").split("/")[2];

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
    @DisplayName("기념일이 정상적으로 리스트 조회된다.")
    void sut_get_anniversary_list() {
        // given
        final AnniversaryCreateRequest request = new AnniversaryCreateRequest(
            "생일",
            LocalDate.of(2000, 2,1), "hello", "solar", List.of(NoticeType.D_DAY)
        );
        final AnniversaryCreateRequest request2 = new AnniversaryCreateRequest(
            "생일",
            LocalDate.of(2000, 5,21), "hello", "solar", List.of(NoticeType.D_DAY)
        );

        RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("deviceId", "deviceId")
            .body(request).log().all()
            .when().post("/api/anniversary/")
            .then().log().all()
            .extract();

        RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("deviceId", "deviceId")
            .body(request2).log().all()
            .when().post("/api/anniversary/")
            .then().log().all()
            .extract();

        // when
        final ExtractableResponse<Response> getResponse = RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("deviceId", "deviceId")
            .when().get("/api/anniversary/")
            .then().log().all()
            .extract();

        // then
        assertThat(getResponse.statusCode()).isEqualTo(OK.value());
    }

    @Test
    @DisplayName("기념일이 정상적으로 수정된다.")
    void sut_update_anniversary() {
        // given
        final AnniversaryCreateRequest request = new AnniversaryCreateRequest(
            "생일",
            LocalDate.of(2000, 2,1), "hello",
            "solar", List.of(NoticeType.D_DAY)
        );
        final String anniversaryId = RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("deviceId", "deviceId")
            .body(request).log().all()
            .when().post("/api/anniversary/")
            .then().log().all()
            .extract()
            .header("Location").split("/")[2];
        final AnniversaryCreateRequest updateRequest = new AnniversaryCreateRequest(
            "생일",
            LocalDate.of(2000, 3,23), "hello2",
            "solar", List.of(NoticeType.ONE_MONTH, NoticeType.ONE_DAYS)
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
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    @DisplayName("기념일이 정상적으로 삭제된다.")
    void sut_delete_anniversary() {
        // given
        final AnniversaryCreateRequest request = new AnniversaryCreateRequest(
            "생일",
            LocalDate.of(2000, 2,1), "hello",
            "solar", List.of(NoticeType.D_DAY)
        );
        final String anniversaryId = RestAssured
            .given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("deviceId", "deviceId")
            .body(request).log().all()
            .when().post("/api/anniversary/")
            .then().log().all()
            .extract()
            .header("Location").split("/")[2];

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
