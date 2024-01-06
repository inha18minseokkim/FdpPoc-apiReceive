package com.example.fdppocapireceive.job;

import com.example.fdppocapireceive.api.ApiReceive;
import com.example.fdppocapireceive.dto.RequestDTO;
import com.example.fdppocapireceive.entity.BaseProduct;
import com.example.fdppocapireceive.entity.OriginalPriceInfo;
import com.example.fdppocapireceive.entity.UserCode;
import com.example.fdppocapireceive.entity.UserGroupCode;
import com.example.fdppocapireceive.repository.BaseProductRepository;
import com.example.fdppocapireceive.repository.OriginalPririceInfoRepository;
import com.example.fdppocapireceive.repository.UserGroupCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<BaseProduct> availableProducts = baseProductRepository.findAllByIsAvailableEquals(true);
        List<UserCode> targetRegion = userGroupCodeRepository.findAllByCodeDetailNameAndUseInfo("KamisApiRegionCode", true).get(0).getUserCodes();
        log.info("지역 총 {}건 로드 완료",targetRegion.size());
        availableProducts.stream().flatMap((availableProduct) -> {
            return targetRegion.stream().flatMap((regionCode) -> {
                RequestDTO requestDTO = RequestDTO.builder().pCertKey(kamisSecret).pCertId(kamisId)
                        .pReturntype("json")
                        .pStartday("2024-01-04")
                        .pEndday("2024-01-04")
                        .pConvertKgYn("N")
                        .pItemcategorycode(availableProduct.getCategoryCode())
                        .pItemcode(availableProduct.getItemCode())
                        .pKindcode(availableProduct.getKindCode())
                        .pProductrankcode(availableProduct.getRankCode())
                        .pCountrycode(regionCode.getCodeDetailName())
                        .pProductclscode(availableProduct.getClassCode())
                        .build();
                return apiReceive.RequestFromServer(requestDTO).getData().getItems().stream()
                        .filter((itemDTO -> !itemDTO.getItemname().equals("[]")))
                        .map((itemDTO -> {
                            OriginalPriceInfo priceData = OriginalPriceInfo.builder()
                                    .baseDate(processDate)
                                    .regionInfo(regionCode)
                                    .baseProduct(availableProduct)
                                    .storeName(itemDTO.getMarketname())
                                    .itemName(itemDTO.getItemname())
                                    .kindName(itemDTO.getKindname())
                                    .price(Long.parseLong(itemDTO.getPrice().replace(",","")))
                                    .build();
                            return priceData;
                        }));
                });

        }).forEach((element) -> originalPririceInfoRepository.save(element));
        return RepeatStatus.FINISHED;
    }
}
