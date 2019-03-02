package pl.wroc.uni.ii.guard.communication.models.dtos;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TestResultData {
    private Long id;

    private String state;

    private Long executionTime;

    private Integer points;

    private String testTypeName;

    private String inputArgument;

    private String expectedAnswer;

    private Long solutionId;
}
