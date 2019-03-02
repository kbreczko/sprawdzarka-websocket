package pl.wroc.uni.ii.guard.communication.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BackendMessage {
    private String userName;

    private TestResultData testResultData;
}
