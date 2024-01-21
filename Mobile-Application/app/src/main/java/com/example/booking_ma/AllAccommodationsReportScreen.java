package com.example.booking_ma;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booking_ma.DTO.ReportAccommodationDTO;
import com.example.booking_ma.DTO.ReportAllAccommodationsDTO;
import com.example.booking_ma.DTO.ReportRangeDTO;
import com.example.booking_ma.service.ServiceUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllAccommodationsReportScreen extends AppCompatActivity {
    private String token;
    private SharedPreferences sharedPreferences;
    private Long myId;

    private Long ownerId;
    private String from;
    private String until;
    private ReportAllAccommodationsDTO report;

    BarChart barChart1;
    BarData barData1;
    BarDataSet barDataSet1;
    ArrayList<BarEntry> barEntries1;
    List<String> labels1;

    BarChart barChart2;
    BarData barData2;
    BarDataSet barDataSet2;
    ArrayList<BarEntry> barEntries2;
    List<String> labels2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_all_accommodations_report);

        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        myId = sharedPreferences.getLong("pref_id", 0L);
        token = sharedPreferences.getString("pref_accessToken", "");

        ownerId = getIntent().getLongExtra("ownerId", -1);
        from = getIntent().getStringExtra("from");
        until = getIntent().getStringExtra("until");

        barChart1 = findViewById(R.id.barChart1);
        barChart2 = findViewById(R.id.barChart2);

        getData();
    }

    private void getData() {
        Call<ReportAllAccommodationsDTO> call = ServiceUtils.reservationService(token).getReportForAllAccommodations(ownerId, new ReportRangeDTO(from, until));

        call.enqueue(new Callback<ReportAllAccommodationsDTO>() {
            @Override
            public void onResponse(Call<ReportAllAccommodationsDTO> call, Response<ReportAllAccommodationsDTO> response) {
                if (response.isSuccessful()) {
                    report = response.body();
                    Log.e("REPORT", report.toString());

                    //graph 1

                    barEntries1 = new ArrayList<>();
                    labels1 = new ArrayList<>();

                    System.out.println("Reservations:");
                    int counterReservations = 1;
                    for (String accommodationName : report.getReservations().keySet()) {
                        barEntries1.add(new BarEntry(counterReservations, report.getReservations().get(accommodationName)));
                        labels1.add(accommodationName);
                        counterReservations++;
                    }

                    XAxis xAxis1 = barChart1.getXAxis();
                    xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis1.setTextColor(Color.WHITE);

                    YAxis leftYAxis1 = barChart1.getAxisLeft();
                    leftYAxis1.setTextColor(Color.WHITE);

                    YAxis rightYAxis1 = barChart1.getAxisRight();
                    rightYAxis1.setTextColor(Color.WHITE);

                    xAxis1.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getAxisLabel(float value, AxisBase axis) {
                            int index = (int) value;
                            if (index >= 0 && index < labels1.size()) {
                                return labels1.get(index);
                            }
                            return "";
                        }
                    });

                    barDataSet1 = new BarDataSet(barEntries1, "Number of reservations");
                    barData1 = new BarData(barDataSet1);

                    barChart1.setData(barData1);

                    barDataSet1.setColors(ColorTemplate.PASTEL_COLORS);
                    barDataSet1.setValueTextColor(Color.WHITE);
                    barDataSet1.setValueTextSize(18f);

                    barChart1.invalidate();

                    //graph 2

                    barEntries2 = new ArrayList<>();
                    labels2 = new ArrayList<>();

                    int counterProfit = 1;
                    for (String accommodationName : report.getProfit().keySet()) {
                        barEntries2.add(new BarEntry(counterProfit, report.getProfit().get(accommodationName)));
                        labels2.add(accommodationName);
                        counterProfit++;
                    }

                    XAxis xAxis2 = barChart1.getXAxis();
                    xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis2.setTextColor(Color.WHITE);

                    YAxis leftYAxis2 = barChart1.getAxisLeft();
                    leftYAxis2.setTextColor(Color.WHITE);

                    YAxis rightYAxis2 = barChart1.getAxisRight();
                    rightYAxis2.setTextColor(Color.WHITE);

                    xAxis2.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getAxisLabel(float value, AxisBase axis) {
                            int index = (int) value;
                            if (index >= 0 && index < labels2.size()) {
                                return labels2.get(index);
                            }
                            return "";
                        }
                    });

                    barDataSet2 = new BarDataSet(barEntries2, "Profit");
                    barData2 = new BarData(barDataSet2);

                    barChart2.setData(barData2);

                    barDataSet2.setColors(ColorTemplate.PASTEL_COLORS);
                    barDataSet2.setValueTextColor(Color.WHITE);
                    barDataSet2.setValueTextSize(18f);

                    barChart2.invalidate();

                } else {
                    Log.e("API Error", "Failed to fetch data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ReportAllAccommodationsDTO> call, Throwable t) {
                Log.e("API Error", "Failed to fetch data", t);
            }
        });
    }
}
