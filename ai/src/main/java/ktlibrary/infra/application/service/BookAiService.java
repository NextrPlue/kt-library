package ktlibrary.infra.application.service;

import ktlibrary.domain.Command.GenerateSummaryCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookAiService {

    private final SummaryService summaryService;

    // 1. 원고 요약 생성
    public String generateSummary(GenerateSummaryCommand generateSummaryCommand) {
        // 도메인 객체에서 필요한 정보 추출
        String manuscriptTitle = generateSummaryCommand.getManuscriptTitle();
        String manuscriptContent = generateSummaryCommand.getManuscriptContent();
        String authorName = generateSummaryCommand.getAuthorName();
        String introduction = generateSummaryCommand.getIntroduction();

        // SummaryService를 호출하여 최종 요약 생성
        return summaryService.summarize(manuscriptTitle, manuscriptContent, authorName, introduction);
    }

//    // 2. 커버 이미지 생성
//    public String generateCoverImage(GenerateCoverCommand generateCoverCommand) {
//
//    }
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
