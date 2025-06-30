package ktlibrary.infra.application.service;

import ktlibrary.domain.Command.GenerateSummaryCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookAiService {

    private final SummaryService summaryService;
    private final CoverService coverService;

    // 1. 원고 요약 생성
    public String generateSummary(GenerateSummaryCommand generateSummaryCommand) {
        String summary = summaryService.summarize(
                generateSummaryCommand.getManuscriptTitle(),
                generateSummaryCommand.getManuscriptContent(),
                generateSummaryCommand.getAuthorName(),
                generateSummaryCommand.getIntroduction()
        );

        generateSummaryCommand.setSummary(summary);

        return summary;
    }

    // 2. 커버 이미지 생성
    public String generateCoverImage(GenerateSummaryCommand generateSummaryCommand) {
        String title = generateSummaryCommand.getManuscriptTitle();
        String summary = generateSummaryCommand.getSummary();

        return coverService.generateCoverImage(title, summary);
    }
//
//    // 3. 카테고리 자동 분류
//    public String classifyCategory(String summary) {
//
//    }
//
//    // 4. 최종 도서 등록
//    public void registerBook(BookRegisterCommand bookRegisterCommand) {
//
//    }
//
//    // 전체 파이프라인 실행
//    public void processBookAiPipeline(GenerateSummaryCommand command) {
//
//    }
}
