package com.dontforget.dontforget.integration;

import com.dontforget.dontforget.app.anniversary.api.request.AnniversaryCreateRequest;
import com.dontforget.dontforget.domain.notice.NoticeType;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

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
        ExtractableResponse<Response> response = RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("deviceId", "deviceId")
                .body(request).log().all()
                .when().post("/api/anniversary/")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
        assertThat(response.header("Location")).isEqualTo("anniversary/1");
    }
}
