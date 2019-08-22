package com.kakaopay.bank.bank.predict;

import com.github.signaflo.timeseries.TimePeriod;
import com.github.signaflo.timeseries.TimeSeries;
import com.github.signaflo.timeseries.forecast.Forecast;
import com.github.signaflo.timeseries.model.arima.Arima;
import com.github.signaflo.timeseries.model.arima.ArimaOrder;
import com.kakaopay.bank.bank.entity.BankMoney;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Component
public class PredictFinanceByArima implements PredictFinance {

    @Override
    public int predictFinanceAmount(List<BankMoney> bankFinances, int predictYear, int predictMonth) {
        TimePeriod timePeriodMonth = TimePeriod.oneMonth();
        TimeSeries timeSeries = TimeSeries.from(
                timePeriodMonth,
                preprocessOffsetDateTimes(bankFinances),
                preprocessData(bankFinances));

        ArimaOrder modelOrder = ArimaOrder.order(0, 0, 0, 1, 1, 1); // Note that intercept fitting will automatically be turned off
        Arima model = Arima.model(timeSeries, modelOrder);

        BankMoney lastBankMoney = bankFinances.get(bankFinances.size() - 1);
        Forecast forecast = model.forecast(12 + (12 - lastBankMoney.getMonth())); // 데이터가 12월 채워져있지 않을 경우 고려

        OffsetDateTime predictOffsetDateTime = OffsetDateTime.of(
                LocalDateTime.of(predictYear, predictMonth, 01, 00, 00),
                ZoneOffset.ofHoursMinutes(00, 00));

        return (int) forecast.pointEstimates().at(predictOffsetDateTime);
    }

    private List<OffsetDateTime> preprocessOffsetDateTimes(List<BankMoney> bankFinances) {
        List<OffsetDateTime> offsetDateTimes = new ArrayList<>();
        for (BankMoney bankFinance : bankFinances) {
            offsetDateTimes.add(OffsetDateTime.of(
                    LocalDateTime.of(bankFinance.getYear(), bankFinance.getMonth(), 01, 00, 00),
                    ZoneOffset.ofHoursMinutes(00, 00)));
        }
        return offsetDateTimes;
    }

    private double[] preprocessData(List<BankMoney> bankFinances) {
        double[] values = new double[bankFinances.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = bankFinances.get(i).getMoney();
        }
        return values;
    }


}
