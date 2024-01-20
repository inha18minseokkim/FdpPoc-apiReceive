package com.example.fdppocapireceive.job;

import com.example.fdppocapireceive.api.ApiReceive;
import com.example.fdppocapireceive.dto.RequestDTO;
import com.example.fdppocapireceive.entity.BaseProduct;
import com.example.fdppocapireceive.entity.OriginalPriceInfo;
import com.example.fdppocapireceive.entity.UserCode;
import com.example.fdppocapireceive.repository.BaseProductRepository;
import com.example.fdppocapireceive.repository.OriginalPririceInfoRepository;
import com.example.fdppocapireceive.repository.UserGroupCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReceiveTasklet implements Tasklet {
    private final BaseProductRepository baseProductRepository;
    private final UserGroupCodeRepository userGroupCodeRepository;
    private final OriginalPririceInfoRepository originalPririceInfoRepository;
    private final ApiReceive apiReceive;
    @Value("${KAMIS_ID}")
    private String kamisId;
    @Value("${KAMIS_SECRET}")
    private String kamisSecret;
    @Value("${PROCESS_DATE}")
    private String processDate;
    @Value("${START_DATE}")
    private String startDate;
    @Value("${END_DATE}")
    private String endDate;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<BaseProduct> availableProducts = baseProductRepository.findAllByIsAvailableEquals(true);
        List<UserCode> targetRegion = userGroupCodeRepository.findAllByCodeDetailNameAndUseInfo("KamisApiRegionCode", true).get(0).getUserCodes();
        log.info("지역 총 {}건 로드 완료",targetRegion.size());
        String requestStartDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyyMMdd")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String requestEndDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyyMMdd")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //이렇게 짜지 말자
        availableProducts.stream().flatMap((availableProduct) -> targetRegion.stream().flatMap((regionCode) -> {
            log.info("상품 id {} 에 대한 수집 시작", availableProduct.getId());
            RequestDTO requestDTO = RequestDTO.builder().pCertKey(kamisSecret).pCertId(kamisId)
                    .pReturntype("json")
                    .pStartday(requestStartDate)
                    .pEndday(requestEndDate)
                    .pConvertKgYn("N")
                    .pItemcategorycode(availableProduct.getCategoryCode())
                    .pItemcode(availableProduct.getItemCode())
                    .pKindcode(availableProduct.getKindCode())
                    .pProductrankcode(availableProduct.getGradeCode())
                    .pCountrycode(regionCode.getCodeDetailName())
                    .pProductclscode(availableProduct.getClassCode())
                    .build();
            return apiReceive
                    .RequestFromServer(requestDTO).getData().getItems().stream()
                    .filter((itemDTO -> !itemDTO.getItemname().equals("[]")))
                    .map((itemDTO -> {
                        try {
                            OriginalPriceInfo priceData = OriginalPriceInfo.builder()
                                    .baseDate(itemDTO.getYyyy() + itemDTO.getRegday().replace("/", ""))
                                    .regionInfo(regionCode)
                                    .baseProduct(availableProduct)
                                    .storeName(itemDTO.getMarketname())
                                    .itemName(itemDTO.getItemname())
                                    .kindName(itemDTO.getKindname())
                                    .price(Long.parseLong(itemDTO.getPrice().replace(",", "")))
                                    .build();
                            return priceData;
                        } catch(NumberFormatException e){
                            log.error("변환 이상 객체 탐지 : {}",itemDTO);
                            return null;
                        }
                    }));
            })).filter((element) -> element != null).forEach((element) -> {
            OriginalPriceInfo originalPriceInfo = getAndMergeEntity(element);
            originalPririceInfoRepository.save(originalPriceInfo);
        });
        return RepeatStatus.FINISHED;
    }
    private OriginalPriceInfo getAndMergeEntity(OriginalPriceInfo element) {
        Optional<OriginalPriceInfo> existedEntity = originalPririceInfoRepository
                .findOriginalPriceInfoByUnique(element.getBaseDate(), element.getBaseProduct(), element.getRegionInfo(), element.getStoreName());
        OriginalPriceInfo originalPriceInfo = existedEntity.orElse(element);
        originalPriceInfo.setBaseDate(element.getBaseDate());
        originalPriceInfo.setRegionInfo(element.getRegionInfo());
        originalPriceInfo.setBaseProduct(element.getBaseProduct());
        originalPriceInfo.setStoreName(element.getStoreName());
        originalPriceInfo.setItemName(element.getItemName());
        originalPriceInfo.setKindName(element.getKindName());
        originalPriceInfo.setPrice(element.getPrice());
        return originalPriceInfo;
    }
}
