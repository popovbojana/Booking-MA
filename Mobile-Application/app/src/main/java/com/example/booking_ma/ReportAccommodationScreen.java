package com.example.booking_ma;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booking_ma.DTO.ReportAccommodationDTO;
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

public class ReportAccommodationScreen extends AppCompatActivity {
    private String token;
    private SharedPreferences sharedPreferences;
    private Long myId;

    private Long accommodationId;
    private String type;
    private ReportAccommodationDTO report;

    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList<BarEntry> barEntries;
    List<String> labels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_report_accommodation);

        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        myId = sharedPreferences.getLong("pref_id", 0L);
        token = sharedPreferences.getString("pref_accessToken", "");

        accommodationId = getIntent().getLongExtra("accommodationId", -1);
        type = getIntent().getStringExtra("type");

        barChart = findViewById(R.id.barChart);

        getData();
    }

    private void getData() {
        Call<ReportAccommodationDTO> call = ServiceUtils.reservationService(token).getReportForAccommodation(accommodationId);

        call.enqueue(new Callback<ReportAccommodationDTO>() {
            @Override
            public void onResponse(Call<ReportAccommodationDTO> call, Response<ReportAccommodationDTO> response) {
                if (response.isSuccessful()) {
                    report = response.body();
                    Log.e("REPORT", report.toString());

                    //graph

                    if (type.equals("reservations")){
                        barEntries = new ArrayList<>();
                        labels = new ArrayList<>();

                        barEntries.add(new BarEntry(1f, report.getReservationsJanuary()));
                        barEntries.add(new BarEntry(2f, report.getReservationsFebruary()));
                        barEntries.add(new BarEntry(3f, report.getReservationsMarch()));
                        barEntries.add(new BarEntry(4f, report.getReservationsApril()));
                        barEntries.add(new BarEntry(5f, report.getReservationsMay()));
                        barEntries.add(new BarEntry(6f, report.getReservationsJune()));
                        barEntries.add(new BarEntry(7f, report.getReservationsJuly()));
                        barEntries.add(new BarEntry(8f, report.getReservationsAugust()));
                        barEntries.add(new BarEntry(9f, report.getReservationsSeptember()));
                        barEntries.add(new BarEntry(10f, report.getReservationsOctober()));
                        barEntries.add(new BarEntry(11f, report.getReservationsNovember()));
                        barEntries.add(new BarEntry(12f, report.getReservationsDecember()));

                        labels.add("January");
                        labels.add("February");
                        labels.add("March");
                        labels.add("April");
                        labels.add("May");
                        labels.add("June");
                        labels.add("July");
                        labels.add("August");
                        labels.add("September");
                        labels.add("October");
                        labels.add("November");
                        labels.add("December");

                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setTextColor(Color.WHITE);

                        YAxis leftYAxis = barChart.getAxisLeft();
                        leftYAxis.setTextColor(Color.WHITE);

                        YAxis rightYAxis = barChart.getAxisRight();
                        rightYAxis.setTextColor(Color.WHITE);

                        xAxis.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getAxisLabel(float value, AxisBase axis) {
                                int index = (int) value;
                                if (index >= 0 && index < labels.size()) {
                                    return labels.get(index);
                                }
                                return "";
                            }
                        });

                        barDataSet = new BarDataSet(barEntries, "Number of reservations");
                        barData = new BarData(barDataSet);

                        barChart.setData(barData);

                        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
                        barDataSet.setValueTextColor(Color.WHITE);
                        barDataSet.setValueTextSize(18f);

                        barChart.invalidate();
                    } else if (type.equals("profit")){
                        barEntries = new ArrayList<>();
                        labels = new ArrayList<>();

                        barEntries.add(new BarEntry(1f, report.getProfitJanuary()));
                        barEntries.add(new BarEntry(2f, report.getProfitFebruary()));
                        barEntries.add(new BarEntry(3f, report.getProfitMarch()));
                        barEntries.add(new BarEntry(4f, report.getProfitApril()));
                        barEntries.add(new BarEntry(5f, report.getProfitMay()));
                        barEntries.add(new BarEntry(6f, report.getProfitJune()));
                        barEntries.add(new BarEntry(7f, report.getProfitJuly()));
                        barEntries.add(new BarEntry(8f, report.getProfitAugust()));
                        barEntries.add(new BarEntry(9f, report.getProfitSeptember()));
                        barEntries.add(new BarEntry(10f, report.getProfitOctober()));
                        barEntries.add(new BarEntry(11f, report.getProfitNovember()));
                        barEntries.add(new BarEntry(12f, report.getProfitDecember()));

                        labels.add("January");
                        labels.add("February");
                        labels.add("March");
                        labels.add("April");
                        labels.add("May");
                        labels.add("June");
                        labels.add("July");
                        labels.add("August");
                        labels.add("September");
                        labels.add("October");
                        labels.add("November");
                        labels.add("December");

                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setTextColor(Color.WHITE);

                        YAxis leftYAxis = barChart.getAxisLeft();
                        leftYAxis.setTextColor(Color.WHITE);

                        YAxis rightYAxis = barChart.getAxisRight();
                        rightYAxis.setTextColor(Color.WHITE);

                        xAxis.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getAxisLabel(float value, AxisBase axis) {
                                int index = (int) value;
                                if (index >= 0 && index < labels.size()) {
                                    return labels.get(index);
                                }
                                return "";
                            }
                        });

                        barDataSet = new BarDataSet(barEntries, "Profit");
                        barData = new BarData(barDataSet);

                        barChart.setData(barData);

                        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
                        barDataSet.setValueTextColor(Color.WHITE);
                        barDataSet.setValueTextSize(18f);

                        barChart.invalidate();
                    }

                } else {
                    Log.e("API Error", "Failed to fetch data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ReportAccommodationDTO> call, Throwable t) {
                Log.e("API Error", "Failed to fetch data", t);
            }
        });
    }
}
