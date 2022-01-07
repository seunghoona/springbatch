package com.inflearn.springbatch.asyncBatch;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class initializerListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }

 /*   private final CustomerRepo customerRepo;

    private static String[] strings = new String[]{"가","나","다","라","마","바","사","아","자","차"};

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            customerRepo.save(Customer.builder()
                .name(createName())
                .age(random.nextInt(strings.length - 1))
                .build());
        }
    }

    public static String createName() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < 3; j++) {
            int a = random.nextInt(strings.length-1);
            stringBuilder.append(strings[a]);
        }
        return stringBuilder.toString();
    }*/
}
